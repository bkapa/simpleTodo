package com.walmartlabs.bkapa.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    int pos =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String item = getIntent().getStringExtra("Item");
        this.pos = getIntent().getIntExtra("pos",0);
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setText(item.toString());
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


    public void onSaveItem(View v){
        EditText etText = (EditText)findViewById(R.id.editText);
        String itemTxt = etText.getText().toString();
        Intent data = new Intent();
        data.putExtra("name", itemTxt);
        data.putExtra("pos",pos);
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }

}
