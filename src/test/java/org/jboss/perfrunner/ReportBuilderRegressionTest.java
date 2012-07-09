package org.jboss.perfrunner;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * A collection of tests for stuff that was once actually broken in the report builder.
 *
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
@RunWith(PerfRunner.class)
public class ReportBuilderRegressionTest {

  @Test
  public void testMultipleMethodsWithSameSeriesName1(
      @Varying(name="duplicate series name", from=1, to=10) int dummySeries,
      @Varying(name="x axis 1", axis=Axis.X, from=1, to=10) int dummyX) {
    // no op
  }

  @Test
  public void testMultipleMethodsWithSameSeriesName2(
      @Varying(name="duplicate series name", from=1, to=10) int dummySeries,
      @Varying(name="x axis 2", axis=Axis.X, from=1, to=10) int dummyX) {
    // no op
  }

  @Test
  public void testMultipleMethodsWithSameXAxisName1(
      @Varying(name="unique series name 1", from=1, to=10) int dummySeries,
      @Varying(name="duplicate x axis", axis=Axis.X, from=1, to=10) int dummyX) {
    // no op
  }

  @Test
  public void testMultipleMethodsWithSameXAxisName2(
      @Varying(name="unique series name 2", from=1, to=10) int dummySeries,
      @Varying(name="duplicate x axis", axis=Axis.X, from=1, to=10) int dummyX) {
    // no op
  }

  @Test
  public void testMultipleMethodsWithAllParamsDuplicate1(
      @Varying(name="duplicate series name", from=1, to=10) int dummySeries,
      @Varying(name="x axis", axis=Axis.X, from=1, to=10) int dummyX) {
    // no op
  }

  @Test
  public void testMultipleMethodsWithAllParamsDuplicate2(
      @Varying(name="duplicate series name", from=1, to=10) int dummySeries,
      @Varying(name="x axis", axis=Axis.X, from=1, to=10) int dummyX) {
    // no op
  }

}
