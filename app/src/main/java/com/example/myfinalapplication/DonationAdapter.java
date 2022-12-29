package com.example.myfinalapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DonationAdapter extends ArrayAdapter<Donation> {
    private Context context;
    private ArrayList<Donation> donationArrayList;
    public DonationAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Donation> updateArrayList) {
        super(context, resource, updateArrayList);
        this.context = context;
        this.donationArrayList = updateArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_row, null,false);
        Donation update1 = donationArrayList.get(position);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvCount = view.findViewById(R.id.tvCount);
        TextView tvLocation = view.findViewById(R.id.tvLocation);
        TextView tvArea = view.findViewById(R.id.tvArea);

        tvName.setText(update1.getName());
        tvCount.setText(update1.getCount() + " ");
        tvLocation.setText(update1.getLocation());
        tvArea.setText(update1.getArea());

        return view;
    }
}
