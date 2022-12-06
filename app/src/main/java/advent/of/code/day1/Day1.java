package advent.of.code.day1;

import advent.of.code.DailyChallenge;

import java.util.List;

class Day1 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    int best = 0;
    int current = 0;

    for (String line : input) {
      if (line.isEmpty()) {
        if (current > best) {
          best = current;
        }
        current = 0;
      } else {
        current += Integer.parseInt(line);
      }
    }

    if (current > best) {
      best = current;
    }

    return "" + best;
  }

  @Override
  public String part2(List<String> input) {
    int current = 0;
    CalorieCount calories = new CalorieCount();

    for (String line : input) {
      if (line.isEmpty()) {
        calories.update(current);
        current = 0;
      } else {
        current += Integer.parseInt(line);
      }
    }
    calories.update(current);

    return "" + calories.total();
  }

  private static class CalorieCount {

    private int first = 0;
    private int second = 0;
    private int third = 0;

    private void update(int currentElf) {
      if (currentElf > first) {
        third = second;
        second = first;
        first = currentElf;
      } else if (currentElf > second) {
        third = second;
        second = currentElf;
      } else if (currentElf > third) {
        third = currentElf;
      }
    }

    private int total() {
      System.out.println("first: " + first);
      System.out.println("second: " + second);
      System.out.println("third: " + third);
      return first + second + third;
    }
  }
}
