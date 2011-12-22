package org.jboss.perfrunner;

import java.util.Random;

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

  @Test
  public void testMultiSleep(
      @Varying(axis=Axis.X, name="Sleep Time", from=0, to=50, step=5) int sleepTime,
      @Varying(axis=Axis.SERIES, name="Calls to sleep()", from=1, to=10) int calls
      ) throws InterruptedException {
    for (int i = 0; i < calls; i++) {
      Thread.sleep((sleepTime/calls));
    }
  }

  @Test
  public void testMultiSleepWithRandom(
      @Varying(axis=Axis.X, name="Sleep Time", from=0, to=50, step=5) int sleepTime,
      @Varying(axis=Axis.SERIES, name="Calls to sleep()", from=1, to=10) int calls,
      @Varying(axis=Axis.SERIES, name="Randomness", from=0, to=10, step=2) int randomness
      ) throws InterruptedException {
    Random r = new Random();
    for (int i = 0; i < calls; i++) {
      int randomAddition = randomness == 0 ? 0 : r.nextInt(randomness);
      Thread.sleep((sleepTime/calls + randomAddition));
    }
  }
}
