package pl.wykop;

import org.apache.commons.codec.digest.DigestUtils;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static pl.wykop.Credentials.WYKOP;

/**
 * Login to Wykop.
 */
public class App {

    final static String LOGIN_URL = "https://a2.wykop.pl/login/index/appkey/";

    public static void main(String[] args) {
        System.out.println("----------- login to wykop via api2 -----------");
        System.out.println("( ͡°( ͡° ͜ʖ( ͡° ͜ʖ ͡°)ʖ ͡°) ͡°)");

        var stringToMd5Hash = new StringBuilder();
        var client = HttpClient.newHttpClient();

        var data = new HashMap<>();
        data.put("login", WYKOP.login());
        data.put("accountkey", WYKOP.accountkey());

        String urlToPost = LOGIN_URL + WYKOP.appkey() + "/";

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
        System.out.println("Resoonse Status = " + response.statusCode());
        System.out.println("Resoonse Body = " + response.body());

    }


    private static HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private static String encrypt(String in) {
        return DigestUtils.md5Hex(in);
    }

}
