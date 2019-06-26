package text.naspay.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import text.naspay.client.NaspayApiClient;
import text.naspay.client.exception.TxNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests here
 */
@SpringBootTest
class ApplicationTest {

    @Autowired
    NaspayApiClient client;

    @BeforeAll
    public static void init() {
        // cloudflare preserve access to api with default java ua, using hack
        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
    }

    @Test
    void testNaspay() {
        assertEquals(client.getTransactionState("dbb46d0c4cfc4cdd9f923aa229106287"), NaspayApiClient.TransactionState.AUTHORIZED);
        assertEquals(client.getTransactionState("5ce5fbd950294c4cbf3f3276e645668a"), NaspayApiClient.TransactionState.DECLINED);
        assertEquals(client.getTransactionState("8a0885e6526c4257ba213292a0d956b0"), NaspayApiClient.TransactionState.TIMEOUT);
        assertEquals(client.getTransactionState("1261481f9625430887e155817e45ec33"), NaspayApiClient.TransactionState.COMPLETED);
        assertThrows(TxNotFoundException.class, () -> {
            client.getTransactionState("f33ae3006b074e649c1095ff8fb35e93");
        });
    }

}