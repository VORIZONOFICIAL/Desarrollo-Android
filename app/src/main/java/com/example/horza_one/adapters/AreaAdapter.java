package com.example.horza_one.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.horza_one.R;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {
    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_calendario_dialog, parent, false);
        return new AreaAdapter.AreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaAdapter.AreaViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
