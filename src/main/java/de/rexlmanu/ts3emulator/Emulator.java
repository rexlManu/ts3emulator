/*
 * © Copyright - Emmanuel Lampe aka. rexlManu 2020.
 */
package de.rexlmanu.ts3emulator;

import de.rexlmanu.ts3emulator.utility.Utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Emulator implements Runnable {

    public static DatagramSocket clientSocket;
    private static InetAddress IPAddress;

    static {
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getByName(Ts3Emulator.TEAMSPEAK_WEBLIST_ADDRESS);
        }catch (Exception e){

        }
    }

    private static final Random RANDOM = ThreadLocalRandom.current();

    private String serverName;
    private int port, maxClients, currentClients;

    public Emulator(String serverName, int port, int maxClients, int currentClients) {
        this.serverName = serverName;
        this.port = port;
        this.maxClients = maxClients;
        this.currentClients = currentClients;
    }

    @Override
    public void run() {
        try {
            byte[] request = new byte[5000];
            byte[] response = new byte[257];
            byte[] rawBytes;
            int nIndex = 0;
            DatagramPacket sendpacket = new DatagramPacket(request, nIndex, IPAddress, 2010);
            DatagramPacket receivePacket = new DatagramPacket(response, response.length);
            DatagramPacket sendpacket2 = new DatagramPacket(request, nIndex, IPAddress, 2010);
            DatagramPacket receivePacket2 = new DatagramPacket(response, response.length);
            nIndex = 0;
            request[nIndex++] = 0x01;
            request[nIndex++] = 0x03;
            request[nIndex++] = 0x00;
            request[nIndex++] = 0x01;
            sendpacket.setData(request, 0, nIndex);
            clientSocket.send(sendpacket);
            clientSocket.receive(receivePacket);
            nIndex = 0;
            request[nIndex++] = 0x01;
            request[nIndex++] = 0x04;
            request[nIndex++] = 0x00;
            request[nIndex++] = 0x02;
            request[nIndex++] = response[4];
            request[nIndex++] = response[5];
            request[nIndex++] = response[6];
            request[nIndex++] = response[7];
            byte[] portBytes = Utils.GetBytes(this.port);
            for (int i = 0; i <= 1; i++)
                request[nIndex++] = portBytes[i];
            byte[] slotsBytes = Utils.GetBytes(this.maxClients);
            for (int i = 0; i <= 1; i++)
                request[nIndex++] = slotsBytes[i];
            byte[] clientnumBytes = Utils.GetBytes(this.currentClients);
            for (int i = 0; i <= 1; i++)
                request[nIndex++] = clientnumBytes[i];
            BitSet bits = new BitSet();
            bits.set(1, true);
            byte[] bitss = bits.toByteArray();
            request[nIndex++] = bitss[0];
            request[nIndex++] = (byte) serverName.length();
            rawBytes = serverName.getBytes();
            System.arraycopy(rawBytes, 0, request, nIndex, rawBytes.length);
            nIndex += rawBytes.length;
            sendpacket2.setData(request, 0, nIndex);
            clientSocket.send(sendpacket2);
            clientSocket.receive(receivePacket2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
