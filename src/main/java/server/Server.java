package server;

import server.states.StateContext;
import server.states.WaitingState;

public class Server extends StateContext implements Runnable {
    public Server() {
        this(1, 1);
    }

    public Server(double minProcessingTime, double minSendingTime) {
        super(minProcessingTime, minSendingTime);
    }

    public boolean push(Object data) {
        if (stopped.get() || data == null){
            return false;
        }

        System.out.printf("Данные %s получены...%n", data);
        return queue.add(data);
    }

    public void start() {
        System.out.println("Запуск сервера...");

        queue.clear();
        stopped.set(false);

        new Thread(this).start();
    }

    public void stop() {
        System.out.println("Вызвана остановка сервера...");
        stopped.set(true);
    }

    @Override
    public void run() {
        if (stopped.get()) return;

        while (!stopped.get()) {
            try {
                setState(new WaitingState());
            } catch (InterruptedException e) {
                e.printStackTrace(System.out);
                stopped.set(true);
            }
        }
    }
}
