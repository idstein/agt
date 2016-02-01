package info.idstein.client4j;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.idstein.services.pathvisitor.StartUp;
import info.idstein.services.pathvisitor.model.Directory;
import info.idstein.services.pathvisitor.model.LongTextFile;
import info.idstein.services.pathvisitor.model.TextFile;

public class ClientTest {

	private HttpServer server;
	private info.idstein.client4j.Client client;

	@Before
	public void setUp() throws Exception {
		server = StartUp.startServer();
		client = new info.idstein.client4j.Client(StartUp.BASE_URI + "visit");
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}
	
	@Test
	public void testData() {
		Directory dir = client.visit("src/test/resources/testData");
		assertThat(dir, notNullValue());
		ClientStarter.print("LONG",LongTextFile.class,dir);
		ClientStarter.print("SHORT",TextFile.class,dir);
	}
}
