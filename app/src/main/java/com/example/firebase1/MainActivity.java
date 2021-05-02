package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
        //Assume that by getting the reference itself You are attaching a path of string and then when
        // you try to read value with their friends without attaching the same path over here as of the child is
        // concerned then even invoking the read method will not do so that is adding a new path in the database whereas for
        // the same if set value is used then a new path will be created with the above string path where the new value will be inserted
        databaseReference = firebaseDatabase.getReference();
        //At most instances get reference with a specified string path will
        // either generate if that particular path doesn't exist in the database for the reference
        // when data is set using set value Or vice versa this would be useful for specifying the part at getting the database reference itself
        pathForEntry = "";
        valueEventListener=new ValueEventListener() { //bcox such method would be called once over evry initialization or button click
            //without being called from database change as a part of initialization and returns null for above scanario
//Simply attaching a  child to a reference which does not exist in the real database and if you try to have valuable listener on it it will be of no use
// at that age it isn't create new path on the database
            //Called at the initial initialization of the value event listener wherever it
            // is initialized or if it is initialized inside some invoke method then it would be called only when such method would be invoked
            //This method will be invoked every time there is a change in data or a new data it is inserted add the reference node in the database
            // practice one time it's initial initialization and then every time the data is changed
//databaseReference.child("sel").addValueEventListener(new ValueEventListener() not possible if such ode dosent exists it will not create any new node sel

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String data = snapshot.getValue(String.class);
                //However specifying random parts that may or may not exist will cause some exception while reading the data from the
                // database if such situations of sub directories and child nodes are not handled
                if (!data.isEmpty()) {
                    readFirebaseTextView1.setText(data);
                    Log.d("Sel",data);
                    //for every press of readData press like for every press it reads data and for 3 presses u have 3 listeners attached on every new update on database
                    //3 times update on 3 different listeners will be called but when observed 2 times called but when u d
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        databaseReference.child("selman").addValueEventListener(valueEventListener);
    }


    private void initViews() {
        firebaseEditText1 = findViewById(R.id.FirebaseEditText1);
        sendToFirebaseButton = findViewById(R.id.SendToFirebaseButton);
        readFirebaseTextView1 = findViewById(R.id.FirebaseTextView1);
        readFromFirebaseButton = findViewById(R.id.ReadFirebaseButton);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseReference.child("selman").removeEventListener(valueEventListener);//if u have seperate listeners attached to seperate nodes
        //u need to explicitly remove them even though the childs listners is removed
    }

    public void sendDataToFirebase(View view) {
        String data = firebaseEditText1.getText().toString().trim();
        if (!data.isEmpty()) {
            //You can also add the value specifying the child as shown below if the child doesn't exist it would create the child note an active value
            //databaseReference.child("Specify the node").setValue......
            databaseReference.child("selman").setValue(data);
            //However attaching a fake child while setting the data would create a path
        }
    }

    public void readDataFromFirebase(View view) {
     // databaseReference.child("selman").addValueEventListener(valueEventListener); same as previous not a good practise so add during initFirebaseConfig and
        // remove on Destroy
    }
}