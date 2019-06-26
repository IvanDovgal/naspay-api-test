package text.naspay.client.impl;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import text.naspay.client.NaspayApiClient;
import text.naspay.client.exception.AuthentificationException;
import text.naspay.client.exception.InvalidInputException;
import text.naspay.client.exception.TxNotFoundException;
import text.naspay.client.impl.util.BearerAuthentificationInterceptor;

import java.net.URI;

public class NaspayApiClientImpl implements NaspayApiClient {

    private final String apiUrl;
    private final RestTemplate restTemplate;

    public NaspayApiClientImpl(String apiUrl, String bearerToken) {
        this.apiUrl = apiUrl;
        this.restTemplate = new RestTemplate();
        restTemplate
                .getInterceptors()
                .add(new BearerAuthentificationInterceptor(bearerToken));
    }

    public TransactionDetails getTransactionDetails(String txId) {
        URI txUri = UriComponentsBuilder.fromUriString(apiUrl)
                .pathSegment("api", "v1", "transactions", txId)
                .build()
                .toUri();
        try {
            return restTemplate.getForObject(txUri, TransactionDetails.class);
        } catch (org.springframework.web.client.HttpClientErrorException.Unauthorized e) {
            throw new AuthentificationException(e);
        } catch (org.springframework.web.client.HttpClientErrorException.BadRequest e) {
            throw new InvalidInputException(e);
        } catch (org.springframework.web.client.HttpClientErrorException.NotFound e) {
            throw new TxNotFoundException(e);
        }
    }

    public TransactionState getTransactionState(String txId) {
        return getTransactionDetails(txId).getState();
    }

}
