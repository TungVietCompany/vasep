package com.vasep.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.vasep.R;
import com.vasep.controller.ConnectApp;
import com.vasep.notification.Information;

/**
 * Created by VietMT on 12/22/2016.
 */

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        final EditText edit_old = (EditText) findViewById(R.id.pass_old);
        final EditText edit_new = (EditText) findViewById(R.id.pass_new);
        final EditText edit_retype_new = (EditText) findViewById(R.id.pass_retype_new);
        CardView cardView = (CardView) findViewById(R.id.btn_change_pass);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                String user_id= pref.getString("user_id","");
                String pass_old= pref.getString("pass","");
                if (edit_old.getText().toString().trim().equals("")||edit_new.getText().toString().trim().equals("")||edit_retype_new.getText().toString().trim().equals("")) {
                    Toast.makeText(ChangePasswordActivity.this,"Dữ liệu rỗng",Toast.LENGTH_SHORT).show();
                } else {
                    if(edit_old.getText().toString().trim().equals(pass_old)) {
                        if(edit_retype_new.getText().toString().trim().equals(edit_new.getText().toString().trim())) {
                            ChangePassAsync changePassAsync = new ChangePassAsync(ChangePasswordActivity.this, user_id,edit_new.getText().toString().trim(),edit_old.getText().toString().trim());
                            changePassAsync.execute();
                        }
                        else
                        {
                            Toast.makeText(ChangePasswordActivity.this,"Mật khẩu mới với nhập lại mật khẩu mới không khớp",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ChangePasswordActivity.this,"Mật khẩu cũ không đúng",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_change_pass);
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

    public class ChangePassAsync extends AsyncTask<Void,Void,Boolean> {

        Context context;
        ProgressDialog dialog;
        String user_id,password,old_password;
        public ChangePassAsync(Context context,String user_id, String password,String old_password){
            this.context = context;
            this.user_id = user_id;
            this.password= password;
            this.old_password=old_password;
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
            return contactController.ChangePass(user_id,password,old_password);
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