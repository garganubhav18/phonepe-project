package com.anubhav.phonepeproject.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anubhav.phonepeproject.R;

import java.util.List;

public class LogoAnswerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private AnswerAdapterListener listener;

    public LogoAnswerAdapter(Context context, AnswerAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AnswerViewHolder(LayoutInflater.from(context).inflate(R.layout.answer_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((AnswerViewHolder)holder).setData(listener.getAnswerList().get(position).toString());
    }

    @Override
    public int getItemCount() {
        return listener.getAnswerList().size();
    }

    interface AnswerAdapterListener {
        List<Character> getAnswerList();
    }
}
