package com.walmartlabs.bkapa.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SimpleTodo extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_todo);
        lvItems = (ListView) findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);

      //  items.add("First Item");
       // items.add("Second Item");
        setupListViewListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_todo, menu);
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

    public void onAddItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemTxt = etNewItem.getText().toString();
        itemsAdapter.add(itemTxt);
        etNewItem.setText(" ");
        writeItems();
    }

    public void setupListViewListener(){

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;

            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private long id;

            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                Intent intent = new Intent(SimpleTodo.this, EditItemActivity.class);

                intent.putExtra("Item", items.get(pos).toString());
                intent.putExtra("id", id);
                intent.putExtra("pos",pos);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String name = data.getExtras().getString("name");
            int pos = data.getExtras().getInt("pos", 0);
            items.remove(pos);
            items.add(pos,name);
        }
    }

    public void readItems(){

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir , "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
            items = new ArrayList<String>();

        }


    }

    public void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir , "todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
