package com.secure.secureguardadmin.shift_managment;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Shift_Activity extends AppCompatActivity {

    private TextView site_title;
    private Button add_new_shift;
    private List<String> shiftIdList;
    private ArrayList<Shift> shiftArrayListData;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        site_title = findViewById(R.id.site_title_shift);
        add_new_shift = findViewById(R.id.add_new_shift);
        shiftIdList=new ArrayList<String>();
        Intent intent = getIntent();
        String str = intent.getStringExtra("ID");
        db = FirebaseFirestore.getInstance();
        site_title.setText(str);
        add_new_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shift = new Intent(getApplicationContext(), ShiftRegActivity.class);
                shift.putExtra("ID", site_title.getText().toString());
                startActivity(shift);
            }
        });

        retrieveItemsForMonth(2023,5);

    }
    private void retrieveItemsForMonth(int year, int month) {

        CollectionReference siteRotaCollection=db.collection("SiteRota").document(site_title.getText().toString()).collection("Years");

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
                    fetchDataForDocumentIds(shiftIdList);
                } else {
                    Log.d("Firestore", "get failed with ", task.getException());
                }
            }
        });
    }




       public void fetchDataForDocumentIds(List< String> shiftIds) {
           // Reference to the collection
           // Replace "your_collection_name" with the actual name of your Firestore collection
           CollectionReference collectionReference = db.collection("Shift");

           // Use the 'whereIn' query to fetch documents based on an array of document IDs
           collectionReference.whereIn(FieldPath.documentId(), shiftIds)
                   .get()
                   .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                       @Override
                       public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                         //  Toast.makeText(getApplicationContext(), "get All shift with ID"+queryDocumentSnapshots.getDocuments(), Toast.LENGTH_LONG).show();

                           // Handle the query result
                           // queryDocumentSnapshots contains the documents matching the document IDs
                           for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                               // Access the data of each document

                               Toast.makeText(getApplicationContext(), "get All shift with ID LOOP COUNT"+document.getString("shiftId"), Toast.LENGTH_LONG).show();

                               // For example, String field = document.getString("field_name");
                           }
                       }
                   });
       }


}