package com.peterstev.unify;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.peterstev.unify.login.Data;

import retrofit2.Retrofit;

public class ChooseSchool extends ListActivity {

    ArrayAdapter<String> arrayAdapter;
    String[] schoolList = {"List Of Schools"};
       Button assCourses, assClasses;
    Intent intent;
    private Data data;

    private Retrofit client;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, schoolList);
        setListAdapter(arrayAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        switch(position){
            case 0:
//                dialog = new Dialog(ChooseSchool.this);
//                dialog.setContentView(R.layout.schools_list);
////                assClasses = (Button) dialog.findViewById(R.id.btn_assigned_classes);
////                assCourses = (Button) dialog.findViewById(R.id.btn_assigned_courses);
//                dialog.setTitle("Welcome getStaffName");
//                dialog.show();

                assCourses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(ChooseSchool.this, Data.class);
                        Toast.makeText(ChooseSchool.this, "Courses", Toast.LENGTH_LONG).show();
                    }
                });
                assClasses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ChooseSchool.this, "Classes", Toast.LENGTH_LONG).show();
                    }
                });

        }
    }
}
