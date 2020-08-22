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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.groupproject.GameActivity;
import edu.neu.madcourse.groupproject.R;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments.GEDDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.BeauticianDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.ConstructionDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.DataEntryDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.FastFoodDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.HackermanDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.PoetDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.PoliceOfficerDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments.SoftwareDeveloperDialog;

public class WorkFragment extends AbstractFragment implements WorkAdapter.OnItemListener{

    private static final String TAG = "WorkFragment";

    private View v;
    private RecyclerView recyclerView;
    private List<ModalClass> mList;
    private WorkAdapter customAdapter;

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
        mList.add(new ModalClass(R.mipmap.work_fast_food, "Fast Food Employee"));
        mList.add(new ModalClass(R.mipmap.work_data_entry, "Data Entry\n(requires GED)"));
        mList.add(new ModalClass(R.mipmap.work_construction, "Construction\n(requires GED)"));
        mList.add(new ModalClass(R.mipmap.work_beautician, "Beautician\n(requires Cosmetology\nLicense)"));
        mList.add(new ModalClass(R.mipmap.work_police, "Police Officer\n(requires Police Academy)"));
        mList.add(new ModalClass(R.mipmap.work_poet, "Poet\n(requires English Degree)"));
        mList.add(new ModalClass(R.mipmap.work_software_developer, "Software Developer\n(requires Comp Sci Degree)"));
        mList.add(new ModalClass(R.mipmap.work_hackerman, "Hacker Man\n(requires Comp Sci Degree)"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_work, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewWork);
        customAdapter = new WorkAdapter(mList, getContext(), this);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: ");
        //Toast.makeText(getContext(), "Item Clicked: " + position, Toast.LENGTH_SHORT).show();

        switch (position) {
            case 0:
                FastFoodDialog fastFoodDialog = new FastFoodDialog();
                fastFoodDialog.show(getFragmentManager(), "fast food dialog");
                break;
            case 1:
                DataEntryDialog dataEntryDialog = new DataEntryDialog();
                dataEntryDialog.show(getFragmentManager(), "data entry dialog");
                break;
            case 2:
                ConstructionDialog constructionDialog = new ConstructionDialog();
                constructionDialog.show(getFragmentManager(), "construction dialog");
                break;
            case 3:
                BeauticianDialog beauticianDialog = new BeauticianDialog();
                beauticianDialog.show(getFragmentManager(), "beautician dialog");
                break;
            case 4:
                PoliceOfficerDialog policeOfficerDialog = new PoliceOfficerDialog();
                policeOfficerDialog.show(getFragmentManager(), "police officer dialog");
                break;
            case 5:
                PoetDialog poetDialog = new PoetDialog();
                poetDialog.show(getFragmentManager(), "poet dialog");
                break;
            case 6:
                SoftwareDeveloperDialog softwareDeveloperDialog = new SoftwareDeveloperDialog();
                softwareDeveloperDialog.show(getFragmentManager(), "software developer dialog");
                break;
            case 7:
                HackermanDialog hackermanDialog = new HackermanDialog();
                hackermanDialog.show(getFragmentManager(), "hackerman dialog");
                break;
        }
    }

    @Override
    public void onButtonClick(int position) {
        Log.d(TAG, "onBUYClick: ");
        //Toast.makeText(getContext(), "Work Clicked: " + position, Toast.LENGTH_SHORT).show();

        switch (position) {
            case 0:
                updateFastFood();
                break;
            case 1:
                updateDataEntry();
                break;
            case 2:
                updateConstruction();
                break;
            case 3:
                updateBeautician();
                break;
            case 4:
                updatePoliceOfficer();
                break;
            case 5:
                updatePoet();
                break;
            case 6:
                updateSoftwareDeveloper();
                break;
            case 7:
                updateHackerman();
                break;
        }

    }

    public void updateFastFood() {
        if (energyCount >= 30 && moodCount >= 3) {
            energyCount -= 30;
            moneyCount += 80;
            hungerCount += 2;
            moodCount -= 3;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);
            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked at Mcdonnells", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 30 resource_energy, and 3 resource_mood to work at Mcdonnells", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateDataEntry() {
        if (gedProgress == 100 && energyCount >= 35 && hungerCount >= 2 && moodCount >= 2) {
            energyCount -= 35;
            moneyCount += 140;
            hungerCount -= 2;
            moodCount -= 2;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked at Data Entry", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED before working Data Entry", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 35 energy, 2 hunger, and 2 mood to work at Data Entry", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateConstruction() {
        if (gedProgress == 100 && energyCount >= 60 && hungerCount >= 5 && moodCount >= 2) {
            energyCount -= 60;
            moneyCount += 180;
            hungerCount -= 5;
            moodCount -= 2;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked a Construction job", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED before working a Construction job", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 60 energy, 5 hunger, and 2 mood to work Construction", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBeautician() {
        if (gedProgress == 100 && cosmetologyProgress == 100 && energyCount >= 40 && hungerCount >= 2 && moodCount >= 2) {
            energyCount -= 40;
            moneyCount += 180;
            hungerCount -= 2;
            moodCount -= 2;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked as a Beautician", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED and Cosmetology license before working as a Beautician", Toast.LENGTH_SHORT).show();
        }
        else if (cosmetologyProgress != 100) {
            Toast.makeText(getContext(), "Need a Cosmetology license before working as a Beautician", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 40 energy, 2 hunger, and 2 mood to work Construction", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePoliceOfficer() {
        if (gedProgress == 100 && policeProgress == 100 && energyCount >= 50 && hungerCount >= 3 && moodCount >= 3) {
            energyCount -= 50;
            moneyCount += 250;
            hungerCount -= 3;
            moodCount -= 3;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked a Police Officer shift", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED and graduation from Police Academy before working as a Police Officer", Toast.LENGTH_SHORT).show();
        }
        else if (policeProgress != 100) {
            Toast.makeText(getContext(), "Need graduation from Police Academy before working as a Police Officer", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 50 energy, 3 hunger, and 3 mood to work as a Police Officer", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePoet() {
        if (gedProgress == 100 && englishProgress == 100 && energyCount >= 35 && hungerCount >= 2 && moodCount >= 1) {
            energyCount -= 35;
            moneyCount += 150;
            hungerCount -= 2;
            moodCount -= 1;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked as a Poet", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED and English Degree before working as a Poet", Toast.LENGTH_SHORT).show();
        }
        else if (englishProgress != 100) {
            Toast.makeText(getContext(), "Need English Degree before working as a Poet", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 35 energy, 2 hunger, and 1 mood to work as a Poet", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateSoftwareDeveloper() {
        if (gedProgress == 100 && computerProgress == 100 && energyCount >= 45 && hungerCount >= 2 && moodCount >= 4) {
            energyCount -= 45;
            moneyCount += 400;
            hungerCount -= 2;
            moodCount -= 4;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked as a Software Developer", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED and Comp Sci Degree before working as a Software Developer", Toast.LENGTH_SHORT).show();
        }
        else if (computerProgress != 100) {
            Toast.makeText(getContext(), "Need Comp Sci Degree before working as a Software Developer", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 45 energy, 2 hunger, and 4 mood to work as a Software Developer", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateHackerman() {
        if (gedProgress == 100 && computerProgress == 100 && energyCount >= 45 && hungerCount >= 2 && moodCount >= 3) {
            energyCount -= 45;
            moneyCount += 300;
            hungerCount -= 2;
            moodCount -= 3;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setMoneyCount(moneyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("moneyCount", moneyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Worked as a Hackerman", Toast.LENGTH_SHORT).show();
        }
        else if (gedProgress != 100) {
            Toast.makeText(getContext(), "Need a GED and Comp Sci Degree before working as a Hackerman", Toast.LENGTH_SHORT).show();
        }
        else if (computerProgress != 100) {
            Toast.makeText(getContext(), "Need Comp Sci Degree before working as a Hackerman", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 45 energy, 2 hunger, and 3 mood to work as a Hackerman", Toast.LENGTH_SHORT).show();
        }
    }

}