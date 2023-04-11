package com.dd.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "zipcode")
	private String zipcode;

	public Address() {
	}

	public Address(String street, String city, String zipcode) {
		super();
		this.street = street;
		this.city = city;
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", zipcode=" + zipcode + "]";
	}

}
