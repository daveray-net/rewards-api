package net.daveray.rewardsapi.data;

import org.springframework.data.repository.CrudRepository;

/**
 * TransactionRepository used by the DataLoader</br>
 * to load the tranaction batch </br>
 * at application startup</br>
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
public interface TransactionRepository extends CrudRepository<Transactions, String>{
	/** well, didn't really use spring magic, except for data loading **/
	/** query service is all about jdbc prepared statements instead **/	
	
}
