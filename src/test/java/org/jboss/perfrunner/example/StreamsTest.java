package org.jboss.perfrunner.example;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.jboss.perfrunner.Axis;
import org.jboss.perfrunner.PerfRunner;
import org.jboss.perfrunner.Varying;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PerfRunner.class)
public class StreamsTest {

  private static final List<BigInteger> NUMBERS;
  static {
    NUMBERS = new ArrayList<>();
    for (int i = 0; i < 1_000_000; i++) {
      NUMBERS.add(BigInteger.valueOf(i));
    }
  }

  @Test
  public void testDifferentSummingApproaches(
      @Varying(name="Method", axis=Axis.SERIES, from=0, to=2) int method,
      @Varying(name="List Length", axis=Axis.X, from=0, to=1_000_000, step=100_000) int listLength,
      @Varying(name="Trial", axis=Axis.PAGE, from=1, to=10) int trial) throws Exception {

    List<BigInteger> input = NUMBERS.subList(0, listLength);
    BigInteger sum;
    switch(method) {
    case 0:
      sum = BigInteger.ZERO;
      for (BigInteger d : input) {
        sum = sum.add(d);
      }
      break;

    case 1:
      sum = input.stream().reduce(BigInteger.ZERO, (d, e) -> d.add(e));
      break;

    case 2:
      sum = input.parallelStream().reduce(BigInteger.ZERO, (d, e) -> d.add(e));
      break;

    default:
      throw new IllegalArgumentException("Bad method " + method);
    }
    //System.out.println("Sum=" + sum);
  }
}
