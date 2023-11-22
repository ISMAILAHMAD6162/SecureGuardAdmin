package com.secure.secureguardadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SplashScreen_Activity extends AppCompatActivity {


    Button spashscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        spashscreen=findViewById(R.id.splashscrrenbuto);
        spashscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Intent intent=new Intent(SplashScreen_Activity.this,Login_Activity.class);
                startActivity(intent);


            }
        });
    }

}