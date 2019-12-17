package com.sujan.ledcontrol;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class SplashActivity extends AppCompatActivity {

    public String callBack="";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();

      getData();
    }



    public void getData() {

        ValueEventListener postListener = new ValueEventListener() {




            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Led").child("Callback").getChildren()) {
                    callBack = dataSnapshot1.getValue().toString();
                    Toast.makeText(SplashActivity.this, ""+callBack, Toast.LENGTH_LONG).show();

                }
                // String callBack = dataSnapshot.child("Led").child("Callback").child("/String").toString();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.addValueEventListener(postListener);


    }


}
