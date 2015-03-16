package com.mycompany.multipagebpass;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ViewPass extends ActionBarActivity {

    private TextView name;
    private TextView address;
    private TextView passtype;
    private TextView passduration;
    private TextView fromdate;
    private TextView todate;
    private TextView amount;
    private String classname;
    ImageView imgFavorite;
    Bitmap bp;
    SQLiteDatabase passdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pass);

        name = (TextView)findViewById(R.id.t_namedisp);
        address = (TextView)findViewById(R.id.t_addressdisp);
        passtype = (TextView)findViewById(R.id.t_passtypedisp);
        passduration = (TextView)findViewById(R.id.t_passdurdisp);
        fromdate = (TextView)findViewById(R.id.t_fromdatedisp);
        todate = (TextView)findViewById(R.id.t_todatedisp);
        amount = (TextView)findViewById(R.id.t_amountdisp);
        imgFavorite = (ImageView)findViewById(R.id.imageView1);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        classname = extras.getString("e_callingclass");

        if (classname.equals("BuyPass.this")) {

            name.setText(extras.getString("e_name"));
            address.setText(extras.getString("e_address"));
            passtype.setText(extras.getString("e_passtype"));
            passduration.setText(extras.getString("e_passduration"));
            fromdate.setText(extras.getString("e_fromdate"));
            todate.setText(extras.getString("e_todate"));
            amount.setText(extras.getString("e_amount"));

        } else  if (classname.equals("MainActivity.this")) {

            passdb=openOrCreateDatabase("passamtDB", Context.MODE_PRIVATE, null);
            Cursor c=passdb.rawQuery("SELECT * FROM validpass",null);
            if(c.moveToFirst())
            {
                name.setText(String.valueOf(c.getString(0)));
                address.setText(String.valueOf(c.getString(1)));
                passtype.setText(String.valueOf(c.getString(2)));
                passduration.setText(String.valueOf(c.getString(3)));
                fromdate.setText(String.valueOf(c.getString(4)));
                todate.setText(String.valueOf(c.getString(5)));
                amount.setText(String.valueOf(c.getInt(6)));

/*                Toast.makeText(getApplicationContext(),  name.getText().toString() ,
                        Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),  address.getText().toString() ,
                        Toast.LENGTH_LONG).show();*/

            }
            else
            {

                Toast.makeText(getApplicationContext(), (String) "No Records in DB",
                        Toast.LENGTH_LONG).show();

            }
            passdb.close();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pass, menu);
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
