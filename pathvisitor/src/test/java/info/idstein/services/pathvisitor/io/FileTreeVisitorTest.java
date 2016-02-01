package info.idstein.services.pathvisitor.io;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import mockit.Tested;

public class FileTreeVisitorTest {
	
	private static final Path LOCATION_OF_NI = Paths.get("src/test/resources/Ni");
	
	@Tested
	FileTreeVisitor visitor;

	@Test
	public void nothingInEkkeEkkeEkkeEkkePtangZooBoing() throws IOException {
		Files.walkFileTree(LOCATION_OF_NI, visitor);
		assertThat(visitor.getRootDirectory(), notNullValue());
		assertThat(visitor.getRootDirectory(), iterableWithSize(0));
	}
}
