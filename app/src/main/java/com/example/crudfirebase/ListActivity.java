package com.example.crudfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudfirebase.adapter.RequestAdapterRecyclerView;
import com.example.crudfirebase.model.Requests;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private DatabaseReference database;

    private ArrayList<Requests> daftarReq;
    private RequestAdapterRecyclerView requestAdapterRecyclerView;

    private RecyclerView rcListRequest;
    private ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance().getReference();

        rcListRequest = findViewById(R.id.rc_list_request);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcListRequest.setLayoutManager(mLayoutManager);
        rcListRequest.setItemAnimator(new DefaultItemAnimator());

        loading = ProgressDialog.show(ListActivity.this,
                null,
                "Please wait...",
                true,
                false);

        database.child("Request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                daftarReq = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                    Requests requests = noteDataSnapshot.getValue(Requests.class);
                    requests.setKey(noteDataSnapshot.getKey());

                    daftarReq.add(requests);

                    requestAdapterRecyclerView = new RequestAdapterRecyclerView(daftarReq, ListActivity.this);
                    rcListRequest.setAdapter(requestAdapterRecyclerView);
                    loading.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.getDetails() + " " + databaseError.getMessage());
                loading.dismiss();

            }
        });
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivity.this, MainActivity.class)
                        .putExtra("id", "")
                        .putExtra("nama", "")
                        .putExtra("email", "")
                        .putExtra("desk", ""));
            }
        });
    }
}