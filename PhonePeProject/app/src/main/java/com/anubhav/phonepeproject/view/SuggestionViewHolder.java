package com.anubhav.phonepeproject.view;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anubhav.phonepeproject.R;
import com.anubhav.phonepeproject.model.LogoDetails;

public class SuggestionViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;
    private LogoSuggestionAdapter.SuggestionAdapterListener listener;

    public SuggestionViewHolder(@NonNull View itemView, LogoSuggestionAdapter.SuggestionAdapterListener listener) {
        super(itemView);
        textView = itemView.findViewById(R.id.suggestion_tv);
        this.listener = listener;
    }

    public void setData(String str, int position) {
        textView.setText(str);
        textView.setOnClickListener(v -> listener.onItemClick(str, position));
    }
}
