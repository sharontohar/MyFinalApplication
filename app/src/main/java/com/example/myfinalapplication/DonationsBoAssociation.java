package com.example.myfinalapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DonationsBoAssociation extends AppCompatActivity implements EventListener<QuerySnapshot> {
    private FirebaseFirestore firestore;
    private ListView donationListView;
    private DonationAdapter adapter;
    private ArrayList<Donation> donationArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_bo_association);

        firestore = FirebaseFirestore.getInstance();
        donationArrayList = new ArrayList<Donation>();

        donationListView = findViewById(R.id.listViewUpdatesForAs);
        adapter = new DonationAdapter(DonationsBoAssociation.this, R.layout.donation_row, donationArrayList);

        donationListView.setAdapter(adapter);

        firestore
                .collection("donations")
                .addSnapshotListener(this);

        firestore.collection("donations")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<DocumentSnapshot> docList = task.getResult().getDocuments();
                    donationArrayList.clear();
                    for(DocumentSnapshot doc : docList){
                        Donation donation = new Donation(
                                doc.getString("name"),
                                doc.getDouble("count"),
                                doc.getString("location"),
                                doc.getString("area")
                        );
                        donationArrayList.add(donation);
                    }
                    adapter.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(DonationsBoAssociation.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
        List<DocumentSnapshot> docList = value.getDocuments();
        donationArrayList.clear();
        for(DocumentSnapshot doc : docList) {
            Donation donation = new Donation(
                    doc.getString("name"),
                    doc.getDouble("count"),
                    doc.getString("location"),
                    doc.getString("area")
            );
            donationArrayList.add(donation);
        }
        adapter.notifyDataSetChanged();
    }
}