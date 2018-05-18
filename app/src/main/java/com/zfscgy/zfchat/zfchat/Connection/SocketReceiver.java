package com.zfscgy.zfchat.zfchat.Connection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.SocketHandler;

public class SocketReceiver implements Runnable
{
    private int bufferSize = 1024;
    private Socket socket;
    private OutputStream outputStream;
    private Handler handler;
    private InputStream inputStream;
    public SocketReceiver()
    {

    }

    public void Init(Handler _handler)
    {
        handler = _handler;
    }
    public void SetSocket(Socket _socket)
    {
        socket = _socket;
    }
    public void Send(final byte[] message)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                try
                {
                    outputStream.write(message);
                    outputStream.flush();
                }
                catch (Exception e)
                {
                    Log.e("Error sending", e.toString());
                }
            }
        }).start();
    }
    public void run()
    {
        if(!socket.isConnected())
        {
            return;
        }
        try
        {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        }
        catch (Exception e)
        {
            Log.e("error in stream:",e.getMessage());
            return;
        }
        byte[] receiveBuffer = new byte[bufferSize];
        int len;
        while(true)
        {
            try
            {
                len = inputStream.read(receiveBuffer);
                if(len == -1)
                {
                    //It means the socket is closed
                    Log.e("Socket", "Closed");
                    break;
                }
            }
            catch (Exception e) {
                Log.e("error in loop:", e.getMessage());
                continue;
            }
            int startIndex = 0;
            while(startIndex < len)
            {
                byte[] receivedBytes = new byte[(int)(256 * receiveBuffer[startIndex + 1] + receiveBuffer[startIndex + 2])];
                receivedBytes = Arrays.copyOfRange(receiveBuffer,startIndex, startIndex + receivedBytes.length);  //notice it is from ... to
                handler.sendMessage(Message.obtain(handler, 0, receivedBytes));
                startIndex += receivedBytes.length;
            }
        }
    }
}

