package com.example.noso.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;


public class Settings extends Fragment {
    ImageView imgUser;
    EditText Username;
    Spinner status;
    Button saveChanges;
    private View parentView;

    public Settings() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_settings, container, false);
        return parentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUser = getActivity().findViewById(R.id.imgUser);
        Username = getActivity().findViewById(R.id.textName);
        status = getActivity().findViewById(R.id.statusSpinner);
        saveChanges = getActivity().findViewById(R.id.saveButton);
    }


}
