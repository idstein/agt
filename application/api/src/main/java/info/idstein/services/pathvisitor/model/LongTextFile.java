package info.idstein.services.pathvisitor.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringJoiner;

public class LongTextFile extends TextFile implements Iterable<Entry<String, Long>>{
	
	private final Map<String, Long> usageByMostFrequentlyUsedWord = new HashMap<>();

	/**
	 * JAXB requires even for marshal operation a no-argument constructor
	 */
	public LongTextFile() {	
	}
	
	public LongTextFile(final String name, final long wordCount, final Map<String, Long> frequencyByWord) {
		super(name, wordCount);
		usageByMostFrequentlyUsedWord.putAll(frequencyByWord);
	}

	@Override
	public Iterator<Entry<String, Long>> iterator() {
		return usageByMostFrequentlyUsedWord.entrySet().iterator();
	}
	
	@Override
	public String toString() {
		final StringJoiner sj = new StringJoiner(" ");
		sj.add(super.toString());
		for(Entry<String, Long> e : this) {
			sj.add(e.getKey());
			sj.add(e.getValue().toString());
		}
		return sj.toString();
	}
}
