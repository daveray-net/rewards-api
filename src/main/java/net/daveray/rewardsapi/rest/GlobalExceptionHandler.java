package net.daveray.rewardsapi.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * GlobalExceptionHandler handles exceptions thrown</br>
 * by the RestController by preparing a suitable</br>
 * JSON Map and returning a ResponseEntity</br>
 * 
 * @author Dave Ray Reizes &lt;daveray@daveray.net&gt;
 * @since November 2023
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * GlobalExceptionHandler handles API exceptions and</br>
	 * prepares a suitable JSON Map response in </br>
	 * the form of: </br>
	 * <pre>
	 * {"HttpStatus: ":"400 BAD_REQUEST",
	 *  "Request: ":"ServletWebRequest:
	 *      uri=/api/v1/rewards/bogus/bogus/bogus;client=0:0:0:0:0:0:0:1",
	 *  "Exception":"java.lang.IllegalArgumentException: 
	 *      No enum constant java.time.Month.BOGUS"}</pre>
	 * 
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler
	@ResponseBody
	ResponseEntity<Map<String, String>> handleException(WebRequest request, Exception exception){
		Map<String, String> errorResponse = new HashMap<>();
		
		//log the exception
		logger.error(request.toString());
		logger.error(exception.toString());
		
		//prepare the response
		errorResponse.put("HttpStatus: ", HttpStatus.BAD_REQUEST.toString());
		errorResponse.put("Request: ", request.toString());
		errorResponse.put("Exception", exception.toString());
		
		if(isTrailingSlash(request)) {
			errorResponse.remove("Exception");
			errorResponse.put("WARNING:", "Trailing slash '/' is not allowed!");
		}
		
		//return the error response
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
				.body(errorResponse);
	}
	
	/**
	 * Catching special exceptions thrown by the RestController</br>
	 * and calling handleException(request, exception)
	 * <pre> ServletWebRequest: uri=/api/v1/rewards/;client=0:0:0:0:0:0:0:1
	 * java.lang.RuntimeException: HttpStatus: 404 NOT_FOUND: use /api/v1/rewards</pre>
	 * 
	 * @param request
	 * @param exception
	 * @return ResponseEntity<Map<String, String>> JSON error message
	 */
	@ExceptionHandler(RuntimeException.class)
	ResponseEntity<Map<String, String>> handleNotFoundException(WebRequest request, Exception exception) {
		logger.error("Runtime thrown, found trailing slash: "+isTrailingSlash(request));
		
		return handleException(request, exception);
	}
	
	/**
	 * since 6.0 SpringFramework does not allow trailing slashes in REST urls
	 * ServletWebRequest: uri=/api/v1/rewards/;client=0:0:0:0:0:0:0:1
	 * 
	 * @param request
	 * @return boolean true if request has trailing slash
	 */
	boolean isTrailingSlash(WebRequest request) {
		List<String> requestAsList = List.of(request.toString().split(";"));
		boolean isTrailingSlash = requestAsList.get(0).toString().endsWith("/");
		return isTrailingSlash;
	}
	
}
