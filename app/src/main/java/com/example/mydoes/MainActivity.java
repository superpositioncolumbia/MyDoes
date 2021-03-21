package com.example.mydoes;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView titlePage, subtitlePage, endPage;

    DatabaseReference databaseReference;
    RecyclerView ourDoes;
    ArrayList<MyDoes> list;
    DoesAdapter doesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titlePage = findViewById(R.id.titlePage);
        subtitlePage = findViewById(R.id.subtitlePage);
        endPage = findViewById(R.id.endPage);

        // Import font
        Typeface MLight = Typeface.createFromAsset(getAssets(), "fonts/ML.tff");
        Typeface MMedium = Typeface.createFromAsset(getAssets(), "fonts/MM.ttf");

        // Customize font
        titlePage.setTypeface(MMedium);
        subtitlePage.setTypeface(MLight);
        endPage.setTypeface(MLight);

        // Working with data
        ourDoes = findViewById(R.id.ourDoes);
        ourDoes.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyDoes>();

        // Get data from Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference().child("DoesApp");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Set code to retrieve data and replace layout
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    MyDoes myDoes = dataSnapshot1.getValue(MyDoes.class);
                    list.add(myDoes);
                }
                doesAdapter = new DoesAdapter(MainActivity.this, list);
                ourDoes.setAdapter(doesAdapter);
                doesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Set code to show an error
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
