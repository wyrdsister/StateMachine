package server.states;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class StateContext {
    private final double minProcessingTime;
    private final double minSendingTime;
    private final double minWaitingTime;
    private State state;
    protected final Queue<Object> queue;
    protected final AtomicBoolean stopped;

    public StateContext(double minProcessingTime, double minSendingTime) {
        this(minProcessingTime, minSendingTime, 0.1);
    }

    public StateContext(double minProcessingTime, double minSendingTime, double minWaitingTime) {
        this.minProcessingTime = minProcessingTime;
        this.minSendingTime = minSendingTime;
        this.minWaitingTime = minWaitingTime;
        this.queue = new ConcurrentLinkedQueue<>();
        this.stopped = new AtomicBoolean();
    }

    public long getMinProcessingTimeByMillisec() {
        return (long) (minProcessingTime * 1000L);
    }

    public long getMinSendingTimeByMillisec() {
        return (long) (minSendingTime * 1000L);
    }

    public long getMinWaitingTimeByMillisec() {
        return (long) (minWaitingTime * 1000L);
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

    public State getState() {
        return state;
    }
}
