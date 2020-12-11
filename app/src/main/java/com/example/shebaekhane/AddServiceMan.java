package com.example.shebaekhane;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shebaekhane.databinding.FragmentAddServiceManBinding;


public class AddServiceMan extends Fragment {
    private FragmentAddServiceManBinding binding;
    private Context context;



    public AddServiceMan() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_add_service_man, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btSubmitInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredential();

            }
        });
    }

    public void checkCredential(){
        String name = binding.tvName.getText().toString();
        String phoneNo = binding.tvPhoneNo.getText().toString();
        String state = binding.tvState.getText().toString();
        String service = binding.tvService.getText().toString();

        if (name.isEmpty()){
            showError(binding.tvName,"Name id Required");
        }
        else if(phoneNo.isEmpty()){
            showError(binding.tvPhoneNo,"Phone Number Required");
        }
        else if (state.isEmpty()) {
            showError(binding.tvState,"State is Required");
        }
        else if(service.isEmpty()){
            showError(binding.tvService,"Mention Service Category");
        }

        else {
            Toast.makeText(context, "Save Database Info", Toast.LENGTH_SHORT).show();


        }

    }

    private void showError(EditText editText, String s) {
        editText.setError(s);
        editText.requestFocus();

    }
}