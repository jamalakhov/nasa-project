package ru.malakhov.nasaproject.nasa;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NasaHttpClient {
    @Value("${nasa.uri}")
    private String URI;

    @Value("${nasa.api.key}")
    private String apiKey;

    @Value("${my.connect}")
    private String connectTimeout;

    @Value("${my.socket}")
    private String socketTimeout;
    private CloseableHttpClient httpClient;

    public void init() {
        httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(Integer.parseInt(connectTimeout))
                        .setSocketTimeout(Integer.parseInt(socketTimeout))
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }

    public CloseableHttpResponse getResponse() throws IOException {
        return httpClient.execute(new HttpGet(URI + apiKey));
    }

    public CloseableHttpResponse getResponse(String uri) throws IOException {
        return httpClient.execute(new HttpGet(uri));
    }
}