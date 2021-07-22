package com.s2icode.codemeterandroid2;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class TCPEchoClient {
    private final String SOCKET_NAME = "serverSocket";
    private LocalSocket client;
    private LocalSocketAddress address;
    private boolean isConnected = false;
    private int connetTime = 1;

    public TCPEchoClient() {
        client = new LocalSocket();
        //address = new LocalSocketAddress(SOCKET_NAME, LocalSocketAddress.Namespace.ABSTRACT);
        address = new LocalSocketAddress(SOCKET_NAME);
        new ConnectSocketThread().start();
    }

    /**
     * Send a message
     * @param msg
     * @Return Socket server returns a message receipt
     */
    public String sendMsg(String msg) {
        if (!isConnected) {
            return "Connect fail";
        }
        try {
            Log.i("SocketClient","Try sendMsg: "+msg);
            byte[] data = new byte[16];
            System.arraycopy(msg.getBytes(), 0, data, 0, msg.getBytes().length);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            PrintWriter out = new PrintWriter(client.getOutputStream());
            //out.println(msg);
            out.print(data);
            out.flush();
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Nothing return";
    }

    public String sendMsgInt(int code) {
        if (!isConnected) {
            return "Connect fail";
        }
        try {
            byte[] data = new byte[16];
            data[0] = (byte)code;
            for (int i =0 ; i < 16; i++) {
                Log.i("SocketClient","byte "+ data[i] + " index: " + i);
            }

            //BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //PrintWriter out = new PrintWriter(client.getOutputStream());
            OutputStream out = client.getOutputStream();
            Log.i("SocketClient","Try sendMsgInt: "+code);
            out.write(data, 0, 16);
            out.flush();
            return "";//in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Nothing return";
    }
    /**
     * Asynchronous connection Socket, if the connection is not on repeated attempts to connect ten times
     *
     * @author Administrator
     *
     */
    private class ConnectSocketThread extends Thread {
        @Override
        public void run() {
            while (!isConnected && connetTime <= 10) {
                try {
                    sleep(1000);
                    Log.i("SocketClient","Try to connect socket;ConnectTime:"+connetTime);
                    client.connect(address);
                    Log.i("SocketClient", "connected!!");
                    isConnected = true;
                } catch (Exception e) {
                    connetTime++;
                    isConnected = false;
                    Log.i("SocketClient","Connect fail " + e.getMessage());
                }
            }
        }
    }

    /**
     * Close Socket
     */
    public void closeSocket() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
