package my.samples.methodsync.sample;

import my.samples.methodsync.annotation.SynchronizedMethod;
import my.samples.methodsync.annotation.SynchronizedMethodParam;

public interface SampleUsageService {

	@SynchronizedMethod
	void incrementKeyVal(@SynchronizedMethodParam Integer key,
						 Integer increment);

	Long getByKey(Integer key);
}
