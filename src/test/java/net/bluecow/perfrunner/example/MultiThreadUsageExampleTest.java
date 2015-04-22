/*
 * Copyright 2011 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bluecow.perfrunner.example;

import static net.bluecow.perfrunner.Axis.X;
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

import net.bluecow.perfrunner.PerfRunner;
import net.bluecow.perfrunner.Varying;

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
      @Varying(name="Threads", from=1, to=10) int threadCount,
      @Varying(name="List Size", axis=X, from=100000, to=1000000, step=100000) int entryCount)
          throws InterruptedException, ExecutionException {

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
}
