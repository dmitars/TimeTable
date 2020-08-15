package com.example.main_app_2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.main_app_2.Network.Requester;
import com.example.main_app_2.SystemClasses.DataBase;
import com.example.main_app_2.SystemClasses.Fragments;

import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {
    private View root;
    private ArrayList<Integer> numberOfGroups;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.dialog_fragment, null);

        Bundle bundle = getArguments();
        numberOfGroups = bundle.getIntegerArrayList("list");

        builder.setView(root)
                .setPositiveButton(R.string.enter_button, (dialog, id) -> {
                    //don't touch
                });
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                int group, course;
                try {
                    course = Integer.parseInt((((EditText) root.findViewById(R.id.course_id)).getText().toString()));
                    group = Integer.parseInt((((EditText) root.findViewById(R.id.group_id)).getText().toString()));
                    DataBase.loadCurrentData(getContext(), course, group);
                    if (DataBase.isNotLoaded())
                         throw new Exception();
                    Fragments.fragmentsUpdate();
                    d.dismiss();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Нет такой группы", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}