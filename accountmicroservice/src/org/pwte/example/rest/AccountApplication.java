package org.pwte.example.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/account/*")
public class AccountApplication extends Application {

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		// Resources
		classes.add(org.pwte.example.rest.resource.CustomerResource.class);
		return classes;
	}

}
