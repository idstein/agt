package info.idstein.client4j;

import java.util.StringJoiner;

import info.idstein.services.pathvisitor.model.Directory;
import info.idstein.services.pathvisitor.model.FileSystemNode;

public class DirectoryFormatter {
	
	public static final String INDENT_CHARACTER = "\t";

	private final Class<? extends FileSystemNode> classOfInterest;
	
	public DirectoryFormatter(Class<? extends FileSystemNode> classOfInterest) {
		this.classOfInterest = classOfInterest;
	}
	
	public String format(Directory root) {
		StringJoiner stringJoiner = new StringJoiner("\n");
		traverseTree(stringJoiner, "", root);
		return stringJoiner.toString();
	}
	
	void traverseTree(StringJoiner joiner, String prefix, Directory root) {
		for (FileSystemNode node : root) {
			if (node instanceof Directory || classOfInterest.equals(node.getClass())) {
				joiner.add(prefix + node);
				if (node instanceof Directory) {
					traverseTree(joiner, INDENT_CHARACTER + prefix, (Directory) node);
				}
			}
		}
	}
}
