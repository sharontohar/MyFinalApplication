package com.example.myfinalapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class donationsBoard extends AppCompatActivity implements View.OnClickListener, EventListener<QuerySnapshot> {
    private FirebaseFirestore firestore;
    private FloatingActionButton floBtnAdd;
    private ListView donationListView;
    private DonationAdapter adapter;
    private ArrayList<Donation> donationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_board);

        firestore = FirebaseFirestore.getInstance();
        donationArrayList = new ArrayList<Donation>();

        floBtnAdd = findViewById(R.id.floBtnAdd);
        donationListView = findViewById(R.id.listViewUpdates);
        adapter = new DonationAdapter(this, R.layout.donation_row, donationArrayList);

        donationListView.setAdapter(adapter);

        firestore
                .collection("updates")
                .addSnapshotListener(this);

        firestore.collection("updates")
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
                    Toast.makeText(donationsBoard.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        floBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == floBtnAdd){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_donation,null,false);
            Button btnAdd = dialogView.findViewById(R.id.btnAdd);
            EditText etName = dialogView.findViewById(R.id.etName);
            EditText etCount = dialogView.findViewById(R.id.etCount);
            EditText etLocation = dialogView.findViewById(R.id.etLocation);
            RadioGroup RbtnArea = dialogView.findViewById(R.id.RbtnArea);
            RbtnArea.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i == R.id.RbtnCentral) {
                        Toast.makeText(getApplicationContext(), "choice: Central",
                                Toast.LENGTH_SHORT).show();
                    } else if(i == R.id.RbtnNorth) {
                        Toast.makeText(getApplicationContext(), "choice: North",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "choice: South",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            RadioButton RbtnCentral = dialogView.findViewById(R.id.RbtnCentral);
            RadioButton RbtnNorth = dialogView.findViewById(R.id.RbtnNorth);
            RadioButton RbtnSouth = dialogView.findViewById(R.id.RbtnSouth);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etName.getText().toString();
                    Double count = Double.parseDouble(etCount.getText().toString());
                    String location = etLocation.getText().toString();
                    String area = "no";

                    int selectedId = RbtnArea.getCheckedRadioButtonId();
                    if(selectedId == RbtnCentral.getId()) {
                        area = "Central";
                    } else if(selectedId == RbtnNorth.getId()) {
                        area = "North";
                    } else {
                        area = "South";
                    }

                    if(name.isEmpty() || location.isEmpty() || count.equals(null) || area.equals("no")){
                        Toast.makeText(donationsBoard.this,"Please...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Donation donation = new Donation(name,count,location,area);
                        firestore.collection("donations")
                                .document(System.currentTimeMillis() + "")
                                .set(donation)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(donationsBoard.this,"added!",Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            Toast.makeText(donationsBoard.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
            builder.setView(dialogView);
            builder.create().show();
        }
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