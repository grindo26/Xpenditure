package com.xpenditure.www.xpenditure;


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
public class LoginFragment extends Fragment implements View.OnClickListener{

    private Button buttonLogin;
    private EditText editTextemail;
    private EditText editTextpassword;
    private TextView textViewreg;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    FragmentTransaction fragmentTransaction;

    public LoginFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        progressDialog = new ProgressDialog(this.getActivity());
        firebaseAuth = firebaseAuth.getInstance();

//        if (firebaseAuth.getCurrentUser() != null) {
//            FragmentManager fragmentManager = getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            fragmentTransaction.replace(R.id.frameLayout, new AccountFragment());
//            fragmentTransaction.commit();
//        } else


            editTextemail = (EditText) rootView.findViewById(R.id.loginemail);
            editTextpassword = (EditText) rootView.findViewById(R.id.loginpassword);
            textViewreg = (TextView) rootView.findViewById(R.id.RegText);
            buttonLogin = (Button) rootView.findViewById(R.id.buttonlogin);

            buttonLogin.setOnClickListener(this);
            textViewreg.setOnClickListener(this);








        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogin) {
            loginUser();
        }

        if (view == textViewreg) {
            //reg activity
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, registerFragment );
            fragmentTransaction.commit();
        }
    }

    private void loginUser() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();


        if(TextUtils.isEmpty(email)){
            //eamil is empty
            Toast.makeText(this.getActivity(), "Please enter Email!" ,Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //eamil is empty
            Toast.makeText(this.getActivity(), "Please enter Password!" ,Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Loggin In");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this.getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){

                    Toast.makeText(LoginFragment.this.getActivity(), "login Sucessfull!!", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();

                    AccountFragment accountFragment = new AccountFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, accountFragment);
//                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();


                }
            else {
                    Toast.makeText(LoginFragment.this.getActivity(), "login unsucessfull!!", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }

            }

        });


    }
}