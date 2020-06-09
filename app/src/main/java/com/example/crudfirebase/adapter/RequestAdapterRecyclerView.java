package com.example.crudfirebase.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudfirebase.MainActivity;
import com.example.crudfirebase.R;
import com.example.crudfirebase.model.Requests;

import java.util.List;

public class RequestAdapterRecyclerView extends RecyclerView.Adapter<RequestAdapterRecyclerView.MyViewHolder> {

    private List<Requests> movieList;
    private Activity mActivity;

    public RequestAdapterRecyclerView(List<Requests> movieList, Activity activity) {
        this.movieList = movieList;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public RequestAdapterRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemVIew = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new MyViewHolder(itemVIew);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdapterRecyclerView.MyViewHolder holder, int position) {
        final Requests movie = movieList.get(position);

        holder.tvNama.setText(movie.getNama());
        holder.tvEmail.setText(movie.getEmail());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDetail = new Intent(mActivity, MainActivity.class);
                goDetail.putExtra("id", movie.getKey());
                goDetail.putExtra("nama", movie.getNama());
                goDetail.putExtra("email", movie.getEmail());
                goDetail.putExtra("desk", movie.getDeks());

                mActivity.startActivity(goDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView tvNama, tvEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linearLayourItem);
            tvNama = itemView.findViewById(R.id.tv_Nama);
            tvEmail = itemView.findViewById(R.id.tv_Email);
        }
    }
}
