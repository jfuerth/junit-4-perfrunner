/*
 * Copyright 2012 Red Hat Inc.
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

import net.bluecow.perfrunner.Axis;
import net.bluecow.perfrunner.PerfRunner;
import net.bluecow.perfrunner.Varying;

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
