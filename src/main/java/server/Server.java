package main.java.server;

import main.java.server.states.StateContext;
import main.java.server.states.WaitingState;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends StateContext implements Runnable {
    public Server() {
        this(1, 1);
    }

    public Server(int minProcessingTime, int minSendingTime) {
        super(minProcessingTime, minSendingTime, new ConcurrentLinkedQueue<>());
    }

    public void push(Object data) {
        if (stopped.get()){
            return;
        }

        System.out.println(String.format("Данные %s получены...", data));
        queue.add(data);
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
            setState(new WaitingState());
        }
    }
}
