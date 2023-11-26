package com.secure.secureguardadmin.site_managment_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.secure.secureguardadmin.R;

public class CheckPoint_Activity extends AppCompatActivity

{
    private Button add_checkPoints;
    private TextView site_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_point);
        site_title=findViewById(R.id.site_id);

        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        site_title.setText(str);
        add_checkPoints=findViewById(R.id.addCheckPoint);
        add_checkPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), CheckPointRegActivity.class);
                intent.putExtra("ID",site_title.getText().toString());
                startActivity(intent);

            }
        });
    }
}