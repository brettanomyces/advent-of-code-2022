package advent.of.code;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class DailyChallenge {

  private final List<String> exampleData;
  private final List<String> data;

  protected DailyChallenge() {
    exampleData = load(day() + ".example");
    data = load(day() + ".input");
  }

  List<String> exampleData() {
    return exampleData;
  }

  List<String> data() {
    return data;
  }

  private String day() {
    return this.getClass().getSimpleName().toLowerCase();
  }

  private List<String> load(String inputFile) {
    try {
      URL resource = this.getClass().getClassLoader().getResource(inputFile);
      return Files.readAllLines(Path.of(resource.toURI()));
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  public abstract String part1(List<String> input);

  public abstract String part2(List<String> input);
}
