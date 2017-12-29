package com.example.dapp;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.FruitAdapter;
import Model.Fruit;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();
    private ListView listView;
    private EditText searchText;
    private ImageButton searchFruit;
    private String searchFruitText;
    private boolean isnull = true;
    private Drawable mIconSearchClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
        listView = findViewById(R.id.search_result);
        searchText = findViewById(R.id.search_text);
        listView.setAdapter(adapter);
        final Resources res = getResources();
        mIconSearchClear = res.getDrawable(R.drawable.clear);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Intent intent = new Intent(MainActivity.this, SearchResult.class);
                Bundle bundle = new Bundle();
                bundle.putString("fruit_name", fruit.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int pad = (int) motionEvent.getX();
                        if (pad > view.getWidth() - 100 && pad < view.getWidth() - 25
                                && !TextUtils.isEmpty(searchText.getText())) {
                            searchText.setText("");
                            int cacheInputType = searchText.getInputType();// backup  the input type
                            searchText.setInputType(InputType.TYPE_NULL);// disable soft input
                            searchText.onTouchEvent(motionEvent);// call native handler
                            searchText.setInputType(cacheInputType);// restore input  type
                            return true;// consume touch even
                        }
                        break;
                }
                return false;
            }

        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    if (!isnull) {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, null, null);
                        isnull = true;
                    }
                } else {
                    if (isnull) {
                        searchText.setCompoundDrawablesWithIntrinsicBounds(null,
                                null, mIconSearchClear, null);
                        isnull = false;
                    }
                }
            }
        });

//        searchFruit = findViewById(R.id.search_button);
//        searchFruit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });


    }

    private void initFruits() {
        for (int i = 0; i < 5; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple);
            Fruit orange = new Fruit("Orange", R.drawable.orange);
            Fruit pear = new Fruit("Pear", R.drawable.pear);
            fruitList.add(apple);
            fruitList.add(orange);
            fruitList.add(pear);
        }
    }
}
