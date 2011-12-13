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
          @Varying(from=1, to=10) int threadCount,
          @Varying(from=100000, to=1000000, step=100000) int entryCount) {

          // code here that relies on threadCount and entryCount
      }

    }

When run, the method demonstrationTest will be called with all the combinations of
threadCount and entryCount that you'd expect. In the above example, demonstrationTest
would be called a total of 100 times.


The Future
----------

Next on the list is to add reporting capabilities. Although the test runner itself is
useful as-is (because JUnit keeps track of how long each test took to execute), I'd
like to see performance charts produced based on the timing data collected for each
collection of test method runs.
