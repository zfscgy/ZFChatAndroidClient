package com.zfscgy.zfchat.zfchat;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zfscgy.zfchat.zfchat.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFinding extends Fragment {

    ZFChatApplication zfChatApplication;
    public void SetZFChatApplication(ZFChatApplication _zfChatApplication)
    {
        zfChatApplication = _zfChatApplication;
    }
    TextView textContactorName;
    Button buttonAdd;
    Button buttonDelte;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_finding, container, false);
        textContactorName = (TextView)rootView.findViewById(R.id.editText_addName);
        buttonAdd = (Button) rootView.findViewById(R.id.button_add);
        buttonDelte = (Button) rootView.findViewById(R.id.button_delete);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ButtonAddContactor();
            }
        });
        buttonDelte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonDeleteContactor();
            }
        });
        return rootView;
    }

    public void ButtonAddContactor()
    {
        zfChatApplication.TryCreateLink(textContactorName.getText().toString());
    }
    public void ButtonDeleteContactor()
    {
        zfChatApplication.TryDeleteLink(textContactorName.getText().toString());
    }

}
