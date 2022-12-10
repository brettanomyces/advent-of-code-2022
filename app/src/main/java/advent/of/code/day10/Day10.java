package advent.of.code.day10;

import advent.of.code.DailyChallenge;

import java.util.List;

public class Day10 extends DailyChallenge {
  @Override
  public String part1(List<String> input) {
    int cycle = 0;
    int regX = 1;
    int checkAt = 20;
    int signalStrengthSum = 0;

    for (String line: input) {
      String[] parts = line.split(" ");
      switch (parts[0]) {
        case "noop" -> {
          cycle++;
          if (cycle == checkAt) {
            int singleStrength = cycle * regX;
            System.out.println("Signal Strength at " + checkAt + ": " + singleStrength +  ", regX: " + regX);
            signalStrengthSum += cycle * regX;
            checkAt += 40;
          }
        }
        case "addx" -> {
          for (int i = 0; i < 2; i++) {
            cycle ++;
            if (cycle == checkAt) {
              int singleStrength = cycle * regX;
              System.out.println("Signal Strength at " + checkAt + ": " + singleStrength +  ", regX: " + regX);
              signalStrengthSum += cycle * regX;
              checkAt += 40;
            }
          }
          regX += Integer.parseInt(parts[1]);
        }
      }
    }

    return "" + signalStrengthSum;
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }
}
