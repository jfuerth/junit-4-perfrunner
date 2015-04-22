/*
 * Copyright 2011 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bluecow.perfrunner;

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
