package io.mosip.core.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Resident {
	
	@Id
	private String uin;
	private String name;
	private String father_name;
	private String dob;
	private String house_no;
	private String street;
	private String city;
	private String dist;
	private String state;
	private String country;
	private String mobile;
	private String email;
	

	public Resident() {}

	public Resident(String uin, String name, String father_name, String dob, String house_no, String street,
			String city, String dist, String state, String country,String mobile, String email) {
		super();
		this.uin = uin;
		this.name = name;
		this.father_name = father_name;
		this.dob = dob;
		this.house_no = house_no;
		this.street = street;
		this.city = city;
		this.dist = dist;
		this.state = state;
		this.country = country;
		this.mobile = mobile;
		this.email = email;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getHouse_no() {
		return house_no;
	}

	public void setHouse_no(String house_no) {
		this.house_no = house_no;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}