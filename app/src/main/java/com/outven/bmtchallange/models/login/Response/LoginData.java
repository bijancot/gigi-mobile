package com.outven.bmtchallange.models.login.Response;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("user")
	private User user;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}
}