package info.idstein.services.pathvisitor.text;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

@RunWith(Parameterized.class)
public class EncodingTest {

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ "UTF-8" }, //
				{ "windows-1252" } //
		});
	}

	public /* NOT private */ Path input;

	public /* NOT private */ String encoding;

	public EncodingTest(String encoding) {
		input = Paths.get("src/test/resources/encoding/", encoding.toUpperCase() + ".txt");
		this.encoding = encoding;
	}

	@Test
	public void checkEncoding() throws IOException {
		CharsetDetector detector = new CharsetDetector();
		detector.setText(Files.readAllBytes(input));
		CharsetMatch match = detector.detect();
		assertThat(match.getName(), equalTo(encoding));
	}

}
