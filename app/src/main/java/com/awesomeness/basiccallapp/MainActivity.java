package com.awesomeness.basiccallapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    CardView dialCard, callCard;
    String wrongNumber = "+91273635262";
    int CALL_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialCard = findViewById(R.id.dial_card);
        callCard = findViewById(R.id.call_card);

        dialCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:"+wrongNumber));
                startActivity(dialIntent);
            }
        });

        callCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+wrongNumber));

                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    askForCallPermission();
                    return;
                }
                startActivity(callIntent);
            }
        });
    }

    public void askForCallPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                CALL_PERMISSION_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if(requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+wrongNumber));
                startActivity(callIntent);
            } else {
                Toast.makeText(MainActivity.this, "You cannot call without accepting this permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
