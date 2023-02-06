package org.example;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Trans {

        public static void main(String[] args) throws IOException, URISyntaxException {
            Trans.sendPost();
            Trans.sendGet();
        }
        private static void sendGet() throws IOException, URISyntaxException {

           URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/kettle/transStatus/")
                    .setParameter("name","Transformation_demo")
                    .setParameter("xml","Y")
                    .build();

            HttpGet get = new HttpGet(uri);
            get.setHeader("content-type","text/xml");
            get.setHeader(HttpHeaders.AUTHORIZATION, "Basic Y2x1c3RlcjpjbHVzdGVy");

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(get)){
                System.out.println("Getting Trans Status... "+response.getStatusLine().toString());
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        }


        private static void sendPost() throws IOException, URISyntaxException {
            ArrayList<NameValuePair> postParameters = new ArrayList<>();
            postParameters.add(new BasicNameValuePair(
                    "trans", "C:/Users/aaravind/Downloads/Transformation_demo.ktr"));
            postParameters.add(new BasicNameValuePair(
                    "level", "debug"));

            URI uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8080)
                    .setPath("/kettle/executeTrans/")
                    .build();

            HttpPost post = new HttpPost(uri);
            post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
            post.setHeader(HttpHeaders.AUTHORIZATION, "Basic Y2x1c3RlcjpjbHVzdGVy");

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)){
                System.out.println("Trans Executing Status- "+response.getStatusLine().toString());
            }
    }
}

