package server.states;

public class ProcessingState implements State {
    private final Object data;

    public ProcessingState(Object data) {
        this.data = data;
    }

    @Override
    public void operate(StateContext stateContext) throws InterruptedException {
        System.out.printf("Обработка данных %s...%n", data);
        Thread.sleep(stateContext.getMinProcessingTimeByMillisec());
        stateContext.setState(new SendingState(data));
    }
}
