package org.pwte.example.service;

import java.util.List;

import org.pwte.example.domain.Customer;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;

public interface CustomerServices {

	/**	
	 * @param customerId
	 * @return customer
	 * @throws CustomerDoesNotExistException
	 * @throws GeneralPersistenceException
	 */
	public Customer loadCustomer(String user) throws CustomerDoesNotExistException, GeneralPersistenceException;
	
	/**
	 * Get all customers
	 * @return List
	 * @throws CustomerDoesNotExistException
	 * @throws GeneralPersistenceException
	 */
	public List<Customer> loadAllCustomers() throws CustomerDoesNotExistException, GeneralPersistenceException;

	

}