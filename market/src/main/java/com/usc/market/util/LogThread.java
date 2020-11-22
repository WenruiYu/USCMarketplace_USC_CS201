package com.usc.market.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * LogThread
 * Usage:
 *  Log user operation to local file for fixed delay
 */
public class LogThread extends Thread {

    /**
     * By default , every 10 min, we flush the log into local
     */
    private final static long FLUSH_GAP = 10  * 1000;

    public static final LogThread LOG = new LogThread();

    private volatile List<String> logList = new LinkedList<>();

    private volatile List<String> logListSwitch = new LinkedList<>();

    private final static String LOG_PATH = "logger.txt";

    private volatile boolean flush = false;

    public LogThread() {
    }

    public static void startLogThread() {
        LOG.setDaemon(true);

        LOG.start();
    }

    public void addLog(String log) {
        logList.add(log);
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(FLUSH_GAP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!LOG.isAlive()) {
                break;
            }

            if (logList.isEmpty()) {
                continue;
            }

            // make sure this operation is atomic
            List<String> tmp = logList;
            logList = logListSwitch;
            logListSwitch = tmp;

            // flush to file and clear the buffer
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(LOG_PATH, true));
                for (String log: logListSwitch) {
                    writer.print(log);
                    writer.println();
                }
                writer.flush();
                logListSwitch.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
