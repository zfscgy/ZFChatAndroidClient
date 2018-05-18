package com.zfscgy.zfchat.zfchat.BytesReader;

import java.util.ArrayList;
import java.util.Arrays;

public class ZFBytesDecoder
{
    public static ArrayList<byte[]> SplitBytesByZero(byte[] bytes, int start)
    {
        ArrayList<byte[]> splited = new ArrayList<byte[]>();
        int i = start;
        int j = i;
        while(j < bytes.length)
        {
            if(bytes[j] == 0)
            {
                if(i!=j)
                {
                    splited.add(Arrays.copyOfRange(bytes, i, j));
                    j++;
                    i = j;
                }
            }
            j++;
        }
        if(i != j)
        {
            splited.add(Arrays.copyOfRange(bytes, i, bytes.length));
        }
        return splited;
    }
}
