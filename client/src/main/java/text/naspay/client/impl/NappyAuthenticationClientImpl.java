package text.naspay.client.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import text.naspay.client.NaspayAuthentificationClient;
import text.naspay.client.exception.AuthentificationException;

import java.net.URI;

public class NappyAuthenticationClientImpl implements NaspayAuthentificationClient {

    private final RestTemplate restTemplate;
    private final URI tokenUri;

    public NappyAuthenticationClientImpl(String apiUrl) {
        this.tokenUri = UriComponentsBuilder.fromUriString(apiUrl)
                .pathSegment("auth", "token")
                .build()
                .toUri();
        this.restTemplate = new RestTemplate();
    }

    private static class TokenResponse {

        @JsonProperty("token_type")
        public String tokenType;

        @JsonProperty("access_token")
        public String accessToken;

    }

    @Override
    public String getBearerToken(String terminalKey, String terminalSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(terminalKey, terminalSecret);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<TokenResponse> exchange = restTemplate.exchange(tokenUri, HttpMethod.GET, entity, TokenResponse.class);
            return exchange.getBody().accessToken;
        }
        catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
            throw new AuthentificationException(e);
        }
    }

}
