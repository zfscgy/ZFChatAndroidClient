package com.zfscgy.zfchat.zfchat.Connection;
import java.lang.Runnable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import android.os.Handler;
import android.util.Log;



public class ConnectionManager implements Runnable
{
    private InetSocketAddress address;
    private Socket socket;
    private SocketReceiver socketReceiver = new SocketReceiver();
    public boolean Init(String ip, String port, Handler receiveHandler)
    {
        socketReceiver.Init(receiveHandler);
        try
        {
            address = new InetSocketAddress(ip, Integer.parseInt(port));
        }
        catch (Exception e)
        {
            return false;
        }
        return  true;
    }
    @Override
    public void run()
    {
        try
        {
            socket.connect(address);
            socketReceiver.SetSocket(socket);
            new Thread(socketReceiver).start();
        }
        catch (Exception e)
        {
            Log.e("can't connect to server", e.toString());
        }
    }
}
