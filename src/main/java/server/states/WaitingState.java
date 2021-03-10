package main.java.server.states;

public class WaitingState implements State {
    @Override
    public void operate(StateContext stateContext) throws InterruptedException {
        if (stateContext.isStopped()) {
            return;
        }

        System.out.println("Ожидание данных...");

        while (true){
            if (stateContext.isStopped()) {
                return;
            }
            if (!stateContext.isEmptyQueue()) {
                stateContext.setState(new ProcessingState(stateContext.getData()));
                return;
            }


            Thread.sleep(100);
        }
    }
}
