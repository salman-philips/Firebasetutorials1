package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText firebaseEditText1, firebaseEditText2;
    private TextView readFirebaseTextView1;
    private Button sendToFirebaseButton, readFromFirebaseButton;
    private ValueEventListener valueEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String pathForEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFirebaseConfig();
    }

    private void initFirebaseConfig() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        pathForEntry = "";
        valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, String> data = (Map<String, String>) snapshot.getValue();

                if (data != null) {
                    Toast.makeText(MainActivity.this, "Name=" + data.get("Name") + " Age=" + data.get("Age"), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child("Users/user1").addValueEventListener(valueEventListener);
        //here I am trying to insert data at Users/user1/name and Users/user1/age but the value event is attached to user1
        //and on update of age and name its called
    }


    private void initViews() {
        firebaseEditText1 = findViewById(R.id.FirebaseEditText1);
        firebaseEditText2 = findViewById(R.id.FirebaseEditText2);
        sendToFirebaseButton = findViewById(R.id.SendToFirebaseButton);
        readFirebaseTextView1 = findViewById(R.id.FirebaseTextView1);
        readFromFirebaseButton = findViewById(R.id.ReadFirebaseButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child("Users/user1").removeEventListener(valueEventListener);
    }

    public void sendDataToFirebase(View view) {
        String name = firebaseEditText1.getText().toString().trim();
        String age = firebaseEditText2.getText().toString().trim();
        if (!name.isEmpty() && !age.isEmpty()) {
            databaseReference.child("Users/user1/Name").setValue(name);
            databaseReference.child("Users/user1/Age").setValue(age);
            //for two setValue calls two times onChange will be called
            //if u enter same data again and again no value event listener will be called
        }
    }

    public void readDataFromFirebase(View view) {

    }
}