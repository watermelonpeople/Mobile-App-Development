package edu.neu.madcourse.groupproject.gamefragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.groupproject.R;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.BookDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.ConcertDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.CruiseDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.EatMcdonnellsDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.RestaurantDialog;
import edu.neu.madcourse.groupproject.gamefragments.dialogfragments.restrelaxfragments.SomaDialog;

public class RestRelaxFragment extends AbstractFragment implements RestRelaxAdapter.OnItemListener {

    private static final String TAG = "RestRelaxFragment";

    private int energyCount;
    private int hungerCount;
    private int moneyCount;
    private int moodCount;

    private View v;
    private RecyclerView recyclerView;
    private List<ModalClass> mList;
    private RestRelaxAdapter mRestRelaxAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
        energyCount = pref.getInt("energyCount", 0);
        hungerCount = pref.getInt("hungerCount", 50);
        moneyCount = pref.getInt("moneyCount", 100);
        moodCount = pref.getInt("moodCount", 50);

        mList = new ArrayList<>();
        mList.add(new ModalClass(R.mipmap.rest_relax_book, "Read a book"));
        mList.add(new ModalClass(R.mipmap.rest_relax_mcdonnells, "Eat at Mcdonnells"));
        mList.add(new ModalClass(R.mipmap.rest_relax_concert, "Visit Concert"));
        mList.add(new ModalClass(R.mipmap.rest_relax_restaurant, "Eat at Restaurant"));
        mList.add(new ModalClass(R.mipmap.rest_relax_cruise, "International Cruise"));
        mList.add(new ModalClass(R.mipmap.rest_relax_soma, "Take 'Soma'"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_rest_relax, container, false);

        recyclerView = v.findViewById(R.id.recyclerViewRestRelax);
        mRestRelaxAdapter = new RestRelaxAdapter(mList, getContext(), this);
        recyclerView.setAdapter(mRestRelaxAdapter);
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
                BookDialog bookDialog = new BookDialog();
                bookDialog.show(getFragmentManager(), "book dialog");
                break;
            case 1:
                EatMcdonnellsDialog eatMcdonnellsDialog = new EatMcdonnellsDialog();
                eatMcdonnellsDialog.show(getFragmentManager(), "eat mcdonnells dialog");
                break;
            case 2:
                ConcertDialog concertDialog = new ConcertDialog();
                concertDialog.show(getFragmentManager(), "concert dialog");
                break;
            case 3:
                RestaurantDialog restaurantDialog = new RestaurantDialog();
                restaurantDialog.show(getFragmentManager(), "restaurant dialog");
                break;
            case 4:
                CruiseDialog cruiseDialog = new CruiseDialog();
                cruiseDialog.show(getFragmentManager(), "cruise dialog");
                break;
            case 5:
                SomaDialog somaDialog = new SomaDialog();
                somaDialog.show(getFragmentManager(), "soma dialog");
                break;
        }
    }

    @Override
    public void onButtonClick(int position) {
        Log.d(TAG, "onBUYClick: ");
        //Toast.makeText(getContext(), "Buy Clicked: " + position, Toast.LENGTH_SHORT).show();

        switch (position) {
            //G.E.D
            case 0:
                updateBook();
                break;
            case 1:
                updateMcdonnells();
                break;
            case 2:
                updateConcert();
                break;
            case 3:
                updateRestaurant();
                break;
            case 4:
                updateCruise();
                break;
            case 5:
                updateSoma();
                break;
        }


    }


    public void updateBook() {
        if (energyCount >= 10 && hungerCount >= 1) {
            energyCount -= 10;
            hungerCount -= 1;
            moodCount += 4;

            //energyCount, hungerCount, moodCount are stored in GameActivity
            setEnergyCount(energyCount);
            setHungerCount(hungerCount);
            setMoodCount(moodCount);

            //gedProgress is stored in StudyFragment
            SharedPreferences pref = getContext().getSharedPreferences("DAY_STEPS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("energyCount", energyCount);
            editor.putInt("hungerCount", hungerCount);
            editor.putInt("moodCount", moodCount);
            editor.apply();

            Toast.makeText(getContext(), "Read a book", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 10 energy, and 1 hunger to read a book.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateMcdonnells() {
        if (energyCount >= 25 && moneyCount >= 60) {
            energyCount -= 25;
            moneyCount -= 60;
            hungerCount += 10;
            moodCount += 1;

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

            Toast.makeText(getContext(), "Ate at Mcdonnells", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 25 energy, and 60 money to eat at Mcdonnells.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRestaurant() {
        if (energyCount >= 35 && moneyCount >= 1500) {
            energyCount -= 35;
            moneyCount -= 1500;
            hungerCount += 25;
            moodCount += 2;

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

            Toast.makeText(getContext(), "Ate at 5 Star Restaurant", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 35 energy and 1500 money to eat at a 5 Star Restaurant.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateConcert() {
        if (energyCount >= 30 && moneyCount >= 1200 && hungerCount >= 2) {
            energyCount -= 30;
            moneyCount -= 1200;
            hungerCount -= 2;
            moodCount += 12;

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

            Toast.makeText(getContext(), "Went to a concert", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 30 energy, 1200 money, and 2 hunger to go to a concert.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCruise() {
        if (energyCount >= 40 && moneyCount >= 3000) {
            energyCount -= 40;
            moneyCount -= 3000;
            hungerCount += 10;
            moodCount += 15;

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

            Toast.makeText(getContext(), "Went on a cruise", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 40 energy, and 3000 money to go on a cruise.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateSoma() {
        if (energyCount >= 60 && moneyCount >= 10000 && hungerCount >= 10) {
            energyCount -= 60;
            moneyCount -= 10000;
            hungerCount -= 10;
            moodCount += 80;

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

            Toast.makeText(getContext(), "Took Soma. 'A gramme is better than a damn!™'", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "Need at least 60 energy, 10000 money and 10 hunger to take Soma.", Toast.LENGTH_SHORT).show();
        }
    }

}