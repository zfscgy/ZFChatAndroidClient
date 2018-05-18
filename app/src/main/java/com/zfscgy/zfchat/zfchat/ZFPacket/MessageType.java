package com.zfscgy.zfchat.zfchat.ZFPacket;

/**
 * Created by HP on 2018/5/16.
 */

public enum MessageType
{
    //Client
    SignIn,
    SignUp,
    CreateLink,
    DeleteLink,
    //Both
    PrivateMessage,
    RoomMessage,
    //ServerToClient
    ConnectionBuilt,
    SignInSucceed,
    SignUpSucceed,
    ContactorList,
    NewContactor,
    ServerInfo,
}