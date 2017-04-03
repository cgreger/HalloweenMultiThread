package com.cgreger.halloweenmultithread;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by katana on 4/1/17.
 */
public class ChildGenerator implements Runnable {

    private final Logger log = Logger.getLogger(this.getClass());
    private Halloween halloween;

    public ChildGenerator(Halloween halloween) {

        this.halloween = halloween;

    }

    public void run() {

        generateChildren();

    }

    public void generateChildren() {

        //Generate a 20 children
        for (int i = 0; i < 20; i++) {

            generateChild(i + 1);

            //Wait one second before next child is generated
            try {

                Thread.sleep(1000);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }

    public void generateChild(int id) {

        Child child = new Child(halloween, id);
        Thread childThread = new Thread(child);
        log.info("Child " + id + " generated");
        childThread.start();

        try
        {
            TimeUnit.SECONDS.sleep((long)(Math.random()*10));
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }

    }

}
