package org.pwte.example.to;

import java.io.Serializable;

import javax.ws.rs.core.Response;

import org.pwte.example.domain.Customer;

public class CustomerSocial implements Serializable {

	private static final long serialVersionUID = 7498146555090034836L;

	private Customer customer;
	private Response socialData;


	public Response getSocialData() {
		return socialData;
	}

	public void setSocialData(Response socialData) {
		this.socialData = socialData;
	}

	public CustomerSocial() {
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
}