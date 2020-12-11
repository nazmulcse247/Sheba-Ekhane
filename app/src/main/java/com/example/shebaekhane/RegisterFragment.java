package com.example.shebaekhane;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shebaekhane.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFragment extends Fragment {
    private FirebaseAuth mAuth;
    private Context context;
    private FragmentRegisterBinding binding;
    private ProgressDialog dialog;




    public RegisterFragment() {
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_register, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(context);

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredential();

            }
        });


    }

    private void checkCredential() {
        String userName = binding.inputUsername.getText().toString();
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();
        String confirmPassword = binding.inputConformPassword.getText().toString();

        if (userName.isEmpty()){
            showError(binding.inputUsername,"User Name is required");
        }
        else if (email.isEmpty() || !email.contains("@")) {
            showError(binding.inputEmail, "Email is not Valid");
        }
        else if(password.isEmpty() || password.length()<6){
            showError(binding.inputPassword, "Password must be 6 Digit");
        }
        else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(binding.inputConformPassword, "password is not match");
        }
        else {

            dialog.setTitle("Registration...");
            dialog.setMessage("Wait..checking Registration status");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Navigation.findNavController(getActivity(),R.id.action_registerFragment_to_userLocationFragment);
                            }
                            else {
                                Toast.makeText(context, "Registration Not Successful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }


    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}