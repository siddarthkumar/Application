package com.example.morphine;


public class User {
    private String username ;
    private  String password;
    private String email;
    private String aiousername;
    private String aiokey;
    private String phonenumber;

    public User() {
    }

    public User(String username, String password, String email, String aiousername, String aiokey, String phone_number)
    {
        this.username=username;
        this.password=password;
        this.email=email;
        this.aiousername=aiousername;
        this.aiokey=aiokey;
        this.phonenumber=phone_number;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAiousername() {
        return aiousername;
    }

    public void setAiousername(String aiokey) {
        this.aiousername = aiokey;
    }

    public String getAiokey() {
        return aiokey;
    }

    public void setAiokey(String aiopassword) {
        this.aiokey = aiopassword;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", aiousername='" + aiousername + '\'' +
                ", aiokey='" + aiokey + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}

