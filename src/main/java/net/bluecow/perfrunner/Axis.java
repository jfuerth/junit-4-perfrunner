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

package net.bluecow.perfrunner;

public enum Axis {
  /**
   * Not an axis at all, really: this indicates that variation in this parameter
   * should be combined with variation in all other SERIES parameters to produce
   * the name of a data series on the report.
   * <p>
   * Any number of varying parameters (including 0) to a given method can be
   * marked as SERIES. If no parameters are marked as SERIES, the chart will have
   * just one line and no legend will appear.
   */
  SERIES,

  /**
   * Indicates that the values of this varying parameter should constitute the X
   * axis values on the report.
   * <p>
   * Exactly one varying parameter to a given method must be on the X axis.
   */
  X,

  /**
   * Indicates that variation in this parameter should be combined with
   * variation in all other PAGE parameters to produce the name of a page
   * (broken out chart) on the report.
   * <p>
   * Any number of varying parameters (including 0) to a given method can be
   * marked as PAGE axis.
   */
  PAGE;
}
