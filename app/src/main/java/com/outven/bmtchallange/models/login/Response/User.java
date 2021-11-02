package com.outven.bmtchallange.models.login.Response;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("gender")
	private String gender;

	@SerializedName("birth_date")
	private String birthDate;

	@SerializedName("name")
	private String name;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("school_class")
	private String schoolClass;

	@SerializedName("nisn")
	private String nisn;

	public String getNisn() {
		return nisn;
	}

	public void setNisn(String nisn) {
		this.nisn = nisn;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setBirthDate(String birthDate){
		this.birthDate = birthDate;
	}

	public String getBirthDate(){
		return birthDate;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setSchoolClass(String schoolClass){
		this.schoolClass = schoolClass;
	}

	public String getSchoolClass(){
		return schoolClass;
	}
}