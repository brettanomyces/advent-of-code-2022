package advent.of.code.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

/*
 * --- Day 2: Rock Paper Scissors ---
 *
 * The Elves begin to set up camp on the beach. To decide whose tent gets to be closest to the snack storage, a giant
 * Rock Paper Scissors tournament is already in progress.
 *
 * Rock Paper Scissors is a game between two players. Each game contains many rounds; in each round, the players each
 * simultaneously choose one of Rock, Paper, or Scissors using a hand shape. Then, a winner for that round is selected:
 * Rock defeats Scissors, Scissors defeats Paper, and Paper defeats Rock. If both players choose the same shape, the
 * round instead ends in a draw.
 *
 * Appreciative of your help yesterday, one Elf gives you an encrypted strategy guide (your puzzle input) that they say
 * will be sure to help you win. "The first column is what your opponent is going to play: A for Rock, B for Paper, and
 * C for Scissors. The second column--" Suddenly, the Elf is called away to help with someone's tent.
 *
 * The second column, you reason, must be what you should play in response: X for Rock, Y for Paper, and Z for Scissors.
 * Winning every time would be suspicious, so the responses must have been carefully chosen.
 *
 * The winner of the whole tournament is the player with the highest score. Your total score is the sum of your scores
 * for each round. The score for a single round is the score for the shape you selected (1 for Rock, 2 for Paper, and 3
 * for Scissors) plus the score for the outcome of the round (0 if you lost, 3 if the round was a draw, and 6 if you
 * won).
 *
 * Since you can't be sure if the Elf is trying to help you or trick you, you should calculate the score you would get
 * if you were to follow the strategy guide.
 *
 * For example, suppose you were given the following strategy guide:
 *
 * A Y
 * B X
 * C Z
 *
 * This strategy guide predicts and recommends the following:
 *
 *     In the first round, your opponent will choose Rock (A), and you should choose Paper (Y). This ends in a win for you with a score of 8 (2 because you chose Paper + 6 because you won).
 *     In the second round, your opponent will choose Paper (B), and you should choose Rock (X). This ends in a loss for you with a score of 1 (1 + 0).
 *     The third round is a draw with both players choosing Scissors, giving you a score of 3 + 3 = 6.
 *
 * In this example, if you were to follow the strategy guide, you would get a total score of 15 (8 + 1 + 6).
 *
 * What would your total score be if everything goes exactly according to your strategy guide?
 */
public class Day2 {

  public static void main(String[] args) throws IOException, URISyntaxException {

    URL inputPath = Day2.class.getClassLoader().getResource("day2.input");

    int total = 0;

    String line;
    try (BufferedReader br = Files.newBufferedReader(Path.of(inputPath.toURI()))) {
      while ((line = br.readLine()) != null) {
        Attack attack = parseAttack(line);
        Response response = parseResponse(line);
        Result result = response.to(attack);
        int score = response.value() + result.value();
        total += score;
      }
    }

    System.out.println("Total score: " + total);
  }

  public static Attack parseAttack(String line) {
    String encoding = line.substring(0, 1);
    return EnumSet.allOf(Attack.class)
        .stream()
        .filter(a -> a.encoding().equals(encoding))
        .findFirst()
        .orElseThrow();
  }

  public static Response parseResponse(String line) {
    String encoding = line.substring(2, 3);
    return EnumSet.allOf(Response.class)
        .stream()
        .filter(r -> r.encoding().equals(encoding))
        .findFirst()
        .orElseThrow();
  }

  enum Result {
    WIN(6),
    DRAW(3),
    LOSS(0);

    private final int value;

    Result(int value) {
      this.value = value;
    }

    public int value() {
      return value;
    }
  }

  enum Attack {
    ROCK("A"),
    PAPER("B"),
    SCISSORS("C");

    private final String encoding;

    Attack(String encoding) {
      this.encoding = encoding;
    }

    public String encoding() {
      return encoding;
    }
  }

  enum Response {
    ROCK("X", 1, Attack.SCISSORS),
    PAPER("Y", 2, Attack.ROCK),
    SCISSORS("Z", 3, Attack.PAPER);

    private final String encoding;
    private final int value;
    private final Attack defeats;

    Response(String encoding, int value, Attack defeats) {
      this.encoding = encoding;
      this.value = value;
      this.defeats = defeats;
    }

    public String encoding() {
      return encoding;
    }

    public int value() {
      return value;
    }

    public Result to(Attack attack) {
      if (defeats.equals(attack)) {
        return Result.WIN;
      } else if (this.name().equals(attack.name())) {
        return Result.DRAW;
      } else {
        return Result.LOSS;
      }
    }
  }
}
