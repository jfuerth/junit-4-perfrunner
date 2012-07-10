package org.jboss.perfrunner.example;

import org.jboss.perfrunner.Axis;
import org.jboss.perfrunner.PerfRunner;
import org.jboss.perfrunner.Varying;
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
      @Varying(name="Sleep Time", axis=Axis.X, from=10, to=100, step=10) int sleepTime) throws Exception {
    Thread.sleep(sleepTime);
  }
}
