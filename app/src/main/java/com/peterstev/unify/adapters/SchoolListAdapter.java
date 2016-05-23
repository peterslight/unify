package com.peterstev.unify.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.peterstev.unify.login.School;

import java.util.List;


public class SchoolListAdapter extends ArrayAdapter<School> {

    private final int resource;

    public SchoolListAdapter(Context context, int resource, List<School> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        School school = this.getItem(position);

        if (convertView == null) {
           convertView = new TextView(this.getContext());
        }

        TextView view = (TextView) convertView;
        view.setTextSize(25);
        view.setText(school.getName());

        return convertView;
    }
}
