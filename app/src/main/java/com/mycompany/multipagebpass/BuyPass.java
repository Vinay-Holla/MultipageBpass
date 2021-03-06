package com.mycompany.multipagebpass;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class BuyPass extends ActionBarActivity {

    private EditText custname;
    private EditText custaddress;
    private EditText fromDateEtxt;
    private TextView toDateEtxt;
    private TextView amount;
    private String passtype;
    private String passduration;
    private String[] ar_passduration;
    private String[] ar_passtype;
    private Button pay_button;
    ImageView imgFavorite;
    Bitmap bp;
    Spinner s_passtype;
    Spinner s_passduration;
    int intamount;
    Uri imageUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    public static final String PREFS_NAME = "User_Inputs";

    Calendar newDate;
    SQLiteDatabase passdb;
    SharedPreferences user_details;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pass);

        setnameaddr();
        setpasstype();
        setpassduration();
        setpassdate();
        setimage();
        beginpayment();
        setDateTimeField();
        setAmountField();
        getSharedpref();

    }

    private void setnameaddr(){

        custname = (EditText)findViewById(R.id.e_custname);
        custaddress = (EditText)findViewById(R.id.e_custaddress);
    }

    private void setpasstype(){

        s_passtype = (Spinner) findViewById(R.id.s_passtype);
        ar_passtype = getResources().getStringArray(R.array.a_passtype);
        passtype = ((Spinner)findViewById(R.id.s_passtype)).getSelectedItem().toString();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, ar_passtype);

        s_passtype.setAdapter(adapter1);
        s_passtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();

                amount.setText("");
                setAmountField();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void setpassduration(){

        Spinner s_passduration = (Spinner) findViewById(R.id.s_passduration);
        ar_passduration = getResources().getStringArray(R.array.a_passduration);
        passduration = ((Spinner)findViewById(R.id.s_passduration)).getSelectedItem().toString();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, ar_passduration);

        s_passduration.setAdapter(adapter);
        s_passduration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                int index = arg0.getSelectedItemPosition();

                fromDateEtxt.setText("");
                toDateEtxt.setText("");
                amount.setText("");
                setAmountField();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void setpassdate() {

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        fromDateEtxt = (EditText) findViewById(R.id.e_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (TextView) findViewById(R.id.t_todatedisp);
        toDateEtxt.setInputType(InputType.TYPE_NULL);

        amount = (TextView) findViewById(R.id.t_amountdisp);
        amount.setInputType(InputType.TYPE_NULL);

        fromDateEtxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });
    }

    private void setimage(){

        imgFavorite = (ImageView)findViewById(R.id.imageView1);
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });

    }

    private void beginpayment(){

                pay_button = (Button)findViewById(R.id.b_pass);

        pay_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {

                String from_date_time;
                String to_date_time;
                String start_time = " 00:00:01";
                String end_time = " 23:59:59";

                from_date_time = fromDateEtxt.getText().toString().concat(start_time);
                to_date_time = toDateEtxt.getText().toString().concat(end_time);

                setSharedpref();
             /*   imgFavorite.buildDrawingCache();
                Bitmap image= imgFavorite.getDrawingCache();*/
                Intent intent = new Intent(BuyPass.this, ViewPass.class);
                Bundle extras = new Bundle();
                extras.putString("e_callingclass", "BuyPass.this");
                extras.putString("e_name",custname.getText().toString());
                extras.putString("e_address",custaddress.getText().toString());
                extras.putString("e_passtype",passtype);
                extras.putString("e_passduration",passduration);
                /*extras.putString("e_fromdate",fromDateEtxt.getText().toString());
                extras.putString("e_todate",toDateEtxt.getText().toString());*/
                extras.putString("e_fromdate",from_date_time);
                extras.putString("e_todate",to_date_time);
                extras.putString("e_amount",amount.getText().toString());
                extras.putParcelable("e_image", bp);
                intent.putExtras(extras);
                startActivity(intent);

                String val1, val2, val3, val4, val5, val6, val7, val8, val9, val10;
                int outamount;
                val1 = custname.getText().toString();
                val2 = custaddress.getText().toString();
                val3 = from_date_time;
                val4 = to_date_time;
                /*val3 = fromDateEtxt.getText().toString();
                val4 = toDateEtxt.getText().toString();*/

                passdb=openOrCreateDatabase("passamtDB", Context.MODE_PRIVATE, null);

                passdb.execSQL("DELETE from validpass;");

                  passdb.execSQL("INSERT into validpass (name, address, passtype, passduration, fromdate, todate, amount) VALUES" +
                          " ('" + val1 + "', '" + val2 + "', '" + passtype + "', '" + passduration + "', '" + val3 + "', '" + val4 + "', " + intamount + ");");


                  Cursor c=passdb.rawQuery("SELECT * FROM validpass",null);
                  if(c.moveToFirst()) {
                      val5 = String.valueOf(c.getString(0));
                      val6 = String.valueOf(c.getString(1));
                      val7 = String.valueOf(c.getString(2));
                      val8 = String.valueOf(c.getString(3));
                      val9 = String.valueOf(c.getString(4));
                      val10 = String.valueOf(c.getString(5));
                      outamount = c.getInt(6);
                  }

                passdb.close();
            }
        });

    }

    public void open(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        bp = (Bitmap) data.getExtras().get("data");
        imgFavorite.setImageBitmap(bp);

        FileOutputStream fos;
        try {
            fos = openFileOutput("VINAY_1", Context.MODE_PRIVATE);
            bp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();

        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));

                toDateSet();

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    private void toDateSet(){

        Calendar newCalendar1 = (Calendar)newDate.clone();
        passduration = ((Spinner)findViewById(R.id.s_passduration)).getSelectedItem().toString();
        int add_days =0;

        switch (passduration){
            case "Daily"   :    add_days = 0;
                break;
            case "Monthly" :    add_days = newCalendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
                break;
        }
        newCalendar1.add(Calendar.DAY_OF_MONTH, add_days);
        toDateEtxt.setText(dateFormatter.format(newCalendar1.getTime()));

    }

    private void setAmountField() {

        passtype = ((Spinner)findViewById(R.id.s_passtype)).getSelectedItem().toString();
        passduration = ((Spinner)findViewById(R.id.s_passduration)).getSelectedItem().toString();

        passdb=openOrCreateDatabase("passamtDB", Context.MODE_PRIVATE, null);

        Cursor c=passdb.rawQuery("SELECT amount FROM passamt WHERE passtype='"+passtype+"' AND passduration='"+passduration+"'",null);
        if(c.moveToFirst())
        {
            amount.setText(String.valueOf(c.getInt(0)));
            intamount = c.getInt(0);

        }
        else
        {
            Toast.makeText(getApplicationContext(), (String)"No Records in DB",
                    Toast.LENGTH_LONG).show();

        }

        passdb.close();
    }

    private void getSharedpref(){

        user_details = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String pref_custname = user_details.getString("custname", null);
        String pref_custaddress = user_details.getString("custaddress", null);

        if (pref_custname != null){
            custname.setText(pref_custname);
        }
        if (pref_custaddress != null){
            custaddress.setText(pref_custaddress);
        }

    }

    private void setSharedpref(){

        user_details = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefeditor = user_details.edit();
        prefeditor.putString("custname", custname.getText().toString());
        prefeditor.putString("custaddress", custaddress.getText().toString());
      /*  prefeditor.putString("fromdate", fromDateEtxt.getText().toString());
        prefeditor.putString("todate",toDateEtxt.getText().toString());*/
        prefeditor.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buy_pass, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
