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

public class GuardRegActivity extends AppCompatActivity {


    Button guardsRegSubmitButon;
   private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard_reg);
        db=FirebaseFirestore.getInstance();
        guardsRegSubmitButon=findViewById(R.id.guard_reg_submit);


        guardsRegSubmitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addGuardProfile();

            }
        });





    }

    public  void addGuardProfile()
    {
        CollectionReference guardProfileRef = db.collection("GuardProfile");

        Guard guard=new Guard("Ahmad","abc","wwewew",11,"111111d");

        guardProfileRef.document(guard.leicenNo).set(guard).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getApplicationContext(),"Added Succesfully",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"added not succesfully",Toast.LENGTH_LONG).show();
            }
        });


    }
}