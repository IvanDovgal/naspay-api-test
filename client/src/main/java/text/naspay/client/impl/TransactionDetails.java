package text.naspay.client.impl;

import text.naspay.client.NaspayApiClient;

public class TransactionDetails {
    private NaspayApiClient.TransactionState state;

    public NaspayApiClient.TransactionState getState() {
        return state;
    }

    public void setState(NaspayApiClient.TransactionState state) {
        this.state = state;
    }
}
