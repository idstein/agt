package info.idstein.services.pathvisitor;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConcurrentClientSupportTest {
	private HttpServer server;

	@Before
	public void setUp() throws Exception {
		server = StartUp.startServer();
	}
	
	@After
	public void tearDown() throws Exception {
		server.shutdownNow();
	}
	
	@Test
	public void requestsFromMultipleClients() throws InterruptedException {
		final int concurrentInstances = 20;
		List<Runnable> concurrentClientRunnables = Collections.nCopies(concurrentInstances, () -> {
			Client c = ClientBuilder.newClient();
			WebTarget target = c.target(StartUp.BASE_URI);
			target.path("visit/src/main/resources/testData").request().get();
		});
		assertConcurrent("Unable to execute concurrent clients!", concurrentClientRunnables, 20);
	}
	
	/**
	 * THIRD-PARTY use instead of concurrentunit due to simple API
	 * https://github.com/junit-team/junit/wiki/Multithreaded-code-and-concurrency
	 * 
	 * @param message Failure message
	 * @param runnables List of concurrent executed {@link Runnable}s
	 * @param maxTimeoutSeconds Timeout to wait until interrupted and fail
	 * @throws InterruptedException 
	 */
	public static void assertConcurrent(final String message, final List<? extends Runnable> runnables, final int maxTimeoutSeconds) throws InterruptedException {
	    final int numThreads = runnables.size();
	    final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());
	    final ExecutorService threadPool = Executors.newFixedThreadPool(numThreads);
	    try {
	        final CountDownLatch allExecutorThreadsReady = new CountDownLatch(numThreads);
	        final CountDownLatch afterInitBlocker = new CountDownLatch(1);
	        final CountDownLatch allDone = new CountDownLatch(numThreads);
	        for (final Runnable submittedTestRunnable : runnables) {
	            threadPool.submit(new Runnable() {
	                public void run() {
	                    allExecutorThreadsReady.countDown();
	                    try {
	                        afterInitBlocker.await();
	            submittedTestRunnable.run();
	                    } catch (final Throwable e) {
	                        exceptions.add(e);
	                    } finally {
	                        allDone.countDown();
	                    }
	                }
	            });
	        }
	        // wait until all threads are ready
	        assertTrue("Timeout initializing threads! Perform long lasting initializations before passing runnables to assertConcurrent", allExecutorThreadsReady.await(runnables.size() * 10, TimeUnit.MILLISECONDS));
	        // start all test runners
	        afterInitBlocker.countDown();
	        assertTrue(message +" timeout! More than" + maxTimeoutSeconds + "seconds", allDone.await(maxTimeoutSeconds, TimeUnit.SECONDS));
	    } finally {
	        threadPool.shutdownNow();
	    }
	    Assert.assertThat(message + "failed with exception(s)", exceptions, iterableWithSize(0));
	}
}
