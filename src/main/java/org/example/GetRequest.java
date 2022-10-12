package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.List;

public class GetRequest {
    public static final String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            // кастомные настройки
//        try (CloseableHttpClient client = HttpClientBuilder.create()
//                .setDefaultRequestConfig(RequestConfig.custom()
//                        .setConnectTimeout(5000)
//                        .setSocketTimeout(30000)
//                        .setRedirectsEnabled(false)
//                        .build())
//                .build();) {
            HttpGet request = new HttpGet(URL);

            try (CloseableHttpResponse response = client.execute(request)) {
                List<Data> responseList = mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {
                });
                responseList.stream().filter(upvotes -> upvotes.getUpvotes() > 0).forEach(System.out::println);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}