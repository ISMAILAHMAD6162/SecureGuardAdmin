package com.secure.secureguardadmin.site_managment_activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.secure.secureguardadmin.Models.Site;
import com.secure.secureguardadmin.R;

public class SiteRegActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private Button submit_site_reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_reg);
        db = FirebaseFirestore.getInstance();

        submit_site_reg=findViewById(R.id.reg_site_submit);
        submit_site_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addSite();
            }
        });



    }



    public void addSite()
    {
        // creating a collection reference
        // for our Firebase Firestore database.
        CollectionReference siteRef = db.collection("Site");

        // adding our data to our courses object class.
        Site site = new Site("Holidays","Ahmad","10000","900000",0,"Ahmad","ahmad@gmail.com","07868209729","ccccw","10-11-2023","20-12-2024");

        // below method is use to add data to Firebase Firestore.
        siteRef.document(site.title).set(site).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getApplicationContext(),"Site Added",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(getApplicationContext(),"Site Added Faild",Toast.LENGTH_LONG).show();

        });

    }



}