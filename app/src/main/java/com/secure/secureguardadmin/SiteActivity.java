package com.secure.secureguardadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SiteActivity extends AppCompatActivity {


    Button add_guard_on_site,add_shift_on_site;
    ListView l;

    ArrayList<Site> siteArrayList;
    ArrayAdapter<Site> arr;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);


        siteArrayList=new ArrayList<Site>();

        l = findViewById(R.id.list);
        db=FirebaseFirestore.getInstance();
        getSiteData();
        arr = new ArrayAdapter<Site>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, siteArrayList);
        l.setAdapter(arr);

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //  Intent intent=new Intent(SiteActivity.this,SiteActivity.class);
               // startActivity(intent);

                Toast.makeText(getApplicationContext(),String.valueOf(position),Toast.LENGTH_LONG).show();

            }
        });



        add_guard_on_site=findViewById(R.id.add_guard_on_site);
        add_shift_on_site=findViewById(R.id.add_shift_on_site);
        add_shift_on_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SiteActivity.this,ShiftRegActivity.class);
                startActivity(intent);
            }
        });

        //get all profile here from GuardProfile Collection and show in Recyclview with detail and with add buton on view;


    }
    public  void getSiteData()
    {
        db.collection("Site").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list)
                {
                    // after getting this list we are passing
                    // that list to our object class.
                    Site obj = d.toObject(Site.class);
                    // and we will pass this object class
                    // inside our arraylist which we have
                    // created for recycler view.

                    siteArrayList.add(obj);
//                  l.notify();

                }

                // after adding the data to recycler view.
                // we are calling recycler view notifyDataSetChanged
                // method to notify that data has been changed in recycler view.

                arr.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),siteArrayList.get(0).title,Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });






}}