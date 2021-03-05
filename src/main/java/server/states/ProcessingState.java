package main.java.server.states;

public class ProcessingState implements State {
    private final Object data;

    public ProcessingState(Object data) {
        this.data = data;
    }

    @Override
    public void operate(StateContext stateContext) {
        System.out.println(String.format("Обработка данных %s...", data));

        boolean success = false;
        try {
            Thread.sleep(stateContext.getMinProcessingTime() * 1000);
            success = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (success) {
            stateContext.setState(new SendingState(data));
        } else {
            stateContext.setState(new WaitingState());
        }
    }
}
