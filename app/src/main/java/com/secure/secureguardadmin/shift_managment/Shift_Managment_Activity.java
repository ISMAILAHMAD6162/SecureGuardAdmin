package com.secure.secureguardadmin.shift_managment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguardadmin.Models.Guard;
import com.secure.secureguardadmin.Models.GuardId;
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.Models.UserRecord;
import com.secure.secureguardadmin.R;
import com.secure.secureguardadmin.RecycleViewAdapters.GuardAssignmentAdapter;
import com.secure.secureguardadmin.RecycleViewAdapters.GuardAssignmentAdapter2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Shift_Managment_Activity extends AppCompatActivity implements AssignGuard{
    private RecyclerView recyclerView_Assigned;
    private RecyclerView recyclerView_Assigne;
    private GuardAssignmentAdapter assignmentAdapter;
    private GuardAssignmentAdapter2 assignmentAdapter2;
    private FirebaseFirestore db;
    private Button add_guard_on_shift;
    private ArrayList<UserRecord> addedProfileList;
    private ArrayList<UserRecord> profileList;
    private  String siteId;
    private String shiftId;
    private Shift shift;
    private ArrayList<String> selectedGuardIds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_managment);

        recyclerView_Assigne=findViewById(R.id.ava_recyclview);
        recyclerView_Assigned=findViewById(R.id.assigned_recyclview);

        addedProfileList=new ArrayList<UserRecord>();
        profileList=new ArrayList<UserRecord>();
        selectedGuardIds=new ArrayList<String>();

        assignmentAdapter=new GuardAssignmentAdapter(addedProfileList,this);
        assignmentAdapter2=new GuardAssignmentAdapter2();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Assigne.setLayoutManager(linearLayoutManager);

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView_Assigned.setLayoutManager(linearLayoutManager2);
        recyclerView_Assigned.setAdapter(assignmentAdapter2);

        recyclerView_Assigne.setAdapter(assignmentAdapter);
        add_guard_on_shift=findViewById(R.id.add_guard_on_shift);

        Intent intent = getIntent();
        if (intent.hasExtra("object")) {
            shift = (Shift) intent.getSerializableExtra("object");
            shiftId=shift.shiftId;
            siteId=shift.siteId;
         }

        db=FirebaseFirestore.getInstance();
        getSelectedProfile();


        add_guard_on_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShiftGuard("123456789");
            }
        });



    }


    public void addShiftGuard(String guardId)
    {


        Map<String, Object> data = new HashMap<>();
        data.put("Id", guardId);

            CollectionReference shiftCollectionRef=db.collection("SHIFTGUARD");

            shiftCollectionRef.document(shiftId).collection("GUARD").document(guardId).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {

                    Toast.makeText(getApplicationContext(),"Shift added",Toast.LENGTH_LONG).show();
                   updateGuardRota(Integer.parseInt(shift.year),Integer.parseInt(shift.month),Integer.parseInt(shift.day),shift.shiftId,guardId);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

    public void updateGuardRota(int year, int month, int day, String item,String guardId)
    {


        CollectionReference GuardRota=db.collection("GUARDROTA").document(guardId).collection("YEARS");

        DocumentReference dayDocument = GuardRota
                .document(String.valueOf(year))
                .collection("MONTHS")
                .document(String.valueOf(month))
                .collection("DAYS")
                .document(String.valueOf(day));

        dayDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@org.checkerframework.checker.nullness.qual.NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Document already exists, update the items
                        List<String> items = (List<String>) document.get("ShiftIds");
                        if (items == null) {
                            items = new ArrayList<>();
                        }
                        items.add(item);
                        Map<String, Object> data = new HashMap<>();
                        data.put("ShiftIds", items);
                        dayDocument.set(data);
                    } else {
                        // Document does not exist, create a new one with the item
                        List<String> items = new ArrayList<>();
                        items.add(item);
                        Map<String, Object> data = new HashMap<>();
                        data.put("ShiftIds", items);
                        dayDocument.set(data);
                    }
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }


            }
        });





       // Toast.makeText(getApplicationContext(),"Update Reqruied",Toast.LENGTH_LONG).show();

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
                                   assignmentAdapter.notifyDataSetChanged();


                                }
                            }
                        }
                    });
        }

    }


    @Override
    public void assign_guard_click(int index)
    {


        Toast.makeText(getApplicationContext(),"Shift added"+index,Toast.LENGTH_LONG).show();

          addShiftGuard(addedProfileList.get(index).getLicenceNumber());

    }
}