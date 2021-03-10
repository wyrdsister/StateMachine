package main.java.server.states;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class StateContext {
    private final int minProcessingTime;
    private final int minSendingTime;
    private State state;
    protected final Queue<Object> queue;
    protected final AtomicBoolean stopped;

    public StateContext(int minProcessingTime, int minSendingTime, Queue<Object> queue) {
        this.minProcessingTime = minProcessingTime;
        this.minSendingTime = minSendingTime;
        this.queue = queue;
        stopped = new AtomicBoolean();
    }

    public int getMinProcessingTime() {
        return minProcessingTime;
    }

    public int getMinSendingTime() {
        return minSendingTime;
    }

    public boolean isEmptyQueue() {
        return queue.isEmpty();
    }

    public boolean isStopped() {
        return stopped.get();
    }

    public Object getData() {
        return queue.poll();
    }

    public void setState(State newState) throws InterruptedException {
        this.state = newState;
        state.operate(this);
    }
}
