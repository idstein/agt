package info.idstein.services.pathvisitor.text;

import static info.idstein.services.pathvisitor.io.Streams.fromIterator;
import static info.idstein.services.pathvisitor.text.WordCounter.countWordOccurences;
import static info.idstein.services.pathvisitor.text.WordCounter.filterByMinOccurrences;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

import info.idstein.services.pathvisitor.model.LongTextFile;
import info.idstein.services.pathvisitor.model.TextFile;

public class TextFileScanner {
	
	public TextFile processPath(final Path file, final String name) throws IOException {
		final Scanner scanner = createScanner(file);
		try {
			final Map<String, Long> frequencyByWord = countWordOccurences(fromIterator(scanner));
			final long wordCount = WordCounter.countWords(frequencyByWord);
			if(wordCount<1000) {
				return new TextFile(name, wordCount);
			} else {
				return new LongTextFile(name, wordCount,filterByMinOccurrences(frequencyByWord));
			}
		} finally {
			scanner.close();
		}
	}

	private Scanner createScanner(final Path file) throws IOException {
		final Scanner scanner = new Scanner(file, Encoding.detectEncoding(file));
		scanner.useDelimiter(WordCounter.wordBoundary);
		return scanner;
	}
}
