package com.example.firebase1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText firebaseEditText1;
    private Button sendToFirebaseButton;
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
    }

    public void sendDataToFirebase(View view) {
        String data = firebaseEditText1.getText().toString().trim();
        if (!data.isEmpty()) {
            databaseReference.setValue(data);
        }
    }
}