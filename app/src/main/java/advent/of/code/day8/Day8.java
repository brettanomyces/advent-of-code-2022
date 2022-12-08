package advent.of.code.day8;

import advent.of.code.DailyChallenge;

import java.util.List;

class Day8 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    int height = input.size();
    int width = input.get(0).length();

    Tree[][] trees = new Tree[height][width];

    //    0|1|2|3|4
    // 0: 3 0 3 7 3
    // 1: 2 5 5 1 2
    // 2: 6 5 3 3 2
    // 3: 3 3 5 4 9
    // 4: 3 5 3 9 0

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        trees[i][j] = new Tree(Integer.parseInt("" + input.get(i).charAt(j)));
      }
    }

    // find visible from left
    for (int i = 0; i < height; i++) {
      int tallest = 0;
      for (int j = 0; j < width; j++) {
        if (j == 0 || trees[i][j].height > tallest) {
          trees[i][j].visibleFromLeft = true;
          tallest = trees[i][j].height;
        }
        // break on height 9, no more visible after that
        if (tallest == 9) {
          break;
        }
      }
    }
    // find visible from right
    for (int i = 0; i < height; i++) {
      int tallest = 0;
      for (int j = width - 1; j >= 0; j--) {
        if (j == width - 1 || trees[i][j].height > tallest) {
          trees[i][j].visibleFromRight = true;
          tallest = trees[i][j].height;
        }
        if (tallest == 9) {
          break;
        }
      }
    }
    // find visible from top
    for (int j = 0; j < width; j++) {
      int tallest = 0;
      for (int i = 0; i < height; i++) {
        if (i == 0 || trees[i][j].height > tallest) {
          trees[i][j].visibleFromTop = true;
          tallest = trees[i][j].height;
        }
        // break on height 9, no more visible after that
        if (tallest == 9) {
          break;
        }
      }
    }
    // find visible from bottom
    for (int j = 0; j < width; j++) {
      int tallest = 0;
      for (int i = height - 1; i >= 0; i--) {
        if (i == height - 1 || trees[i][j].height > tallest) {
          trees[i][j].visibleFromBottom = true;
          tallest = trees[i][j].height;
        }
        // break on height 9, no more visible after that
        if (tallest == 9) {
          break;
        }
      }
    }

    int count = 0;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (trees[i][j].isVisible()) {
          count++;
        }
      }
    }

    return "" + count;
  }

  @Override
  public String part2(List<String> input) {
    return null;
  }

  static class Tree {

    private final int height;
    boolean visibleFromLeft = false;
    boolean visibleFromRight = false;
    boolean visibleFromTop = false;
    boolean visibleFromBottom = false;

    public Tree(int height) {
      this.height = height;
    }

    public boolean isVisible() {
      return visibleFromLeft || visibleFromRight || visibleFromTop || visibleFromBottom;
    }

    @Override
    public String toString() {
      return "" + height;
    }
  }

}
