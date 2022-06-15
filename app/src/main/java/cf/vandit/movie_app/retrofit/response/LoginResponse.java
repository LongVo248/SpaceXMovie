package cf.vandit.movie_app.retrofit.response;

import androidx.annotation.NonNull;

public class LoginResponse {
    private int id;
    private String username;
    private String password;
    private boolean enabled;
    private String email;
    private String avatar;
    private String firstname;
    private String lastname;
    private String birthday;
    private String town;
    private String address;
    private String phoneNumber;
    private boolean gender;
    private String authenticationToken;

    public LoginResponse(int id, String username, String password, boolean enabled, String email, String avatar, String firstname, String lastname, String birthday, String address, String phoneNumber, boolean gender, String authenticationToken) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.avatar = avatar;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.authenticationToken= authenticationToken;
    }

    public LoginResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @NonNull
    @Override
    public String toString() {
        return "LoginResponse{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender=" + gender + '\'' +
                ", authenticationToken=" + authenticationToken +
                '}';
    }
}
