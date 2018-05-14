package com.zfscgy.zfchat.zfchat;
import java.util.ArrayList;
import android.app.Application;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
/**
 * Use this class to store global variables
 * And maintain the socket connection
 */

public class ZFChatApplication extends Application
{
    String username;
    boolean isConnected;
    boolean isLogin;
    ArrayList<byte[]> unFetchedMessages;
    //This handler will be pass to the connection thread
    //Once the handler received a message, it will append the ArrayList unFetchedMessages
    Handler receivingHandler = new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            byte[] receivedBytes = (byte[])message.obj;
            unFetchedMessages.add(receivedBytes);
        }
    };

    public void TryConnect(String IPAndPort)
    {

    }

    public void LoginInAsUser(String _username)
    {
        username = _username;
        isLogin = true;
    }

}
