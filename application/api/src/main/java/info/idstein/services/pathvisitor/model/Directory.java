package info.idstein.services.pathvisitor.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Directory extends FileSystemNode implements Iterable<FileSystemNode> {
	private final List<FileSystemNode> children = new ArrayList<>();
	
	public void addChild(final FileSystemNode node) {
		children.add(node);
	}
	
	/**
	 * JAXB requires even for marshal operation a no-argument constructor
	 */
	public Directory() {
	}

	public Directory(final String name) {
		super(name);
	}

	@Override
	public Iterator<FileSystemNode> iterator() {
		return children.iterator();
	}
	
	public boolean hasChildren() {
		return !children.isEmpty();
	}
}
