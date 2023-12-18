package com.secure.secureguardadmin.guardManagment;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ViewGroup;
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

public class GuardActivity extends AppCompatActivity implements GuardProfileAcceptInterface {

    private ProgressDialog progressDialog;

    private FirebaseFirestore db;
    private GuardProfileRequestAdapter guardProfileRequestAdapter;
    private GuardProfileAdapter guardProfileAdapter;
    private RecyclerView guard_profile_recview;
    private RecyclerView guard_profile_req_recycleview;
    private ArrayList<UserRecord> newProfileList;
    private ArrayList<UserRecord> profileList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guard);




        newProfileList = new ArrayList<UserRecord>();
        profileList = new ArrayList<UserRecord>();

        guard_profile_recview = findViewById(R.id.guard_profile);

        guardProfileAdapter = new GuardProfileAdapter(profileList, getApplicationContext());

        guard_profile_recview.setAdapter(guardProfileAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        guard_profile_recview.setLayoutManager(linearLayoutManager2);

        guard_profile_req_recycleview = findViewById(R.id.new_profile_request);
        guardProfileRequestAdapter = new GuardProfileRequestAdapter(newProfileList, this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        guard_profile_req_recycleview.setLayoutManager(linearLayoutManager);
        guard_profile_req_recycleview.setAdapter(guardProfileRequestAdapter);

        showLoadingDialog();
        db = FirebaseFirestore.getInstance();

        getGuardProfile();
    }


    public void getGuardProfile() {

        db.collection("UserRecord").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.size() > 0) {

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list) {

                        UserRecord obj = d.toObject(UserRecord.class);

                       // Toast.makeText(getApplicationContext(), "Faild" + obj.getState(), Toast.LENGTH_LONG).show();

                        int k = Integer.parseInt(obj.getState().trim());
                        if (k == 1) {

                            newProfileList.add(obj);
                        } else {
                            if (k != 0)
                                profileList.add(obj);
                        }

                        guardProfileRequestAdapter.notifyDataSetChanged();
                        guardProfileAdapter.notifyDataSetChanged();
    dismissLoadingDialog();
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
    public void acceptClick(int position) {

        showLoadingDialog();
        profileState(newProfileList.get(position), position, 2);
    }

    @Override
    public void rejectClik(int position) {
        profileState(newProfileList.get(position), position, 0);
    }


    public void profileState(UserRecord userRecord, int index, int state) {
        userRecord.setState(String.valueOf(state));

        db.collection("UserRecord").document(userRecord.getLicenceNumber()).set(userRecord).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dismissLoadingDialog();
                if (state == 2) {
                    profileList.add(userRecord);
                    newProfileList.remove(index);
                    guardProfileRequestAdapter.notifyDataSetChanged();
                    guardProfileAdapter.notifyDataSetChanged();
                }
                if (state == 0) {
                    newProfileList.remove(index);
                    guardProfileRequestAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


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

}