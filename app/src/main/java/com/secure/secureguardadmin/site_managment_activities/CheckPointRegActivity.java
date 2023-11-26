package com.secure.secureguardadmin.site_managment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.secure.secureguardadmin.Models.CheckPoint;
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.R;

public class CheckPointRegActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView site_title;
    private Button add_checkpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_point_reg);
        site_title=findViewById(R.id.site_title_reg);
        add_checkpoint=findViewById(R.id.add_checkpoint);
        db=FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        site_title.setText(str);
        add_checkpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
addCheckPoint(site_title.getText().toString());
            }
        });




    }

    public  void addCheckPoint(String site_id)
    {

        CheckPoint obj=new CheckPoint("2nd Door","50","23232","343434",0);


        CollectionReference siteCheckPointsCollection=db.collection("SiteCheckPoints");
        siteCheckPointsCollection.document(site_id).collection("Checks").document(obj.title).set(obj).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CheckPointRegActivity.this, "Check add succesfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckPointRegActivity.this, "Check add succesfully", Toast.LENGTH_SHORT).show();

            }
        });
        //code here

    }
}