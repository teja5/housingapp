package com.vk.housing.database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PropertySaved {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long ps_id;

	long psc_id;
	
	long psp_id;

	public long getPs_id() {
		return ps_id;
	}

	public void setPs_id(long ps_id) {
		this.ps_id = ps_id;
	}

	public long getPsc_id() {
		return psc_id;
	}

	public void setPsc_id(long psc_id) {
		this.psc_id = psc_id;
	}

	public long getPsp_id() {
		return psp_id;
	}

	public void setPsp_id(long psp_id) {
		this.psp_id = psp_id;
	}
	
}
