package com.vasep.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vasep.R;
import com.vasep.async.LoginAsync;

/**
 * Created by Administrator on 21/12/2016.
 */

public class SignInActivity extends AppCompatActivity {
    EditText txt_username,password;
    CardView btn_sign_in;
    TextView txt_forgot,txt_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Init();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sign_in);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.contact));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.btn_back1);

        /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginAsync loginAsync= new LoginAsync(SignInActivity.this,txt_username.getText().toString(),password.getText().toString());
                loginAsync.execute();
            }
        });


    }

    public void Init(){
        txt_username=(EditText) findViewById(R.id.screen_username);
        password=(EditText) findViewById(R.id.screen_pass);
        btn_sign_in=(CardView) findViewById(R.id.screen_login);
        txt_forgot=(TextView) findViewById(R.id.txt_forgot_pass);
        txt_sign_up=(TextView) findViewById(R.id.txt_sign_up);
    }
}
