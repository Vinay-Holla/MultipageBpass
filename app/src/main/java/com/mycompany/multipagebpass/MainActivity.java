package com.mycompany.multipagebpass;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    SQLiteDatabase passdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b_buypass = (Button) findViewById(R.id.b_buypass);
        Button v_viewpass = (Button) findViewById(R.id.b_viewpass);

        b_buypass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, BuyPass.class);
                startActivity(intent);
               /* Toast.makeText(getApplicationContext(), (String)"In Buypass",
                        Toast.LENGTH_LONG).show();*/
            }
        });

        v_viewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewPass.class);
                Bundle extras = new Bundle();
                extras.putString("e_callingclass", "MainActivity.this");
                intent.putExtras(extras);
                startActivity(intent);

                /*Toast.makeText(getApplicationContext(), (String)"In Viewpass",
                        Toast.LENGTH_LONG).show();*/
            }
        });

        createdb();
    }

    private void createdb(){

        passdb=openOrCreateDatabase("passamtDB", Context.MODE_PRIVATE, null);
        passdb.execSQL("CREATE TABLE IF NOT EXISTS passamt(passtype VARCHAR,passduration VARCHAR,amount int);");
        passdb.execSQL("INSERT INTO passamt (passtype, passduration, amount) VALUES ('Black Board','Daily',60);");
        passdb.execSQL("INSERT INTO passamt (passtype, passduration, amount) VALUES ('Black Board','Monthly',850);");
        passdb.execSQL("INSERT INTO passamt (passtype, passduration, amount) VALUES ('Red Board','Daily',80);");
        passdb.execSQL("INSERT INTO passamt (passtype, passduration, amount) VALUES ('Red Board','Monthly',1050);");
        passdb.execSQL("INSERT INTO passamt (passtype, passduration, amount) VALUES ('Vajra','Daily',150);");
        passdb.execSQL("INSERT INTO passamt (passtype, passduration, amount) VALUES ('Vajra','Monthly',2250);");

        passdb.execSQL("CREATE TABLE IF NOT EXISTS validpass(name VARCHAR, address VARCHAR, passtype VARCHAR," +
                "passduration VARCHAR, fromdate VARCHAR, todate VARCHAR, amount int;");
        passdb.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
