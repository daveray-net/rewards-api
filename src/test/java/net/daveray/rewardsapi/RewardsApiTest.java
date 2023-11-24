package net.daveray.rewardsapi;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import net.daveray.rewardsapi.srvc.RewardsService;

/**
 * RewardsApiTest unit test</br>
 * Rest API endpoints</br>
 *  - http://localhost:8080/api/v1/rewards</br>
 *  - http://localhost:8080/api/v1/rewards/{month}</br>
 *  - http://localhost:8080/api/v1/rewards/{month}/{customerId}</br>
 *  - http://localhost:8080/api/v1/rewards/{firstMonth}/{customerId}/{lastMonth}</br>
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
//@DataJdbcTest
@AutoConfigureTestDatabase
@SpringBootTest(
 webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, 
 useMainMethod=SpringBootTest.UseMainMethod.ALWAYS
 ) 
public class RewardsApiTest {
	Logger logger = LoggerFactory.getLogger(RewardsService.class);

    @LocalServerPort
    private int port;
    int port() {
    	return this.port;
    }
	//http://localhost:8080/api/v1/rewards
	static final String URL = "http://localhost:";
	static final String APIV1REWARDS = "/api/v1/rewards";
			
	final String API_V1_REWARDS() {
		return new StringBuilder().append(URL).append(port()).append(APIV1REWARDS).toString();
	}
	
    /**
     * Test http://localhost:8080/api/v1/rewards
     * @throws IOException
     */	
    @Test
    //@DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    void
    testRewards() throws IOException{
    	String result = TestOkHttpClient.get( API_V1_REWARDS() );
    	
    	assertNotNull(result, API_V1_REWARDS()+": returned null value");
    	logger.info("testRewards():"+result);

    }
    
    /**
     * Test http://localhost:8080/api/v1/rewards/{month}
     * @throws IOException
     */
    @Test
    //@DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    void testRewardsMonth() throws IOException{
    	String url = new StringBuilder().append( API_V1_REWARDS() ).append("/august").toString();
    	String result = TestOkHttpClient.get( url );
    	logger.info("testRewardsMonth():"+result);
    }
    
    /**
     * Test http://localhost:8080/api/v1/rewards/{month}/{customerId}
     * @throws IOException 
     */
    @Test
    //@DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    void testRewardsMonthCustomerId() throws IOException{
    	String url = new StringBuilder().append( API_V1_REWARDS() ).append("/july/000000000").toString();
    	String result = TestOkHttpClient.get( url );
    	logger.info("testRewardsMonthCustomerId():"+result);
    }
   
    /**
     * Test http://localhost:8080/api/v1/rewards/{firstMonth}/{customerId}/{lastMonth}
     * @throws IOException 
     */
    @Test
    //@DirtiesContext(hierarchyMode = HierarchyMode.CURRENT_LEVEL)
    void testRewardsMonthCustomerIdMonth() throws IOException{
    	String url = new StringBuilder().append( API_V1_REWARDS() ).append("/july/000000000/september").toString();
    	String result = TestOkHttpClient.get( url );
    	logger.info("testRewardsMonthCustomerIdMonth():"+result);
    }
}
