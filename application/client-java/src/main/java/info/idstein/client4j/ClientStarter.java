package info.idstein.client4j;

import static java.lang.String.format;

import info.idstein.services.pathvisitor.model.Directory;
import info.idstein.services.pathvisitor.model.FileSystemNode;
import info.idstein.services.pathvisitor.model.LongTextFile;
import info.idstein.services.pathvisitor.model.TextFile;

public interface ClientStarter {
	public static final String INDENT_CHARACTER = "\t";

	public static void main(String[] args) {
		final Client client = new Client("http://localhost:8080/services/visit");
		Directory dir = client.visit("src/main/resources/testData");
		print("LONG",LongTextFile.class,dir);
		print("SHORT",TextFile.class,dir);
	}
	
	static void print(String type, Class<? extends FileSystemNode> classOfInterest, Directory fromRoot) {
		System.out.println(format("**** %s FILES ****", type));
		DirectoryFormatter filesFormatter = new DirectoryFormatter(classOfInterest);
		System.out.println(filesFormatter.format(fromRoot));
	}
}
