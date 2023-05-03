package com.example.dynamiclist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> itemList = new ArrayList<>();
    private EditText editText;
    public Button addButton;
    private Button removeButton;
    private RecyclerView recyclerView;
    private DynamicListRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Map each UI element to a field variable
        editText = findViewById(R.id.editText);
        addButton = findViewById(R.id.addButton);
        removeButton = findViewById(R.id.removeButton);
        recyclerView = findViewById(R.id.listRecyclerView);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DynamicListRecyclerViewAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // Set up addButton click listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editText.getText().toString().trim();
                if (!item.isEmpty()) {
                    if (itemList.contains(item)){
                        Toast.makeText(MainActivity.this, "Item already in list", Toast.LENGTH_SHORT).show();
                    } else {
                        itemList.add(item);
                        adapter.notifyItemInserted(itemList.size() - 1);
                    }
                }
            }
        });

        // Set up removeButton click listener
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemList.isEmpty()) {
                    Toast.makeText(MainActivity.this, "List is empty", Toast.LENGTH_SHORT).show();
                } else {
                    itemList.remove(itemList.size() - 1);
                    adapter.notifyItemRemoved(itemList.size());
                }
            }
        });

        // Set up back-hardware button click listener
        findViewById(android.R.id.content).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    if (itemList.isEmpty()) {
                        // Exit the app if the list is empty
                        finish();
                    } else {
                        itemList.remove(itemList.size() - 1);
                        adapter.notifyItemRemoved(itemList.size());
                    }
                    return true;
                }
                return false;
            }
        });
    }
}