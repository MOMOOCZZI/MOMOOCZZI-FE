package com.example.momooczzi_fe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class Step3Fragment extends Fragment {

    private SharedViewModel sharedViewModel;
    private EditText txtheapen;
    private String msg;
    private Button save;

    public Step3Fragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step3, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        txtheapen = view.findViewById(R.id.editMessage);
        save = view.findViewById(R.id.save);
        save.setOnClickListener(v->{
            msg = txtheapen.getText().toString();
            sharedViewModel.setHappen(msg);
            Log.e("RECOMMAND_DEBUG",  "메세지" + sharedViewModel.getHappen().getValue());
        });
        return view;
    }
}
