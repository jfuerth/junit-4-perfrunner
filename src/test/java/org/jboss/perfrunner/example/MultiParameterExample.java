package org.jboss.perfrunner.example;

import static org.jboss.perfrunner.Axis.X;

import org.jboss.perfrunner.PerfRunner;
import org.jboss.perfrunner.Varying;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Examples of using PerfRunner test methods with multiple parameters.
 *
 * @author Jonathan Fuerth <jfuerth@redhat.com>
 */
@RunWith(PerfRunner.class)
public class MultiParameterExample {

  /**
   * A basic two-parameter method that demonstrates (by printing to the console)
   * how all combinations of parameters are fed to the test by PerfRunner.
   *
   * @param arg1 The first parameter, ranging from 1 to 3.
   * @param arg2 The second parameter, ranging from 0 to 500,000 in steps of 100,000.
   */
  @Test
  public void printArguments(
      @Varying(name="Arg 1", from=1, to=3) int arg1,
      @Varying(name="Arg 2", axis=X, from=0, to=500000, step=100000) int arg2) {
    System.out.println("  printArguments("+arg1+","+arg2+")");
  }

}
