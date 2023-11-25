package com.secure.secureguardadmin.site_managment_activities;
import com.secure.secureguardadmin.Models.Site;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguardadmin.Models.Site;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;
import java.util.List;

public class SiteActivity extends AppCompatActivity implements SiteItemClick{


    Button add_site;
    private RecyclerView site_Item_RecycleView;
    private SiteRecycleViewAdapter siteRecycleViewAdapter;
    private ArrayList<Site> siteArrayList;

    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site);
        site_Item_RecycleView=findViewById(R.id.site_item_recyclerview);
        add_site=findViewById(R.id.add_new_site);
        siteArrayList=new ArrayList<Site>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        site_Item_RecycleView.setLayoutManager(linearLayoutManager);
        siteRecycleViewAdapter=new SiteRecycleViewAdapter(siteArrayList,this::onClick);
        site_Item_RecycleView.setAdapter(siteRecycleViewAdapter);


        db=FirebaseFirestore.getInstance();
        getSiteData();



        add_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent site=new Intent(getApplicationContext(),SiteRegActivity.class);
                startActivity(site);
            }
        });



    }
    public  void getSiteData()
    {
        db.collection("Site").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.size() > 0) {

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        Site obj = d.toObject(Site.class);
                        // and we will pass this object class
                        // inside our arraylist which we have
                        // created for recycler view.

                        siteArrayList.add(obj);
                        siteRecycleViewAdapter.notifyDataSetChanged();

                    }

                    // after adding the data to recycler view.
                    // we are calling recycler view notifyDataSetChanged
                    // method to notify that data has been changed in recycler view.

                    siteRecycleViewAdapter.notifyDataSetChanged();
                 //   Toast.makeText(getApplicationContext(), siteArrayList.get(0).title, Toast.LENGTH_LONG).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Faild",Toast.LENGTH_LONG).show();

            }
        });






}


    @Override
    public void onClick(int index) {

        Intent site_managment=new Intent(getApplicationContext(),Site_Managment_DashBoard_Activity.class);
        site_managment.putExtra("ID",siteArrayList.get(index).title);
        startActivity(site_managment);
        //Toast.makeText(getApplicationContext(),"INDEX" +index,Toast.LENGTH_LONG).show();
    }
}