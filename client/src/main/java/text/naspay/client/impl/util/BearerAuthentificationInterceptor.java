package text.naspay.client.impl.util;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class BearerAuthentificationInterceptor implements ClientHttpRequestInterceptor {

    private final String token;

    public BearerAuthentificationInterceptor(String token) {
        this.token = token;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        httpRequest.getHeaders().set("Authorization", "Bearer "+ token);
        return clientHttpRequestExecution.execute(httpRequest, bytes);
    }

}
