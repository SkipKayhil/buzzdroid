package io.github.skipkayhil.buzz.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.skipkayhil.buzz.R;

public class BusesView extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View inflatedView = inflater.inflate(R.layout.fragment_buses_view, container, false);
        return inflatedView;
    }
}
