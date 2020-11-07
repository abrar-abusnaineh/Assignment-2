package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
private EditText name;
private EditText height;
private EditText weight;
private Spinner gender;
private EditText bmi;
private TextView bmiLabel;
public static final String username="Name";
public static final String userheight="Height";
public static final String userweight="Weight";
public static final String userbmi="BMI";
public static final String usergender="Gender";
public static final String FLAG="flag";
private SharedPreferences prefs;
private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.nameLabel);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        bmi=findViewById(R.id.bmiEditText);
        bmiLabel=findViewById(R.id.bmilabel);
        gender=findViewById(R.id.gender);
//        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
//                this,android.R.layout.simple_spinner_dropdown_item,
//                getResources().getStringArray(R.array.gender)); //Your resource name
//
//        gender.setAdapter(arrayAdapter);
        setUpSharedPref();
        checkPref();

    }

    public void calculateBMI(View view) {
        String heightVal=height.getText().toString();
        String weightVal=weight.getText().toString();
        if(heightVal.length()==0 || weightVal.length()==0){
         Toast  toast= Toast.makeText(this,"The values of height and weight must be entered",Toast.LENGTH_SHORT);
            toast.show();
        return;}
      double heightDouble=Double.parseDouble(heightVal);
      double weightDouble=Double.parseDouble(weightVal);
      double bmiVal=weightDouble/(heightDouble*heightDouble);
        String bmiFormat = String.format(Locale.getDefault(),
                "%.2f",bmiVal);
        bmiLabel.setText(bmiFormat);
        bmi.setText(bmiFormat);
//      bmiLabel.setText(String.valueOf(bmiVal));
//      bmi.setText(String.valueOf(bmiVal));


    }

    public void saveData(View view) {
String userName=name.getText().toString();
String userHeight=height.getText().toString();
String userWeight=weight.getText().toString();
String userGender=gender.getSelectedItem().toString();
String userBmi=bmi.getText().toString();

editor.putString(username,userName);
editor.putString(userheight,userHeight);
editor.putString(userweight,userWeight);
editor.putString(userbmi,userBmi);
editor.putString(usergender,userGender);
editor.putBoolean(FLAG,true);
editor.commit();
        Toast  toast= Toast.makeText(this,"Data Saved Successfully",Toast.LENGTH_SHORT);
        toast.show();
    }

    public void Timer(View view) {
        Intent intent=new Intent(this,TimerActivity.class);
        //sending intent
        startActivity(intent);
    }
    public void checkPref(){
boolean flag=prefs.getBoolean(FLAG,false);
if(flag){
    String nameSaved=prefs.getString(username,"");
    String heightSaved=prefs.getString(userheight,"");
    String weightSaved=prefs.getString(userweight,"");
    String bmiSaved=prefs.getString(userbmi,"");
    String genderSaved=prefs.getString(usergender,"");
    name.setText(nameSaved);
    height.setText(heightSaved);
    weight.setText(weightSaved);
    if(genderSaved.equals("Male"))
        gender.setSelection(0);
    else
        gender.setSelection(1);
    bmi.setText(bmiSaved);
    bmiLabel.setText(bmiSaved);
}
    }
    private void setUpSharedPref(){
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        editor=prefs.edit();
    }

}