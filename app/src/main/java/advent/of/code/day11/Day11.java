package advent.of.code.day11;

import advent.of.code.DailyChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Day11 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    List<Monkey> monkies = new ArrayList<>();
    for (int i = 0; i < input.size(); i += 7) {
      monkies.add(new Monkey(input.subList(i, i + 6)));
    }
    System.out.println(monkies);
    return null;
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }

  static class Monkey {
    private int number;
    private int[] items;  // value represents worry level
    private Function<Integer, Integer> operation;  //  modifies worry level
    private Function<Integer, Integer> throwTo;  // takes a worry level and returns a monkey

    public Monkey(List<String> lines) {
      number = monkeyNumber(lines.get(0));
      items = items(lines.get(1));
      operation = operation(lines.get(2));
      throwTo = throwTo(lines.subList(3, 6));
    }

    private static int monkeyNumber(String line) {
      return Integer.parseInt(line.substring(7).replace(":", ""));
    }

    private static int[] items(String line) {
      return Arrays.stream(line.substring(18).split(","))
          .map(String::trim)
          .map(Integer::parseInt)
          .mapToInt(Integer::intValue)
          .toArray();
    }

    private static Function<Integer, Integer> operation(String s) {
      String end = s.substring(25);
      if ('*' == s.charAt(23)) {
        if ("old".equals(end)) {
          return i -> i * i;
        } else {
          return i -> i * Integer.parseInt(end);
        }
      } else {  // add
        if ("old".equals(end)) {
          return i -> i + i;
        } else {
          return i -> i + Integer.parseInt(end);
        }
      }
    }

    private static Function<Integer, Integer> throwTo(List<String> lines) {
      int divisor = Integer.parseInt(lines.get(0).substring(21));
      int monkeyA = Integer.parseInt(lines.get(1).trim().split(" ")[5]);  // true
      int monkeyB = Integer.parseInt(lines.get(2).trim().split(" ")[5]);  // false
      return i -> {
        if (i % divisor == 0) {
          return monkeyA;
        } else {
          return monkeyB;
        }
      };
    }
  }
}
