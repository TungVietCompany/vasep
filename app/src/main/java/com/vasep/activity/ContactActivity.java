package com.vasep.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.localizationactivity.LocalizationActivity;
import com.vasep.R;
import com.vasep.adapter.AdapterMenu;
import com.vasep.async.ContactAsync;
import com.vasep.models.Article;
import com.vasep.notification.Information;

import java.nio.Buffer;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactActivity extends LocalizationActivity {

    @Bind(R.id.screen7_fullname)
    EditText screen7_fullname;

    @Bind(R.id.screen7_phone)
    EditText screen7_phone;

    @Bind(R.id.screen7_address)
    EditText screen7_address;

    @Bind(R.id.screen7_note)
    EditText screen7_note;

    @Bind(R.id.screen7_send)
    CardView screen7_send;
    boolean flag=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);



        ButterKnife.bind(this);
        Intent i = getIntent();
        final Article article = (Article)i.getSerializableExtra("article");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_contact);
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
                try{
                    String type_send =getIntent().getStringExtra("type_send")==null?"":getIntent().getStringExtra("type_send");
                    if(type_send.equals("")) {
                        Intent intent = new Intent(ContactActivity.this, PurchaseActivity.class);
                        intent.putExtra("article", article);
                        startActivity(intent);
                        finish();
                    }else{
                        onBackPressed();
                    }
                }catch (Exception err){

                }

            }
        });


        screen7_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(screen7_fullname.getText().toString().trim().length()==0){
                    Toast.makeText(ContactActivity.this, Information.check_name,Toast.LENGTH_SHORT).show();
                }
                else if(!isValidMobile(screen7_phone.getText().toString())){
                    Toast.makeText(ContactActivity.this, Information.check_phone,Toast.LENGTH_SHORT).show();
                }
                else if(screen7_address.getText().toString().trim().length()==0){
                    Toast.makeText(ContactActivity.this, Information.check_address,Toast.LENGTH_SHORT).show();
                }

                else if(screen7_note.getText().toString().trim().length()==0){
                    Toast.makeText(ContactActivity.this, Information.check_note,Toast.LENGTH_SHORT).show();
                }
                else {
                    ContactAsync contactAsync = new ContactAsync(ContactActivity.this, screen7_fullname.getText().toString(),
                            screen7_phone.getText().toString(), screen7_address.getText().toString(), screen7_note.getText().toString());
                    contactAsync.execute();

                    Intent intent = new Intent(ContactActivity.this, PurchaseActivity.class);
                    intent.putExtra("article", article);
                    startActivity(intent);
                    finish();
                }
                // set ngôn ngữ


            }
        });


    }

    private boolean isValidMobile(String phone2)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone2))
        {
            if(phone2.length() < 6 || phone2.length() > 13)
            {
                check = false;
            }
            else
            {
                check = true;
            }
        }
        else
        {
            check=false;
        }
        return check;
    }
}
