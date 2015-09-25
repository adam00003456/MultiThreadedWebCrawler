import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

public class ThreadStressTest {

	private static final int WARM_RUNS = 3;
	private static final int TIME_RUNS = 5;
	private static final int THREADS = 5;

	/**
	 * Tests the inverted index output multiple times, to make sure the
	 * results are always consistent.
	 */
	@Test(timeout = ThreadTest.TIMEOUT * TIME_RUNS)
	public void testIndexConsistency() {
		ThreadTest.ThreadOutputTest tester =
				new ThreadTest.ThreadOutputTest(String.valueOf(THREADS));

		for (int i = 0; i < TIME_RUNS; i++) {
			tester.testThreadIndexComplex();
		}
	}

	/**
	 * Tests the search result output multiple times, to make sure the
	 * results are always consistent.
	 */
	@Test(timeout = ThreadTest.TIMEOUT * TIME_RUNS)
	public void testSearchConsistency() {
		ThreadTest.ThreadOutputTest tester =
				new ThreadTest.ThreadOutputTest(String.valueOf(THREADS));

		for (int i = 0; i < TIME_RUNS; i++) {
			tester.testThreadSearchComplex();
		}
	}

	/**
	 * Makes sure that your code runs faster with more threads.
	 */
	@Test(timeout = ThreadTest.TIMEOUT * (WARM_RUNS + TIME_RUNS))
	public void testRuntime() {
    		double singleAverage = benchmark(String.valueOf(1)) / 1000000000.0;
    		double threadAverage = benchmark(String.valueOf(THREADS)) / 1000000000.0;

    		System.out.printf("%d Threads: %.2f s%n", 1, singleAverage);
    		System.out.printf("%d Threads: %.2f s%n", THREADS, threadAverage);
    		System.out.printf("  Speedup: %.2f %n", singleAverage / threadAverage);

    		Assert.assertTrue(singleAverage >= threadAverage);
	}

	private double benchmark(String numThreads) {
		String base = ProjectTest.getBaseDirectory();

		Path input  = Paths.get(base, "input", "index");
		Path query  = Paths.get(base, "input", "queries", "complex.txt");

		String[] args = new String[] {
				"-d", input.toAbsolutePath().normalize().toString(),
				"-q", query.toAbsolutePath().normalize().toString(),
				"-t", numThreads
		};

		long total = 0;
		long start = 0;

    		try {
    			for (int i = 0; i < WARM_RUNS; i++) {
    				Driver.main(args);
    			}

    			for (int i = 0; i < TIME_RUNS; i++) {
    				start = System.nanoTime();
    				Driver.main(args);
    				total += System.nanoTime() - start;
    			}
    		}
    		catch (Exception e) {
    			StringWriter writer = new StringWriter();
    			e.printStackTrace(new PrintWriter(writer));

    			Assert.fail(String.format(
    					"%n" + "Test Case: %s%n" + "Exception: %s%n",
    					"Benchmark: " + numThreads, writer.toString()));
    		}

    		return (double) total / TIME_RUNS;
	}
}
