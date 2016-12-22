package com.vasep.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vasep.R;
import com.vasep.async.LoginAsync;
import com.vasep.controller.ConnectApp;
import com.vasep.notification.Information;

/**
 * Created by Administrator on 21/12/2016.
 */

public class SignInActivity extends AppCompatActivity {
    EditText txt_username,password;
    CardView btn_sign_in;
    TextView txt_forgot,txt_sign_up;
    int key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Init();
        try{
            key=getIntent().getExtras().getInt("key");
        }catch (Exception err){

        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sign_in);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.login));
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
                if(txt_username.getText().toString().trim().equals("")||password.getText().toString().trim().equals("")) {
                    Toast.makeText(SignInActivity.this,getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
                }else {
                    LoginAsync loginAsync = new LoginAsync(SignInActivity.this, txt_username.getText().toString(), password.getText().toString());
                    loginAsync.execute();

                }
            }
        });

        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignInActivity.this, ForgotPassActivity.class);
                startActivity(intent);
            }
        });

        txt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
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

    public class LoginAsync extends AsyncTask<Void,Void,Boolean> {

        Context context;
        ProgressDialog dialog;
        String username,pass;
        public LoginAsync(Context context,String username,String pass){
            this.context = context;
            this.username = username;
            this.pass = pass;

        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage(Information.loading);
            dialog.setIndeterminate(true);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ConnectApp contactController = new ConnectApp(context);
            return contactController.Login(username,pass);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try{
                if(aBoolean == true){
                    //onBackPressed();
                    Intent intent = getIntent();
                    intent.putExtra("result",key);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(context,context.getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }
            dialog.dismiss();

        }


    }

}
