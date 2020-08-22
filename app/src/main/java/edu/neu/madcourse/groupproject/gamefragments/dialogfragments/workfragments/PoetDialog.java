package edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PoetDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //if not, then getActivity()
        builder.setTitle("Poet")
                .setMessage("Work as a Poet.\nA poet writes poetry.\nSome poets can make a living through publishing books of their poetry.\nRequires GED and English Degree.\nCosts 35 energy, 2 hunger and 1 mood.\nGrants 150 money.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
