package com.example.btl_appbandienthoai.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_appbandienthoai.Home;
import com.example.btl_appbandienthoai.R;
import com.example.btl_appbandienthoai.login.LoginActivity;
import com.example.btl_appbandienthoai.login.model.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {

    private View mView;
    private Home home;
    TextView profileName, profileEmail, profileContact, profileBirthdate;
    TextView titleName;
    String nameFromDB,emailFromDB,birthdateFromDB,contactFromDB;
    Button logout;
    private FirebaseAuth auth;
    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_user, container, false);
        // Inflate the layout for this fragment
        initItem();
        return mView;
    }

    private void initItem() {
        profileName = mView.findViewById(R.id.profileName);
        profileEmail = mView.findViewById(R.id.profileEmail);
        profileContact = mView.findViewById(R.id.profileContact);
        profileBirthdate = mView.findViewById(R.id.profileBirthdate);
        titleName = mView.findViewById(R.id.titleName);
        logout = mView.findViewById(R.id.logout);
        showAllUserData();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(mView.getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void showAllUserData(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("profile");
        reference.orderByChild("email").startAt(email)
                .endAt(email + "\uf8ff")
                .addValueEventListener(new ValueEventListener() {
                    UserDetails userDetails;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.i("Snapshot", snapshot.toString());
                        for (DataSnapshot data : snapshot.getChildren()) {
                            Log.i("data", data.getKey());
                            userDetails = data.getValue(UserDetails.class);
                            break;
                        }
                        String nameUser = userDetails.getName();
                        String contactUser = userDetails.getContact();
                        String emailUser = userDetails.getEmail();
                        String birthdateUser = userDetails.getBirthdate();
                        titleName.setText(nameUser);
                        profileName.setText(nameUser);
                        profileEmail.setText(emailUser);
                        profileContact.setText(contactUser);
                        profileBirthdate.setText(birthdateUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(mView.getContext(), "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}