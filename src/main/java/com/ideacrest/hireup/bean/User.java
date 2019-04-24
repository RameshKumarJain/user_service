package com.ideacrest.hireup.bean;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

public class User {

	@NotNull
	@Min(1)
	private long _id;

	@NotNull
	private UserType userType;

	@NotBlank
	private String name;

	@NotNull
	@Min(15)
	@Max(80)
	private int age;

	@NotBlank
	@Pattern(regexp = "^[\\w.]+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$")
	private String email;

	@NotBlank
	@Length(min = 10, max = 10)
	private String mobNo;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}

}
