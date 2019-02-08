package com.example.dipak_pc.listingpractice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Details_Screen extends AppCompatActivity {

    private TextView tvDate, tvId, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details__screen);

        initVar();

        tvTitle.setText(getIntent().getStringExtra("title").toString());
        tvId.setText(getIntent().getStringExtra("id").toString());
        tvDate.setText(getIntent().getStringExtra("date").toString());
    }

    public void initVar(){
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvId = (TextView)findViewById(R.id.tvID);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        Intent intent = new Intent(Details_Screen.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
