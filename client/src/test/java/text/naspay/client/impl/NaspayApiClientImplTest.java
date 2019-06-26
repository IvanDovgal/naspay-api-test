package text.naspay.client.impl;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import text.naspay.client.NaspayApiClient;
import text.naspay.client.NaspayAuthentificationClient;
import text.naspay.client.exception.TxNotFoundException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NaspayApiClientImplTest extends SetupTest {

    public static final String TOKEN = "466be7f8b80b3bf8bf20147e76c9ae5d";
    public static final String TX_ID = "dbb46d0c4cfc4cdd9f923aa229106287";


    private NaspayApiClientImpl client;

    @BeforeAll
    public void setUp() throws Exception {
        this.client = new NaspayApiClientImpl(getApiUrl(), TOKEN);
    }

    @BeforeEach
    public void mockResponse() throws Exception {
        stubFor(get(urlEqualTo("/api/v1/transactions/(.*)")).willReturn(aResponse().withStatus(401)));
        stubFor(get(urlMatching("/api/v1/transactions/(.*)"))
                .withHeader("Authorization", equalTo("Bearer " + TOKEN))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{}")));
        stubFor(get(urlEqualTo("/api/v1/transactions/" + TX_ID))
                .withHeader("Authorization", equalTo("Bearer " + TOKEN))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"state\": \"AUTHORIZED\" }")));
    }

    @Test
    public void getTransactionDetails() {
        assertNotNull(client.getTransactionDetails("dbb46d0c4cfc4cdd9f923aa229106287"));
    }

    @Test
    public void getTransactionState() {
        assertEquals(client.getTransactionState("dbb46d0c4cfc4cdd9f923aa229106287"), NaspayApiClient.TransactionState.AUTHORIZED);
        assertThrows(TxNotFoundException.class, () -> {
            client.getTransactionState("f33ae3006b074e649c1095ff8fb35e93");
        });
    }


}