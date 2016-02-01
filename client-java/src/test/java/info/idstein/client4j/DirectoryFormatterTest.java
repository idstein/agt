package info.idstein.client4j;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import info.idstein.services.pathvisitor.model.Directory;
import info.idstein.services.pathvisitor.model.TextFile;

public class DirectoryFormatterTest {

	@Test
	public void emptyDirectory() {
		DirectoryFormatter formatter = new DirectoryFormatter(TextFile.class);
		assertThat(formatter.format(new Directory()), equalTo(""));
	}
	
	@Test
	public void nestedDirectoryStartWithTab() {
		final Directory root = new Directory();
		final Directory nestedA = new Directory("nestedA");
		final Directory nestedB = new Directory("nestedB");
		nestedA.addChild(nestedB);
		root.addChild(nestedA);
		DirectoryFormatter formatter = new DirectoryFormatter(TextFile.class);
		assertThat(formatter.format(root), containsString("\tnestedB"));
	}

}
