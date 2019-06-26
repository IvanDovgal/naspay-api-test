package text.naspay.cli;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import text.naspay.client.NaspayApiClient;
import text.naspay.client.exception.AuthentificationException;
import text.naspay.client.exception.TxNotFoundException;
import text.naspay.client.impl.NaspayApiClientImpl;
import text.naspay.client.impl.NappyAuthenticationClientImpl;

import javax.annotation.PostConstruct;

/**
 * Wrapper for NappyAuthenticationClientImpl with internal getting token
 */
@Component
public class SimpleNaspayApiClient implements NaspayApiClient {

    public static final String API_URL = "https://demo.naspay.com";

    private final String terminalKey;
    private final String terminalSecret;
    private final NappyAuthenticationClientImpl naspayAuthClient;
    private NaspayApiClient apiClient;

    public SimpleNaspayApiClient(
            @Value("${naspay.terminal-key}")
                    String terminalKey,
            @Value("${naspay.terminal-secret}")
                    String terminalSecret
    ) {
        this.terminalKey = terminalKey;
        this.terminalSecret = terminalSecret;
        this.naspayAuthClient = new NappyAuthenticationClientImpl(API_URL);
    }

    private NaspayApiClient initClient(boolean force) {
        if (this.apiClient == null || force) {
            String bearerToken = naspayAuthClient.getBearerToken(terminalKey, terminalSecret);
            this.apiClient = new NaspayApiClientImpl(API_URL, bearerToken);
        }
        return this.apiClient;
    }

    @PostConstruct
    public void init() {
        initClient(false);
    }

    @Override
    public TransactionState getTransactionState(String txId) throws AuthentificationException, TxNotFoundException {
        NaspayApiClient naspayApiClient = initClient(false);
        try {
            return naspayApiClient.getTransactionState(txId);
        } catch (AuthentificationException e) {
            // token maybe outdated, try to get new token
            naspayApiClient = initClient(true);
            return naspayApiClient.getTransactionState(txId);
        }
    }


}
