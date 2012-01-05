package org.jboss.perfrunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A test that makes charts with small data sets (fast to run and easy to debug in the browser).
 *
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
@RunWith(PerfRunner.class)
public class SmallTest {

  @Test
  public void tenDataPoints(
      @Varying(name="Sleep Time", axis=Axis.X, to=10) int sleepTime) throws Exception {
    Thread.sleep(0, sleepTime);
  }
}
