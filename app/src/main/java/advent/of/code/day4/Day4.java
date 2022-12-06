package advent.of.code.day4;

import advent.of.code.DailyChallenge;

import java.util.List;

class Day4 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    int count = 0;
    for (String line : input) {
      Range assignment1 = new Range(line.substring(0, line.indexOf(",")));
      Range assignment2 = new Range(line.substring(line.indexOf(",") + 1));

      if (assignment1.contains(assignment2) || assignment2.contains(assignment1)) {
        count++;
      }
    }
    return "" + count;
  }

  @Override
  public String part2(List<String> input) {
    int count = 0;
    for (String line : input) {
      Range assignment1 = new Range(line.substring(0, line.indexOf(",")));
      Range assignment2 = new Range(line.substring(line.indexOf(",") + 1));

      if (assignment1.overlaps(assignment2)) {
        count++;
      }
    }
    return "" + count;
  }

  static class Range {

    private final int start;
    private final int stop;

    public Range(String encoding) {
      start = Integer.parseInt(encoding.substring(0, encoding.indexOf("-")));
      stop = Integer.parseInt(encoding.substring(encoding.indexOf("-") + 1));
    }

    public boolean contains(Range range) {
      return this.start <= range.start && this.stop >= range.stop;
    }

    public boolean startsInside(Range range) {
      return this.start >= range.start && this.start <= range.stop;
    }

    public boolean finishesInside(Range range) {
      return this.stop >= range.start && this.stop <= range.stop;
    }

    public boolean overlaps(Range range) {
      return contains(range) || startsInside(range) || finishesInside(range);
    }

    @Override
    public String toString() {
      return start + "-" + stop;
    }
  }
}
