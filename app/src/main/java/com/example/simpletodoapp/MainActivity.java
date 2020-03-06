package com.example.simpletodoapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);


        //etItem.setText("I'm doing this from java!");
        loadItems();

        // items = new ArrayList<>();
        //items.add("GO TO SCHOOL");
        //items.add("GO TO OOO");
        //items.add("Mob");

       ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
           @Override
           public void onItemLongClicked(int position) {
               // Delete the item from the model
               items.remove(position);
               // Notify the adapter
               itemsAdapter.notifyItemRemoved(position);
               Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
           }

        };
        itemsAdapter = new ItemsAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();
                // Add item to the model
                items.add(todoItem);
                //Notify adapter that an item is inserted
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }
    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }
    // This function will load items by reading every line of the file{
        private void loadItems() {
            try {
                items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
            } catch (IOException e) {
                Log.e("MainAvtivity", "Error readint items",e);
                items =  new ArrayList<>();
            }
        }
        //This fonction saves items by writing them intro the data file
        private void saveItems() {
            try {
                FileUtils.writeLines(getDataFile(), items);
            } catch (IOException e) {
                Log.e("MainAvtivity", "Error writing items",e);
            }
        }

    }

