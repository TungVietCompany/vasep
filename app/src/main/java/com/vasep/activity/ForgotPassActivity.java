package com.vasep.activity;

import android.app.ProgressDialog;
import android.content.Context;
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

public class ForgotPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        final EditText edit=(EditText) findViewById(R.id.screen_email_send);
        CardView cardView= (CardView) findViewById(R.id.screen_forgot_send);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edit.getText().toString().trim().equals("")) {
                    if(!checkEmail(edit.getText().toString().trim())) {
                        ForgotAsync forgotAsync = new ForgotAsync(ForgotPassActivity.this, edit.getText().toString());
                        forgotAsync.execute();
                    }else{
                        Toast.makeText(ForgotPassActivity.this,getResources().getString(R.string.email_err),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //Toast.makeText(ForgotPassActivity.this,"",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_forgot);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Material Search");
        toolbar.setTitleTextColor(Color.parseColor("#0c69d3"));

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.forgot));
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
    public class ForgotAsync extends AsyncTask<Void,Void,Boolean> {

        Context context;
        ProgressDialog dialog;
        String email;
        public ForgotAsync(Context context,String email){
            this.context = context;
            this.email = email;


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
            return contactController.Forgot(email);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            try{
                if(aBoolean == true){
                    onBackPressed();
                }else{
                    //Toast.makeText(context,context.getResources().getString(R.string.login_err),Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){

            }
            dialog.dismiss();

        }


    }

}
