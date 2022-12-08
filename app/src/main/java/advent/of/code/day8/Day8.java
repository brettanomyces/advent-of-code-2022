package advent.of.code.day8;

import advent.of.code.DailyChallenge;

import java.util.List;

class Day8 extends DailyChallenge {

  @Override
  public String part1(List<String> input) {
    Tree[][] trees = trees(input);
    int height = trees.length;
    int width = trees[0].length;

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
    Tree[][] trees = trees(input);
    int height = trees.length;
    int width = trees[0].length;

    //    0|1|2|3|4
    // 0: 3 0 3 7 3
    // 1: 2 5 5 1 2
    // 2: 6 5 3 3 2
    // 3: 3 3 5 4 9
    // 4: 3 5 3 9 0

    int maxScenicScore = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Tree currentTree = trees[i][j];
        // looking east
        for (int k = j - 1; k >= 0; k--) {
          currentTree.visibilityEast++;
          if (trees[i][k].height >= currentTree.height) {
            break;
          }
        }
        // looking west
        for (int k = j + 1; k < width; k++) {
          currentTree.visibilityWest++;
          if (trees[i][k].height >= currentTree.height) {
            break;
          }
        }
        // looking north
        for (int l = i - 1; l >= 0; l--) {
          currentTree.visibilityNorth++;
          if (trees[l][j].height >= currentTree.height) {
            break;
          }
        }
        // looking south
        for (int l = i + 1; l < height; l++) {
          currentTree.visibilitySouth++;
          if (trees[l][j].height >= currentTree.height) {
            break;
          }
        }
        if (currentTree.scenicScore() > maxScenicScore) {
          maxScenicScore = currentTree.scenicScore();
        }
      }
    }
    return "" + maxScenicScore;
  }

  Tree[][] trees(List<String> input) {
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

    return trees;
  }

  static class Tree {

    private final int height;
    boolean visibleFromLeft = false;
    boolean visibleFromRight = false;
    boolean visibleFromTop = false;
    boolean visibleFromBottom = false;

    int visibilityEast = 0;  // left
    int visibilityWest = 0;  // right
    int visibilityNorth = 0;  // top
    int visibilitySouth = 0;  // bottom

    public Tree(int height) {
      this.height = height;
    }

    public boolean isVisible() {
      return visibleFromLeft || visibleFromRight || visibleFromTop || visibleFromBottom;
    }

    public int scenicScore() {
      return visibilityEast * visibilityWest * visibilityNorth * visibilitySouth;
    }

    @Override
    public String toString() {
      return "" + height;
    }
  }

}
