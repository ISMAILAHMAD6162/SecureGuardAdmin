package com.secure.secureguardadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {


    Button regNewSiteButon,guardRegButon;
    ListView l;

   ArrayList<Site> siteArrayList;
    ArrayAdapter<Site> arr;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        siteArrayList=new ArrayList<Site>();
        regNewSiteButon=findViewById(R.id.reg_site);
        guardRegButon=findViewById(R.id.guar_profile_reg);



        guardRegButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),GuardRegActivity.class);
                startActivity(intent);
            }
        });



        l = findViewById(R.id.list);
        db=FirebaseFirestore.getInstance();

        getSiteData();


        arr = new ArrayAdapter<Site>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, siteArrayList);
        l.setAdapter(arr);







        regNewSiteButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SiteRegActivity.class);
                startActivity(intent);
            }
        });
    }


    public  void getSiteData()
    {
        db.collection("Site").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d : list) {
                    // after getting this list we are passing
                    // that list to our object class.
                    Site obj = d.toObject(Site.class);

                    // and we will pass this object class
                    // inside our arraylist which we have
                    // created for recycler view.
                    siteArrayList.add(obj);
//                    l.notify();

                }

                // after adding the data to recycler view.
                // we are calling recycler view notifyDataSetChanged
                // method to notify that data has been changed in recycler view.

                arr.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),queryDocumentSnapshots.getDocuments().toString(),Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
}