package my.samples.methodsync;

import my.samples.methodsync.sample.SampleUsageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodsyncApplicationTests {

	private @Autowired
	SampleUsageService sampleUsageService;

	@Test
	public void contextLoads() {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		HashSet<Future> futures = new HashSet<>();
		for (int i = 0; i <= 2000; i++) {
			Future<?> future = executor.submit((new Runnable() {
				@Override
				public void run() {
					sampleUsageService.incrementKeyVal(15, 1);
				}
			}));
			futures.add(future);
		}

		while (futures.size() > 0) {
			futures.removeAll(futures.stream().filter(f -> f.isDone()).collect(Collectors.toList()));
		}

		long byKey = sampleUsageService.getByKey(15);
		assertEquals(2000L, byKey);
	}
}
