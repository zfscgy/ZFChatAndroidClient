package com.zfscgy.zfchat.zfchat;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * Fragment containing the contacotors list
 * It displays contactors' list and have a method to add string to the list
 * And have a onclick method
 */
public class FragmentPeople extends Fragment {
    ArrayList<String> ContactorList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_people, container, false);
    }

    //When activity is created, then we can use getView()
    //Otherwise, if call getView() in onCreateView, it will be null and have exception
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        InitListView(getView());
    }

    public void InitListView(View rootView)
    {
        ListView listView = (ListView)rootView.findViewById(R.id.listView_contactors);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                ContactorList
        );
        listView.setAdapter(listViewAdapter);
    }

    //Add contactor to the contactor list
    public void AddOneContactor(String name)
    {
        ContactorList.add(name);
    }

}
