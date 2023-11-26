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


    private String siteId;
    private FirebaseFirestore db;
    public Button shift_reg_submit_buton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_reg);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        siteId=str;
        shift_reg_submit_buton=findViewById(R.id.shift_reg_submit_buton);
        db=FirebaseFirestore.getInstance();

        shift_reg_submit_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //outDev
                //outDev
                //outDev
                //outDev
                /*
                   need to pass two thing to this function of type string ,one uniquely generated id for shift , and site id which can be get from variable site id which is already just use it
                   1. state will be always zero
                   2. guards no will be Always zero

                   rest have to get from front after validations and pass this add shift function
                 */

                addShift("100",siteId,"7:00","19:00","2023","11","29",0,0);
            }
        });
    }



    public void addShift(String shiftId,String siteId,String startTime,String endTime,String year,String month,String day,int state,int guardNo)

    {

        // SHIFT ID WILL BE GET FROM PARAMATER OF THIS FUN

        Shift objDuc=new Shift(startTime,endTime,year,month,day,state,guardNo,shiftId,siteId);

        CollectionReference shiftCollectionRef=db.collection("SHIFT");

        shiftCollectionRef.document(objDuc.shiftId).set(objDuc).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                updateSiteRota(objDuc.year,objDuc.month,objDuc.day,objDuc.shiftId);

                //OUT DEV
                //OUT DEV
                Toast.makeText(getApplicationContext(), "shift added successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


                Toast.makeText(getApplicationContext(), "shift not added successfully", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void updateSiteRota(String year, String month, String day, String shiftId) {


        CollectionReference siteRotaCollection=db.collection("SiteRota").document(siteId).collection("Years");
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
                        items.add(shiftId);
                        Map<String, Object> data = new HashMap<>();
                        data.put("items", items);
                        dayDocument.set(data);
                    } else {
                        // Document does not exist, create a new one with the item
                        List<String> items = new ArrayList<>();
                        items.add(shiftId);
                        Map<String, Object> data = new HashMap<>();
                        data.put("items", items);
                        dayDocument.set(data);
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception e) {

            }
        });
    }













}