package advent.of.code.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

class Part1 {

  public static void main(String[] args) {
    URL inputPath = Part1.class.getClassLoader().getResource("day4.input");

    int count = 0;

    String line;
    try (BufferedReader br = Files.newBufferedReader(Path.of(inputPath.toURI()))) {
      while ((line = br.readLine()) != null) {
        Range assignment1 = new Range(line.substring(0, line.indexOf(",")));
        Range assignment2 = new Range(line.substring(line.indexOf(",") + 1));

        if (assignment1.contains(assignment2) || assignment2.contains(assignment1)) {
          count++;
        }
      }
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Count: " + count);
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
  }
}
