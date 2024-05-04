package com.example.activity1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GradeRecycleViewHolder extends RecyclerView.ViewHolder {

    final TextView code;
    final TextView des;
    final TextView val;

    public GradeRecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        code = itemView.findViewById(R.id.txtsubjectcode);
        des = itemView.findViewById(R.id.txtsubjectdescription);
        val = itemView.findViewById(R.id.txtsubjectgrade);
    }
}
