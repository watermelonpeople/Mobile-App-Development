package edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PoliceOfficerDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //if not, then getActivity()
        builder.setTitle("Police Officer")
                .setMessage("Work as a Police Officer.\nPolice Officers apprehend suspects, respond to complaints, observe violations, and make arrests.\nRequires GED and graduation from Police Academy.\nCosts 50 energy, 3 hunger and 3 mood.\nGrants 250 money.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
