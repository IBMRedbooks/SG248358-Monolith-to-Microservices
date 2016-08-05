package org.pwte.example.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CONTACT_NUMBERS database table.
 * 
 */
@Entity
@Table(name="CONTACT_NUMBERS")
@NamedQuery(name="ContactNumber.findAll", query="SELECT c FROM ContactNumber c")
public class ContactNumber implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="\"TYPE\"")
	private String type;

	private String phone;

	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="CUSTOMER_ID")
	private Customer customer;

	public ContactNumber() {
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}