package net.bluecow.perfrunner.example;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import net.bluecow.perfrunner.Axis;
import net.bluecow.perfrunner.PerfRunner;
import net.bluecow.perfrunner.Varying;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(PerfRunner.class)
public class BigDecimalVsRegex {

  private static final char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  private static final Random rand = new Random();
  private static final Pattern JSON_NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?");
  private static final PrintWriter OUT;

  static {
    try {
      OUT = new PrintWriter("/dev/null");
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private String randomNumber(int digits) {
    StringBuilder sb = new StringBuilder(digits);
    for (int i = 0; i < digits; i++) {
      sb.append(numbers[rand.nextInt(10)]);
    }
    return sb.toString();
  }

  @Test
  public void bigDecimal(
          @Varying(axis=Axis.X, name="Digits", from=1, to=40) int digits,
          @Varying(axis=Axis.PAGE, name="Iterations", from=10000, to=10000) int iterations) {
    List<BigDecimal> results = new ArrayList<BigDecimal>();
    for (int i = 0; i < iterations; i++) {
      results.add(new BigDecimal(randomNumber(digits)));
    }
    OUT.println(results);
  }

  @Test
  public void regularExpression(
          @Varying(axis=Axis.X, name="Digits", from=1, to=40) int digits,
          @Varying(axis=Axis.PAGE, name="Iterations", from=10000, to=10000) int iterations) {
    List<String> results = new ArrayList<String>();
    for (int i = 0; i < iterations; i++) {
      String randomNumber = randomNumber(digits);
      if (!JSON_NUMBER_PATTERN.matcher(randomNumber).matches()) {
        throw new NumberFormatException("Not a JSON number: \"" + digits + "\"");
      }
      results.add(randomNumber);
    }
    OUT.println(results);
  }

}
