package org.pwte.example.service;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.pwte.example.domain.Customer;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;

@Stateless
@RolesAllowed(value="SecureShopper")
public class CustomerServicesImpl implements CustomerServices {

	@PersistenceContext
	protected EntityManager em;

	@Resource
	SessionContext ctx;

	public Customer loadCustomer(String userName) throws CustomerDoesNotExistException, GeneralPersistenceException {
		Query query = em.createQuery("select c from Customer c where c.name = :userName");
		query.setParameter("userName", userName);
		return (Customer) query.getSingleResult();
	}
		
	public List<Customer> loadAllCustomers() throws CustomerDoesNotExistException, GeneralPersistenceException {
		Query query = em.createQuery("select c from Customer c");
		return query.getResultList();
	}
	
	

}
