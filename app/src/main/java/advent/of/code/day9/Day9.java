package advent.of.code.day9;

import advent.of.code.DailyChallenge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 extends DailyChallenge {

  // ......
  // ......
  // ......
  // ......
  // H.....

  @Override
  public String part1(List<String> input) {
    Position head = new Position(0, 0);
    Position tail = new Position(0, 0);

    Set<String> uniquePositions = new HashSet<>();

    for (int i = 0; i < input.size(); i++) {
      int count = Integer.parseInt(input.get(i).substring(2));
      for (int j = 0; j < count; j++) {
        switch (input.get(i).charAt(0)) {
          case 'R' : {
            head.x++;
            break;
          }
          case 'L' : {
            head.x--;
            break;
          }
          case 'U' : {
            head.y++;
            break;
          }
          case 'D' : {
            head.y--;
            break;
          }
        }
        follow(head, tail);
        uniquePositions.add(tail.toString());
      }
    }

    return "" + uniquePositions.size();
  }

  //  X...X
  //  .AAA.
  //  .ATA.
  //  .AAA.
  //  X...X
  //
  // row 0
  // H(1,0) - T(2,2) = (-1, -2) => T.x--, T.y--
  // H(2,0) - T(2,2) = (0, -2) => T.y--
  // H(3,0) - T(2,2) = (1, -2) => T.x++, T.y--
  // row 1
  // H(0,1) - T(2,2) = (-2, -1) => T.x--, T.y--
  // H(4,1) - T(2,2) = (2, -1) => T.x++, T.y--
  // row 2
  // H(0,2) - T(2,2) = (-2, 0) => T.x--
  // H(4,2) - T(2,2) = (2, 0) => T.x++
  // row 3
  // H(0,3) - T(2,2) = (-2, 1) => T.x--, T.y++
  // H(4,3) - T(2,2) = (2, 1) => T.x++, T.y++
  // row 4
  // H(1,4) - T(2,2) = (-1, 2) => T.x--, T.y++
  // H(2,4) - T(2,2) = (0, 2) => T.y++
  // H(3,4) - T(2,2) = (1, 2) => T.x++, T.y++

  void follow(Position head, Position tail) {
    int dx = head.x - tail.x;
    int dy = head.y - tail.y;
    if (dx == -1 && dy == -2) {  // 0
      tail.x--; tail.y--;
    } else if (dx == 0 && dy == -2) {  // 0
      tail.y--;
    } else if (dx == 1 && dy == -2) {  // 0
      tail.x++; tail.y--;
    } else if (dx == -2 && dy == -1) {  // 1
      tail.x--; tail.y--;
    } else if (dx == 2 && dy == -1) {  // 1
      tail.x++; tail.y--;
    } else if (dx == -2 && dy == 0) {  // 2
      tail.x--;
    } else if (dx == 2 && dy == 0) {  // 2
      tail.x++;
    } else if (dx == -2 && dy == 1) {  // 3
      tail.x--; tail.y++;
    } else if (dx == 2 && dy == 1) {  // 3
      tail.x++; tail.y++;
    } else if (dx == -1 && dy == 2) {  // 4
      tail.x--; tail.y++;
    } else if (dx == 0 && dy == 2) {  // 4
      tail.y++;
    } else if (dx == 1 && dy == 2) { // 4
      tail.x++; tail.y++;
    }
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }

  static class Position {
    int x;
    int y;

    public Position(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public String toString() {
      return "(%s, %s)".formatted(x, y);
    }
  }
}
