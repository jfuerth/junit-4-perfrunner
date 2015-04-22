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

import java.util.Random;

import net.bluecow.perfrunner.Axis;
import net.bluecow.perfrunner.PerfRunner;
import net.bluecow.perfrunner.Varying;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PerfRunner.class)
public class ExamplePerformanceTest {

  @Test
  public void testSleep(
      @Varying(axis=Axis.X, name="Sleep Time", from=0, to=100, step=5) int sleepTime
      ) throws InterruptedException {
    Thread.sleep(sleepTime);
  }

  @Test
  public void testMultiSleep(
      @Varying(axis=Axis.X, name="Sleep Time", from=0, to=50, step=5) int sleepTime,
      @Varying(axis=Axis.SERIES, name="Calls to sleep()", from=1, to=10) int calls
      ) throws InterruptedException {
    for (int i = 0; i < calls; i++) {

      // deal with the remainder by adding extra time on the last iteration
      int remainder;
      if (i == calls - 1) {
        remainder = sleepTime % calls;
      } else {
        remainder = 0;
      }

      Thread.sleep((sleepTime / calls) + remainder);
    }
  }

  /**
   * Demonstrates introduction of a page axis to declutter the results by
   * breaking it out into multiple charts.
   */
  @Test
  public void testMultiSleepWithRandom(
      @Varying(axis=Axis.X, name="Sleep Time", from=0, to=50, step=5) int sleepTime,
      @Varying(axis=Axis.SERIES, name="Calls to sleep()", from=1, to=10) int calls,
      @Varying(axis=Axis.PAGE, name="Randomness", from=0, to=10, step=2) int randomness
      ) throws InterruptedException {
    Random r = new Random();
    for (int i = 0; i < calls; i++) {

      int randomAddition = randomness == 0 ? 0 : r.nextInt(randomness);

      // deal with the remainder by adding extra time on the last iteration
      int remainder;
      if (i == calls - 1) {
        remainder = sleepTime % calls;
      } else {
        remainder = 0;
      }

      Thread.sleep((sleepTime/calls + remainder + randomAddition));
    }
  }
}
