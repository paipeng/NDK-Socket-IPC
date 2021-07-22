package com.s2icode.codemeterandroid2;

import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class TCPEchoServer {
    private static final String TAG = TCPEchoServer.class.getSimpleName();
    private final String SOCKET_NAME = "serverEchoSocket";
    private boolean isConnected = false;
    private int connetTime = 1;

    public TCPEchoServer() {

        new LocalServerSocketThread().start();
    }


    /* LocalServerSocket */
    public class LocalServerSocketThread extends Thread {

        int bufferSize = 2;
        byte[] buffer;
        int bytesRead;
        int totalBytesRead;
        int posOffset;
        LocalServerSocket server;
        LocalSocket receiver;
        InputStream input;
        private volatile boolean stopThread;

        public LocalServerSocketThread() {
            Log.d(TAG, " +++ Begin of localServerSocket() +++ ");

            buffer = new byte[bufferSize];
            bytesRead = 0;
            totalBytesRead = 0;
            posOffset = 0;

            try {
                server = new LocalServerSocket(SOCKET_NAME);
            } catch (IOException e) {
                e.printStackTrace();
            }

            stopThread = false;
        }

        public void run() {
            Log.d(TAG, " +++ Begin of run() +++ ");
            while (!stopThread) {

                if (null == server){
                    Log.d(TAG, "The LocalServerSocket is NULL !!!");
                    stopThread = true;
                    break;
                }

                try {
                    Log.d(TAG, "LocalServerSocket begins to accept()");
                    receiver = server.accept();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG, "LocalServerSocket accept() failed !!!");
                    e.printStackTrace();
                    continue;
                }

                try {
                    input = receiver.getInputStream();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG, "getInputStream() failed !!!");
                    e.printStackTrace();
                    continue;
                }

                Log.d(TAG, "The client connect to LocalServerSocket");

                while (receiver != null) {

                    try {
                        Log.d(TAG, "read...");
                        bytesRead = input.read(buffer, posOffset,
                                (bufferSize - totalBytesRead));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        Log.d(TAG, "There is an exception when reading socket");
                        e.printStackTrace();
                        break;
                    }

                    if (bytesRead >= 0) {
                        Log.d(TAG, "Receive data from socket, bytesRead = "
                                + bytesRead);
                        posOffset += bytesRead;
                        totalBytesRead += bytesRead;
                    }

                    if (totalBytesRead == bufferSize) {
                        Log.d(TAG, "The buffer is full !!!");
                        String str = new String(buffer);
                        Log.d(TAG, "The context of buffer is : " + str);

                        bytesRead = 0;
                        totalBytesRead = 0;
                        posOffset = 0;

                        try {
                            receiver.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        receiver = null;
                    }

                }
                Log.d(TAG, "The client socket is NULL !!!");
            }
            Log.d(TAG, "The LocalSocketServer thread is going to stop !!!");
            if (receiver != null){
                try {
                    receiver.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (server != null){
                try {
                    server.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        public void setStopThread(boolean value){
            stopThread = value;
            Thread.currentThread().interrupt(); // TODO : Check
        }

    }
    /**
     * Close Socket
     */
    public void closeSocket() {

    }
}