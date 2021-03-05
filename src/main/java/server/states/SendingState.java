package main.java.server.states;

public class SendingState implements State{
    private final Object data;

    public SendingState(Object data) {
        this.data = data;
    }

    @Override
    public void operate(StateContext stateContext) {
        System.out.println(String.format("Отправка данных %s...", data));

        try {
            Thread.sleep(stateContext.getMinSendingTime() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stateContext.setState(new WaitingState());
    }
}
