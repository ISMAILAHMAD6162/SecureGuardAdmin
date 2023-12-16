package com.secure.secureguardadmin.site_managment_activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
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
import com.secure.secureguardadmin.Models.Shift;
import com.secure.secureguardadmin.R;
import com.secure.secureguardadmin.shift_managment.ShiftRegActivity;
import com.secure.secureguardadmin.shift_managment.ShiftReyceviewAdapter;
import com.secure.secureguardadmin.shift_managment.Shift_Activity;

import java.util.ArrayList;
import java.util.List;

public class Site_Managment_DashBoard_Activity extends AppCompatActivity {


    private ShiftReyceviewAdapter shiftReyceviewAdapter;
    private RecyclerView shiftRecycleview;
    private List<String> shiftIdList;
    private ArrayList<Shift> shiftArrayListData;
    private TextView site_title;
    private String siteId;
    private FirebaseFirestore db;
    private TextView start_shift,open_checkpoin_buton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_managment_dash_board);
        site_title=findViewById(R.id.sit_title_managment);
        start_shift=findViewById(R.id.start_shift);
        open_checkpoin_buton=findViewById(R.id.open_check_points);
        Intent intent = getIntent();
        siteId = intent.getStringExtra("ID");
        site_title.setText(siteId);
        db=FirebaseFirestore.getInstance();
        shiftArrayListData=new ArrayList<Shift>();
        shiftIdList=new ArrayList<String>();

        shiftReyceviewAdapter=new ShiftReyceviewAdapter(shiftArrayListData);
        shiftRecycleview=findViewById(R.id.shift_item_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        shiftRecycleview.setLayoutManager(linearLayoutManager);
        shiftRecycleview.setAdapter(shiftReyceviewAdapter);
        getTodayShift();

        start_shift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shift=new Intent(getApplicationContext(), Shift_Activity.class);
                shift.putExtra("ID",site_title.getText().toString());
                startActivity(shift);

            }
        });

        open_checkpoin_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"me me",Toast.LENGTH_LONG).show();
                Intent checkpoints=new Intent(getApplicationContext(),CheckPoint_Activity.class);
                checkpoints.putExtra("ID",site_title.getText().toString());
                 startActivity(checkpoints);

            }
        });



    }


    public void getTodayShift()
    {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CollectionReference siteRotaCollection=db.collection("SiteRota").document(siteId).collection("Years");
        DocumentReference dayDocument = siteRotaCollection
                .document(String.valueOf(year))
                .collection("months")
                .document(String.valueOf(month))
                .collection("days")
                .document(String.valueOf(day));

        dayDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {



                DocumentSnapshot d =task.getResult();
                Toast.makeText(getApplicationContext(), "ABC outer"+d.getData() , Toast.LENGTH_LONG).show();
                if(d.exists())
                {

                    List<String> items = (List<String>) d.get("items");
                    if (items != null) {
                        // Do something with the retrieved items for each day
                        for (String item : items) {

                            shiftIdList.add(item);

                        }

                        getShiftData(shiftIdList);
                        {

                        }

                    }

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }


    public void getShiftData(List< String> shiftIds)
    {

        if (shiftIdList!=null) {

            if (shiftIdList.size() > 0) {

                // Reference to the collection
                // Replace "your_collection_name" with the actual name of your Firestore collection
                CollectionReference collectionReference = db.collection("SHIFT");

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

                                    if (queryDocumentSnapshots.size() > 0) {

                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                                        shiftReyceviewAdapter.notifyDataSetChanged();
                                        //
                                        Shift obj = document.toObject(Shift.class);
                                        shiftArrayListData.add(obj);
                                        shiftReyceviewAdapter.notifyDataSetChanged();

                                        //  Toast.makeText(getApplicationContext(), "get All shift with ID LOOP COUNT" + document.getString("shiftId"), Toast.LENGTH_LONG).show();

                                    }
                                    // For example, String field = document.getString("field_name");
                                }
                            }
                        });
            }
        }
    }
}