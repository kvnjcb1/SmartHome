package com.example.kevinjacob.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton, toggle2, toggle3;
    DatabaseReference ref1, ref2, ref3;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseDatabase database = FirebaseDatabase.getInstance();


        toggleButton = findViewById(R.id.toggleLight);
        toggle2 = findViewById(R.id.toggleButton);
        toggle3 = findViewById(R.id.toggleButton2);

        {
            ref1 = database.getReference("light1");

            // only listen once
            ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(Boolean.class)) {
                        toggleButton.setChecked(true);
                    } else {
                        toggleButton.setChecked(false);
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            /*ref1 = database.getReference("light1");
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(Boolean.class)) {
                        textView.setText("Light is ON");
                    } else {
                        textView.setText("Light is OFF");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
        }
        //2
        {
            ref2 = database.getReference("light2");
            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(Boolean.class))
                    {
                        toggle2.setChecked(true);
                    }
                    else{
                        toggle2.setChecked(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });}


        {    //3

            ref3 = database.getReference("light3");
            ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue(Boolean.class))
                    {
                        toggle3.setChecked(true);
                    }
                    else{
                        toggle3.setChecked(false);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });}



        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ref1.setValue(isChecked);
            }
        });
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ref2.setValue(isChecked);
            }
        });
        toggle3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ref3.setValue(isChecked);
            }
        });
    }
}


