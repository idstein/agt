package info.idstein.services.pathvisitor.text;

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface WordCounter {

	static Map<String, Long> countWordOccurences(final Stream<String> stream) {
		return stream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
	}
	
	static long countWords(final Map<String, Long> wordOccurences) {
		return wordOccurences.entrySet().stream().mapToLong(e -> e.getValue()).sum();
	}

	static Map<String, Long> filterByMinOccurrences(Map<String, Long> frequencyByWord) {
		return frequencyByWord.entrySet().
		stream().filter(e -> e.getValue() >= 50).collect(Collectors.toMap( e -> e.getKey(), e -> e.getValue()));
	}

	final static Pattern wordBoundary = Pattern.compile("\\W+");

}
