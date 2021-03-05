package main.java.server.states;

public interface State {
    void operate(StateContext stateContext);
}
