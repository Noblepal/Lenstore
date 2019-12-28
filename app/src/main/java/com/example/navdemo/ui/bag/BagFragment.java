package com.example.navdemo.ui.bag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navdemo.R;
import com.example.navdemo.ui.brands.BrandsViewModel;

public class BagFragment extends Fragment {
    private BagViewModel bagViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bagViewModel = ViewModelProviders.of(this).get(BagViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bag, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        bagViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
