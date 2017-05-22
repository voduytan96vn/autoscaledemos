package com.redhat.ejb;

import javax.ejb.*;

@Stateful
@Remote(Service.class)

public class ServiceEJB implements Service {

	int total;

	@Override
	public String exec(String arg) {

		return "Hello " + arg;

	}

	@Override
	public int add() {

		total++;
		return total;

	}

}
