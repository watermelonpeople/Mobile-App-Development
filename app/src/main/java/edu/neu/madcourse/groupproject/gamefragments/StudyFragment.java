package edu.neu.madcourse.groupproject.gamefragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.groupproject.GameActivity;
import edu.neu.madcourse.groupproject.R;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.BookDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments.ComputerDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments.CosmetologyDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments.EnglishDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments.GEDDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments.PoliceAcademyDialog;

public class StudyFragment extends AbstractFragment implements StudyAdapter.OnItemListener{

    private static final String TAG = "StudyFragment";

    private View v;
    private RecyclerView recyclerView;
    private List<ModalClass> mList;
    private StudyAdapter customAdapter;
    private ProgressBar progressBar;

    private int energyCount;
    private int hungerCount;
    private int moneyCount;
    private int moodCount;

    private int gedProgress;
    private int cosmetologyProgress;
    private int policeProgress;
    private int englishProgress;
    private int computerProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
        energyCount = pref.getInt("energyCount", 0);
        hungerCount = pref.getInt("hungerCount", 50);
        moneyCount = pref.getInt("moneyCount", 100);
        moodCount = pref.getInt("moodCount", 50);

        gedProgress = pref.getInt("gedProgress", 0);
        cosmetologyProgress = pref.getInt("cosmetologyProgress", 0);
        policeProgress = pref.getInt("policeProgress", 0);
        englishProgress = pref.getInt("englishProgress", 0);
        computerProgress = pref.getInt("computerProgress", 0);


        mList = new ArrayList<>();
        mList.add(new ModalClass(R.mipmap.study_ged, "GED", gedProgress));
        mList.add(new ModalClass(R.mipmap.study_cosmetology, "Cosmetology License\n(requires GED)", cosmetologyProgress));
        mList.add(new ModalClass(R.mipmap.study_police, "Police Academy\n(requires GED)", policeProgress));
        mList.add(new ModalClass(R.mipmap.study_english, "Bach. English\n(requires GED)", englishProgress));
        mList.add(new ModalClass(R.mipmap.study_computer, "Bach. Comp Sci\n(requires GED)", computerProgress));


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_study, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewStudy);
        customAdapter = new StudyAdapter(mList, getContext(), this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return v;
    }

    @Override
    public void onItemClick(int position) {
        //mList.get(position);
        Log.d(TAG, "onItemClick: ");
        //Toast.makeText(getContext(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

        switch (position) {
            case 0:
                GEDDialog gedDialog = new GEDDialog();
                gedDialog.show(getFragmentManager(), "ged dialog");
                break;
            case 1:
                CosmetologyDialog cosmetologyDialog = new CosmetologyDialog();
                cosmetologyDialog.show(getFragmentManager(), "cosmetology dialog");
                break;
            case 2:
                PoliceAcademyDialog policeAcademyDialog = new PoliceAcademyDialog();
                policeAcademyDialog.show(getFragmentManager(), "police academy dialog");
                break;
            case 3:
                EnglishDialog englishDialog = new EnglishDialog();
                englishDialog.show(getFragmentManager(), "english dialog");
                break;
            case 4:
                ComputerDialog computerDialog = new ComputerDialog();
                computerDialog.show(getFragmentManager(), "computer dialog");
                break;
        }
    }

    @Override
    public void onButtonClick(int position) {
        Log.d(TAG, "onBUYClick: ");
        //Toast.makeText(getContext(), "Study Clicked: " + position, Toast.LENGTH_SHORT).show();

        switch (position) {
            //G.E.D
            case 0:
                updateGED();
                break;
            case 1:
                updateCosmetology();
                break;
            case 2:
                updatePoliceAcademy();
                break;
            case 3:
                updateEnglish();
                break;
            case 4:
                updateComputer();
                break;
        }

    }

    public void updateGED() {
        if (gedProgress < 100 && energyCount >= 10 && hungerCount >= 1 && moodCount >= 1) {
            energyCount -= 10;
            hungerCount -= 1;
            moodCount -= 1;

            gedProgress += 1;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("gedProgress", gedProgress);
            editor.putInt("energyCount", energyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            //adding to our progressBar
            View itemView = recyclerView.getChildAt(0);
            progressBar = itemView.findViewById(R.id.progress_horizontal);
            progressBar.setProgress(gedProgress);



            Toast.makeText(getContext(), "GED Progress: " + gedProgress, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "GED progress increased", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress == 100) {
            Toast.makeText(getContext(), "You already have a GED", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 10 energy, 1 hunger, and 1 mood to pursue GED", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCosmetology() {
        if (gedProgress == 100 && cosmetologyProgress < 100 && energyCount >= 15 && moneyCount >= 300 && hungerCount >= 1 && moodCount >= 1) {
            energyCount -= 15;
            moneyCount -= 300;
            hungerCount -= 1;
            moodCount -= 1;
            cosmetologyProgress += 1;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);
            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("cosmetologyProgress", cosmetologyProgress);
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            //adding to our progressBar
            View itemView = recyclerView.getChildAt(1);
            progressBar = itemView.findViewById(R.id.progress_horizontal);
            progressBar.setProgress(cosmetologyProgress);



            Toast.makeText(getContext(), "Cosmetology Progress: " + cosmetologyProgress, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "Cosmetology progress increased", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED before pursuing a Cosmetology License", Toast.LENGTH_SHORT).show();
        }
        else if (cosmetologyProgress == 100) {
            Toast.makeText(getContext(), "You already have a Cosmetology License", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 15 energy, 300 money, 1 hunger, and 1 mood to pursue Cosmetology License", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePoliceAcademy() {
        if (gedProgress == 100 && policeProgress < 100 && energyCount >= 20 && moneyCount >= 400 && hungerCount >= 2 && moodCount >= 2) {
            energyCount -= 20;
            moneyCount -= 400;
            hungerCount -= 2;
            moodCount -= 2;
            policeProgress += 1;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);
            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("policeProgress", policeProgress);
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            //adding to our progressBar
            View itemView = recyclerView.getChildAt(2);
            progressBar = itemView.findViewById(R.id.progress_horizontal);
            progressBar.setProgress(policeProgress);



            Toast.makeText(getContext(), "Police Academy Progress: " + policeProgress, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "Police progress increased", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED before pursuing a Police License", Toast.LENGTH_SHORT).show();
        }
        else if (policeProgress == 100) {
            Toast.makeText(getContext(), "You already have a Police License", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 20 energy, 400 money, 2 hunger, and 2 mood to pursue Police License", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateEnglish() {
        if (gedProgress == 100 && englishProgress < 100 && energyCount >= 15 && moneyCount >= 600 && hungerCount >= 1 && moodCount >= 1) {
            energyCount -= 15;
            moneyCount -= 600;
            hungerCount -= 1;
            moodCount -= 1;
            englishProgress += 1;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);
            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("englishProgress", englishProgress);
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            //adding to our progressBar
            View itemView = recyclerView.getChildAt(3);
            progressBar = itemView.findViewById(R.id.progress_horizontal);
            progressBar.setProgress(englishProgress);



            Toast.makeText(getContext(), "English Degree Progress: " + englishProgress, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "English Progress increased", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED before pursuing an English Degree", Toast.LENGTH_SHORT).show();
        }
        else if (englishProgress == 100) {
            Toast.makeText(getContext(), "You already have an English Degree", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 15 energy, 600 money, 1 hunger, and 1 mood to pursue English Degree", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateComputer() {
        if (gedProgress == 100 && computerProgress < 100 && energyCount >= 15 && moneyCount >= 600 && hungerCount >= 1 && moodCount >= 4) {
            energyCount -= 15;
            moneyCount -= 600;
            hungerCount -= 1;
            moodCount -= 4;
            computerProgress += 1;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);
            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("computerProgress", computerProgress);
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            //adding to our progressBar
            View itemView = recyclerView.getChildAt(4);
            progressBar = itemView.findViewById(R.id.progress_horizontal);
            progressBar.setProgress(computerProgress);



            Toast.makeText(getContext(), "Comp Sci Degree Progress: " + computerProgress, Toast.LENGTH_SHORT).show();
            //Toast.makeText(getContext(), "Comp Sci Progress increased", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED before pursuing a Comp Sci Degree", Toast.LENGTH_SHORT).show();
        }
        else if (computerProgress == 100) {
            Toast.makeText(getContext(), "You already have a Comp Sci Degree", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 15 energy, 600 money, 1 hunger, and 4 mood to pursue Comp Sci Degree", Toast.LENGTH_SHORT).show();
        }
    }


}