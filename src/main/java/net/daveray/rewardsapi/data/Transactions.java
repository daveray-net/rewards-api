/**
 * 
 */
package net.daveray.rewardsapi.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * JDBC style @Table
 * with lombok style @Data
 * and @NoArgsConstructor to support Jackson
 */
@Data
@NoArgsConstructor
@Table("TRANSACTIONS")
public class Transactions {
	
	@Id()
	@Column
	String id;
	
	@Column
	@Version 
	Integer version;

	@Column
	@NonNull
	Long unixtime;
	
	@Column
	@NonNull
	/*-- pardon my french, lemois - since month is a keyword */
	String lemois;
	
	@Column
	@NonNull
	/*-- monid for monthly range queries and sorting */
	String monid;	
	
	@Column
	@NonNull
	Integer customerId;

	@Column
	@NonNull
	String name;

	@Column
	@NonNull
	String amount;

}
