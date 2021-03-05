package main.java.server.states;

public class WaitingState implements State {
    @Override
    public void operate(StateContext stateContext) {
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

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
