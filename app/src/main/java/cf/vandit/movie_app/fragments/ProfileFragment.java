package cf.vandit.movie_app.fragments;


import static cf.vandit.movie_app.utils.Constants.timestampToString;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.activities.ChangePasswordActivity;
import cf.vandit.movie_app.activities.EditProfileActivity;
import cf.vandit.movie_app.activities.LoginActivity;
import cf.vandit.movie_app.activities.MainActivity;
import cf.vandit.movie_app.retrofit.dto.AccountInfo;

public class ProfileFragment extends Fragment {
    TextView firstname,lastname, birthday, gender, email, changePassword;
    Button btnEditProfile, btnLogout;
    String query;
    AccountInfo accountInfo;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        setControl();
        accountInfo= MainActivity.accountInfo;
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void setControl() {
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        accountInfo= MainActivity.accountInfo;
        System.out.println(accountInfo);
        firstname= view.findViewById(R.id.firstname);
        lastname= view.findViewById(R.id.lastname);
        birthday= view.findViewById(R.id.birthday);
        gender= view.findViewById(R.id.gender);
        email= view.findViewById(R.id.email);
        changePassword= view.findViewById(R.id.changePassword);
        btnEditProfile= view.findViewById(R.id.btnEditProfile);
        btnLogout= view.findViewById(R.id.btnLogout);
        firstname.setText(accountInfo.getFirstname());
        lastname.setText(accountInfo.getLastname());

        birthday.setText(timestampToString(accountInfo.getBirthday()));


        if (accountInfo.isGender()){
            gender.setText("Male");
        } else {
            gender.setText("Female");
        }

        email.setText(accountInfo.getEmail());


        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), ChangePasswordActivity.class);
                intent.putExtra("accountInfo", accountInfo);
                startActivity(intent);
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.accountInfo= null;
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
    }
}
