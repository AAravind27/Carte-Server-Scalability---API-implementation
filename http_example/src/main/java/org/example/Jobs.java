package org.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Jobs {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Jobs.sendGet();
        Jobs.sendPost();
    }

    private static void sendGet() throws IOException, URISyntaxException {

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8080)
                .setPath("/kettle/jobImage/")
                .setParameter("name","Long_running_job")
                .build();
        HttpGet get = new HttpGet(uri);
        get.addHeader("content-type","image/png");
        get.setHeader(HttpHeaders.AUTHORIZATION, "Basic Y2x1c3RlcjpjbHVzdGVy");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(get)){
            System.out.println(response.getStatusLine().toString());
            int code = response.getStatusLine().getStatusCode();

            // You are getting stream from httpclient. It needs to be converted to byte array
            // We will use byteArrayOutputStream.
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(byteArrayOutputStream);

            if(code == HttpStatus.SC_OK){
                BufferedImage final_img = ImageIO.read(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                File output_file = new File("Jobs.png");
                ImageIO.write(final_img, "png", output_file);

            }
        }
    }

    private static void sendPost() throws IOException, URISyntaxException {
        ArrayList<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair(
                "name", "Long_running_job"));
        postParameters.add(new BasicNameValuePair(
                "xml", "Y"));

        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost")
                .setPort(8081)
                .setPath("/kettle/removeJob/")
                .build();


        HttpPost post = new HttpPost(uri);
        post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
        post.setHeader(HttpHeaders.AUTHORIZATION, "Basic Y2x1c3RlcjpjbHVzdGVy");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)){
            System.out.println("Status- "+response.getStatusLine().toString());
            System.out.println(EntityUtils.toString(response.getEntity()));
        }
    }
}

