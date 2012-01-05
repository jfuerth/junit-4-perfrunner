package org.jboss.perfrunner;

import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

class RunStats {

  private final long nanoTime;
  private final int gcCount;
  private final int heapInUse;

  private RunStats(long timeNanos, int gcCount, int heapInUse) {
    this.nanoTime = timeNanos;
    this.gcCount = gcCount;
    this.heapInUse = heapInUse;
  }

  public static RunStats create() {
    int gcTotal = 0;
    for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
      gcTotal += gcBean.getCollectionCount();
    }

    return new RunStats(
        System.nanoTime(),
        gcTotal,
        (int) ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed());
  }

  public RunStats relativeToNow() {
    RunStats now = RunStats.create();
    return new RunStats(now.nanoTime - nanoTime,
        now.gcCount - gcCount,
        now.heapInUse - heapInUse);
  }

  /**
   * Returns the test execution time in milliseconds. The value is likely to
   * include fractional milliseconds, because the number was measured using
   * System.nanoTime().
   */
  public double timeMillis() {
    return nanoTime / 1000000.0;
  }

  /**
   * Appends a JavaScript object literal representation of the run statistics to {@code sb}.
   *
   * @param sb target for the generated JavaScript
   */
  public void appendTo(Appendable sb) throws IOException {
    sb.append("{ nanoTime: " + nanoTime + ", gcCount: " + gcCount + ", heapInUse: " + heapInUse + "}");
  }
}
