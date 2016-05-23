package com.peterstev.unify.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.peterstev.unify.R;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.Holder> {

    private static final String TAG = ClassListAdapter1.class.getSimpleName();
    private static clickListener mClickListener;
    View row;

    public ClassListAdapter(clickListener listener) {
        mClickListener = listener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        row = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list_listview_cardview, parent, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public interface clickListener {
        void onItemClick(int position);
        //  void onItemLongClick(int position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mview;
        private ImageView student_avatar;
        private TextView student_name, reg_number;

        public Holder(View itemView) {
            super(itemView);
            mview = itemView;

            student_avatar = (ImageView) itemView.findViewById(R.id.student_avatar);
            student_name = (TextView) itemView.findViewById(R.id.student_name);
            reg_number = (TextView) itemView.findViewById(R.id.student_reg_number);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getLayoutPosition());
        }
    }
}