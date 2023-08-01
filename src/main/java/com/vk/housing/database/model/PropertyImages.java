package com.vk.housing.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class PropertyImages {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long pi_id;
	
	long ppi_id;

	String pi_img;

	public long getPi_id() {
		return pi_id;
	}

	public void setPi_id(long pi_id) {
		this.pi_id = pi_id;
	}

	public long getPpi_id() {
		return ppi_id;
	}

	public void setPpi_id(long ppi_id) {
		this.ppi_id = ppi_id;
	}

	public String getPi_img() {
		return pi_img;
	}

	public void setPi_img(String pi_img) {
		this.pi_img = pi_img;
	}
	
}
