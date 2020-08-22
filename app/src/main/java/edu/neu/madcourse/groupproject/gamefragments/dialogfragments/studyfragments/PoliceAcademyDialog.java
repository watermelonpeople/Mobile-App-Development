package edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class PoliceAcademyDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //if not, then getActivity()
        builder.setTitle("Police Academy")
                .setMessage("Pursue Police certification.\nPolice cadets learn state laws, criminal investigations, patrol procedures, firearms training, traffic control and defensive driving.\nRequires a GED.\nCosts 20 energy, 400 money, 2 hunger and 2 mood.\nGrants 1 progress towards Police certification.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
