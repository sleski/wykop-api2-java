package pl.wykop;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static pl.wykop.Credentials.WYKOP;

/**
 * Created by Slawomir Leski on 22-07-2020.
 */
public class Mikroblog extends Common{

	final static String ADD_ENTRY = "https://a2.wykop.pl/entries/add/appkey/";

	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("We need userKey as argument!! ( ͡° ʖ̯ ͡°)");
			return;
		}
		System.out.println("----------- add post to wykop/mikroblog via api2 -----------");
		System.out.println("( ͡°( ͡° ͜ʖ( ͡° ͜ʖ ͡°)ʖ ͡°) ͡°)");
		var userkey = args[0];
		var stringToMd5Hash = new StringBuilder();
		var client = HttpClient.newHttpClient();
		var message = "The wykop api2 works great! \n ( ͡°( ͡° ͜ʖ( ͡° ͜ʖ ͡°)ʖ ͡°) ͡°) \n #wykopapi2java";
		var data = new HashMap<>();
		data.put("body", message);

		var urlToPost = ADD_ENTRY + WYKOP.appkey() + "/userkey/" + userkey;

		stringToMd5Hash.append(WYKOP.secretkey()).append(urlToPost)
				.append(message);

		var request = HttpRequest.newBuilder()
				.POST(ofFormData(data))
				.uri(URI.create(urlToPost))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.header("apisign", encrypt(stringToMd5Hash.toString()))
				.build();

		CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
		var response = httpResponseCompletableFuture.join();
		System.out.println("Resoonse Status = " + response.statusCode());
		System.out.println("Resoonse Body = " + response.body());
	}

}
