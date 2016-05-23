package com.peterstev.unify.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peterstev.unify.R;


public class ClassListEditAllAdapter extends RecyclerView.Adapter<ClassListEditAllAdapter.Holder> {

    private static final String TAG = ClassListAdapter.class.getSimpleName();

    public ClassListEditAllAdapter() {
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_edit_all, parent, false);

        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 200;
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView mPhoto;
        private TextView mName, mPrice;

        public Holder(View itemView) {
            super(itemView);
        }
    }
}
