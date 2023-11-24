package net.daveray.rewardsapi.func;

import java.util.function.Function;

import org.springframework.stereotype.Component;

/**
 * CalculatePointsFunction calculate the total points</br>
 * for a transaction using the formula</br>
 * <pre>
 * (amount - 50) x 1 + (amount - 100) x 1 = total points.</pre>
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@Component
public class CalculatePointsFunction implements Function<String, Integer> {
	/**
	 * Points rules (amount - 50) x 1 + (amount - 100) x 1
	 **/
	public Integer apply(String amount) {
		int points = 0;
		amount = amount.substring(0, amount.indexOf("."));
		int amt = Integer.valueOf(amount).intValue();
		if (amt > 50) {
			points = (points + (amt - 50));
		}
		if (amt > 100) {
			points = (points + (amt - 100));
		}
		return points;
	}
}
