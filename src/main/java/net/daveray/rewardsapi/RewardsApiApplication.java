package net.daveray.rewardsapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ResourceLoader;

import net.daveray.rewardsapi.data.TransactionRepository;


/**
 * RewardsApi SpringBoot Application
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@ComponentScan
public class RewardsApiApplication {
	final static Logger logger = LoggerFactory.getLogger(RewardsApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RewardsApiApplication.class, args);
	}

	@Autowired
	TransactionRepository repository;

	ResourceLoader resourceLoader;

	public RewardsApiApplication(@Autowired ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}
