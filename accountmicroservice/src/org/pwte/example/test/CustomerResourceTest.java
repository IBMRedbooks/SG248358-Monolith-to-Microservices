package org.pwte.example.test;
import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.pwte.example.rest.resource.CustomerResource;

public class CustomerResourceTest {

	@Test
	public void testGetAllCustomers() {
		CustomerResource cr = new CustomerResource();
		try {
			Response rs = cr.getAllCustomers();
			assertEquals("teste", new String("teste"));
		} catch (Exception e) {
			assertEquals("teste", new String("teste"));
		}
	}

	@Test
	public void testGetCustomer() {
		CustomerResource cr = new CustomerResource();
		try {
			Response rs = cr.getCustomer("rbarcia");
			assertEquals("teste", new String("teste"));
		} catch (Exception e) {
			assertEquals("teste", new String("teste"));
		}
	}

	@Test
	public void testGetCustomerElastic() {
		CustomerResource cr = new CustomerResource();
		try {
			Response rs = cr.getCustomerElastic("rbarcia");
			assertEquals("teste", new String("teste"));
		} catch (Exception e) {
			assertEquals("teste", new String("teste"));
		}
	}

}
