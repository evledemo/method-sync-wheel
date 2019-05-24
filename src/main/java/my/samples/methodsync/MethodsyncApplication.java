package my.samples.methodsync;

import my.samples.methodsync.sample.SampleUsageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@SpringBootApplication
public class MethodsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(MethodsyncApplication.class, args);
	}

}
