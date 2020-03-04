package com.example.main_app_2;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class CustomDialogFragment extends DialogFragment {
    private Datable  datable;
    private View root;
    private ArrayList<Integer> numberOfGroups;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root =inflater.inflate(R.layout.dialog_fragment, null);

        Bundle bundle=getArguments();
        numberOfGroups=bundle.getIntegerArrayList("list");

        builder .setView(root)
                .setPositiveButton(R.string.enter_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //don't touch
                    }
                });
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        datable = (Datable) context;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int group, course;
                   try {
                       course = Integer.parseInt((((EditText) root.findViewById(R.id.course_id)).getText().toString()));
                       group = Integer.parseInt((((EditText) root.findViewById(R.id.group_id)).getText().toString()));
                       DataBase.course = course;
                       DataBase.group = group;
                       DataBase.data = Requester.makeRequest(group,course);
                       if(DataBase.data == null)
                           throw new Exception();
                       datable.setGroupAndCourseInfo(group,course);
                       Fragments.fragmentsUpdate();
                       d.dismiss();
                   } catch (Exception e) {
                       Toast.makeText(getContext(), "Нет такой группы", Toast.LENGTH_LONG).show();
                   }
                }
            });
        }
    }





}