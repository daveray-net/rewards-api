package net.daveray.rewardsapi.srvc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


/**
 * QueryService provides JDBC flavored persistence</br>
 * contains the sql queries and executes prepared statements</br>
 * returning data to the RewardsService</br>
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@Service
public class QueryService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /* all customers all months */
	public List<Map<String, Object>> rewardsAll() {
		String query ="""
				select * from transactions 
				order by (name, monid) asc
				""";
		
		return jdbcTemplate.queryForList(query);		
	}

    /* all customers single month */
	public List<Map<String, Object>> rewardsAllMonths(String month) {
		String query ="""
				select * from transactions 
				where lemois = ?
				order by (name, monid) asc
				""";
		
		return jdbcTemplate.queryForList(query, month.toUpperCase());			
	}	
	
	/* single customer single month */
	public List<Map<String, Object>> rewardsSingle(String month, String customerId) {
		String query ="""
				select * from transactions 
				where lemois = ?
				and customer_id = ? 
				order by (name, monid) asc
				""";
		
		return jdbcTemplate.queryForList(query, List.of(month.toUpperCase(), customerId).toArray());			
	}		
	
	/* single customer monthly totals */
	public List<Map<String, Object>> rewardsSingleMonths(int monthBegin, String customerId, int monthEnd) {
		String query ="""
				select * from transactions 
				where (monid >= ? and monid <= ?)
				and customer_id = ? 
				order by (name, monid) asc
				""";
		
		return jdbcTemplate.queryForList(query, List.of(monthBegin, monthEnd, customerId).toArray());			
	}	


}
