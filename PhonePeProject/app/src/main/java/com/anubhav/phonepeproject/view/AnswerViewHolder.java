package com.anubhav.phonepeproject.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anubhav.phonepeproject.R;

public class AnswerViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public AnswerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.answer_tv);
    }

    public void setData(String str){
        textView.setText(str);
    }
}
