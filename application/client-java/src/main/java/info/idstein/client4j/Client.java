package info.idstein.client4j;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import info.idstein.services.pathvisitor.model.Directory;

public class Client {

	final WebTarget target;
	
	public Client(String url) {
		target = ClientBuilder.newClient().target(url);
	}
	
	public Directory visit(String resource) {
		return target.path(resource).request(MediaType.APPLICATION_JSON)
				.get(Directory.class);
	}
}
