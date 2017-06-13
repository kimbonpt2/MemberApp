package com.maiatech.member;

/**
 * Created by kim4p on 6/4/2017.
 */

public class Member {
    public String name;
    private String regency;
    private String phone;
    private String address;
    private String email;
    private String skype;
    private String facebook;
    private String age;
    private String avatar;

    public Member() {
    }


    public Member(String name, String regency, String age, String phone, String address, String email, String facebook, String skype, String avatar) {
        this.name = name;
        this.regency = regency;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.skype = skype;
        this.facebook = facebook;
        this.age = age;
        this.avatar = avatar;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegency() {
        return regency;
    }

    public void setRegency(String regency) {
        this.regency = regency;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
