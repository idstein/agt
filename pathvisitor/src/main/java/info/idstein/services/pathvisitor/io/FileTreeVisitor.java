package info.idstein.services.pathvisitor.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Stack;

import info.idstein.services.pathvisitor.model.Directory;
import info.idstein.services.pathvisitor.text.TextFileScanner;

public class FileTreeVisitor extends SimpleFileVisitor<Path> {

	private static final String SUFFIX_TXT_FILE = ".txt";

	private Stack<Directory> directoryStack = new Stack<>();
	private Directory currentDirectory;

	static String pathToNodeName(final Path path) {
		return path.getFileName().toString();
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		currentDirectory = new Directory(pathToNodeName(dir));
		directoryStack.push(currentDirectory);
		return super.preVisitDirectory(dir, attrs);
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		currentDirectory = addToParentDirectory(directoryStack.pop());
		return super.postVisitDirectory(dir, exc);
	}

	private Directory addToParentDirectory(final Directory childDirectory) {
		if (directoryStack.isEmpty())
			return childDirectory;
		final Directory parentDirectory = directoryStack.peek();
		if (childDirectory.hasChildren())
			parentDirectory.addChild(childDirectory);
		return parentDirectory;
	}

	public Directory getRootDirectory() {
		return currentDirectory;
	}

	private final TextFileScanner scanner = new TextFileScanner();

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (isTextFile(file)) {
			currentDirectory.addChild(scanner.processPath(file, pathToNodeName(file)));
		}
		return super.visitFile(file, attrs);
	}

	private boolean isTextFile(Path file) {
		return pathToNodeName(file).endsWith(SUFFIX_TXT_FILE);
	}
}
