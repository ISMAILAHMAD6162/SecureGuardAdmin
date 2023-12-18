package com.secure.secureguardadmin.site_managment_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguardadmin.Models.GuardId;
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.Models.SiteId;
import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;
import com.secure.secureguardadmin.guardManagment.GuardProfileAdapter;
import com.secure.secureguardadmin.guardManagment.GuardProfileRequestAdapter;

import java.util.ArrayList;
import java.util.List;

public class SiteAssignGuardActivity extends AppCompatActivity implements GuardAddInterface{



    private FirebaseFirestore db;
    private SiteGuardsAvaliableAdapter avaliableAdapter;
    private SiteaddedGuardAdapter addedAdapter;
    private RecyclerView aaliable_recyclview;
    private RecyclerView added_recyclview;
    private ArrayList<UserRecord> addedProfileList;
    private ArrayList<UserRecord> profileList;
    private  String siteId;


    private ArrayList<String> selectedGuardIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_assign_guard);
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        siteId=str;
        selectedGuardIds=new ArrayList<String>();
        addedProfileList = new ArrayList<UserRecord>();
        profileList = new ArrayList<UserRecord>();

        aaliable_recyclview = findViewById(R.id.aaliable_recyclview);

        avaliableAdapter = new SiteGuardsAvaliableAdapter(profileList, getApplicationContext(),this);

        aaliable_recyclview.setAdapter(avaliableAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        aaliable_recyclview.setLayoutManager(linearLayoutManager2);

        added_recyclview = findViewById(R.id.added_recyclview);
        addedAdapter = new SiteaddedGuardAdapter(addedProfileList, this,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        added_recyclview.setLayoutManager(linearLayoutManager);
        added_recyclview.setAdapter(addedAdapter);

        db = FirebaseFirestore.getInstance();
        getSelectedProfile();
        getGuardProfile();


    }

    public  void getSelectedProfile()
    {

        db.collection("SITEGUARDS").document(siteId).collection("Gaurds").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    if (queryDocumentSnapshots.size() > 0) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                        GuardId obj = document.toObject(GuardId.class);
                        selectedGuardIds.add(obj.guardId);

                    }

                }

                if(selectedGuardIds.size()>0)
                {
                    getGuardProfileBYId(selectedGuardIds);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }



    public void getGuardProfileBYId(List< String> prifileId)
    {

        if(selectedGuardIds.size()>0) {

            CollectionReference collectionReference = db.collection("UserRecord");

            collectionReference.whereIn(FieldPath.documentId(), prifileId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {

                                if (queryDocumentSnapshots.size() > 0) {

                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    UserRecord obj = document.toObject(UserRecord.class);
                                    addedProfileList.add(obj);
                                    addedAdapter.notifyDataSetChanged();


                                }
                            }
                        }
                    });
        }

    }




    public void getGuardProfile() {

        db.collection("UserRecord").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.size() > 0) {

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list) {

                        UserRecord obj = d.toObject(UserRecord.class);

                        Toast.makeText(getApplicationContext(), "Faild" + obj.getState(), Toast.LENGTH_LONG).show();

                        int k = Integer.parseInt(obj.getState().trim());
                        if (k !=10&&k!=0&&k!=1) {

                            profileList.add(obj);
                        }

                        avaliableAdapter.notifyDataSetChanged();

                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });


    }



    @Override
    public void add_Guard_Click(int index) {

       add(index);
    }

    @Override
    public void remove_Guard_Click(int index) {
        removed(index);

    }


    public void removed(int index)
    {
        db.collection("SITEGUARDS").document(siteId).collection("Gaurds").document(addedProfileList.get(index).getLicenceNumber()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                profileState(addedProfileList.get(index),index,2);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void add(int index)
    {
        GuardId guardId=new GuardId(profileList.get(index).getLicenceNumber());
        db.collection("SITEGUARDS").document(siteId).collection("Gaurds").document(profileList.get(index).getLicenceNumber()).set(guardId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                profileState(profileList.get(index),index,10);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    public void profileState(UserRecord userRecord, int index, int state) {

        userRecord.setState(String.valueOf(state));
        db.collection("UserRecord").document(userRecord.getLicenceNumber()).set(userRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


                if (state == 10) {

                    addedProfileList.add(userRecord);
                    profileList.remove(index);
                    addedAdapter.notifyDataSetChanged();
                    avaliableAdapter.notifyDataSetChanged();
                }
                if (state == 2)
                {
                    profileList.add(userRecord);
                    addedProfileList.remove(index);
                    addedAdapter.notifyDataSetChanged();
                    avaliableAdapter.notifyDataSetChanged();
                }




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}