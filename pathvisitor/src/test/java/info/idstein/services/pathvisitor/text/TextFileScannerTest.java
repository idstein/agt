package info.idstein.services.pathvisitor.text;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Test;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

public class TextFileScannerTest {

	@Test
	public void unableToRead(@Mocked Scanner scanner) throws IOException {
		final Path path = Paths.get("src/test/resources/encoding/UTF-8.txt");
		new NonStrictExpectations() {
			{
				scanner.next();
				result = new IOException();
			}
		};
		final TextFileScanner textScanner = new TextFileScanner();
		textScanner.processPath(path, "fooBar");
		new Verifications() {
			{
				scanner.close();
			}
		};
	}
}
