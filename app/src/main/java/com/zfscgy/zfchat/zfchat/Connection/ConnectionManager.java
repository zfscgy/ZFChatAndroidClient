package com.zfscgy.zfchat.zfchat.Connection;
import java.lang.Runnable;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Handler;
import android.util.Log;

import com.zfscgy.zfchat.zfchat.LoginActivity;


public class ConnectionManager implements Runnable
{
    private InetSocketAddress address;
    private Socket socket;
    private SocketReceiver socketReceiver = new SocketReceiver();
    private Thread ConnectingThread;
    private Thread ReceivingThread;
    public boolean Init(String ip, String port, Handler receiveHandler)
    {
        if(socket != null && socket.isConnected() && ! socket.isClosed())
        {
            Log.e("Socket","ALready Connected");
            return true;
        }
        socket = new Socket();
        socketReceiver.Init(receiveHandler);
        try
        {
            address = new InetSocketAddress(ip, Integer.parseInt(port));
            ConnectingThread = new Thread(this);
            ConnectingThread.start();
        }
        catch (Exception e)
        {
            Log.e("Exception in Init",e.getMessage());
            return false;
        }
        return  true;
    }
    public  void Send(byte[] messgae)
    {
        socketReceiver.Send(messgae);
    }
    @Override
    public void run()
    {
        try
        {
            Log.e("Socket",address.toString());
            socket.connect(address);
            socketReceiver.SetSocket(socket);
            ReceivingThread = new Thread(socketReceiver);
            ReceivingThread.start();
        }
        catch (Exception e)
        {
            Log.e("can't connect to server", e.toString());
        }
    }
}
