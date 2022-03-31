package com.example.ed_talk.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.ed_talk.AddInterviewExperience.AddInterviewExperienceActivity;
import com.example.ed_talk.R;
import com.example.ed_talk.Utils.SharedPrefManager;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment{
    LinearLayout shareInterviewExperience;

    private SharedPrefManager prefManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        shareInterviewExperience = (LinearLayout)root.findViewById(R.id.shareInterviewExperience);


        prefManager = new SharedPrefManager(getActivity());
        if(prefManager.isPopupDialogShown()==false){
            showReportedDialog();
        }

        shareInterviewExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AddInterviewExperienceActivity.class);
                startActivity(i);
            }
        });


        return root;
    }

    public void showReportedDialog() {
        prefManager.setPopupShown(true);

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.interview_exp_request_dialog, null, false);


        Button addButton = dialogView.findViewById(R.id.okBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}
