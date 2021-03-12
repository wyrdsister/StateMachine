package server.states;

public interface State {
    void operate(StateContext stateContext) throws InterruptedException;
}
