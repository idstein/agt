package info.idstein.client4j;

import static java.lang.String.format;

import info.idstein.services.pathvisitor.model.Directory;
import info.idstein.services.pathvisitor.model.FileSystemNode;
import info.idstein.services.pathvisitor.model.LongTextFile;
import info.idstein.services.pathvisitor.model.TextFile;

public interface ClientStarter {
	public static final String DEFAULT_LOCATION_PATH = "src/main/resources/testData";
	public static final String BASE_VISIT_SERVICE_URI = "http://localhost:8080/services/visit";
	public static final String INDENT_CHARACTER = "\t";

	public static void main(String[] args) {
		final Client client = new Client(args.length == 2 ? args[1] : BASE_VISIT_SERVICE_URI);
		Directory dir = client.visit(args.length == 1 ? args[0] : DEFAULT_LOCATION_PATH);
		print("LONG",LongTextFile.class,dir);
		print("SHORT",TextFile.class,dir);
	}
	
	static void print(String type, Class<? extends FileSystemNode> classOfInterest, Directory fromRoot) {
		System.out.println(format("**** %s FILES ****", type));
		DirectoryFormatter filesFormatter = new DirectoryFormatter(classOfInterest);
		System.out.println(filesFormatter.format(fromRoot));
	}
}
