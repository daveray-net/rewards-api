package net.daveray.rewardsapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.daveray.rewardsapi.data.TransactionRepository;
import net.daveray.rewardsapi.data.Transactions;

/**
 * DataLoader loads batch data into the database using the repository</br>
 * by reading a file in src/main/resources/rewards-api_transaction-list.json</br>
 * if the file is not present, calls DataWrangler to generate the file</br>
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@Component
@Configuration
public class DataLoader implements CommandLineRunner {
	Logger logger = LoggerFactory.getLogger(DataLoader.class);
	
	@Value("${json.data.file}")
	String jsonDataFilePath;
	
	@Autowired
	TransactionRepository repository;
	void repository(@Autowired TransactionRepository repository) {
		this.repository = repository;
	}
	
	TransactionRepository repository(){
		return repository;
	}	
	
	String jsonFilePath(){
		return "src/main/resources/"+jsonDataFilePath;
	}
	
	public void run(String... args) {
		logger.info("CommandLineRunner DataLoader is running...");
		
		logger.info("CommandLineRunner DataLoader jsonDataFilePath="+jsonDataFilePath);
		//jsonDataFilePath="src/main/resources/"+jsonDataFilePath;
		logger.info("CommandLineRunner DataLoader jsonFilePath()="+jsonFilePath());
		
		Path pathToFile = Path.of( jsonFilePath() ).toAbsolutePath();
	    boolean isReadable = pathToFile.toFile().canRead();
		logger.info("CommandLineRunner DataLoader jsonDataFile isReadable="+isReadable);

		DataWrangler.generateJsonDatafile(pathToFile);

	    while( isReadable == false ) {
			try {
				logger.info("thread sleeping isReadable="+isReadable);
				Thread.sleep(1000);
				isReadable = pathToFile.toFile().canRead();
			} catch (InterruptedException e) {
				logger.error("thread sleeping: "+e.getMessage());
			}
		}
		logger.info("CommandLineRunner DataLoader jsonDataFile isReadable="+isReadable);
		
		doLoadData();
	}
	
	
	//@Bean
	void doLoadData() {
		ObjectMapper mapper = new ObjectMapper();

		BufferedReader fileReader=new BufferedReader(BufferedReader.nullReader());		
		try {
			//read json from file and map to a list of transactions...
			fileReader = Files.newBufferedReader(Path.of( jsonFilePath() ).toAbsolutePath(), StandardCharsets.UTF_8);

			final BufferedReader reader = fileReader;
			List<Transactions> transactions = new ArrayList<Transactions>();
			//return args -> {
			String line="";
			while((line = reader.readLine())!=null) {
				//logger.debug("line1="+line);

				/* data file is a json list, so skip list tokens: ],[ */
				if(line.startsWith("[") && line.length()<=2) {/* skip */} else 
					if(line.startsWith("]") && line.length()<=2) {/* skip */} else
						if(line.startsWith("], [") && line.length()<=4) {/* skip */} else
							if(line.endsWith(", ")) { /* skip */ } else
							{
								//logger.debug("line2="+line);
								transactions.add(mapper.readValue(line, Transactions.class));
							}
			}
			logger.info("transactions="+transactions);

			//load the data objects into database...
			repository().saveAll(transactions);
			// };	
		} catch (IOException e) {
			logger.error(e.toString());
		}

	}
}
