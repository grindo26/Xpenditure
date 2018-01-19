package com.xpenditure.www.xpenditure;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    Button buttonReg;
    EditText editTextEmail;
    EditText editTextPassword;
    TextView textViewLoginText;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        //code idhar


        buttonReg = (Button) rootView.findViewById(R.id.buttonReg);
        editTextEmail = (EditText) rootView.findViewById(R.id.regemail);
        editTextPassword = (EditText) rootView.findViewById(R.id.regpassword);
        textViewLoginText = (TextView) rootView.findViewById(R.id.loginText);
        progressDialog = new ProgressDialog(this.getActivity());

        firebaseAuth = FirebaseAuth.getInstance();

//        if(firebaseAuth.getCurrentUser() != null){
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frameLayout,new AccountFragment());
//            fragmentTransaction.commit();
//        }

        buttonReg.setOnClickListener(this);
        textViewLoginText.setOnClickListener(this);


        return rootView;

    }

    @Override
    public void onClick(View view) {

        if (view == buttonReg) {
            registeruser();
        }

        if (view == textViewLoginText) {
            //login activity
            LoginFragment loginFragment = new LoginFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, loginFragment);
            fragmentTransaction.commit();
        }


    }

    private void registeruser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //eamil is empty
            Toast.makeText(this.getActivity(), "Please enter Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //eamil is empty
            Toast.makeText(this.getActivity(), "Please enter Password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() < 8) {
            //eamil is empty
            Toast.makeText(this.getActivity(), "Password shoulde be grater than 8 characters!", Toast.LENGTH_SHORT).show();
            return;
        }


        // if validation is ok
        progressDialog.setMessage("Registering user");
        progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterFragment.this.getActivity(), "Registered sucessfully", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();

                        LoginFragment loginFragment= new LoginFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, loginFragment );
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();


                    } else {
                        Toast.makeText(RegisterFragment.this.getActivity(), "Registeration unsucessful", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }
            });


        }

    }
