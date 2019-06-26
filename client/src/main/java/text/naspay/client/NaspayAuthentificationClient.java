package text.naspay.client;

public interface NaspayAuthentificationClient {

    String getBearerToken(String terminalKey, String terminalSecret);

}
