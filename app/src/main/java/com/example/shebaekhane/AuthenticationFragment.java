package com.example.shebaekhane;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shebaekhane.databinding.FragmentAuthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class AuthenticationFragment extends Fragment {
    private Context context;
    private FragmentAuthenticationBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog dialog;



    public AuthenticationFragment() {
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
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.fragment_authentication, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(context);
        binding.textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_authenticationFragment_to_registerFragment);

            }
        });

        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCredential(view);
            }
        });
    }

    private void checkCredential(View v) {
       String email = binding.inputEmail.getText().toString();
       String password = binding.inputPassword.getText().toString();

       if (email.isEmpty() || !email.contains("@")){
           showError(binding.inputEmail,"Email is not valid");
       }
       else if(password.isEmpty() || password.length() < 6){
           showError(binding.inputPassword, "Password not match");

       }
       else {
           dialog.setTitle("Login...");
           dialog.setMessage("Wait..checking login status");
           dialog.setCanceledOnTouchOutside(false);
           dialog.show();

           mAuth.signInWithEmailAndPassword(email,password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()){
                               //Toast.makeText(context, "login Success", Toast.LENGTH_SHORT).show();
                               dialog.dismiss();
                               Navigation.findNavController(v).navigate(R.id.action_authenticationFragment_to_userLocationFragment);

                           }
                           else {
                               Toast.makeText(context, "make Sure Internet connection", Toast.LENGTH_SHORT).show();
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