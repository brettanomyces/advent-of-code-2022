package advent.of.code.day9;

import advent.of.code.DailyChallenge;
import advent.of.code.DailyTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day9Test extends DailyTest {

  public Day9Test() {
    super(new Day9(), "13", "6406", "1", "2643");
  }

  @Test
  void runPart2LargerExample() {
    List<String> largerExample = List.of(
        "R 5",
        "U 8",
        "L 8",
        "D 3",
        "R 17",
        "D 10",
        "L 25",
        "U 20"
    );

    var actual = day.part2(largerExample);
    assertEquals("36", actual);
  }
}