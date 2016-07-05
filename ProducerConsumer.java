package com.example.helloworld.producerconsumer;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * Created by h128850 on 7/5/16.
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        final Queue<Integer> shared=new LinkedList<>();
        Thread producerThread=new Producer(shared);
        Thread consumerThread=new Consumer(shared);

        producerThread.start();
        consumerThread.start();
    }
}
class Producer extends Thread{
    final Queue<Integer> shared;
    public Producer(Queue<Integer> sharedQ) {
        this.shared = sharedQ;
    }

    @Override
    public void run() {
        for(int k=0;k<10;k++){
            synchronized (shared){
                while (shared.size()>0){
                    try {
                        System.out.println("Producer waiting "+shared.size());
                        shared.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Producing");
                shared.add(k);
                shared.notify();
            }
        }
    }
}
class Consumer extends Thread{
    final Queue<Integer> shared;
    public Consumer(Queue<Integer> shared) {
        this.shared = shared;
    }

    @Override
    public void run() {
        int x;
        while(true){
            synchronized (shared){
                while (shared.size()==0){
                    try {
                        shared.wait();
                        System.out.println("Consumer waiting "+shared.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    System.out.println("Consuming");
                    x=shared.poll();
                    shared.notify();
                    if(x==9)
                        break;

            }
        }
    }
}
