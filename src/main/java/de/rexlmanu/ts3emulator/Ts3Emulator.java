/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2020.
 */
package de.rexlmanu.ts3emulator;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.sql.Time;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Ts3Emulator {

    public static final String TEAMSPEAK_WEBLIST_ADDRESS = "51.68.181.92";
    //weblist.teamspeak.com
    private static List<Emulator> emulators = new ArrayList<>();
    private static String[] keyWords = new String[]{"KEYWORDS"};

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        System.out.println("Using the following keywords: " + keyWords);

        ExecutorService service = Executors.newFixedThreadPool(256);

        int port = 0;
        for (String keyWord : keyWords) {
            for (int i = 0; i < 20; i++) {
                emulators.add(new Emulator(keyWord + "#" + i + " - join 4 fun", i + (port * 500), 1338, 1337));
                System.out.println(i + (port * 500));
            }
            port++;
        }


        /*service.submit(() -> {
            try {
                while (true) {
                    System.out.println("Update Serverlist Count:" + emulators.size());
                    emulators.forEach(emulator -> {
                        service.submit(emulator);
                        try {
                            TimeUnit.NANOSECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                    TimeUnit.MINUTES.sleep(20);
                    Emulator.clientSocket.close();
                    try {
                        Emulator.clientSocket = new DatagramSocket();
                        System.out.println("NEW CONNECTION");
                    } catch (SocketException e) {
                        System.out.println("ERROR BLACKLIST MABY?");
                    }
                }
            } catch (InterruptedException e) {
            }
        });*/
    }

}
