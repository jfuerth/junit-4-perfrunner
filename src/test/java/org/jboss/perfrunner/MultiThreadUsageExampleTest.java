package org.jboss.perfrunner;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PerfRunner.class)
public class MultiThreadUsageExampleTest {

  @Before
  public void beforeTest() {
    System.out.println("->beforeTest");
  }

  @After
  public void afterTest() {
    System.out.println("<-afterTest");
  }

  @Test
  public void fillArrayBlockingQueue(
      @Varying(from=1, to=10) int threadCount,
      @Varying(from=100000, to=1000000, step=100000) int entryCount) throws InterruptedException, ExecutionException {

    final Collection<Integer> list = new ArrayBlockingQueue<Integer>(entryCount);

    ExecutorService exec = Executors.newFixedThreadPool(threadCount);

    class ListAdder implements Callable<Object> {
      private final int addCount;

      public ListAdder(int addCount) {
        super();
        this.addCount = addCount;
      }

      public Object call() throws Exception {
        for (int i = 0; i < addCount; i++) {
          list.add(Integer.valueOf(0));
        }
        return null;
      }
    };

    final List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
    int addsPerThread = entryCount / threadCount;
    int remainder = entryCount % threadCount;
    for (int i = 0; i < threadCount; i++) {
      if (i == 0) {
        tasks.add(new ListAdder(addsPerThread + remainder));
      } else {
        tasks.add(new ListAdder(addsPerThread));
      }
    }

    exec.invokeAll(tasks);
    exec.shutdown();
    exec.awaitTermination(1, TimeUnit.DAYS);

    assertEquals(entryCount, list.size());
  }

  @Test
  public void printArguments(
      @Varying(from=1, to=10) int threadCount,
      @Varying(from=0, to=1000000, step=100000) int entryCount) {
    System.out.println("  printArguments("+threadCount+","+entryCount+")");
  }
}
