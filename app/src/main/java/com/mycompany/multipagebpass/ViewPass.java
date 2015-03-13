package com.mycompany.multipagebpass;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewPass extends ActionBarActivity {

    private TextView name;
    private TextView address;
    private TextView passtype;
    private TextView passduration;
    private TextView fromdate;
    private TextView todate;
    private TextView amount;
    ImageView imgFavorite;
    Bitmap bp;

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

        name.setText(extras.getString("e_name"));
        address.setText(extras.getString("e_address"));
        passtype.setText(extras.getString("e_passtype"));
        passduration.setText(extras.getString("e_passduration"));
        fromdate.setText(extras.getString("e_fromdate"));
        todate.setText(extras.getString("e_todate"));
        amount.setText(extras.getString("e_amount"));

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
