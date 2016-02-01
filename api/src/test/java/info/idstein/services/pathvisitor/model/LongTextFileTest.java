package info.idstein.services.pathvisitor.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class LongTextFileTest {

	@Test
	public void createEmpty() {
		final LongTextFile file = new LongTextFile();
		assertThat(file, notNullValue());
		assertThat(file.getWordCount(), equalTo(0l));
		assertThat(file.getName(), nullValue());
		assertThat(file, CoreMatchers.not(CoreMatchers.hasItem(CoreMatchers.any(Entry.class))));
	}

	@Test
	public void createReal() {
		final String name = "fooBar";
		final long wordCount = 42l;
		final Map<String, Long> frequencyByWord = new HashMap<>();
		frequencyByWord.put(name, wordCount);

		final LongTextFile file = new LongTextFile(name, wordCount, frequencyByWord);
		assertThat(file, notNullValue());
		assertThat(file.getWordCount(), equalTo(wordCount));
		assertThat(file.getName(), equalTo(name));
		assertThat(file,
				hasItem(allOf(//
						hasProperty("key", equalTo(name)), //
						hasProperty("value", equalTo(wordCount))//
		)));
	}
	
	@Test
	public void renderToString() {
		final String name = "fooBar";
		final long wordCount = 42l;
		final Map<String, Long> frequencyByWord = new HashMap<>();
		frequencyByWord.put(name, wordCount);

		final LongTextFile file = new LongTextFile(name, wordCount, frequencyByWord);
		assertThat(file.toString(), equalTo("fooBar 42 fooBar 42"));
	}
}
