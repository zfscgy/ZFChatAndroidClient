package com.zfscgy.zfchat.zfchat;

import java.util.ArrayList;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Fragment containing the contacotors list
 * It displays contactors' list and have a method to add string to the list
 * And have a onclick method
 */
public class FragmentPeople extends Fragment {
    ArrayList<Map<String,String>> ContactorList = new ArrayList<>();
    ListView listView;
    ZFChatApplication zfChatApplication;
    public void SetZFChatApplication(ZFChatApplication _zfChatApplication)
    {
        zfChatApplication = _zfChatApplication;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_people, container, false);
        InitListView(rootView);
        // Inflate the layout for this fragment
        return rootView;
    }


    public FragmentPeople()
    {
        ContactorList.add(new HashMap<String, String>(){{put("name","!!!ChatRoom!!!");}});
    }
    //When activity is created, then we can use getView()
    //Otherwise, if call getView() in onCreateView, it will be null and have exception
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public void InitListView(View rootView)
    {
        listView = (ListView)rootView.findViewById(R.id.listView_contactors);
        SimpleAdapter listAdapter= new SimpleAdapter(
                getActivity(),ContactorList, android.R.layout.simple_list_item_1, new String[] { "name" }, new int[]{ android.R.id.text1} );
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                zfChatApplication.SetCurrentTalker(id == 0 ? null : ContactorList.get((int)id).get("name"));
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);
            }
        });
    }

    //Refresh the contactor list, with a new list.
    public void RefreshContactorList(final ArrayList<String> _contactorList)
    {
        ContactorList.clear();
        ContactorList.add(new HashMap<String, String>(){{put("name","!!!ChatRoom!!!");}});
        for(int i = 0; i < _contactorList.size(); i++)
        {
            final String name = _contactorList.get(i);
            ContactorList.add(new HashMap<String, String>(){{put("name",name);}});
        }
    }

    //Add contactor to the contactor list
    public void AddOneContacter(final String name)
    {
        ContactorList.add(new HashMap<String, String>(){{put("name",name);}});
    }

    public void DeleteOneContacter(String name)
    {
        for(int i = 0; i < ContactorList.size(); i++)
        {
            if(ContactorList.get(i).get("name").equals(name))
            {
                ContactorList.remove(i);
                break;
            }
        }
    }
}
