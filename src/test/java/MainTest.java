import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MainTest {
  private final InputStream originalIn = System.in;
  private final PrintStream originalOut = System.out;
  private ByteArrayOutputStream output;

  @BeforeEach
  void captureOutput() {
    output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
  }

  @AfterEach
  void restoreStreams() {
    System.setIn(originalIn);
    System.setOut(originalOut);
  }

  private String run(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    Main.main(new String[0]);
    return output.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
  }

  @Test
  void continuesCalculationWithPreviousResult() {
    assertEquals("5.0\n15.0\n12.0\n4.0\n", run("2 + 3 * 3 - 3 / 3 S"));
  }

  @Test
  void keepsResultOnDivisionByZero() {
    assertEquals("Ошибка: деление на ноль невозможно\n5.0\n", run("10 / 0 / 2 S"));
  }

  @Test
  void reportsUnsupportedOperation() {
    assertEquals("Ошибка: неподдерживаемая операция %\n7.0\n", run("4 % 2 + 3 S"));
  }

  @Test
  void resetsResultOnClearCommand() {
    assertEquals("3.0\n10.0\n", run("1 + 2 c 5 * 2 s"));
  }
}
