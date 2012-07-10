/*
 * Copyright 2012 Red Hat Inc.
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

package org.jboss.perfrunner.example;

import static org.junit.Assert.assertEquals;

import org.jboss.perfrunner.Axis;
import org.jboss.perfrunner.PerfRunner;
import org.jboss.perfrunner.Varying;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PerfRunner.class)
public class StringConcatencationTest {

  @Test
  public void concatenateStrings(
          @Varying(name="String Length", axis=Axis.X, from=100, to=1000) int length) {
    String s = "";
    for (int i = 0; i < length; i++) {
      s += "x";
    }

    // just to ensure the value is used
    assertEquals(length, s.length());
  }

  @Test
  public void concatenateStringBuffer(
          @Varying(name="String Length", axis=Axis.X, from=100, to=1000) int length) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      sb.append("x");
    }

    // just to ensure the value is used
    assertEquals(length, sb.length());
  }

  @Test
  public void concatenateStringBuilder(
          @Varying(name="String Length", axis=Axis.X, from=100, to=1000) int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append("x");
    }

    // just to ensure the value is used
    assertEquals(length, sb.length());
  }

  /**
   * Running exactly the same String Buffer test again as a cautionary example
   * of why you need to take microbenchmark results with a grain of salt!
   */
  @Test
  public void concatenateStringBufferAgain(
          @Varying(name="String Length", axis=Axis.X, from=100, to=1000) int length) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < length; i++) {
      sb.append("x");
    }

    // just to ensure the value is used
    assertEquals(length, sb.length());
  }

  /**
   * Running exactly the same String Builder test again as a cautionary example
   * of why you need to take microbenchmark results with a grain of salt!
   */
  @Test
  public void concatenateStringBuilderAgain(
          @Varying(name="String Length", axis=Axis.X, from=100, to=1000) int length) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append("x");
    }

    // just to ensure the value is used
    assertEquals(length, sb.length());
  }

}
