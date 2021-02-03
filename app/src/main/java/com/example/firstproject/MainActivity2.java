package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView rvandroidversion;
    FloatingActionButton fab_button;
    androidAdapter adapter;
    androidVersion androidVersionDB;
    ArrayList<String>types=new ArrayList<>();
    ArrayList<Integer>number=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rvandroidversion = findViewById(R.id.rvandroidversion);
        fab_button = findViewById(R.id.fab_button);
        androidVersionDB = new androidVersion(this);

        adapter = new androidAdapter();
        rvandroidversion.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvandroidversion.setLayoutManager(layoutManager);
        rvandroidversion.setAdapter(adapter);

        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivityForResult(intent, 100);

            }
        });
        Cursor versionCursor = androidVersionDB.getversions();
        if (versionCursor != null && versionCursor.moveToFirst()) {
            do {
                types.add(versionCursor.getString(versionCursor.getColumnIndex("androidDB")));
                number.add(versionCursor.getInt(versionCursor.getColumnIndex("androidVersion")));
            }
            while (versionCursor.moveToNext());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK && resultCode==100){
            String name=data.getExtras().getString("key");
            int version=data.getExtras().getInt("key1",0);
            types.add(name);
            number.add(version);


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public class androidAdapter extends RecyclerView.Adapter<androidAdapter.androidViewHolder>{

        @NonNull
        @Override
        public androidAdapter.androidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.version,parent,false);
            return new androidViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull androidViewHolder holder, int position) {
            holder.tvversionName.setText(types.get(position));
            holder.tvversionNumber.setText(number.get(position)+"");

        }

        @Override
        public int getItemCount() {
            return types.size();
        }

        public class androidViewHolder extends RecyclerView.ViewHolder{
            TextView tvversionName,tvversionNumber;
            ImageView ivAndroid;
            public androidViewHolder(@NonNull View itemView) {
                super(itemView);
                tvversionName=itemView.findViewById(R.id.tvversionname);
                tvversionNumber=itemView.findViewById(R.id.tvversionnumber);
                ivAndroid=itemView.findViewById(R.id.imageicon);
            }
        }
    }
    }

    