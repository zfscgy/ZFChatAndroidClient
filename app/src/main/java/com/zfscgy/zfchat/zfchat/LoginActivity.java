package com.zfscgy.zfchat.zfchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ZFChatApplication zfChatApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        zfChatApplication = (ZFChatApplication)getApplication();
        zfChatApplication.SetLoginActivity(this);
    }

    public void ButtonConnect(View view)
    {
        zfChatApplication.TryConnect(((EditText)findViewById(R.id.editText_serverIP)).getText().toString(),
                ((EditText)findViewById(R.id.editText_serverPort)).getText().toString());
    }
    public void ButtonSignIn(View view)
    {
        zfChatApplication.TrySignIn(
                ((EditText)findViewById(R.id.editText_username)).getText().toString(),
                ((EditText)findViewById(R.id.editText_password)).getText().toString()
        );
    }
    public void ButtonSignUp(View vie)
    {
        zfChatApplication.TrySignUp(
                ((EditText)findViewById(R.id.editText_username)).getText().toString(),
                ((EditText)findViewById(R.id.editText_password)).getText().toString()
        );
    }

    public void OnConnected()
    {
        ((TextView)findViewById(R.id.textView_serverinfo)).setText(R.string.connected);
    }
    public void OnSignInSucceed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void OnSignInFailed()
    {
        Log.e("Login Failure","Sign in failed");
    }
}
