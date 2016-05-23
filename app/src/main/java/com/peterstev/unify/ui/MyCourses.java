package com.peterstev.unify.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.peterstev.unify.R;

public class MyCourses extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] coursesList = {"Mathematics", "English Language", "Biology", "Chemistry", "Physics"};
    Intent intent;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_courses);
        toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(MyCourses.this, android.R.layout.simple_list_item_1, coursesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MyCourses.this, ClassList.class);
                intent.putExtra("parent", "com.peterstev.unify.ui.MyCourses");
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });
    }
}