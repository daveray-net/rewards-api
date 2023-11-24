package net.daveray.rewardsapi.rest;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.daveray.rewardsapi.srvc.RewardsService;

/**
 * RewardsController is the RestController for the API</br>
 * provides the Rest API endpoints</br>
 *  - http://localhost:8080/api/v1/rewards</br>
 *  - http://localhost:8080/api/v1/rewards/{month}</br>
 *  - http://localhost:8080/api/v1/rewards/{month}/{customerId}</br>
 *  - http://localhost:8080/api/v1/rewards/{firstMonth}/{customerId}/{lastMonth}</br>
 *  
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@RestController
@RequestMapping("/api/v1")
public class RewardsController {

	@Autowired
	RewardsService rewardsService;
	
    /* all customers all months */
	@GetMapping("/rewards")
	public List<Map<String, String>> rewardsAll() {
		return rewardsService.rewardsAll();
	}

    /* all customers single month */
	@GetMapping("/rewards/{month}")
	public List<Map<String, String>> rewardsAllMonths(@PathVariable("month") String month) {
		return rewardsService.rewardsAllMonths(month);
	}	
	
	/* single customer single month */
	@GetMapping("/rewards/{month}/{customerId}")
	public List<Map<String, String>> rewardsSingle(@PathVariable("month") String month, 
			@PathVariable("customerId") String customerId) {
		return rewardsService.rewardsSingle(month, customerId);
	}		
	
	/* single customer monthly totals */
	@GetMapping("/rewards/{monthBegin}/{customerId}/{monthEnd}")
	public List<Map<String, String>> rewardsSingleMonths(@PathVariable("monthBegin") String monthBegin, 
			@PathVariable("customerId") String customerId, @PathVariable("monthEnd") String monthEnd) {
		return rewardsService.rewardsSingleMonths(monthBegin, customerId, monthEnd);
	}	
	
	@GetMapping("/error")
	public Map<String, String> error(){
		throw new RuntimeException("HttpStatus: "+HttpStatus.NO_CONTENT);
	} 
	
}
