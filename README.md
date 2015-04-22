junit-4-perfrunner
==================

This is a test runner that simplifies the creation of performance and scalability tests.

Usage
-----

Create a test class like this, and give it to JUnit 4.x:

    import net.bluecow.perfrunner.*;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    
    @RunWith(PerfRunner.class)
    public class SmallTest {
    
      @Test
      public void tenDataPoints(
          @Varying(name="Sleep Time", axis=Axis.X, from=10, to=100, step=10) int sleepTime) throws Exception {
        Thread.sleep(sleepTime);
      }
    }

When run, the method tenDataPoints() will be called ten times: with the sleepTime parameter
set to 10, 20, 30, ..., 100. The resulting report looks [like this](http://jfuerth.github.com/junit-4-perfrunner/perfrunner-net.bluecow.perfrunner.example.SmallTest.html).

You can use as many parameters as you like. When you include multiple parameters, PerfRunner calls your method with every
possible combination of the given arguments (their Cartesian Product). For example, the following test:

    import static net.bluecow.perfrunner.Axis.X;
    import net.bluecow.perfrunner.*;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    
    @RunWith(PerfRunner.class)
    public class MultiParameterExample {
    
      @Test
      public void printArguments(
          @Varying(name="Arg 1", from=1, to=3) int arg1,
          @Varying(name="Arg 2", axis=X, from=0, to=500000, step=100000) int arg2) {
        System.out.println("  printArguments("+arg1+","+arg2+")");
      }
    }

produces the following console output:

    printArguments(1,0)
    printArguments(2,0)
    printArguments(3,0)
    printArguments(1,100000)
    printArguments(2,100000)
    printArguments(3,100000)
    printArguments(1,200000)
    printArguments(2,200000)
    printArguments(3,200000)
    printArguments(1,300000)
    printArguments(2,300000)
    printArguments(3,300000)
    printArguments(1,400000)
    printArguments(2,400000)
    printArguments(3,400000)
    printArguments(1,500000)
    printArguments(2,500000)
    printArguments(3,500000)

When you have multiple parameters, sometimes you will want to produce a series of charts rather than one crazy
chart with a thousand different data series on it. You can do this by assigning one of your @Varying parameters
to the PAGE axis. See ExamplePerformanceTest.testMultiSleepWithRandom()
[(source)](https://github.com/jfuerth/junit-4-perfrunner/blob/master/src/test/java/org/jboss/perfrunner/example/ExamplePerformanceTest.java#L40)
[(output)](http://jfuerth.github.com/junit-4-perfrunner/perfrunner-net.bluecow.perfrunner.example.ExamplePerformanceTest.html)
for details.

That's all you need to know. Happy testing!


Recent Developments
-------------------

 * Added reporting capabilities by writing out HTML files that use flot to plot charts
   when displayed in the browser.
   * You can toggle the visibility of a series by clicking its entry in the legend
   * currently just a POC because the generated files embed unminified, uncustomizable JS and CSS resources
   * mousing over a point on the chart displays the number of garbage collections and change in heap size since the previous test ran


The Future
----------

 * Labels for chart axes (needs 3rd party flot extension)
 * Combine and minify jQuery and flot before embedding in the generated HTML
 * indicate GC and heap grow/shrink events right on the chart so you don't have to mouse over a point to find them
 * make the report generator configurable using system properties
   * allow linking to JS and CSS rather than embedding them
   * allow embedding of user-supplied JS and CSS resources
 * publish this project to Maven Central
 * allow the option of multiple identical runs, possibly in random order; aggregate min/max/avg before reporting
 * allow tests to return a long with their own timing information (in fact, it doesn't have to be time; it could be any measurement the test author wants to plot)
