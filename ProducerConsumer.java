import java.util.*;

import static java.lang.Thread.sleep;
import java.util.LinkedList;


public class Solution{
    public static void main(String[] args) throws InterruptedException
    {
            ProducerConsumer producerConsumer = new ProducerConsumer();

            //create 2 threads, 1 for producer to produce item and 2nd for consumer//
        //implementing runnable class//
        Thread producerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    producerConsumer.produce();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });


        Thread consumerThread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    producerConsumer.consume();
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });


        producerThread.start();
        consumerThread.start();
        producerThread.join();
        consumerThread.join();

    }


    public static class ProducerConsumer{
        //create 2 functions for producer and consumer//
        //linked list is used to add and remove items//
        //producer will add items at the end of linked list//
        //consumer will remove item from front of linked list//

        LinkedList <Integer> myList = new LinkedList<>();
        int size = 5;

        public void produce()throws InterruptedException
        {
            int item = 0;
            while (true){
                synchronized (this)
                {
                    //first check if the list is full, if yes then wait until there is space//
                    while (myList.size() == size){
                        wait();
                    }
                    item = item + 1;
                    System.out.println("Producer produced the value: " + item);
                    myList.add(item);

                    //after adding item notify the consumer//
                    notify();
                    //sleep gives some time to understand the program is working or not by giving some
                    //time between producer and consumer//
                    sleep(1000);
                }
            }
        }

            public void consume()throws InterruptedException{
                while (true){
                    synchronized (this){
                        //consumer will wait if list is empty until producer adds an item//
                        while (myList.size() == 0){
                            wait();
                        }

                        int val = myList.remove();
                        System.out.println("Consumer has consumed item " + val);

                        notify();
                        sleep(1000);
                    }
                }
            }

    }
}