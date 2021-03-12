package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ApplicationProperties {
    private final Properties properties;

    public ApplicationProperties() {
        this("application.properties");
    }

    public ApplicationProperties(String filename){
        this.properties = new Properties();
        init(filename);
    }

    private void init(String filename) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(filename);
        if (resource == null) return;

        try (FileInputStream stream = new FileInputStream(resource.getPath())) {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public double minProcessingTime() {
        return Double.parseDouble(properties.getProperty("min_process_time_sec", "1"));
    }

    public double minSendingTime() {
        return Double.parseDouble(properties.getProperty("min_send_time_sec", "1"));
    }
}
