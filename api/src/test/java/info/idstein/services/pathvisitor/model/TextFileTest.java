package info.idstein.services.pathvisitor.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TextFileTest {

	private static final String FOO_BAR = "fooBar";

	@Test
	public void createEmpty() {
		final TextFile file = new TextFile();
		assertThat(file, notNullValue());
		assertThat(file.getWordCount(), equalTo(0l));	
		assertThat(file.getName(), nullValue());	
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeWordCount() {
		new TextFile(FOO_BAR, -1);
	}
	
	@Test
	public void createReal() {
		final long wordCount = 42l;
		final TextFile file = new TextFile(FOO_BAR,wordCount);
		assertThat(file, notNullValue());
		assertThat(file.getWordCount(), equalTo(wordCount));	
		assertThat(file.getName(), equalTo(FOO_BAR));
	}
}
