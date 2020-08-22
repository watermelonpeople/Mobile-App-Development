package edu.neu.madcourse.groupproject.gamefragments.dialogfragments.studyfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class CosmetologyDialog extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext()); //if not, then getActivity()
        builder.setTitle("Cosmetology")
                .setMessage("Pursue a Cosmetology certificate.\nCosmetologists learn hair cutting, styling, coloring, nail care design and waxing.\nRequires a GED.\nCosts 15 energy, 300 money, 1 hunger and 1 mood.\nGrants 1 progress towards Cosmetology certification.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
