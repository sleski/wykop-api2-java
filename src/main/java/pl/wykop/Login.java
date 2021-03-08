package pl.wykop;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.concurrent.CompletableFuture;

import static pl.wykop.Credentials.WYKOP;

/**
 * Login to Wykop.
 */
public class Login extends Common{

    final static String LOGIN_URL = "https://a2.wykop.pl/login/index/appkey/";

    public static void main(String[] args) throws IOException {
        System.out.println("----------- login to wykop via api2 -----------");
        System.out.println("( ͡°( ͡° ͜ʖ( ͡° ͜ʖ ͡°)ʖ ͡°) ͡°)");

        var stringToMd5Hash = new StringBuilder();
        var client = HttpClient.newHttpClient();

        var data = new LinkedHashMap<>();
        data.put("login", WYKOP.login());
        data.put("accountkey", WYKOP.accountkey());

        var urlToPost = LOGIN_URL + WYKOP.appkey() + "/";

        stringToMd5Hash.append(WYKOP.secretkey()).append(urlToPost)
                .append(WYKOP.login()).append(",").append(WYKOP.accountkey());

        var request = HttpRequest.newBuilder()
                .POST(ofFormData(data))
                .uri(URI.create(urlToPost))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("apisign", encrypt(stringToMd5Hash.toString()))
                .build();

        CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        var response = httpResponseCompletableFuture.join();
        JsonNode jsonNode = new ObjectMapper().readTree(response.body());
        if (jsonNode.has("data")) {
            String userkey = jsonNode.get("data").get("userkey").toString();
            System.out.println("userkey = " + userkey);
        }
        System.out.println("Resoonse Status = " + response.statusCode());
        System.out.println("Resoonse Body = " + response.body());
    }

}
