package com.example.dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;


/**
 * Created by Administrator on 2017/12/28.
 */

public class fruitSelect extends AppCompatActivity {
    private TextView fruitNameText;
    private String fruitName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fruit_message);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        fruitNameText = findViewById(R.id.searchResult_name);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fruitName = bundle.getString("fruit_name");
        fruitNameText.setText(fruitName);
    }

    //返回上一个界面
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
