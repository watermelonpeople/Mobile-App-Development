package edu.neu.madcourse.groupproject.gamefragments;

import androidx.fragment.app.Fragment;

import edu.neu.madcourse.groupproject.GameActivity;

public class AbstractFragment extends Fragment {

    //These methods are used by all Fragments.
    public void setEnergyCount(int energyValue) {
        ((GameActivity) getActivity()).setEnergyText(energyValue);
    }

    public void setHungerCount(int hungerValue) {
        ((GameActivity) getActivity()).setHungerText(hungerValue);
    }

    public void setMoneyCount(int moneyValue) {
        ((GameActivity) getActivity()).setMoneyText(moneyValue);
    }

    public void setMoodCount(int moodValue) {
        ((GameActivity) getActivity()).setMoodText(moodValue);
    }

}
