package advent.of.code.day11;

import advent.of.code.DailyChallenge;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    List<Monkey> monkeys = parseMonkeys(input);
    Function<BigInteger, BigInteger> relaxFunc = (n) -> n.divide(BigInteger.valueOf(3));
    return "" + monkeyBusiness(monkeys, 20, relaxFunc);
  }

  @Override
  public String part2(List<String> input) {
    List<Monkey> monkeys = parseMonkeys(input);

    BigInteger relaxFactor = monkeys.stream().map(m -> m.divisor).reduce(BigInteger.ONE, (a, b) -> a.multiply(b));
    Function<BigInteger, BigInteger> relaxFunc = (n) -> n.mod(relaxFactor);

    return "" + monkeyBusiness(monkeys, 10000, relaxFunc);
  }

  public List<Monkey> parseMonkeys(List<String> input) {
    List<Monkey> monkeys = new ArrayList<>();
    for (int i = 0; i < input.size(); i += 7) {
      monkeys.add(new Monkey(input.subList(i, i + 6)));
    }
    return monkeys;
  }

  public long monkeyBusiness(List<Monkey> monkeys, int rounds, Function<BigInteger, BigInteger> relaxFunc) {
    for (int round = 0; round < rounds; round++ ) {
      for (int monkey = 0; monkey < monkeys.size(); monkey++) {
        monkeys.get(monkey).takeTurns(monkeys, relaxFunc);
      }
//       System.out.println("After round %s, the monkeys are holding items with these worry levels:".formatted(round));
      monkeys.forEach(m -> {
//         System.out.println("Monkey %s: %s".formatted(m.number, m.items.toString()));
      });
    }

    return monkeys.stream()
        .map(m -> {
          // System.out.println("Monkey %s inspected items %s times.".formatted(m.number, m.inspections));
          return m.inspections;
        })
        .sorted(Comparator.reverseOrder())
        .limit(2)
        .reduce(1L, (a, b) -> a * b);  // monkey business
  }


  static class Monkey {

    private long inspections = 0;

    private long number;
    private List<BigInteger> items;  // value represents worry level
    private Function<BigInteger, BigInteger> inspectFunc;  //  modifies worry level
    private Function<BigInteger, Integer> targetFunc;  // takes a worry level and returns a monkey
    private BigInteger divisor;

    public Monkey(List<String> lines) {
      number = monkeyNumber(lines.get(0));
      items = items(lines.get(1));
      inspectFunc = buildInspectFunc(lines.get(2));
      targetFunc = buildTargetFunc(lines.subList(3, 6));
    }

    private static long monkeyNumber(String line) {
      return Long.parseLong(line.substring(7).replace(":", ""));
    }

    private static List<BigInteger> items(String line) {
      return Arrays.stream(line.substring(18).split(","))
          .map(String::trim)
          .map(BigInteger::new)
          .sorted(Comparator.reverseOrder())
          .collect(Collectors.toCollection(ArrayList::new));
    }

    private static Function<BigInteger, BigInteger> buildInspectFunc(String s) {
      String end = s.substring(25);
      if ('*' == s.charAt(23)) {
        if ("old".equals(end)) {
          return i -> i.pow(2);
        } else {
          BigInteger multiplier = new BigInteger(end);
          return i -> i.multiply(multiplier);
        }
      } else {  // add
        if ("old".equals(end)) {
          BigInteger two = BigInteger.valueOf(2);
          return i -> i.multiply(two);
        } else {
          BigInteger addition = new BigInteger(end);
          return i -> i.add(addition);
        }
      }
    }

    private Function<BigInteger, Integer> buildTargetFunc(List<String> lines) {
      divisor = new BigInteger(lines.get(0).substring(21));
      int monkeyA = Integer.parseInt(lines.get(1).trim().split(" ")[5]);  // true
      int monkeyB = Integer.parseInt(lines.get(2).trim().split(" ")[5]);  // false
      return i -> {
        if (i.mod(divisor).equals(BigInteger.ZERO)) {
          // System.out.println("    Current worry level is divisible by %s.".formatted(divisor));
          return monkeyA;
        } else {
          // System.out.println("    Current worry level is not divisible by %s.".formatted(divisor));
          return monkeyB;
        }
      };
    }

    int target(BigInteger item) {
      return targetFunc.apply(item);
    }

    void catchItem(BigInteger item) {
      items.add(0, item);
    }

    void takeTurns(List<Monkey> monkeys, Function<BigInteger, BigInteger> relaxFunc) {
      // System.out.println("Monkey " + number);
      while (!items.isEmpty()) {
        inspections++;
        BigInteger worry = items.remove(items.size() - 1);
        // System.out.println("  Monkey inspects an item with a worry level of " + currentWorryLevel);
        worry = inspectFunc.apply(worry);
        // System.out.println("    Worry level is adjusted to " + newWorryLevel);

        worry = relaxFunc.apply(worry);

        int target = target(worry);
        // System.out.println("    Item with worry level %s is thrown to monkey %s".formatted(item, target));
        monkeys.get(target).catchItem(worry);
      }
    }
  }
}
