package advent.of.code.day2;

import advent.of.code.DailyChallenge;

import java.util.List;

class Day2 implements DailyChallenge {

  @Override
  public String part1(List<String> input) {
    int total = 0;
    for (String line : input) {
      Move attack = parseAttack(line);
      Move response = parseResponse(line);
      Result result = determineResult(attack, response);
      int score = response.value + result.value;
      total += score;
    }
    return "" + total;
  }

  @Override
  public String part2(List<String> input) {
    int total = 0;
    for (String line : input) {
      Move attack = parseAttack(line);
      Result result = parseResult(line);
      Move response = determineResponse(attack, result);
      int score = response.value + result.value;
      total += score;
    }
    return "" + total;
  }

  private static Move parseAttack(String line) {
    String encoding = line.substring(0, 1);
    return switch (encoding) {
      case "A" -> Move.ROCK;
      case "B" -> Move.PAPER;
      case "C" -> Move.SCISSORS;
      default -> throw new IllegalArgumentException();
    };
  }

  private static Move parseResponse(String line) {
    String encoding = line.substring(2, 3);
    return switch (encoding) {
      case "X" -> Move.ROCK;
      case "Y" -> Move.PAPER;
      case "Z" -> Move.SCISSORS;
      default -> throw new IllegalArgumentException();
    };
  }

  private static Result parseResult(String line) {
    String encoding = line.substring(2, 3);
    return switch (encoding) {
      case "X" -> Result.LOSS;
      case "Y" -> Result.DRAW;
      case "Z" -> Result.WIN;
      default -> throw new IllegalArgumentException();
    };
  }

  private static Result determineResult(Move attack, Move response) {
    if (response.beat.equals(attack)) {
      return Result.WIN;
    } else if (response.equals(attack)) {
      return Result.DRAW;
    } else {
      return Result.LOSS;
    }
  }

  private static Move determineResponse(Move attack, Result result) {
    if (Result.DRAW.equals(result)) {
      return attack;
    } else if (Result.LOSS.equals(result)) {
      return attack.beat;
    } else {  // Result.WIN
      // e.g. attack = ROCK, attack.beat = SCISSORS, attack.beat.beat = PAPER
      return attack.beat.beat;
    }
  }

  private enum Result {
    WIN(6),
    DRAW(3),
    LOSS(0);

    private final int value;

    Result(int value) {
      this.value = value;
    }
  }

  private enum Move {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    static {  // avoid illegal forward reference
      ROCK.beat = SCISSORS;
      PAPER.beat = ROCK;
      SCISSORS.beat = PAPER;
    }

    private final int value;
    private Move beat;

    Move(int value) {
      this.value = value;
    }
  }
}
