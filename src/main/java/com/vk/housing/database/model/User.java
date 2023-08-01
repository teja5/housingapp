package com.vk.housing.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.vk.housing.util.ValidationMessage;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long user_id;

	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	String phone;

	@Column(nullable = false)
	String email_id;

	@Column(nullable = false)
	String password;

	String image_path;

	// 1- general
	int login_type;

	String otp;

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getLogin_type() {
		return login_type;
	}

	public void setLogin_type(int login_type) {
		this.login_type = login_type;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public ValidationMessage validate(User user) {

		if (user.getName().equals("") || user.getName().trim().length() == 0 || user.getName() == null) {
			return new ValidationMessage("Enter User Name", false);
		} else {
			return new ValidationMessage("Success", true);
		}

	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

}
