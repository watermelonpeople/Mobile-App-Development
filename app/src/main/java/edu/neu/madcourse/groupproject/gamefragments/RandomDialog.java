package edu.neu.madcourse.groupproject.gamefragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RandomDialog extends AppCompatDialogFragment {

    private RandomDialogListener listener;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //if not, then getActivity()
        builder.setTitle("Blessed")
                .setMessage("The RNG gods have noticed your efforts. Choose a reward")
                .setPositiveButton("20 Sec Boost", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.chosenReward(1);
                        Toast.makeText(getContext(), "For the next 20 seconds, all steps are worth x100 energy!", Toast.LENGTH_SHORT);
                    }
                })
                .setNegativeButton("1000 Energy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.chosenReward(2);
                        Toast.makeText(getContext(), "Chose 1000 Energy", Toast.LENGTH_SHORT).show();
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RandomDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement RandomDialogListener");
        }
    }
    public interface RandomDialogListener {
        void chosenReward(int selection);
    }
}
