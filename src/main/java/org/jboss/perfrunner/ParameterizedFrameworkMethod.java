package org.jboss.perfrunner;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.runners.model.FrameworkMethod;

class ParameterizedFrameworkMethod extends FrameworkMethod {

  private final Integer[] params;

  public ParameterizedFrameworkMethod(Method method, int[] params) {
    super(method);

    // Have to copy into a boxed array because that's what Method.invoke will require
    this.params = new Integer[params.length];
    for (int i = 0; i < params.length; i++) {
      this.params[i] = params[i];
    }
  }

  /**
   * Invokes the target method with the parameters that were given in the constructor.
   *
   * @param target The target instance for the invocation.
   * @param ignored ignored.
   */
  @Override
  public Object invokeExplosively(Object target, Object... ignored) throws Throwable {
    return super.invokeExplosively(target, (Object[]) params);
  }

  /**
   * Returns the parameters that will be used when invoking the test method.
   */
  public Integer[] getParameters() {
    return params;
  }

  @Override
  public String toString() {
    return String.format("ParameterizedFrameworkMethod@%8x: %s",
        System.identityHashCode(this), Arrays.toString(params));
  }
}
