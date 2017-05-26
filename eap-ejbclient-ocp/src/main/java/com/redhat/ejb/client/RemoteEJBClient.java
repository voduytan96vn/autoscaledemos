 
package com.redhat.ejb.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import com.redhat.ejb.Service;
 

public class RemoteEJBClient {

	public static void main(String[] args) throws Exception {
		Service service = lookupServiceEJB();
		
		for (int ii=0;ii<100;ii++) {
			System.out.println("Called EJB. Total is " +service.add());
            Thread.sleep(30000);
		}
			

	}

	private static Service lookupServiceEJB() throws NamingException {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		final Context context = new InitialContext(jndiProperties);

		return (Service) context
				.lookup("ejb:/hademo/ServiceEJB!com.redhat.ejb.Service?stateful");
	}
}
