package com.apps.mytodolistapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    public EditText modifiedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        //setContentView(R.layout.activity_edit_date_select_priority);

        modifiedText = (EditText) findViewById(R.id.editText);
        Intent i = new Intent();
        String ediText = getIntent().getStringExtra("texttoEdit").toString();
        //modifiedText.setText(ediText);
        //modifiedText.setSelection(modifiedText.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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
    //on button click listener
    public void saveitemtoList(View view) {
        modifiedText.getText().toString();
        //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        Intent sendresults = new Intent(EditItemActivity.this,MainActivity.class);
        sendresults.putExtra("ModifiedText",modifiedText.getText().toString());
        sendresults.putExtra("code", 20);
        setResult(RESULT_OK, sendresults);
        finish();

    }


}
