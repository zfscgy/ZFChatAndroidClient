package com.zfscgy.zfchat.zfchat;
import java.util.ArrayList;
import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.zfscgy.zfchat.zfchat.Connection.ConnectionManager;

import java.util.HashMap;

import com.zfscgy.zfchat.zfchat.ZFPacket.MessageType;
import com.zfscgy.zfchat.zfchat.ZFPacket.ZFPacket;
/**
 * Use this class to store global variables
 * And maintain the socket connection
 */

public class ZFChatApplication extends Application
{
    private String username;
    private boolean isConnected;
    private boolean isLogin;
    private ConnectionManager connectionManager = new ConnectionManager();
    //Fragments


    //Activities
    private LoginActivity loginActivity;
    private ArrayList<String> savedContactorList;
    public void SetLoginActivity(LoginActivity _loginActivity)
    {
        loginActivity = _loginActivity;
    }

    private FragmentPeople fragmentPeople;
    public void SetPeopleFrament(FragmentPeople _fragmentPeople)
    {
        fragmentPeople = _fragmentPeople;
        RefreshContactorList();
    }

    private FragmentChat fragmentChat;
    public void SetChatFragment(FragmentChat _fragmentChat)
    {
        fragmentChat = _fragmentChat;
    }

    private FragmentFinding fragmentFinding;
    public void SetFindingFragment(FragmentFinding _fragmentFinding)
    {
        fragmentFinding = _fragmentFinding;
    }

    String currentTalker;
    public void SetCurrentTalker(String _currentTalker)
    {
        currentTalker = _currentTalker;
    }

    private HashMap<String, ArrayList<ZFPacket>> savedMessages = new HashMap<>();
    private ArrayList<ZFPacket> savedRoomMessages = new ArrayList<>();
    MessageActivity messageActivity;
    public void SetMessageActivity(MessageActivity _messageActivity)
    {
        messageActivity = _messageActivity;
    }





    //This handler will be pass to the connection thread
    //Once the handler received a message, it will append the ArrayList unFetchedMessages
    Handler receivingHandler = new Handler()
    {
        @Override
        public void handleMessage(Message message)
        {
            byte[] receivedBytes = (byte[])message.obj;
            ZFPacket zfPacket = new ZFPacket(receivedBytes);
            switch (zfPacket.type)
            {
                case ConnectionBuilt:
                    isConnected = true;
                    loginActivity.OnConnected();
                    break;
                case SignInSucceed:
                    SignInSucceed();
                    savedContactorList = zfPacket.MsgParts;
                    break;
                case PrivateMessage:
                    GetReceivedMessagePacket(zfPacket);
                    break;
                case ServerInfo:
                    ReceivedServerInfo(zfPacket.MsgParts.get(0));
                    break;
                case NewContactor:
                    AddNewContacter(zfPacket.MsgParts.get(0));
                    break;
                case RoomMessage:
                    ReceivedRoomMessage(zfPacket);
                    break;
                default:break;
            }
        }
    };

    public boolean TryConnect(String strIP, String strPort)
    {
        return
            connectionManager.Init(strIP,strPort,receivingHandler);
    }
    public void TrySignUp(String _username, String _password)
    {
        username = _username;
        connectionManager.Send(new ZFPacket(MessageType.SignUp,_username, _password).GetBytesPacket());
    }
    public void TrySignIn(String _username, String _password)
    {
        username = _username;
        connectionManager.Send(new ZFPacket(MessageType.SignIn, _username, _password).GetBytesPacket());
    }
    public void TryCreateLink(String linkingUser)
    {
        connectionManager.Send(new ZFPacket(MessageType.CreateLink, username, linkingUser).GetBytesPacket());
    }
    public void TryDeleteLink(String unlinkingUser)
    {
        connectionManager.Send(new ZFPacket(MessageType.DeleteLink, username, unlinkingUser).GetBytesPacket());
        fragmentPeople.DeleteOneContacter(unlinkingUser);
    }

    public void SetSavedMessageToTalk()
    {
        String allmsg = "";
        ArrayList<ZFPacket> MsgPacketList = savedMessages.get(currentTalker);
        if(MsgPacketList == null)
        {
            return;
        }
        for(int i = 0; i < MsgPacketList.size(); i++)
        {
            ArrayList<String> msg = MsgPacketList.get(i).MsgParts;
            allmsg += msg.get(0) + ":\n   " + msg.get(2) + "\n";
        }
        messageActivity.SetSavedMessage(allmsg);
    }
    public void SetSavedRoomMessage()
    {
        String allmsg = "";
        for(int i = 0; i < savedRoomMessages.size(); i++)
        {
            ArrayList<String> msg = savedRoomMessages.get(i).MsgParts;
            allmsg += msg.get(0) + ":\n   " + msg.get(1) + "\n";
        }
        messageActivity.SetSavedMessage(allmsg);
    }
    public void SendMessageToCurrentContactor(String message)
    {
        if(currentTalker == null)
        {
            connectionManager.Send(new ZFPacket(MessageType.RoomMessage, username, message).GetBytesPacket());
        }
        else
        {
            connectionManager.Send(new ZFPacket(MessageType.PrivateMessage, username, currentTalker, message).GetBytesPacket());
        }
    }

    private void SignInSucceed()
    {
        isLogin = true;
        loginActivity.OnSignInSucceed();
    }
    private void RefreshContactorList()
    {
        ArrayList<String> csList = new ArrayList<String>();
        for(int i = 0; i < savedContactorList.size(); i++)
        {
            csList.add(savedContactorList.get(i));
        }
        fragmentPeople.RefreshContactorList(csList);
    }
    private void SignUpSucceed()
    {
        isLogin = true;
        loginActivity.OnSignInSucceed();
    }
    private void SignInFailed()
    {

    }
    private void ReceivedRoomMessage(ZFPacket packet)
    {
        if(currentTalker == null && messageActivity!= null)
        {
            messageActivity.ReceiveChatMessage(packet.MsgParts.get(0), "",packet.MsgParts.get(1));
        }
        savedRoomMessages.add(packet);
    }
    private void GetReceivedMessagePacket(ZFPacket packet)
    {
        String sender = packet.MsgParts.get(0);
        String receiver = packet.MsgParts.get(1);
        String msg = packet.MsgParts.get(2);
        String key;
        if(sender.equals(username))
        {
            key = receiver;
        }
        else
        {
            key = sender;
        }
        if(!savedMessages.containsKey(key))
        {
            savedMessages.put(key, new ArrayList<ZFPacket>());
        }
        savedMessages.get(key).add(packet);
        fragmentChat.ReceiveChatMessage(key,sender + ":" + msg);
        if(key.equals(currentTalker) && messageActivity != null)
        {
            messageActivity.ReceiveChatMessage(sender, receiver, msg);
        }
    }

    private void AddNewContacter(String contactorName)
    {
        fragmentPeople.AddOneContacter(contactorName);
    }

    private void ReceivedServerInfo(String serverInfo)
    {
        Toast.makeText(getApplicationContext(), serverInfo, Toast.LENGTH_SHORT).show();
    }

}
