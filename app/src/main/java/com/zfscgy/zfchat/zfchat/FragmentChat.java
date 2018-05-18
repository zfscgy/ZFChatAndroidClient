package com.zfscgy.zfchat.zfchat;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChat extends Fragment
{

    ZFChatApplication zfChatApplication;
    ListView listView;
    ArrayList<Map<String,String>> ChatList = new ArrayList<>();
    SimpleAdapter listAdapter;
    public void SetZFChatApplication(ZFChatApplication _zfChatApplication)
    {
        zfChatApplication = _zfChatApplication;
    }
    public void InitListView(View rootView)
    {
        listView = (ListView)rootView.findViewById(R.id.listView_chats);
        listAdapter= new SimpleAdapter(
                getActivity(),ChatList, android.R.layout.simple_list_item_1, new String[] { "text" }, new int[]{ android.R.id.text1} );
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                zfChatApplication.SetCurrentTalker(ChatList.get((int)id).get("sender"));
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        InitListView(rootView);
        return rootView;
    }


    public void ReceiveChatMessage(final String sender,final String message)
    {
        boolean isInList = false;
        for(int i = 0; i< ChatList.size(); i++)
        {
            if(ChatList.get(i).get("sender").equals(sender))
            {
                ChatList.remove(i);
            }
        }
        ChatList.add(0, new HashMap<String, String>() {{
            put("sender",sender);
            put("text",sender + ":\n   " +  message);
        }});
        if(listView!= null)
        {
            ((SimpleAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

}
