junit-4-perfrunner
==================

This is a test runner that simplifies the creation of performance and scalability tests.

Usage
-----

Create a test class like this, and give it to JUnit 4.x:

    @RunWith(PerfRunner.class)
    public class MultiThreadUsageExampleTest {

      @Test
      public void demonstrationTest(
          @Varying(name="Threads", from=1, to=10) int threadCount,
          @Varying(name="List Size", axis=Axis.X, from=100000, to=1000000, step=100000) int entryCount) {

          // code here that relies on threadCount and entryCount
      }

    }

When run, the method demonstrationTest will be called with all the combinations of
threadCount and entryCount that you'd expect. In the above example, demonstrationTest
would be called a total of 100 times.


Recent Developments
-------------------

 * Added reporting capabilities by writing out HTML files that use flot to plot charts
   when displayed in the browser.
   * You can toggle the visibility of a series by clicking its entry in the legend
   * currently just a POC because the generated files depend on external JS and CSS files


The Future
----------

 * Labels for chart axes (needs 3rd party flot extension)
 * package flot, minimize it, and embed it in the generated HTML
   * also embed the CSS file
 * make the report generator configurable using system properties
 * publish the test runner to Maven Central
 * allow the option of multiple identical runs, possibly in random order; aggregate min/max/avg before reporting
