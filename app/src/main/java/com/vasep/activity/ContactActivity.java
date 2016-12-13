package com.vasep.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.vasep.R;
import com.vasep.adapter.AdapterMenu;
import com.vasep.async.ContactAsync;
import com.vasep.models.Article;

import java.nio.Buffer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        actionBar.setTitle("Liên hệ");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.btn_back1);

        /*click vào nut home tren toolbar*/
        View view = toolbar.getChildAt(0);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this,PurchaseActivity.class);
                intent.putExtra("article",article);
                startActivity(intent);
                finish();
            }
        });


        screen7_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactAsync contactAsync = new ContactAsync(ContactActivity.this,screen7_fullname.getText().toString(),
                        screen7_phone.getText().toString(),screen7_address.getText().toString(),screen7_note.getText().toString());
                contactAsync.execute();

                Intent intent = new Intent(ContactActivity.this,PurchaseActivity.class);
                intent.putExtra("article",article);
                startActivity(intent);
                finish();
            }
        });
    }
}
