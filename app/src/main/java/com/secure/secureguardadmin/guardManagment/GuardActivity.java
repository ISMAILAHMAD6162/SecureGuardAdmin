package com.secure.secureguardadmin.guardManagment;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguardadmin.Models.Site;
import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;
import com.secure.secureguardadmin.site_managment_activities.SiteRecycleViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GuardActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private  GuardProfileRequestAdapter guardProfileRequestAdapter;
    private RecyclerView guard_profile_recview;
    private RecyclerView guard_profile_req_recycleview;
    private ArrayList<UserRecord>  newProfileList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);
        guard_profile_req_recycleview=findViewById(R.id.new_profile_request);
        guardProfileRequestAdapter=new GuardProfileRequestAdapter(newProfileList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        guard_profile_req_recycleview.setLayoutManager(linearLayoutManager);
        guard_profile_req_recycleview.setAdapter(guardProfileRequestAdapter);

      ///  db=FirebaseFirestore.getInstance();

        //  getGuardProfile();
    }


    public void getGuardProfile() {

        db.collection("Site").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.size() > 0) {

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list) {

                        Site obj = d.toObject(Site.class);


                       // siteArrayList.add(obj);
                     //   siteRecycleViewAdapter.notifyDataSetChanged();

                    }

                   // siteRecycleViewAdapter.notifyDataSetChanged();
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

}