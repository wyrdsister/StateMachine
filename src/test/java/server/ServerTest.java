package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ServerTest {
    private Server server;

    @BeforeEach
    public void setUp() {
        server = new Server();
        server.start();
    }

    @AfterEach
    public void tearDown(){
        server.stop();
    }

    @Test
    void testPushNull() {
        boolean added = server.push(null);
        assertFalse(added);
        assertTrue(server.isEmptyQueue());
    }

    @Test
    void testPushAfterStop() {
        server.stop();

        boolean added = server.push(new Object());
        assertFalse(added);
        assertTrue(server.isEmptyQueue());
    }

    @Test
    void testStopWithoutData() {
        server.stop();
        assertTrue(server.isStopped());
        assertTrue(server.isEmptyQueue());
    }

    @Test
    void testStopWithData() {
        server.push("Test data");
        server.stop();
        assertTrue(server.isStopped());
        assertFalse(server.isEmptyQueue());
    }
}