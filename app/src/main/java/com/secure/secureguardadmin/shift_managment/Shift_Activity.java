package com.secure.secureguardadmin.shift_managment;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.Models.ShiftID;
import com.secure.secureguardadmin.Models.Site;
import com.secure.secureguardadmin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Shift_Activity extends AppCompatActivity implements ShiftItemClickInterface{

   // private TextView site_title;
    private String site_id;
    private ImageView add_new_shift;
    private ShiftRecycelviewAdapter shiftRecycelviewAdapter;
    private RecyclerView shiftRecycleview;
    private List<String> shiftIdList;
    private ArrayList<Shift> shiftArrayListData;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
      //  site_title = findViewById(R.id.site_title_shift);
        add_new_shift = findViewById(R.id.add_new_shift);
        shiftArrayListData=new ArrayList<Shift>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        shiftRecycleview=findViewById(R.id.shift_item_recyclerview);
        shiftRecycleview.setLayoutManager(linearLayoutManager);
        shiftRecycelviewAdapter=new ShiftRecycelviewAdapter(shiftArrayListData,this::shiftItemClicklistne);
        shiftRecycleview.setAdapter(shiftRecycelviewAdapter);
        shiftIdList=new ArrayList<String>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        site_id=str;
        db = FirebaseFirestore.getInstance();

        add_new_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shift = new Intent(getApplicationContext(), ShiftRegActivity.class);
                shift.putExtra("ID", site_id.toString());
                startActivity(shift);
            }
        });
        retrieveItemsForMonth(2023,12);

    }



      private void retrieveItemsForMonth(int year, int month) {

        CollectionReference siteRotaCollection=db.collection("SiteRota").document(site_id).collection("Years");

        CollectionReference monthCollection = siteRotaCollection
                .document(String.valueOf(year))
                .collection("months")
                .document(String.valueOf(month)).collection("days");

        monthCollection.get().addOnCompleteListener(new OnCompleteListener<com.google.firebase.firestore.QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<com.google.firebase.firestore.QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot dayDocument : task.getResult().getDocuments()) {
                        List<String> items = (List<String>) dayDocument.get("items");
                        if (items != null) {
                            // Do something with the retrieved items for each day
                            for (String item : items) {

                                Toast.makeText(getApplicationContext(),"id "+item,Toast.LENGTH_LONG).show();
                                shiftIdList.add(item);
                            }

                        }
                    }
                    getShiftData(shiftIdList);
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }
            }
        });
    }

       public void getShiftData(List< String> shiftIds)
       {


           if(shiftIdList.size()>0) {


               CollectionReference collectionReference = db.collection("SHIFT");

               collectionReference.whereIn(FieldPath.documentId(), shiftIds)
                       .get()
                       .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                           @Override
                           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                               for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                   // Access the data of each document

                                   if (queryDocumentSnapshots.size() > 0) {

                                       List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                       shiftRecycelviewAdapter.notifyDataSetChanged();
                                       //
                                       Shift obj = document.toObject(Shift.class);
                                       shiftArrayListData.add(obj);
                                       shiftRecycelviewAdapter.notifyDataSetChanged();


                                   }
                               }
                           }
                       });
           }

       }



    @Override
    public void shiftItemClicklistne(int index) {



        Intent intent=new Intent(getApplicationContext(),Shift_Managment_Activity.class);
        intent.putExtra("object",shiftArrayListData.get(index));
        startActivity(intent);


    }

}