package com.secure.secureguardadmin.shift_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.secure.secureguardadmin.Models.Guard;
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shift_Managment_Activity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String shiftId;
    private Button add_guard_on_shift;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_managment);
        add_guard_on_shift=findViewById(R.id.add_guard_on_shift);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        shiftId=str;
        db=FirebaseFirestore.getInstance();


        add_guard_on_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShiftGuard("123456789");
            }
        });



    }


    public void addShiftGuard(String guardId)
    {


        Map<String, Object> data = new HashMap<>();
        data.put("Id", guardId);
            CollectionReference shiftCollectionRef=db.collection("SHIFTGUARD");

            shiftCollectionRef.document(shiftId).collection("GUARD").document(guardId).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    updateGuardRota(2023,11,29,shiftId,guardId);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });



        }

    public void updateGuardRota(int year, int month, int day, String item,String guardId)
    {


        CollectionReference GuardRota=db.collection("GUARDROTA").document(guardId).collection("YEARS");

        DocumentReference dayDocument = GuardRota
                .document(String.valueOf(year))
                .collection("MONTHS")
                .document(String.valueOf(month))
                .collection("DAYS")
                .document(String.valueOf(day));

        dayDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Document already exists, update the items
                        List<String> items = (List<String>) document.get("ShiftIds");
                        if (items == null) {
                            items = new ArrayList<>();
                        }
                        items.add(item);
                        Map<String, Object> data = new HashMap<>();
                        data.put("ShiftIds", items);
                        dayDocument.set(data);
                    } else {
                        // Document does not exist, create a new one with the item
                        List<String> items = new ArrayList<>();
                        items.add(item);
                        Map<String, Object> data = new HashMap<>();
                        data.put("ShiftIds", items);
                        dayDocument.set(data);
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }


            }
        });





        Toast.makeText(getApplicationContext(),"Update Reqruied",Toast.LENGTH_LONG).show();

    }

}