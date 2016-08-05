package org.pwte.example.rest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pwte.example.domain.Customer;
import org.pwte.example.exception.CustomerDoesNotExistException;
import org.pwte.example.exception.GeneralPersistenceException;
import org.pwte.example.service.CustomerServices;
import org.pwte.example.to.CustomerSocial;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewRequest;

@Path("/data")
@Stateless
public class CustomerResource {
	@EJB
	CustomerServices customerServices;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomers() throws Exception {
		try {
			List<CustomerSocial> csList = getAllCustomersData();
			return Response.ok(csList).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@GET
	@Path("/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@PathParam("userName") String userName) throws Exception {
		try {
			CustomerSocial cs = getCustomerData(userName);
			return Response.ok(cs).build();
		} catch (Exception e) {
			throw e;
		}
	}

	@GET
	@Path("/elastic/{userName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomerElastic(@PathParam("userName") String userName) throws Exception {
		try {

			// Create a new CloudantClient instance for account endpoint
			CloudantClient client = ClientBuilder.account("<Cloudant Username>")
					.username("<Cloudant Username>")
					.password("<Cloudant Password>")
					.disableSSLAuthentication().build();
			Database db = client.database("accounts", false);

			ViewRequest view = db.getViewRequestBuilder("accounts", "all")
					.newPaginatedRequest(Key.Type.STRING, Object.class).reduce(false).includeDocs(true)
					.startKey(userName).build();

			return Response.ok(view.getResponse()).build();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Get data all customers
	 * 
	 * @return
	 * @throws Exception
	 */
	private List<CustomerSocial> getAllCustomersData() throws Exception {
		try {
			List<CustomerSocial> csList = new ArrayList<CustomerSocial>();
			List<Customer> customerList = customerServices.loadAllCustomers();
			for (int i = 0; i < customerList.size(); i++) {
				CustomerSocial cs = new CustomerSocial();
				cs.setCustomer(customerList.get(i));
				cs.setSocialData(getCustomerElasticData(customerList.get(i).getUsername()));
				csList.add(cs);
			}
			return csList;
		} catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} catch (GeneralPersistenceException e) {
			throw new WebApplicationException(e);
		}
	}

	/**
	 * Get data for user
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	private CustomerSocial getCustomerData(String userName) throws Exception {
		try {
			Customer customer = customerServices.loadCustomer(userName);
			CustomerSocial cs = new CustomerSocial();
			cs.setCustomer(customer);
			cs.setSocialData(getCustomerElasticData(customer.getUsername()));
			return cs;
		} catch (CustomerDoesNotExistException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} catch (GeneralPersistenceException e) {
			throw new WebApplicationException(e);
		}
	}

	/**
	 * Get social data for user
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	@Produces(MediaType.APPLICATION_JSON)
	private Response getCustomerElasticData(String userName) throws Exception {
		try {

			// Create a new CloudantClient instance for account endpoint
			CloudantClient client = ClientBuilder.account("<Cloudant Username>")
					.username("<Cloudant Username>")
					.password("<Cloudant Username>")
					.disableSSLAuthentication().build();
			Database db = client.database("accounts", false);

			ViewRequest view = db.getViewRequestBuilder("accounts", "all")
					.newPaginatedRequest(Key.Type.STRING, Object.class).reduce(false).includeDocs(true)
					.startKey(userName).build();

			return Response.ok(view.getResponse()).build();
		} catch (Exception e) {
			throw e;
		}
	}

}
