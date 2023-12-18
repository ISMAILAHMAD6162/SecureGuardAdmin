package com.secure.secureguardadmin.site_managment_activities;
import com.secure.secureguardadmin.Models.Site;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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



    private ProgressDialog progressDialog;
    private LinearLayout layoutAlert;
    ImageView add_site;
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
        siteRecycleViewAdapter=new SiteRecycleViewAdapter(siteArrayList,this,this);
        site_Item_RecycleView.setAdapter(siteRecycleViewAdapter);


        db=FirebaseFirestore.getInstance();
        showLoadingDialog();
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

                        Site obj = d.toObject(Site.class);
                        siteArrayList.add(obj);
                        siteRecycleViewAdapter.notifyDataSetChanged();

                    }

                       dismissLoadingDialog();
                    siteRecycleViewAdapter.notifyDataSetChanged();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            //    Toast.makeText(getApplicationContext(),"Faild",Toast.LENGTH_LONG).show();

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

    @Override
    public void alertNotifcation(int index) {

    }

    @Override
    public void sos_Alert_Click(int index) {

        showAlertDialog();
    }


    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false); // Set to true if you want the dialog to be cancelable
        progressDialog.show();
    }

    private void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private void showAlertDialog() {
        // Create an AlertDialog Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title and message for the dialog box
        builder.setTitle("Alert Sending")
                .setMessage("Your Alert Deliver to all secure guards");

        // Set positive button and its click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the "OK" button is clicked
                dialog.dismiss();
            }
        });

        /*
        // Set negative button and its click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do something when the "Cancel" button is clicked
                dialog.dismiss();
            }
        });

         */

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}