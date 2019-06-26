package text.naspay.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import text.naspay.client.NaspayApiClient;

@Component
public class CliRunner implements CommandLineRunner {

    private final NaspayApiClient client;

    public CliRunner(NaspayApiClient client) {
        this.client = client;
    }

    @Override
    public void run(String... args) throws Exception {
        if(args.length > 0) {
            System.out.println(client.getTransactionState(args[0]));
            System.exit(0);
        }
    }
}
