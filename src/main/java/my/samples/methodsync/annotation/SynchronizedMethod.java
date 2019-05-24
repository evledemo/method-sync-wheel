package my.samples.methodsync.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Alternative way for synchronized block.
 * Wrap method call with synchronized block, and synchronize on passed param
 * which is annotated with {@link SynchronizedMethodParam}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SynchronizedMethod {
}
