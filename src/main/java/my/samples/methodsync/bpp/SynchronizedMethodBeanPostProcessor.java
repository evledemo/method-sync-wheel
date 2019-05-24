package my.samples.methodsync.bpp;

import my.samples.methodsync.annotation.SynchronizedMethod;
import my.samples.methodsync.annotation.SynchronizedMethodParam;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Post processor for synchronizing method call, on single param.
 * It is expected that type of method param returns correctly
 * {@link Object#hashCode()}.
 * If annotated method param is null than synchronization executes on proxy
 * object.
 */
@Component
public class SynchronizedMethodBeanPostProcessor implements BeanPostProcessor {

	Map<String, Class> map = new HashMap<String, Class>();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		boolean anyMatch =
				Stream.of(bean.getClass().getDeclaredMethods()).anyMatch(m -> m.isAnnotationPresent(SynchronizedMethod.class));
		if (anyMatch)
			map.put(beanName, bean.getClass());

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Class beanClass = map.get(beanName);
		if (beanClass != null) {
			return Proxy.newProxyInstance(beanClass.getClassLoader(),
					beanClass.getInterfaces(), new ParamSynchronizedInvocationHandler(bean));
		}
		return bean;
	}

	private class ParamSynchronizedInvocationHandler implements InvocationHandler {

		private Object bean;

		public ParamSynchronizedInvocationHandler(Object bean) {
			this.bean = bean;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Parameter[] parameters = method.getParameters();
			if (method.isAnnotationPresent(SynchronizedMethod.class)) {
				for (int i = 0; i < parameters.length; i++) {
					boolean anyMatch =
							parameters[i].isAnnotationPresent(SynchronizedMethodParam.class);
					if (anyMatch) {
						Object o = args[i];
						if (o != null) {
							synchronized (String.valueOf(o.hashCode()).intern()) {
								return method.invoke(bean, args);
							}
						} else {
							synchronized (proxy) {
								return method.invoke(bean, args);
							}
						}
					}
				}
			}
			return method.invoke(bean, args);
		}
	}
}
