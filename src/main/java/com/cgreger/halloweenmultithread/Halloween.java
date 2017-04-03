package com.cgreger.halloweenmultithread;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by katana on 4/1/17.
 */
public class Halloween {

    private final Logger log = Logger.getLogger(this.getClass());
    private long startTime;
    private long stopTime;
    private long durration;
    private static final int MAX_CHILDREN_AT_DOOR = 10;
    private List<Child> childrenAtDoor;
    private boolean continueHalloween = true;
    private Thread rileyThread;
    private Thread genThread;


    public Halloween() {

        childrenAtDoor = new ArrayList<Child>();

    }

    public void simulateHalloween() {

        startTime = System.currentTimeMillis();
        log.info("Trick or Treating has begun");

        Riley riley = new Riley(this);
        ChildGenerator gen = new ChildGenerator(this);


        rileyThread = new Thread(riley);
        genThread = new Thread(gen);

        rileyThread.start();
        genThread.start();

        try {

            rileyThread.join();
            genThread.join();

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

    public void startTrickOrTreating() {

        synchronized (childrenAtDoor) {

            while (childrenAtDoor.size() == 0 && continueHalloween) {

                log.info("Riley is watching TV");

                try {

                    childrenAtDoor.wait();

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

            log.info("Riley answers the door");

            List<Child> toRemove = new ArrayList<Child>();

            for (Child child : childrenAtDoor) {

                log.info("Riley hands out candy to child " + child.getId());

                try {

                    rileyThread.sleep(3000);

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

                log.info("Child " + child.getId() + " leaves with candy");
                toRemove.add(child);

            }

            childrenAtDoor.removeAll(toRemove);

        }

    }

    public void stopTrickOrTreating() {

        stopTime = System.currentTimeMillis();
        durration = (stopTime - startTime) / 1000;
        log.info("Trick or Treating has stopped.");
        log.info("Trick or Treating lasted " + durration + " seconds");

    }

    public void addChildAtDoor(Child child) {

        synchronized (childrenAtDoor) {

            if (childrenAtDoor.size() == MAX_CHILDREN_AT_DOOR) {

                log.info("Too many children at the door");
                log.info("Child " + child.getId() + " passes by");

            } else {

                childrenAtDoor.add(child);
                log.info("Child " + child.getId() + " rings doorbell");

            }

            if (childrenAtDoor.size() == 1) {

                childrenAtDoor.notify();

            }

        }

    }

    public void setContinueHalloween(boolean continueHalloween) {

        this.continueHalloween = continueHalloween;

    }

    public boolean getContinueHalloween() {

        return continueHalloween;

    }

    public List<Child> getChildrenAtDoor() {

        return childrenAtDoor;

    }

}
