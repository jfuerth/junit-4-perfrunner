package org.jboss.perfrunner;

import static org.junit.Assert.assertEquals;

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
