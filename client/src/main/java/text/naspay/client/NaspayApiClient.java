package text.naspay.client;

import text.naspay.client.exception.AuthentificationException;
import text.naspay.client.exception.TxNotFoundException;

public interface NaspayApiClient {

    public enum TransactionState {
        ERROR,
        CREATED,
        PENDING,
        IN_PROGRESS,
        AUTHORIZED,
        ON_HOLD,
        COMPLETED,
        REFUNDED,
        PARTIALLY_REFUNDED,
        DECLINED,
        VOIDED,
        CANCELLED,
        TIMEOUT
    }

    TransactionState getTransactionState(String txId) throws AuthentificationException, TxNotFoundException;

}
