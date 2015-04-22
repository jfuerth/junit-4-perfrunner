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

import static org.junit.Assert.assertEquals;
import net.bluecow.perfrunner.PerfRunDescription;

import org.junit.Test;
import org.junit.runner.Description;

public class PerfRunDescriptionTest {

  @Test
  public void testOneVaryingParam() {
    Description d = Description.createTestDescription(PerfRunDescriptionTest.class, "methodName[1000]");
    PerfRunDescription prd = new PerfRunDescription(d);
    assertEquals(PerfRunDescriptionTest.class.getName(), prd.getClassName());
    assertEquals("methodName", prd.getMethodName());
    assertEquals(1, prd.getParamValues().size());
    assertEquals(1000.0, prd.getParamValues().get(0).doubleValue(), 0.001);
  }

  @Test
  public void testOneVaryingParamSingleChar() {
    Description d = Description.createTestDescription(PerfRunDescriptionTest.class, "methodName[6]");
    PerfRunDescription prd = new PerfRunDescription(d);
    assertEquals(PerfRunDescriptionTest.class.getName(), prd.getClassName());
    assertEquals("methodName", prd.getMethodName());
    assertEquals(1, prd.getParamValues().size());
    assertEquals(6.0, prd.getParamValues().get(0).doubleValue(), 0.001);
  }

  @Test
  public void testTwoVaryingParams() {
    Description d = Description.createTestDescription(PerfRunDescriptionTest.class, "methodName[1000, 3456]");
    PerfRunDescription prd = new PerfRunDescription(d);
    assertEquals(PerfRunDescriptionTest.class.getName(), prd.getClassName());
    assertEquals("methodName", prd.getMethodName());
    assertEquals(2, prd.getParamValues().size());
    assertEquals(1000.0, prd.getParamValues().get(0).doubleValue(), 0.001);
    assertEquals(3456.0, prd.getParamValues().get(1).doubleValue(), 0.001);
  }

  @Test
  public void testTwoVaryingParamsSingleChar() {
    Description d = Description.createTestDescription(PerfRunDescriptionTest.class, "methodName[8, 3]");
    PerfRunDescription prd = new PerfRunDescription(d);
    assertEquals(PerfRunDescriptionTest.class.getName(), prd.getClassName());
    assertEquals("methodName", prd.getMethodName());
    assertEquals(2, prd.getParamValues().size());
    assertEquals(8.0, prd.getParamValues().get(0).doubleValue(), 0.001);
    assertEquals(3.0, prd.getParamValues().get(1).doubleValue(), 0.001);
  }

}
