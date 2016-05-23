package com.peterstev.unify.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.peterstev.unify.R;

public class ClassListFragment extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_list_recyclerview);
    }

}
