package net.daveray.rewardsapi.srvc;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.daveray.rewardsapi.func.MonthlyRewardsFunction;

/**
 * RewardsService contains the primary business logic at the service layer
 * calls the QueryService to retrieve transaction data
 * returns apiResponse to the RestController
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */@Service
public class RewardsService {
	Logger logger = LoggerFactory.getLogger(RewardsService.class);

    @Autowired
    QueryService queryService;
    
    @Autowired
    MonthlyRewardsFunction monthlyRewards;

    /* all customers all months */
	public List<Map<String, String>> rewardsAll() {
		List<Map<String, String>> apiResponse = new ArrayList<>();
		List<Map<String, Object>> processor = new ArrayList<>();

		List<Map<String, Object>> transactions = queryService.rewardsAll();
		if(!transactions.isEmpty()) {

			for( Map<String, Object> t : transactions ) {
				processor.add(t);
				//apply rewards and append to apiResponse
				if(processor.size()>0) {
					apiResponse.add( monthlyRewards.apply( processor ) );
					processor.clear();
				}
			}
		}
			
		return apiResponse;
	}

    /* all customers single month */
	public List<Map<String, String>> rewardsAllMonths(String month) {
		List<Map<String, String>> apiResponse = new ArrayList<>();
		List<Map<String, Object>> processor = new ArrayList<>();
		Map<Object, Object> customers = new HashMap<>();
		
		List<Map<String, Object>> transactions = queryService.rewardsAllMonths(month);
		if(!transactions.isEmpty()) {

			//squash the customerId's onto a keySet
			for( Map<String,Object> m : transactions ) {				
				customers.put(m.get("CUSTOMER_ID"), m.get("NAME"));
			}
			//process transactions by customerId
			for(Object c : customers.keySet()) {

				for( Map<String, Object> t : transactions ) {
					logger.debug("t="+t.toString());

					if(t.containsValue(c.toString())) {
						logger.debug("t[c]="+t.containsValue(c.toString()));

						processor.add(t);
					}
				}
				//apply rewards and append to apiResponse
				if(processor.size()>0) {
					apiResponse.add( monthlyRewards.apply( processor ) );
					processor.clear();
				}
			}
		}

		return apiResponse;
	}	
	
	
	/* single customer single month */
	public List<Map<String, String>> rewardsSingle(String month, String customerId) {		
		List<Map<String, String>> apiResponse = new ArrayList<>();
		
		List<Map<String, Object>> transactions = queryService.rewardsSingle(month, customerId);
		if(!transactions.isEmpty()) {
			apiResponse.add( monthlyRewards.apply(transactions) );
		}
		
		return apiResponse;
	}		
	
	/* single customer monthly totals for three months */
	public List<Map<String, String>> rewardsSingleMonths(String monthBegin, String customerId, String monthEnd) {
		List<Map<String, String>> apiResponse = new ArrayList<>();
		List<Map<String, Object>> monthslice = new ArrayList<>();
		
		List<Map<String, Object>> transactions = queryService
				.rewardsSingleMonths(valueOf(monthBegin), customerId, valueOf(monthEnd));
		
		if(!transactions.isEmpty()) {
			
			//only three months of data is available
			monthslice = transactions.stream()
			    .filter( t -> t.get("LEMOIS").toString().equalsIgnoreCase(monthBegin) )
			       .collect(Collectors.toList());
			apiResponse.add( monthlyRewards.apply(monthslice) );

			//next month
			monthslice = transactions.stream()
				    .filter( t -> t.get("LEMOIS").toString().equalsIgnoreCase(nextMonth(monthBegin)) )
				       .collect(Collectors.toList());
				apiResponse.add( monthlyRewards.apply(monthslice) );
				
			//query can be for only two months, right?
			if( !nextMonth(monthBegin).equalsIgnoreCase(monthEnd) ) {
			monthslice = transactions.stream()
				    .filter( t -> t.get("LEMOIS").toString().equalsIgnoreCase(monthEnd) )
				       .collect(Collectors.toList());
			apiResponse.add( monthlyRewards.apply(monthslice) );
			}
			
		}
		
		return apiResponse;
	}

	
	//TODO move to utils class
	
	/**
	 * @param monthName string
	 * @return int month value
	 */
	private int valueOf(String monthName) {
		int value=0;
		if(monthName instanceof String) {
			value = Month.valueOf(monthName.toUpperCase()).getValue();
		}
		return value;
	}
	
	/**
	 * @param monthName string
	 * @return int month value
	 */
	private String nextMonth(String monthName) {
		int value=1;
		if(monthName instanceof String) {
			value=value+valueOf(monthName);
		}
		return Month.of(value).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
	}
		
		
}
