package com.redhat.ejb;

import javax.ejb.*;

@Stateful
@Remote(Service.class)

public class ServiceEJB implements Service {

	int total;

	@Override
	public int add() {
		System.out.println("Running on node "+System.getProperty("jboss.node.name"));
		return total++;

	}

}
