package org.pwte.example.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CUSTOMER database table.
 * 
 */
@Entity
@NamedQuery(name="Customer.findAll", query="SELECT c FROM Customer c")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CUSTOMER_ID")
	private int customerId;

	private String addressline1;

	private String addressline2;

	@Lob
	@Column(name="BUSINESS_DESCRIPTION")
	private String businessDescription;

	@Column(name="BUSINESS_PARTNER")
	private String businessPartner;

	@Column(name="BUSINESS_VOLUME_DISCOUNT")
	private String businessVolumeDiscount;

	private String city;

	private String country;

	@Column(name="\"NAME\"")
	private String name;

	@Column(name="OPEN_ORDER")
	private int openOrder;

	@Column(name="RESIDENTIAL_FREQUENT_CUSTOMER")
	private String residentialFrequentCustomer;

	@Column(name="RESIDENTIAL_HOUSEHOLD_SIZE")
	private short residentialHouseholdSize;

	@Column(name="\"STATE\"")
	private String state;

	@Column(name="\"TYPE\"")
	private String type;

	private String username;

	private String zip;

	//bi-directional many-to-one association to ContactNumber
	@OneToMany(mappedBy="customer")
	private List<ContactNumber> contactNumbers;

	public Customer() {
	}

	public int getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getAddressline1() {
		return this.addressline1;
	}

	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getAddressline2() {
		return this.addressline2;
	}

	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}

	public String getBusinessDescription() {
		return this.businessDescription;
	}

	public void setBusinessDescription(String businessDescription) {
		this.businessDescription = businessDescription;
	}

	public String getBusinessPartner() {
		return this.businessPartner;
	}

	public void setBusinessPartner(String businessPartner) {
		this.businessPartner = businessPartner;
	}

	public String getBusinessVolumeDiscount() {
		return this.businessVolumeDiscount;
	}

	public void setBusinessVolumeDiscount(String businessVolumeDiscount) {
		this.businessVolumeDiscount = businessVolumeDiscount;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpenOrder() {
		return this.openOrder;
	}

	public void setOpenOrder(int openOrder) {
		this.openOrder = openOrder;
	}

	public String getResidentialFrequentCustomer() {
		return this.residentialFrequentCustomer;
	}

	public void setResidentialFrequentCustomer(String residentialFrequentCustomer) {
		this.residentialFrequentCustomer = residentialFrequentCustomer;
	}

	public short getResidentialHouseholdSize() {
		return this.residentialHouseholdSize;
	}

	public void setResidentialHouseholdSize(short residentialHouseholdSize) {
		this.residentialHouseholdSize = residentialHouseholdSize;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public List<ContactNumber> getContactNumbers() {
		return this.contactNumbers;
	}

	public void setContactNumbers(List<ContactNumber> contactNumbers) {
		this.contactNumbers = contactNumbers;
	}

	public ContactNumber addContactNumber(ContactNumber contactNumber) {
		getContactNumbers().add(contactNumber);
		contactNumber.setCustomer(this);

		return contactNumber;
	}

	public ContactNumber removeContactNumber(ContactNumber contactNumber) {
		getContactNumbers().remove(contactNumber);
		contactNumber.setCustomer(null);

		return contactNumber;
	}

}