package com.sujan.ledcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

public class MainActivity extends AppCompatActivity {
    ImageButton imageButton;
    Button button;
    int imageId;
    public String callBack="", status = "";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imageButton = findViewById(R.id.imageButton);
        button = findViewById(R.id.button);
        checkStatus();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getData();
                if (callBack.equals("OFF")) {

                    button.setText("OFF");
                    imageId = R.drawable.on;
                    setData();

                    loadImage();

                } else if (callBack.equals("ON")) {

                    button.setText("ON");
                    imageId = R.drawable.off;
                    setData();

                    loadImage();


                }

            }
        });

    }


    public void loadImage() {

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), imageId);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 250, 250, true);
        imageButton.setImageBitmap(bMapScaled);
    }

    public void getData() {

        ValueEventListener postListener = new ValueEventListener() {




            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Led").child("Callback").getChildren()) {
                    callBack = dataSnapshot1.getValue().toString();
                    Toast.makeText(MainActivity.this, ""+callBack, Toast.LENGTH_LONG).show();

                }
               // String callBack = dataSnapshot.child("Led").child("Callback").child("/String").toString();
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

            mDatabase.addValueEventListener(postListener);


    }

    public void setData() {

        if (button.getText().toString().equals("OFF")) {
            status = "ON";
        } else if (button.getText().toString().equals("ON")) {
            status = "OFF";
        } else {
            status = "OFF";
        }
        mDatabase.child("Led").child("Status").child("/String").setValue(status);

    }

    public void checkStatus() {
         getData();
          //   Toast.makeText(this, ""+getData, Toast.LENGTH_SHORT).show();
            if (callBack.equals("OFF")) {
                status = "OFF";
                button.setText("ON");
                imageId = R.drawable.off;
            } else if(callBack.equals("ON")) {
                status = "ON";
                button.setText("OFF");
                imageId = R.drawable.on;
            }
            else {
                status = "OFF";
                button.setText("ON");
                imageId = R.drawable.off;
            }
            loadImage();
    }

//    public boolean isOnline() {
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        return (networkInfo != null && networkInfo.isConnected());
//    }




}
