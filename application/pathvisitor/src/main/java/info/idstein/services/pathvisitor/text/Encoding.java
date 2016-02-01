package info.idstein.services.pathvisitor.text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public interface Encoding {

	public static String detectEncoding(final Path file) throws IOException {
		try {
			CharsetDetector detector = new CharsetDetector();
			detector.setText(Files.readAllBytes(file));
			CharsetMatch match = detector.detect();
			return match.getName();
		} catch (IOException io) {
			return "UTF-8";
		}
	}

}
