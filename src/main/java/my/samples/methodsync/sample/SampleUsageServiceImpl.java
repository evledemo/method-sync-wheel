package my.samples.methodsync.sample;

import my.samples.methodsync.annotation.SynchronizedMethod;
import my.samples.methodsync.annotation.SynchronizedMethodParam;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SampleUsageServiceImpl implements SampleUsageService {

	private static Map<Integer, Integer> data = new HashMap<>();

	@Override
	@SynchronizedMethod
	public void incrementKeyVal(@SynchronizedMethodParam Integer key,
								final Integer increment) {
		// not sync operation
		if (key != null) {
			try {
				Thread.sleep((long)(Math.random() * 10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Long byKey = getByKey(key);
			data.compute(key, (k, v) -> (v == null) ? 0 : v + increment);
		}
	}

	@Override
	public Long getByKey(Integer key) {
		return Optional.ofNullable(data.get(key)).map(v -> Long.valueOf(v)).orElse(null);
	}
}
