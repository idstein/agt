package info.idstein.services.pathvisitor;

import static java.nio.file.attribute.PosixFilePermissions.asFileAttribute;
import static java.nio.file.attribute.PosixFilePermissions.fromString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import info.idstein.services.pathvisitor.model.Directory;

public class VisitResourceTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = StartUp.startServer();
		// create the client
		Client c = ClientBuilder.newClient();
		target = c.target(StartUp.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testParse() {
		Directory responseMsg = target.path("visit/src/test/resources/testData").request(MediaType.APPLICATION_JSON)
				.get(Directory.class);
		assertThat(responseMsg, notNullValue());
	}

	@Test
	public void testEmpty() {
		Directory responseMsg = target.path("visit/src/test/resources/zero").request(MediaType.APPLICATION_JSON)
				.get(Directory.class);
		assertThat(responseMsg, hasItem(hasProperty("wordCount", equalTo(0l))));
	}

	@Test(expected = InternalServerErrorException.class)
	public void writeOnlyNoRead() throws IOException {
		final Path writeOnlyFile = Paths.get("src/test/resources/writeOnly/writeOnly.txt");
		try {
			Files.createFile(writeOnlyFile, asFileAttribute(fromString("-w--w--w-")));
			Files.write(writeOnlyFile, "bla bla".getBytes());
			assertThat(Files.isReadable(writeOnlyFile), equalTo(false));
			target.path("visit/src/test/resources/writeOnly").request(MediaType.APPLICATION_JSON).get(Directory.class);
		} finally {
			if (Files.exists(writeOnlyFile))
				Files.delete(writeOnlyFile);
		}
	}

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void longTextFilesHave() {
		Directory responseMsg = target.path("visit/src/test/resources/counting").request(MediaType.APPLICATION_JSON)
				.get(Directory.class);
		assertThat(responseMsg,
				hasItems(hasProperty("wordCount", equalTo(1000l)), hasProperty("wordCount", equalTo(1l))));
		assertThat(responseMsg,
				hasItem(hasItem(allOf(hasProperty("key", equalTo("bla")), hasProperty("value", equalTo(1000l))))));
	}

	@Test
	public void nestedTextFilesAreFound() {
		Directory responseMsg = target.path("visit/src/test/resources/nested").request(MediaType.APPLICATION_JSON)
				.get(Directory.class);
		assertThat(responseMsg, hasItem(hasItem(hasItem(hasItem(hasProperty("wordCount", equalTo(1l)))))));
	}
}
