package com.anubhav.phonepeproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anubhav.phonepeproject.R;

import java.util.List;

public class LogoSuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private SuggestionAdapterListener listener;

    public LogoSuggestionAdapter(Context context, SuggestionAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuggestionViewHolder(LayoutInflater.from(context).inflate(R.layout.suggestion_row, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SuggestionViewHolder)holder).setData(listener.getSuggestionList().get(position).toString(), position);
    }

    @Override
    public int getItemCount() {
        return listener.getSuggestionList().size();
    }

    interface SuggestionAdapterListener {
        List<Character> getSuggestionList();

        void onItemClick(String c, int position);
    }
}
