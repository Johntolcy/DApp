package com.example.dapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import Database.DBHelper;
import JavaBean.UserFood;
import SearchDao.FoodDao;
import SearchDao.UserIntakeDao;
import Util.Staticfinal_Value;

public class FoodRecordItem extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private NumberFormat numberFormat;
    private String item_id;
    private String userId;
    private String UIclass;
    private String UIdate;
    private String foodName;
    private String foodSize;
    private String foodEnergy;
    private String foodId;
    private String percent;
    //暂时除去能量
    private String[] nutrition = new String[21];
    private String[] nutriArr = new String[21];
    private String[] unit = {"千卡", "克", "克", "克", "克",
            "克", "克", "克", "毫克", "毫克",
            "毫克", "毫克", "毫克", "毫克", "毫克",
            "毫克", "毫克", "毫克", "毫克", "毫克",
            "毫克"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.foodrecord_info);
        Staticfinal_Value sfv = new Staticfinal_Value();
        dbHelper = new DBHelper(this, "DApp.db", null, sfv.staticVersion());
        Toolbar toolbar = findViewById(R.id.FR_I_toolbar);
        setSupportActionBar(toolbar);
        TextView date = findViewById(R.id.FR_I_date);
        TextView food_class = findViewById(R.id.FR_I_class);
        TextView food_size = findViewById(R.id.FRI_size);
        TextView food_nutrition = findViewById(R.id.FRI_nutrition);
        TextView food_egy_size = findViewById(R.id.FRI_energy_size);
        TextView food_protein = findViewById(R.id.FRI_protein_size);
        TextView food_fat = findViewById(R.id.FRI_fat_size);
        TextView food_CH = findViewById(R.id.FRI_CH_size);
        TextView food_DF = findViewById(R.id.FRI_DF_size);
        TextView food_water = findViewById(R.id.FRI_water_size);
        TextView food_CLS = findViewById(R.id.FRI_CLS_size);
        TextView food_VA = findViewById(R.id.FRI_VA_size);
        TextView food_VB1 = findViewById(R.id.FRI_VB1_size);
        TextView food_VB2 = findViewById(R.id.FRI_VB2_size);
        TextView food_VB3 = findViewById(R.id.FRI_VB3_size);
        TextView food_VE = findViewById(R.id.FRI_VE_size);
        TextView food_VC = findViewById(R.id.FRI_VC_size);
        TextView food_Ga = findViewById(R.id.FRI_Ga_size);
        TextView food_Na = findViewById(R.id.FRI_Na_size);
        TextView food_Fe = findViewById(R.id.FRI_Fe_size);
        TextView food_Mg = findViewById(R.id.FRI_Mg_size);
        TextView food_P = findViewById(R.id.FRI_P_size);
        TextView food_Zn = findViewById(R.id.FRI_Zn_size);
        TextView food_K = findViewById(R.id.FRI_K_size);
        TextView food_purine = findViewById(R.id.FRI_purine_size);
        Intent intent = getIntent();
        Bundle bundle_from_FRLV = intent.getExtras();
        if (bundle_from_FRLV != null) {
            item_id = bundle_from_FRLV.getString("itemId");
            userId = bundle_from_FRLV.getString("User_id");
            UIclass = bundle_from_FRLV.getString("intake_class");
            UIdate = bundle_from_FRLV.getString("intake_date");
            foodName = bundle_from_FRLV.getString("food_name");
            foodSize = bundle_from_FRLV.getString("itemSize");
            foodEnergy = bundle_from_FRLV.getString("itemEnergy");
            foodId = bundle_from_FRLV.getString("food_id");
        }
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        date.setText(UIdate);
        food_class.setText(UIclass);
        String fz = foodName + foodSize + "克";
        food_size.setText(fz);
        food_nutrition.setText(foodEnergy);

        FoodDao foodDao = new FoodDao(this);
        nutriArr = foodDao.findNutrition(foodId);
        percent = numberFormat.format(Float.valueOf(foodSize) / 100);
        for (int i = 0; i < 21; i++) {
            if (nutriArr[i] != null && !nutriArr[i].equals("…") && !nutriArr[i].equals("Tr") && nutriArr[i].length() > 0 && !nutriArr[i].equals("—") && !nutriArr[i].equals("┄") && !nutriArr[i].equals("─")) {
                nutrition[i] = numberFormat.format(Float.valueOf(nutriArr[i]) * Float.valueOf(percent)) + unit[i];
            } else {
                nutrition[i] = "—";
            }
        }
        food_egy_size.setText(nutrition[0]);
        food_protein.setText(nutrition[1]);
        food_fat.setText(nutrition[2]);
        food_CH.setText(nutrition[3]);
        food_DF.setText(nutrition[4]);
        food_water.setText(nutrition[5]);
        food_CLS.setText(nutrition[6]);
        food_VA.setText(nutrition[7]);
        food_VB1.setText(nutrition[8]);
        food_VB2.setText(nutrition[9]);
        food_VB3.setText(nutrition[10]);
        food_VC.setText(nutrition[11]);
        food_VE.setText(nutrition[12]);
        food_Ga.setText(nutrition[13]);
        food_Na.setText(nutrition[14]);
        food_Fe.setText(nutrition[15]);
        food_Mg.setText(nutrition[16]);
        food_Zn.setText(nutrition[17]);
        food_P.setText(nutrition[18]);
        food_K.setText(nutrition[19]);
        food_purine.setText(nutrition[20]);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoodRecordItem.this.finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fri_tb, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteRecord:
                sqLiteDatabase = dbHelper.getWritableDatabase();
                deleteRecord(foodName, foodSize, userId, UIclass, UIdate);
                sqLiteDatabase.delete("UserFood", "_id=?", new String[]{item_id});
                Toast.makeText(this, "记录删除", Toast.LENGTH_SHORT).show();
                dbHelper.close();
                sqLiteDatabase.close();
                FoodRecordItem.this.finish();
                break;
            default:
                break;
        }
        return true;
    }

    public void deleteRecord(String fruitName, String fruitSize, String userId, String UIclass, String UIdate) {
        FoodDao foodDao = new FoodDao(this);
        ContentValues values = new ContentValues();
        String[] nutArray = new String[23];
        //字典表里的营养值
        nutArray[0] = foodDao.find_energy(fruitName);
        nutArray[1] = foodDao.find_protein(fruitName);
        nutArray[3] = foodDao.find_DF(fruitName);
        nutArray[4] = foodDao.find_CH(fruitName);
        nutArray[2] = foodDao.find_fat(fruitName);
        nutArray[5] = foodDao.find_water(fruitName);
        nutArray[15] = foodDao.find_CLS(fruitName);
        nutArray[6] = foodDao.find_vA(fruitName);
        nutArray[7] = foodDao.find_vB1(fruitName);
        nutArray[8] = foodDao.find_vB2(fruitName);
        nutArray[9] = foodDao.find_vB3(fruitName);
        nutArray[10] = foodDao.find_vE(fruitName);
        nutArray[11] = foodDao.find_vC(fruitName);
        nutArray[12] = foodDao.find_Fe(fruitName);
        nutArray[14] = foodDao.find_Na(fruitName);
        nutArray[17] = foodDao.find_Mg(fruitName);
        nutArray[18] = foodDao.find_Zn(fruitName);
        nutArray[13] = foodDao.find_Ga(fruitName);
        nutArray[16] = foodDao.find_K(fruitName);
        nutArray[19] = foodDao.find_P(fruitName);
        nutArray[20] = foodDao.find_purine(fruitName);
        UserIntakeDao userIntakeDao = new UserIntakeDao(this);
        //营养表里的营养值
        String[] intakeNutri = userIntakeDao.getFromUserIntake(userId, UIclass, UIdate);
        //营养表里的营养名称
        String[] NutName = {"UI_energy", "UI_protein", "UI_fat", "UI_DF", "UI_CH", "UI_water", "UI_VA", "UI_VB1",
                "UI_VB2", "UI_VB3", "UI_VE", "UI_VC", "UI_Fe", "UI_Ga", "UI_Na", "UI_CLS", "UI_K", "UI_Mg",
                "UI_Zn", "UI_P", "UI_purine"};
        //每100克倍数
        float f_fruitSize = Float.valueOf(fruitSize);
        String percent = numberFormat.format(f_fruitSize / 100);
        float f_percent = Float.valueOf(percent);
        //临时赋值
        String result_ab;
        //要减去的所有营养
        for (int i = 0; i < 21; i++) {
            if (nutArray[i] != null && !nutArray[i].equals("…") && !nutArray[i].equals("Tr") && nutArray[i].length() > 0 && !nutArray[i].equals("—") && !nutArray[i].equals("┄") && !nutArray[i].equals("─")) {
                nutArray[i] = numberFormat.format(Float.valueOf(nutArray[i]) * f_percent).replace(",", "");
                result_ab = numberFormat.format(Float.valueOf(intakeNutri[i]) - Float.valueOf(nutArray[i]));
                values.put(NutName[i], result_ab);
            }
            sqLiteDatabase.update("UserIntake", values, "User_id = ? and UI_date=? and UI_class=?", new String[]{userId, UIdate, UIclass});
        }
    }


}
