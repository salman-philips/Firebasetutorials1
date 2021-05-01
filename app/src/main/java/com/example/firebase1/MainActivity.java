package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText firebaseEditText1;
    private TextView readFirebaseTextView1;
    private Button sendToFirebaseButton,readFromFirebaseButton;
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
        /**
         * The entry point for accessing a Firebase Database. You can get an instance by calling {@link
         * FirebaseDatabase#getInstance()}. To access a location in the database and read or write data, use
         * {@link FirebaseDatabase#getReference()}.
         */
        /**
         * Gets the default FirebaseDatabase instance.
         *
         * @return A FirebaseDatabase instance.
         */
        firebaseDatabase = FirebaseDatabase.getInstance();
        /**
         * Gets a DatabaseReference for the database root node.
         *
         * @return A DatabaseReference pointing to the root node.
         */
        databaseReference = firebaseDatabase.getReference();
        pathForEntry = "";
    }

    private void initViews() {
        firebaseEditText1 = findViewById(R.id.FirebaseEditText1);
        sendToFirebaseButton = findViewById(R.id.SendToFirebaseButton);
        readFirebaseTextView1=findViewById(R.id.FirebaseTextView1);
        readFromFirebaseButton=findViewById(R.id.ReadFirebaseButton);
    }

    public void sendDataToFirebase(View view) {
        String data = firebaseEditText1.getText().toString().trim();
        if (!data.isEmpty()) {
            databaseReference.setValue(data);
        }
    }

    public void readDataFromFirebase(View view) {
        //Called at the initial initialization of the value event listener wherever it
        // is initialized or if it is initialized inside some invoke method then it would be called only when such method would be invoked
        //This method will be invoked every time there is a change in data or a new data it is inserted add the reference node in the database
        // practice one time it's initial initialization and then every time the data is changed
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data=snapshot.getValue(String.class);
                if(!data.isEmpty()){
                    readFirebaseTextView1.setText(data);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}