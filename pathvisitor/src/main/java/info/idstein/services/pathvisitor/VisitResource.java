package info.idstein.services.pathvisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import info.idstein.services.pathvisitor.io.FileTreeVisitor;
import info.idstein.services.pathvisitor.model.Directory;

@Path("/visit")
@Produces(MediaType.APPLICATION_JSON)
public class VisitResource {

	/**
	 * Offers a GET web service for traversal of a file tree.
	 * 
	 * Any text file is opened, a word count performed and if the amount of
	 * words exceeds 1000 a per word frequency is also reported.
	 *
	 * @return {@link Directory} directory tree that references only text files.
	 * @throws IOException Any i/o exception is internally mapped to a HTTP 500
	 */
	@GET
	@Path("{directory : .+}")
	public Directory traverseTextFileTree(@PathParam("directory") String directory) throws IOException {
		final java.nio.file.Path location = Paths.get(".", directory);
		final FileTreeVisitor visitor = new FileTreeVisitor();
		Files.walkFileTree(location, visitor);
		return visitor.getRootDirectory();
	}
}
