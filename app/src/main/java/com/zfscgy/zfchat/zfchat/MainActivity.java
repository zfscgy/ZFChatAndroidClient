package com.zfscgy.zfchat.zfchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    FragmentChat fragmentChat = new FragmentChat();
    FragmentPeople fragmentPeople = new FragmentPeople();
    FragmentFinding fragmentFinding = new FragmentFinding();
    ZFChatApplication zfChatApplication;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    setTitle("Chat");
                    getFragmentManager().beginTransaction().replace(R.id.main_frame, fragmentChat).commit();
                    return true;
                case R.id.navigation_contactor:
                    setTitle("Contacters");
                    getFragmentManager().beginTransaction().replace(R.id.main_frame, fragmentPeople).commit();
                    return true;
                case R.id.navigation_find:
                    setTitle("Find");
                    getFragmentManager().beginTransaction().replace(R.id.main_frame, fragmentFinding).commit();
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        zfChatApplication = (ZFChatApplication)getApplication();
        zfChatApplication.SetPeopleFrament(fragmentPeople);
        zfChatApplication.SetChatFragment(fragmentChat);
        zfChatApplication.SetFindingFragment(fragmentFinding);
        fragmentPeople.SetZFChatApplication(zfChatApplication);
        fragmentChat.SetZFChatApplication(zfChatApplication);
        fragmentFinding.SetZFChatApplication(zfChatApplication);
        setTitle("Chat");
        getFragmentManager().beginTransaction().replace(R.id.main_frame, fragmentChat).commit();
    }

}
