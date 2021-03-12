import properties.ApplicationProperties;
import server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationProperties properties = new ApplicationProperties();

        System.out.println("Для остановки сервера нажмите 'Enter'...");

        Server server = new Server(properties.minProcessingTime(), properties.minSendingTime());
        server.start();

        pushTestData(server);

        System.in.read();
        server.stop();
        System.exit(0);
    }

    private static void pushTestData(Server server) {
        new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace(System.out);
                }
                server.push(i);
            }
        }).start();
    }
}
