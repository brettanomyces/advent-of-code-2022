package advent.of.code.day11;

import advent.of.code.DailyChallenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    List<Monkey> monkeys = new ArrayList<>();
    for (int i = 0; i < input.size(); i += 7) {
      monkeys.add(new Monkey(input.subList(i, i + 6)));
    }

    for (int round = 0; round < 20; round++ ) {
      for (int monkey = 0; monkey < monkeys.size(); monkey++) {
        monkeys.get(monkey).takeTurns(monkeys);
      }
      System.out.println("After round %s, the monkeys are holding items with these worry levels:".formatted(round));
      monkeys.forEach(m -> {
        System.out.println("Monkey %s: %s".formatted(m.number, m.items.toString()));
      });
    }

    return monkeys.stream()
        .map(m -> {
          System.out.println("Monkey %s inspected items %s times.".formatted(m.number, m.inspections));
          return m.inspections;
        })
        .sorted(Comparator.reverseOrder())
        .limit(2)
        .reduce(1, (a, b) -> a * b)  // monkey business
        .toString();
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }

  static class Monkey {

    private int inspections = 0;

    private int number;
    private List<Integer> items;  // value represents worry level
    private Function<Integer, Integer> inspectFunc;  //  modifies worry level
    private Function<Integer, Integer> targetFunc;  // takes a worry level and returns a monkey

    public Monkey(List<String> lines) {
      number = monkeyNumber(lines.get(0));
      items = items(lines.get(1));
      inspectFunc = buildInspectFunc(lines.get(2));
      targetFunc = buildTargetFunc(lines.subList(3, 6));
    }

    private static int monkeyNumber(String line) {
      return Integer.parseInt(line.substring(7).replace(":", ""));
    }

    private static List<Integer> items(String line) {
      return Arrays.stream(line.substring(18).split(","))
          .map(String::trim)
          .map(Integer::parseInt)
          .sorted(Comparator.reverseOrder())
          .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Function<Integer, Integer> buildInspectFunc(String s) {
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

    private static Function<Integer, Integer> buildTargetFunc(List<String> lines) {
      int divisor = Integer.parseInt(lines.get(0).substring(21));
      int monkeyA = Integer.parseInt(lines.get(1).trim().split(" ")[5]);  // true
      int monkeyB = Integer.parseInt(lines.get(2).trim().split(" ")[5]);  // false
      return i -> {
        if (i % divisor == 0) {
          System.out.println("    Current worry level is divisible by %s.".formatted(divisor));
          return monkeyA;
        } else {
          System.out.println("    Current worry level is not divisible by %s.".formatted(divisor));
          return monkeyB;
        }
      };
    }

    public int target(Integer item) {
      return targetFunc.apply(item);
    }

    public void catchItem(Integer item) {
      items.add(0, item);
    }

    void inspect() {
      inspections++;
      int currentWorryLevel = items.remove(items.size() - 1);
      System.out.println("  Monkey inspects an item with a worry level of " + currentWorryLevel);
      int newWorryLevel = inspectFunc.apply(currentWorryLevel);
      System.out.println("    Worry level is adjusted to " + newWorryLevel);
      items.add(newWorryLevel);
    }

    public void relax() {
      int currentWorryLevel = items.remove(items.size() - 1);
      int newWorryLevel = currentWorryLevel / 3;  // divide by three and round down
      System.out.println("    Monkey gets bored with item. Worry level is divided by 3 to " + newWorryLevel);
      items.add(newWorryLevel);
    }

    public void takeTurns(List<Monkey> monkeys) {
      System.out.println("Monkey " + number);
      while (!items.isEmpty()) {
        inspect();
        relax();
        int item = items.remove(items.size() - 1);  // remove last item
        int target = target(item);
        System.out.println("    Item with worry level %s is thrown to monkey %s".formatted(item, target));
        monkeys.get(target).catchItem(item);
      }
    }
  }
}
