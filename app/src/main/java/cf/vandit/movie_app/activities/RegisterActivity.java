package cf.vandit.movie_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import cf.vandit.movie_app.R;
import cf.vandit.movie_app.retrofit.RetrofitService;
import cf.vandit.movie_app.retrofit.request.RegisterRequest;
import cf.vandit.movie_app.retrofit.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView dob, btn;
    EditText username, email, password, confirmPassword, firstName, lastName;
    RadioButton rdbMale, rdbFemale;
    ImageView imgCalendar;
    String birthday, gender;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setControl();
        setEvent();
    }

    private void setEvent() {
        birthday = null;
        gender = null;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dob.setText(String.format("%d/%d/%d", i2, i1 + 1, i));
                    }
                }, year, month, day);
                System.out.println("dob= "+dob.getText().toString());
                if (month < 10) {
                    birthday = year + "-0" + month + "-" + day;
                } else {
                    birthday = year + "-" + month + "-" + day;
                }
                System.out.println(birthday);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = (rdbMale.isChecked()) ? "1" : (rdbFemale.isChecked() ? "0" : "-1");
                System.out.println(birthday);
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(email.getText().toString())
                        || TextUtils.isEmpty(firstName.getText().toString()) || TextUtils.isEmpty(lastName.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(confirmPassword.getText().toString())
                        || gender.equals("-1") || birthday == null) {
                    Toast.makeText(RegisterActivity.this, "Username / Password / Required", Toast.LENGTH_LONG).show();
                } else {
                    signup();
                }
            }
        });
    }

    private void signup(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setFirstname(firstName.getText().toString());
        registerRequest.setLastname(lastName.getText().toString());
        registerRequest.setBirthday(birthday);
        if (password.getText().toString().equals(confirmPassword.getText().toString())){
            registerRequest.setPassword(password.getText().toString());
        } else {
            Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_LONG).show();
        }

        if (rdbMale.isChecked()){
            registerRequest.setGender(true);
        }else{
            registerRequest.setGender(false);
        }
        Call<RegisterResponse> registerResponseCall= RetrofitService.getAccountService().userRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body().getStatusCode() == null){
                    Toast.makeText(RegisterActivity.this,"Register Successful", Toast.LENGTH_LONG).show();
                    RegisterResponse registerResponse= response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.err.println("call=="+call);
                            System.out.println(registerResponse);
                            startActivity(new Intent(RegisterActivity.this, VerifyEmail.class));
                        }
                    }, 700);
                } else {
                    Toast.makeText(RegisterActivity.this,"Register Failed", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                System.err.println("call"+call);
                startActivity(new Intent(RegisterActivity.this, VerifyEmail.class));
                Toast.makeText(RegisterActivity.this,"Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    private void setControl() {
        btnRegister = findViewById(R.id.btnRegister);
        btn = findViewById(R.id.alreadyHaveAccount);
        username = findViewById(R.id.inputUsername);
        email = findViewById(R.id.inputEmail);
        firstName = findViewById(R.id.inputFirstName);
        lastName = findViewById(R.id.inputLastName);
        password = findViewById(R.id.inputPassword);
        confirmPassword = findViewById(R.id.inputConformPassword);
        rdbMale = findViewById(R.id.rdbMale);
        rdbFemale = findViewById(R.id.rdbFemale);
        imgCalendar = findViewById(R.id.imgCalendar);
        dob = findViewById(R.id.inputDateOfBirth);
    }
}