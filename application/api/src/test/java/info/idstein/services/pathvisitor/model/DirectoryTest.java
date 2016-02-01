package info.idstein.services.pathvisitor.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import mockit.Mocked;

public class DirectoryTest {

	@Test
	public void createEmpty() {
		final Directory dir = new Directory();
		assertThat(dir, notNullValue());
		assertThat(dir.hasChildren(), equalTo(false));	
		assertThat(dir.getName(), nullValue());	
	}
	
	@Test
	public void createWithRealName() {
		final String name = "Blabla";
		final Directory dir = new Directory(name);
		assertThat(dir, notNullValue());
		assertThat(dir.hasChildren(), equalTo(false));	
		assertThat(dir.getName(), equalTo(name));	
	}
	
	@Test
	public void addChild(@Mocked FileSystemNode child) {
		final Directory dir = new Directory();
		dir.addChild(child);
		assertThat(dir.hasChildren(), equalTo(true));
		assertThat(dir, hasItem(child));
	}
}
