package com.peterstev.unify.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.peterstev.unify.R;
import com.peterstev.unify.adapters.ClassListAdapter;
import com.peterstev.unify.adapters.ClassListAdapter1;

public class ClassList extends AppCompatActivity implements ClassListAdapter.clickListener, ClassListAdapter1.clickListener {
    private static final String TAG = ClassList.class.getSimpleName();

    RecyclerView mRecyclerView;
    ClassListAdapter classListAdapter;
    Toolbar toolbar;
    RecyclerView.LayoutManager layoutManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list_recyclerview);

        toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        classListAdapter = new ClassListAdapter(ClassList.this);
        configViews();
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(ClassList.this, "You clicked item" + " " + position, Toast.LENGTH_SHORT).show();
        intent = new Intent(ClassList.this, Profile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    private void configViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = (new LinearLayoutManager(ClassList.this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setAdapter(classListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_class_edit_all, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_all) {
            intent = new Intent(ClassList.this, ClassListEditAll.class);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }
}