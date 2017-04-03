package com.cgreger.halloweenmultithread;


import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by katana on 4/1/17.
 */
public class Riley implements Runnable {

    private final Logger log = Logger.getLogger(this.getClass());
    private Halloween halloween;

    public Riley(Halloween halloween) {

        this.halloween = halloween;

    }

    public void run() {

        watchTV();

    }

    public void watchTV() {

        while (halloween.getContinueHalloween()) {

            halloween.startTrickOrTreating();

        }

        halloween.stopTrickOrTreating();

    }

}
