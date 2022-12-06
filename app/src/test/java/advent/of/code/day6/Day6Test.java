package advent.of.code.day6;

import advent.of.code.DailyTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test extends DailyTest {

  public Day6Test() {
    super(new Day6(), "7", "1953", "19", "2301");
  }

  @ParameterizedTest
  @CsvSource({
      "bvwbjplbgvbhsrlpgdmjqwftvncz, 5",
      "nppdvjthqldpwncqszvftbrmjlhg, 6",
      "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 10",
      "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 11"
  })
  void runPart1OtherExamples(String input, String expected) {
    var actual = day.part1(List.of(input));
    assertEquals(expected, actual);
  }

  @ParameterizedTest
  @CsvSource({
      "mjqjpqmgbljsphdztnvjfqwrcgsmlb, 19",
      "bvwbjplbgvbhsrlpgdmjqwftvncz, 23",
      "nppdvjthqldpwncqszvftbrmjlhg, 23",
      "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg, 29",
      "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw, 26",
  })
  void runPart2OtherExamples(String input, String expected) {
    var actual = day.part2(List.of(input));
    assertEquals(expected, actual);
  }
}