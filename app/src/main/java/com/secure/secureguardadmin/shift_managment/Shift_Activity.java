package com.secure.secureguardadmin.shift_managment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.secure.secureguardadmin.R;

public class Shift_Activity extends AppCompatActivity {

    private TextView site_title;
    private Button add_new_shift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        site_title=findViewById(R.id.site_title_shift);
        add_new_shift=findViewById(R.id.add_new_shift);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        site_title.setText(str);

        add_new_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shift=new Intent(getApplicationContext(),ShiftRegActivity.class);
                shift.putExtra("ID",site_title.getText().toString());
                startActivity(shift);
            }
        });

    }
}