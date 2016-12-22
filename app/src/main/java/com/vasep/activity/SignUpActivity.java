package com.vasep.activity;

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
import com.vasep.controller.ConnectApp;
import com.vasep.notification.Information;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 21/12/2016.
 */

public class SignUpActivity extends AppCompatActivity {
    EditText screen_fullname_signup,screen_email_signup,screen_user_signup,screen7_pass,screen_rep_pass_signup;
    CardView screen7_send_sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Init();
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_sign_up);
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
        screen7_send_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(screen7_pass.getText().toString().equals(screen_rep_pass_signup.getText().toString()))
                {
                    if(checkEmail(screen_email_signup.getText().toString().trim())) {
                        if(!screen_user_signup.getText().toString().trim().equals("")) {
                            SignUpAsync signUpAsync = new SignUpAsync(SignUpActivity.this, screen_user_signup.getText().toString(), screen7_pass.getText().toString(), screen_fullname_signup.getText().toString(), screen_email_signup.getText().toString());
                            signUpAsync.execute();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this,getResources().getString(R.string.email_err),Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(SignUpActivity.this,getResources().getString(R.string.signup_pas),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    public boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }
    public void Init(){
        screen_fullname_signup=(EditText) findViewById(R.id.screen_fullname_signup);
        screen_email_signup=(EditText) findViewById(R.id.screen_email_signup);
        screen_user_signup=(EditText) findViewById(R.id.screen_user_signup);
        screen7_pass=(EditText) findViewById(R.id.screen7_pass);
        screen_rep_pass_signup=(EditText) findViewById(R.id.screen_rep_pass_signup);
        screen7_send_sign_up=(CardView) findViewById(R.id.screen7_send_sign_up);

    }
    public class SignUpAsync extends AsyncTask<Void,Void,Boolean> {

        Context context;
        ProgressDialog dialog;
        String username,pass, fullName, email;
        public SignUpAsync(Context context,String username,String pass,String fullName, String email){
            this.context = context;
            this.username = username;
            this.pass = pass;
            this.email= email;
            this.fullName= fullName;
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
            return contactController.SignUp(username,pass,fullName,email);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try{
                if(aBoolean == true){
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(context,context.getResources().getString(R.string.signup_err),Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }
            dialog.dismiss();

        }


    }
}
