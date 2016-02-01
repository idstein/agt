package info.idstein.services.pathvisitor.io;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Streams {
	/**
	 * Java 8 does not come with built-in support for streams over iterators based on
	 * {@link java.util.Spliterator}
	 * 
	 * @param iterator An iterator
	 * @return A stream, no support for parallel execution
	 */
	static public <T> Stream<T> fromIterator(final Iterator<T> iterator) {
		Iterable<T> iterable = () -> iterator;
		Stream<T> stream = StreamSupport.stream(iterable.spliterator(), false);
		return stream;
	}

}
