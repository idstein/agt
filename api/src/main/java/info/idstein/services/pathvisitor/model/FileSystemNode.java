package info.idstein.services.pathvisitor.model;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({TextFile.class, LongTextFile.class})
public abstract class FileSystemNode  {
	
	private String name;
	
	/**
	 * JAXB requires even for marshal operation a no-argument constructor
	 */
	public FileSystemNode() {
	}
	
	public FileSystemNode(final String name) {
		this.name = Objects.requireNonNull(name, "name can not be null");
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
