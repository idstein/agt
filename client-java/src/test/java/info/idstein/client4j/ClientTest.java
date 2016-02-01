package info.idstein.client4j;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.idstein.services.pathvisitor.StartUp;

public class ClientTest {

	private HttpServer server;

	@Before
	public void setUp() throws Exception {
		server = StartUp.startServer();
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}
	
	@Test
	public void testData() {
		ClientStarter.main(new String[]{"src/test/resources/testData", StartUp.BASE_URI + "visit"});
	}
}
