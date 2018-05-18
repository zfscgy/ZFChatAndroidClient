package com.zfscgy.zfchat.zfchat.ZFPacket;

import android.util.Log;

import com.zfscgy.zfchat.zfchat.BytesReader.ZFBytesDecoder;
import com.zfscgy.zfchat.zfchat.MessageActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by HP on 2018/5/16.
 */

class ZFMessageMappping
{
    private static HashMap<Byte,MessageType> ByteToType
            = new HashMap<Byte, MessageType>()
                {{
                    put((byte)0x02, MessageType.SignIn);
                    put((byte)0x01, MessageType.SignUp);
                    put((byte)0x03, MessageType.PrivateMessage);
                    put((byte)0x04, MessageType.RoomMessage);
                    put((byte)0x05, MessageType.CreateLink);
                    put((byte)0x06, MessageType.DeleteLink);
                    put((byte)0xff, MessageType.ConnectionBuilt);
                    put((byte)0xfe, MessageType.ServerInfo);
                    put((byte)0xe0, MessageType.SignUpSucceed);
                    put((byte)0xf0, MessageType.SignInSucceed);
                    put((byte)0xf1, MessageType.ContactorList);
                    put((byte)0xf2, MessageType.NewContactor);
                }};
    private static HashMap<MessageType, Byte> TypeToByte
            = new HashMap<MessageType, Byte>()
                {{
                    put(MessageType.SignIn, (byte)0x02);
                    put(MessageType.SignUp, (byte)0x01);
                    put(MessageType.PrivateMessage,(byte)0x03);
                    put(MessageType.RoomMessage, (byte)0x04);
                    put(MessageType.CreateLink, (byte)0x05);
                    put(MessageType.DeleteLink, (byte)0x06);
                    put(MessageType.ConnectionBuilt,(byte)0xff);
                    put(MessageType.ServerInfo,(byte)0xfe);
                    put(MessageType.SignInSucceed,(byte)0xf0);
                    put(MessageType.ContactorList,(byte)0xf1);
                    put(MessageType.NewContactor, (byte)0xf2);
                }};
    public static byte GetTypeByte(MessageType type)
    {
        return TypeToByte.get(type);
    }
    public static MessageType GetByteType(byte b)
    {
        return ByteToType.get(b);
    }
}
public class ZFPacket
{
    public MessageType type;
    public Charset charset = StandardCharsets.UTF_8;
    public ArrayList<String> MsgParts = new ArrayList<String>();
    public ZFPacket(MessageType _type, String... _msgs)
    {
        type = _type;
        MsgParts.addAll(Arrays.asList(_msgs));
    }
    public ZFPacket(MessageType _type, ArrayList<String> _msgParts)
    {
        type = _type;
        MsgParts = _msgParts;
    }
    public ZFPacket(byte[] msg)
    {
        type = ZFMessageMappping.GetByteType(msg[0]);
        ArrayList<byte[]> bParts = ZFBytesDecoder.SplitBytesByZero(msg, 3);
        for(int i = 0; i < bParts.size(); i++)
        {
            MsgParts.add(new String(bParts.get(i),charset));
        }
    }
    public byte[] GetBytesPacket()
    {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try
        {
            bo.write(new byte[] {ZFMessageMappping.GetTypeByte(type),0,0});
            bo.write(MsgParts.get(0).getBytes(charset));
            for(int i = 1; i< MsgParts.size(); i++)
            {
                bo.write(new byte[] {0});
                bo.write(MsgParts.get(i).getBytes(charset));
            }
            byte[] pBytes = bo.toByteArray();
            pBytes[1] = (byte)(pBytes.length / 256);
            pBytes[2] = (byte)(pBytes.length % 256);
            return pBytes;
        }
        catch (IOException e)
        {
            Log.e("Try Sign In",e.getMessage());
            return null;
        }
    }
}
