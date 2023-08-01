package com.vk.housing.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.vk.housing.util.ValidationMessage;

@Entity
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long property_id;

	@Column(nullable = false)
	String p_address;

	@Column(nullable = false)
	String p_phone;

	// sell - rent
	int property_for;

	// resedential - commercial
	int building_type;

	// apartment indipendent home villa pent house
	int property_type;

	// construction status
	int construction_status;

	int age_of_property;

	int bhk;

	int bath_room;

	int balcony;

	int furnish_type;

	int parking;

	String avalable_from;

	String price;

	String security_deposit;

	String maintanace_charge;

	String built_area;

	String carpet_area;

	String offers_any;

	String city;

	String locality;

	String image_path;

	@Column(nullable = false)
	long uid;

	public long getProperty_id() {
		return property_id;
	}

	public void setProperty_id(long property_id) {
		this.property_id = property_id;
	}

	public String getP_address() {
		return p_address;
	}

	public void setP_address(String p_address) {
		this.p_address = p_address;
	}

	public String getP_phone() {
		return p_phone;
	}

	public void setP_phone(String p_phone) {
		this.p_phone = p_phone;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public int getProperty_for() {
		return property_for;
	}

	public void setProperty_for(int property_for) {
		this.property_for = property_for;
	}

	public int getBuilding_type() {
		return building_type;
	}

	public void setBuilding_type(int building_type) {
		this.building_type = building_type;
	}

	public int getProperty_type() {
		return property_type;
	}

	public void setProperty_type(int property_type) {
		this.property_type = property_type;
	}

	public int getConstruction_status() {
		return construction_status;
	}

	public void setConstruction_status(int construction_status) {
		this.construction_status = construction_status;
	}

	public int getAge_of_property() {
		return age_of_property;
	}

	public void setAge_of_property(int age_of_property) {
		this.age_of_property = age_of_property;
	}

	public int getBhk() {
		return bhk;
	}

	public void setBhk(int bhk) {
		this.bhk = bhk;
	}

	public int getBath_room() {
		return bath_room;
	}

	public void setBath_room(int bath_room) {
		this.bath_room = bath_room;
	}

	public int getBalcony() {
		return balcony;
	}

	public void setBalcony(int balcony) {
		this.balcony = balcony;
	}

	public int getFurnish_type() {
		return furnish_type;
	}

	public void setFurnish_type(int furnish_type) {
		this.furnish_type = furnish_type;
	}

	public int getParking() {
		return parking;
	}

	public void setParking(int parking) {
		this.parking = parking;
	}

	public String getAvalable_from() {
		return avalable_from;
	}

	public void setAvalable_from(String avalable_from) {
		this.avalable_from = avalable_from;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSecurity_deposit() {
		return security_deposit;
	}

	public void setSecurity_deposit(String security_deposit) {
		this.security_deposit = security_deposit;
	}

	public String getMaintanace_charge() {
		return maintanace_charge;
	}

	public void setMaintanace_charge(String maintanace_charge) {
		this.maintanace_charge = maintanace_charge;
	}

	public String getBuilt_area() {
		return built_area;
	}

	public void setBuilt_area(String built_area) {
		this.built_area = built_area;
	}

	public String getCarpet_area() {
		return carpet_area;
	}

	public void setCarpet_area(String carpet_area) {
		this.carpet_area = carpet_area;
	}

	public String getOffers_any() {
		return offers_any;
	}

	public void setOffers_any(String offers_any) {
		this.offers_any = offers_any;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
	}

	public ValidationMessage validate(Property property) {

		if (property.getP_phone().equals("") || property.getP_phone().trim().length() == 0
				|| property.getP_phone() == null) {
			return new ValidationMessage("Enter Phone Number", false);
		} else {
			return new ValidationMessage("Success", true);
		}

	}

}
