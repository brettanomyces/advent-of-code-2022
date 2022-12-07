package advent.of.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class DailyTest {

  protected final DailyChallenge day;
  private final String[] results;

  public DailyTest(DailyChallenge day, String ... results) {
    this.day = day;
    this.results = results;
  }

  @Test
  public void runPart1Example() {
    String result = day.part1(day.exampleData());
    assertEquals(results[0], result);
  }

  @Test
  public void runPart1() {
    String result = day.part1(day.data());
    assertEquals(results[1], result);
  }

  @Test
  public void runPart2Example() {
    String result = day.part2(day.exampleData());
    assertEquals(results[2], result);
  }

  @Test
  public void runPart2() {
    String result = day.part2(day.data());
    assertEquals(results[3], result);
  }
}
