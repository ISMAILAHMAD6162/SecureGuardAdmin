package com.secure.secureguardadmin.shift_managment;

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
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.Models.ShiftID;
import com.secure.secureguardadmin.Models.SiteRota;
import com.secure.secureguardadmin.R;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftRegActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView site_title;
    public Button shift_reg_submit_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_reg);
        site_title=findViewById(R.id.shift_reg_act);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        site_title.setText(str);
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

        Shift objDuc=new Shift("22:00","07:00","2011","05","13",0,0,"shift7","eeeabc");


        CollectionReference shiftCollectionRef=db.collection("Shift");

        shiftCollectionRef.document(objDuc.shiftId).set(objDuc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {



                Toast.makeText(getApplicationContext(),"added succefulllu",Toast.LENGTH_LONG).show();
                addScheduleItem(2023,5,6,objDuc.shiftId);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(getApplicationContext(),"Not added succefulllu",Toast.LENGTH_LONG).show();

            }
        });


    }




    private void addScheduleItem(int year, int month, int day, String item) {


        CollectionReference siteRotaCollection=db.collection("SiteRota").document(site_title.getText().toString()).collection("Years");

        DocumentReference dayDocument = siteRotaCollection
                .document(String.valueOf(year))
                .collection("months")
                .document(String.valueOf(month))
                .collection("days")
                .document(String.valueOf(day));

        dayDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Document already exists, update the items
                        List<String> items = (List<String>) document.get("items");
                        if (items == null) {
                            items = new ArrayList<>();
                        }
                        items.add(item);
                        Map<String, Object> data = new HashMap<>();
                        data.put("items", items);
                        dayDocument.set(data);
                    } else {
                        // Document does not exist, create a new one with the item
                        List<String> items = new ArrayList<>();
                        items.add(item);
                        Map<String, Object> data = new HashMap<>();
                        data.put("items", items);
                        dayDocument.set(data);
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }


            }
        });
    }









    public void UpdateSiteRota()

    {

        SiteRota objDuc=new SiteRota("shift5","2023","12","3");

        ShiftID shiftID=new ShiftID("Shift5");
        CollectionReference siteRotaCollection=db.collection("SiteRota");

        siteRotaCollection.document(site_title.getText().toString()).collection("Year").document(objDuc.year).collection("Month").document(objDuc.month).collection("Days").document(objDuc.day).collection("ShiftId").document(objDuc.ShiftId).set(shiftID).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {



                Toast.makeText(getApplicationContext(),"Rota Update succefulllu",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(getApplicationContext(),"Not update succefulllu",Toast.LENGTH_LONG).show();

            }
        });


    }




}