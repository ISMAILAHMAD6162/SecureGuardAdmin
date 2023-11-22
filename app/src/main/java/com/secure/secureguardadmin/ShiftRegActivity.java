package com.secure.secureguardadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ShiftRegActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    public Button shift_reg_submit_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_reg);
        shift_reg_submit_buton=findViewById(R.id.shift_reg_submit_buton);
        db=FirebaseFirestore.getInstance();

        shift_reg_submit_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addShift();
            }
        });
    }


    public void addShift()

    {

        Shift objDuc=new Shift("22:00","07:00","2011","05","13",0,0,"bcd","abc");


        CollectionReference shiftCollectionRef=db.collection("Shift");

        shiftCollectionRef.document(objDuc.siteId).collection("ShiftID").document(objDuc.shiftId).set(objDuc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                Toast.makeText(getApplicationContext(),"added succefulllu",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(getApplicationContext(),"Not added succefulllu",Toast.LENGTH_LONG).show();

            }
        });


    }



}