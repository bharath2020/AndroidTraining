package com.feathertouch.layoutexamples.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.feathertouch.layoutexamples.R;
import com.feathertouch.layoutexamples.services.LocationDetector;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocationDetector.INSTANCE.setContext(this);

        EditText titleInputField = (EditText)findViewById(R.id.titleInputField);

    titleInputField.setText("Three states");




    }

    private void buttonClick() {
        String message = "Nandini restuarant";
        Intent selectedRestuarant = new Intent();
        selectedRestuarant.putExtra("res_name", message);

        RestuarantListviewActivity listView = new RestuarantListviewActivity();
        listView.startActivity(selectedRestuarant);

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
