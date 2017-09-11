package com.challenge.bizbuzzz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.challenge.bizbuzzz.Utility.SharedPreferenceUtility;

/**
 * Created by Guidezie on 10-09-2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button bt_login;
    EditText et_username,et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        et_password = (EditText) findViewById(R.id.et_password);
        //checking if sharedpreferences has hardcoded password or not
        if(SharedPreferenceUtility.getPasswordList(LoginActivity.this)==null)
        {
            SharedPreferenceUtility.setPasswordList(LoginActivity.this,"123456 234567 345678 456789 567890");
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_login:
                if(validate())
                {
                    if(SharedPreferenceUtility.getPasswordList(LoginActivity.this).contains(et_password.getText().toString()))
                    {
                       startActivity( new Intent(LoginActivity.this,MainActivity.class));
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Incorrect password",Toast.LENGTH_LONG).show();
                    }
                }

        }
    }

    public boolean validate()
    {
        if(et_password.getText().toString().length()!=6)
        {
            Toast.makeText(LoginActivity.this,"Please enter 6 digit password",Toast.LENGTH_LONG).show();
            return false;
        }
        else if(et_password.getText().toString().contains(" "))
        {
            Toast.makeText(LoginActivity.this,"Not Allowing Whitespace",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
}
