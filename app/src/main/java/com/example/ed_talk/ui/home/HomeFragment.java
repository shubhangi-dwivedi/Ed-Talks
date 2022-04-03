package com.example.ed_talk.ui.home;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ed_talk.AddInterviewExperience.AddInterviewExperienceActivity;
import com.example.ed_talk.R;
import com.example.ed_talk.Utils.SharedPrefManager;
import com.example.ed_talk.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    LinearLayout shareInterviewExperience;

    private SharedPrefManager prefManager;

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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