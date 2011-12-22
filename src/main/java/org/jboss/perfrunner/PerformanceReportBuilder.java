package org.jboss.perfrunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

public class PerformanceReportBuilder extends RunListener {

  /**
   * A method parameter name and the value that was given for it during a
   * particular method invocation.
   */
  public static class ParamValue {
    private final String paramName;
    private final double paramValue;

    public ParamValue(String paramName, double paramValue) {
      this.paramName = paramName;
      this.paramValue = paramValue;
    }

    public String getParamName() {
      return paramName;
    }

    public double getParamValue() {
      return paramValue;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
          + ((paramName == null) ? 0 : paramName.hashCode());
      long temp;
      temp = Double.doubleToLongBits(paramValue);
      result = prime * result + (int) (temp ^ (temp >>> 32));
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      ParamValue other = (ParamValue) obj;
      if (paramName == null) {
        if (other.paramName != null)
          return false;
      }
      else if (!paramName.equals(other.paramName))
        return false;
      if (Double.doubleToLongBits(paramValue) != Double
          .doubleToLongBits(other.paramValue))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return paramName + "=" + paramValue;
    }
  }

  /**
   * The unique identifier for a Series. Instances are immutable. Suitable for
   * use as a key in a hash collection.
   */
  private static class SeriesKey {
    private final List<ParamValue> paramValues;

    /**
     * Creates a new series key based on the given param values.
     *
     * @param paramValues
     *          the collection of parameter values that define this series. A
     *          copy is made of this list, so you are free to modify the one
     *          that you passed in.
     */
    public SeriesKey(List<ParamValue> paramValues) {
      this.paramValues = new ArrayList<ParamValue>(paramValues);
    }
    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
          + ((paramValues == null) ? 0 : paramValues.hashCode());
      return result;
    }
    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      SeriesKey other = (SeriesKey) obj;
      if (paramValues == null) {
        if (other.paramValues != null)
          return false;
      }
      else if (!paramValues.equals(other.paramValues))
        return false;
      return true;
    }
    @Override
    public String toString() {
      return paramValues.toString();
    }
  }

  private static class Point {
    private final double x;
    private final double y;
    public Point(double x, double y) {
      this.x = x;
      this.y = y;
    }
    public void appendTo(Appendable sb) throws IOException {
      sb.append("[").append(String.valueOf(x)).append(",").append(String.valueOf(y)).append("]");
    }
  }

  private static class Series {
    private final SeriesKey key;
    private final List<Point> points = new ArrayList<Point>();

    public Series(SeriesKey key) {
      this.key = key;
    }

    public void addPoint(double x, long nanos) {
      points.add(new Point(x, nanos));
    }
    public void appendTo(Appendable sb) throws IOException {
      sb.append("\n { data: [");
      boolean first = true;
      for (Point p : points) {
        if (!first) {
          sb.append(",");
        }
        p.appendTo(sb);
        first = false;
      }
      sb.append("], label: \"").append(key.toString()).append("\" }");
    }
  }

  private static class MethodRunData {
    private final Map<SeriesKey, Series> series = new LinkedHashMap<SeriesKey, Series>();
    private final String className;
    private final String methodName;

    public MethodRunData(PerfRunDescription desc) {
      this.className = desc.getClassName();
      this.methodName = desc.getMethodName();

      // TODO this will be series meta definitions
      // FIXME for now, assume two params: first is series and second is x-axis
//      for (int i = 0; i < desc.getParamValues().size(); i++) {
//        series.add(new Series("" + i)); // TODO series name annotation
//      }
    }

    public boolean isSameChart(PerfRunDescription desc) {
      return desc.getClassName().equals(className) && desc.getMethodName().equals(methodName);
    }

    public void addTestRunData(PerfRunDescription desc, long testDurationNanos) {
      if (!isSameChart(desc)) throw new IllegalArgumentException("Given description is for data that doesn't belong on this chart");

      // FIXME for now, assume two params: first is series and second is x-axis
      SeriesKey key = new SeriesKey(Collections.singletonList(new ParamValue("first param", desc.getParamValues().get(0))));
      Series s = series.get(key);
      if (s == null) {
        s = new Series(key);
        series.put(key, s);
      }

      // FIXME assuming param 1 is x-axis
      s.addPoint(desc.getParamValues().get(1), testDurationNanos);
    }

    public void appendJavascriptTo(Appendable sb, int chartNum) throws IOException {
      sb.append("\n<h1>" + className + "." + methodName + "</h1>\n");
      sb.append("<div id=chart" + chartNum + " style='width: 800px; height: 600px'></div>\n");
      sb.append("<script language=javascript>\n");
      sb.append("$(function() {\n");
      sb.append(" $.plot($('#chart" + chartNum + "'), ");
      sb.append("[");
      boolean first = true;
      for (Series s : series.values()) {
        if (!first) {
          sb.append(",");
        }
        s.appendTo(sb);
        first = false;
      }
      sb.append("\n]\n");
      sb.append(");\n");
      sb.append("});\n");
      sb.append("</script>\n");
      chartNum++;
    }
  }

  private PrintWriter out;

  /**
   * Time the most recent test started.
   */
  private long startTime;

  private MethodRunData runData;

  /**
   * Keeps track of how many charts we've made in the output file. Needed for
   * creating unique div identifiers.
   */
  private int chartNum;

  @Override
  public void testRunStarted(Description description) throws FileNotFoundException {
    out = new PrintWriter("perfrunner-" + description.getClassName() + ".html");
    String reportTitle = "PerfRunner report: " + description.getDisplayName() + " (generated on " + new Date() + ")";
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println(" <title>" + reportTitle + "</title>");
    out.println(" <script language=javascript src='jquery.js'></script>");
    out.println(" <script language=javascript src='jquery.flot.js'></script>");
    out.println("</head>");
    out.println("<body>");
    out.println(" <h1>" + reportTitle + "</h1>");
  }

  @Override
  public void testStarted(Description description) {
    startTime = System.nanoTime();
  }

  @Override
  public void testFinished(Description description) {
    long testDuration = System.nanoTime() - startTime;

    try {
      PerfRunDescription desc = new PerfRunDescription(description);

      // check if run data needs to be dumped or (re)created
      if (runData == null) {
        runData = new MethodRunData(desc);
      }
      else if (!runData.isSameChart(desc)) {
        // done with this one. dump the chart data!
        runData.appendJavascriptTo(out, chartNum++);
        runData = new MethodRunData(desc);
      }

      // in all cases, we add this observation
      runData.addTestRunData(desc, testDuration);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void testRunFinished(Result result) {
    if (runData != null) {
      try {
        runData.appendJavascriptTo(out, chartNum++);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    out.println("</body>");
    out.flush();
    out.close();
  }
}
