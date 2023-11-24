package net.daveray.rewardsapi.rest;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ErrorController to catch unknown errors </br>
 * causing 404 responses to be thrown by </br>
 * spring-mvc or the RestController</br>
 *  
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
@RestController
public class ErrorController {

	/**
	 * Wraps HttpStatus.NOT_FOUND
	 * and re-throws as RuntimeException
	 * 
	 * @see GlobalExceptionHandler
	 * @throws RuntimeException
	 */
	@GetMapping("/**")
	public Map<String, String> catchNotFoundExceptions(){
		String message = new StringBuilder()
				.append(HttpStatus.NOT_FOUND.toString())
					.append(": use /api/v1/rewards").toString();
		throw new RuntimeException("HttpStatus: "+message);
	} 
	
}
