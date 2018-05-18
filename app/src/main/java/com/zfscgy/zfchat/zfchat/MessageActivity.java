package com.zfscgy.zfchat.zfchat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    TextView textView_input;
    TextView textView_msg;
    ScrollView scrollView;
    ZFChatApplication zfChatApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        textView_input = (TextView) findViewById(R.id.editText_input);
        textView_msg = (TextView) findViewById(R.id.textView_msg);
        scrollView = (ScrollView) findViewById(R.id.scrollView_msg);
        zfChatApplication = (ZFChatApplication)getApplication();
        zfChatApplication.SetMessageActivity(this);
        if(zfChatApplication.currentTalker == null)
        {
            zfChatApplication.SetSavedRoomMessage();
        }
        else
        {
            zfChatApplication.SetSavedMessageToTalk();
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void ButtonSendMessage(View view)
    {
        zfChatApplication.SendMessageToCurrentContactor(textView_input.getText().toString());
        textView_input.setText("");
    }

    public void ReceiveChatMessage(String sender, String receiver, String msg)
    {
        textView_msg.append(sender + ":\n   " + msg + "\n");
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    public void SetSavedMessage(String allMessage)
    {
        textView_msg.setText(allMessage);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }
}
