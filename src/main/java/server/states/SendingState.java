package main.java.server.states;

public class SendingState implements State{
    private final Object data;

    public SendingState(Object data) {
        this.data = data;
    }

    @Override
    public void operate(StateContext stateContext) throws InterruptedException {
        System.out.printf("Отправка данных %s...%n", data);
        Thread.sleep(stateContext.getMinSendingTime() * 1000L);
        stateContext.setState(new WaitingState());
    }
}
