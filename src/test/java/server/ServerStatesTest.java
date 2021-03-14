package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import server.states.ProcessingState;
import server.states.SendingState;
import server.states.WaitingState;


import static org.junit.jupiter.api.Assertions.*;

public class ServerStatesTest {
    private Server server;

    @AfterEach
    public void tearDown(){
        server.stop();
    }

    @Test
    public void testWaitingSateIfQueueIsEmpty() throws InterruptedException {
        server = new Server();
        server.start();
        Thread.sleep(10L);
        assertTrue(server.isEmptyQueue());
        assertEquals(WaitingState.class, server.getState().getClass());
    }

    @Test
    public void testProcessingSateIfQueueIsNotEmpty() throws InterruptedException {
        server = new Server(3, 1);
        server.start();
        server.push("Test Data 1");
        server.push("Test Data 2");
        Thread.sleep(server.getMinWaitingTimeByMillisec());
        assertEquals(ProcessingState.class, server.getState().getClass());
        assertFalse(server.isEmptyQueue());
    }

    @Test
    public void testSendingSateIfDataWasProcessed() throws InterruptedException {
        server = new Server(1, 2);
        server.start();
        server.push("Test Data");
        Thread.sleep(server.getMinWaitingTimeByMillisec() + server.getMinProcessingTimeByMillisec());
        assertEquals(SendingState.class, server.getState().getClass());
    }

    @Test
    public void testStateCycle() throws InterruptedException {
        server = new Server(2, 2);
        server.start();
        Thread.sleep(10L);
        assertEquals(WaitingState.class, server.getState().getClass());
        server.push("Test Data");
        Thread.sleep(1000L);
        assertEquals(ProcessingState.class, server.getState().getClass());
        Thread.sleep(2000L);
        assertEquals(SendingState.class, server.getState().getClass());
        Thread.sleep(2000L);
        assertEquals(WaitingState.class, server.getState().getClass());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1000})
    public void testExpectedMaxTimeWorkServer(int countData) throws InterruptedException {
        server = new Server(0.01, 0.01);
        server.start();
        for (int i = 0; i < countData; i++) {
            server.push("Test Data " + i);
        }

        long maxTimeProcessAndSend = (long) (0.02 * countData * 1000L);
        long maxTimeWait = countData / 4 * server.getMinWaitingTimeByMillisec();
        Thread.sleep(maxTimeProcessAndSend + maxTimeWait);

        assertTrue(server.isEmptyQueue());
    }
}
