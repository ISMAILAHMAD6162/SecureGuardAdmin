package com.secure.secureguardadmin.site_managment_activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.secure.secureguardadmin.R;
import com.secure.secureguardadmin.shift_managment.ShiftRegActivity;
import com.secure.secureguardadmin.shift_managment.Shift_Activity;

public class Site_Managment_DashBoard_Activity extends AppCompatActivity {

    private TextView site_title;
    private Button start_shift,open_checkpoin_buton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_managment_dash_board);
        site_title=findViewById(R.id.sit_title_managment);
        start_shift=findViewById(R.id.start_shift);
        open_checkpoin_buton=findViewById(R.id.open_check_points);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        site_title.setText(str);

        start_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shift=new Intent(getApplicationContext(), Shift_Activity.class);
                shift.putExtra("ID",site_title.getText().toString());
                startActivity(shift);

            }
        });

        open_checkpoin_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"me me",Toast.LENGTH_LONG).show();
                Intent checkpoints=new Intent(getApplicationContext(),CheckPoint_Activity.class);
                checkpoints.putExtra("ID",site_title.getText().toString());
                 startActivity(checkpoints);

            }
        });



    }
}