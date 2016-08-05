package org.pwte.example.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import org.junit.Test;
import org.pwte.example.rest.AccountApplication;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class otherTest {

	@Test
	public void otherTest1() throws IOException {

		try {
			URI uri = UriBuilder.fromUri("http://localhost/").port(9080).build();

			// Create an HTTP server listening at port 8282
			HttpServer server = HttpServer.create(new InetSocketAddress(uri.getPort()), 0);
			// Create a handler wrapping the JAX-RS application
			HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new AccountApplication(),
					HttpHandler.class);
			// Map JAX-RS handler to the server root
			server.createContext(uri.getPath(), handler);
			// Start the server
			server.start();

			Client client = ClientBuilder.newClient();

			// Valid URIs
			assertEquals(200, client.target("http://localhost:8282/customer/data").request().get().getStatus());
			assertEquals(200, client.target("http://localhost:8282/customer/data/rbarcia").request().get().getStatus());
			assertEquals(200,
					client.target("http://localhost:8282/customer/data/elastic/rbarcia").request().get().getStatus());

			// Invalid URIs
			assertEquals(404,
					client.target("http://localhost:8282/customer/data/elastic1/rbarcia").request().get().getStatus());

			// Stop HTTP server
			server.stop(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}