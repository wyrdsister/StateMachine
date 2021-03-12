package properties;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationPropertiesTest {

    @Test
    public void testInitializationNullFilename() {
        assertThrows(NullPointerException.class, () -> new ApplicationProperties(null));
    }

    @Test
    public void testInitializationWithIncorrectFilename() {
        assertDoesNotThrow(() -> new ApplicationProperties("null/null.properties"));
    }

    @Test
    public void testInitializationWithNullProperties() {
        ApplicationProperties nullProperties = new ApplicationProperties("NullApplication.properties");
        assertThrows(NumberFormatException.class, nullProperties::minProcessingTime);
        assertThrows(NumberFormatException.class, nullProperties::minSendingTime);
    }

    @Test
    public void testInitializationWithIncorrectProperties() {
        ApplicationProperties nullProperties = new ApplicationProperties("IncorrectApplication.properties");
        assertEquals(1, nullProperties.minProcessingTime());
        assertEquals(1, nullProperties.minSendingTime());
    }
}