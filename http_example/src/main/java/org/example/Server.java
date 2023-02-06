package org.example;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Server {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Server.sendGet();
        Server.sendPost();
    }

    private static void sendGet() throws IOException, URISyntaxException {

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8080)
                .setPath("/kettle/status/")
                .setParameter("xml", "Y")
                .build();
        HttpGet get = new HttpGet(uri);
        get.setHeader(HttpHeaders.AUTHORIZATION, "Basic Y2x1c3RlcjpjbHVzdGVy");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)) {
            System.out.println(response.getStatusLine().toString());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }

    private static void sendPost() throws IOException, URISyntaxException {

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8080)
                .setPath("/kettle/listSocket/")
                .setParameter("host", "127.0.0.1")
                .build();
        HttpPost post = new HttpPost(uri);
        post.setHeader("Content-type", "application/x-www-form-urlencoded");

        post.setHeader(HttpHeaders.AUTHORIZATION, "Basic Y2x1c3RlcjpjbHVzdGVy");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {
            System.out.println("Status- " + response.getStatusLine().toString());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }
}