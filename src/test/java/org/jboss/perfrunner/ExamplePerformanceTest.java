package org.jboss.perfrunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PerfRunner.class)
public class ExamplePerformanceTest {

  @Test
  public void testSleep(
      @Varying(axis=Axis.X, name="Sleep Time", from=0, to=100, step=5) int sleepTime,
      @Varying(axis=Axis.SERIES, name="This will appear in the legend") int x
      ) throws InterruptedException {
    Thread.sleep(sleepTime);
  }
}
