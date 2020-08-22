package edu.neu.madcourse.groupproject.gamefragments.dialogfragments.workfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class HackermanDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //if not, then getActivity()
        builder.setTitle("Hackerman")
                .setMessage("Work as a Hacker.\nHackers perform advanced penetration tests to identify vulnerabilities in computer systems.\nRequires GED and Comp Sci Degree.\nCosts 45 energy, 2 hunger and 3 mood.\nGrants 300 money.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
