package main.java.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ApplicationProperties {
    private final Properties properties;

    public ApplicationProperties() {
        this.properties = new Properties();
        init();
    }

    private void init() {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("resources/application.properties");
        if (resource == null) return;

        try (FileInputStream stream = new FileInputStream(resource.getPath())) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int minProcessingTime() {
        return Integer.parseInt(properties.getProperty("min_process_time_sec", "1"));
    }

    public int minSendingTime() {
        return Integer.parseInt(properties.getProperty("min_send_time_sec", "1"));
    }
}
