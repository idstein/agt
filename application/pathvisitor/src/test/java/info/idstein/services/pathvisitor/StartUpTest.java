package info.idstein.services.pathvisitor;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;

public class StartUpTest {
	
	@After
	public void resetInput() {
		System.setIn(System.in);
	}
	
	@Test
	public void enterTerminatesExecution() throws Exception {
		Callable<Void> task = () -> {
			StartUp.main(null);
			return null;
		};
		ExecutorService executor = Executors.newFixedThreadPool(1);
		ByteArrayInputStream in = new ByteArrayInputStream("\n".getBytes());
		System.setIn(in);
		Future<Void> future = executor.submit(task);
		future.get(5, TimeUnit.SECONDS);
	}

}
