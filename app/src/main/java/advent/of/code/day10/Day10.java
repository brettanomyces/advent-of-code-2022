package advent.of.code.day10;

import advent.of.code.DailyChallenge;

import java.util.List;

class Day10 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    int cycle = 0;
    int regX = 1;
    int checkAt = 20;
    int signalStrengthSum = 0;

    for (String line : input) {
      String[] parts = line.split(" ");
      switch (parts[0]) {
        case "noop" -> {
          cycle++;
          if (cycle == checkAt) {
            signalStrengthSum += cycle * regX;
            checkAt += 40;
          }
        }
        case "addx" -> {
          for (int i = 0; i < 2; i++) {
            cycle++;
            if (cycle == checkAt) {
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
    // https://stackoverflow.com/a/16812721/1427295
    char[] screen = new String(new char[240]).replace("\0", " ").toCharArray();
    print(screen);

    int cycle = 0;
    int regX = 1;

    for (String line : input) {
      String[] parts = line.split(" ");
      switch (parts[0]) {
        case "noop" -> {
          draw(screen, cycle, regX);
          cycle++;
        }
        case "addx" -> {
          for (int i = 0; i < 2; i++) {
            draw(screen, cycle, regX);
            cycle++;
          }
          regX += Integer.parseInt(parts[1]);
        }
      }
    }

    return print(screen);
  }

  static void draw(char[] screen, int cycle, int spritePosition) {
    int cursor = cycle % 40;  // cursor is per row, each row is length 40
    if (spritePosition - 1 == cursor || spritePosition == cursor || spritePosition + 1 == cursor) {
      screen[cycle] = '#';
    }
  }

  static String print(char[] screen) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < 240; i += 40) {
      str.append(screen, i, 40);
      str.append("\n");
    }
    return str.toString();
  }
}
