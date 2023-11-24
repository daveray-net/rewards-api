package net.daveray.rewardsapi;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * static ok http client for unit test to call rest api
 * 
 * @author Dave Ray Reizes <daveray@daveray.net>
 * @since November 2023
 */
public abstract class TestOkHttpClient {
	
	static OkHttpClient client = null;
	
	static OkHttpClient client() {
		if(client==null) {
			client = new OkHttpClient();
		}
		return client;
	}
	
	static String get(String url) throws IOException {
	  Request request = new Request.Builder()
	      .url(url)
	      .build();

	  try (Response response = client().newCall(request).execute()) {
	    return response.body().string();
	  }
	}
}
