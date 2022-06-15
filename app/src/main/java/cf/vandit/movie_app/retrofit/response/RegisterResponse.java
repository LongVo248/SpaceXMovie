package cf.vandit.movie_app.retrofit.response;

import androidx.annotation.NonNull;

import java.util.Date;

public class RegisterResponse {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Date birthday;
    private boolean gender;
    private String password;
    private String statusCode;

    public RegisterResponse() {
    }

    public RegisterResponse(String username, String email, String firstname, String lastname, Date birthday, boolean gender, String password, String statusCode) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.gender = gender;
        this.password = password;
        this.statusCode = statusCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @NonNull
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", statusCode='" + statusCode +
                '}';
    }
}
