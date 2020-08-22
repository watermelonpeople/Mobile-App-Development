package edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ComputerDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //if not, then getActivity()
        builder.setTitle("Comp Sci Degree")
                .setMessage("Pursue a Comp Sci Degree.\nA Comp Sci degree develops the skills and knowledge required to design, develop, test, and produce computers and their subsystems.\nRequires a GED.\nCosts 15 energy, 600 money, 1 hunger and 1 mood.\nGrants 1 progress towards Comp Sci Degree.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
