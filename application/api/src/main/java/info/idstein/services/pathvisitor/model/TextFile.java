package info.idstein.services.pathvisitor.model;

import static java.lang.String.format;

public class TextFile extends FileSystemNode {
	
	private final long wordCount;
	
	/**
	 * JAXB requires even for marshal operation a no-argument constructor
	 */
	public TextFile() {
		wordCount = 0;
	}

	public TextFile(final String name, final long wordCount) {
		super(name);
		if(wordCount < 0)
			throw new IllegalArgumentException("word count is negative");
		this.wordCount = wordCount;
	}

	public long getWordCount() {
		return wordCount;
	}

	@Override
	public String toString() {
		return format("%s %d", super.toString(), getWordCount());
	}
}
