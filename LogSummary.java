
import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

import static java.time.temporal.ChronoUnit.SECONDS;

public class LogSummary {

    // properties

    public final String APP_LOG_PATH = "transactionlog.txt";
    public final String SALES_LOG_PATH = "salesReport.txt";
    private final String EVENT_FORMATTER = " %1$-25s";
    public String formattedEvent = String.format("\n" + EVENT_FORMATTER, LocalDateTime.now().truncatedTo(SECONDS).toString());




    public void writeAppLog(String formattedEvent) {
        write(APP_LOG_PATH, formattedEvent);
    }



/*   OPTIONAL
  public void writeSalesLog(String message) {
  Calling write with the SALE_LOG_PATH param
  write(SALES_LOG_PATH, message);
}
*/


    private void write(String destinationFile, String formattedEvent) {

        try (PrintWriter pw = new PrintWriter(new FileOutputStream(destinationFile, true))) {

            pw.write(formattedEvent);

        } catch (FileNotFoundException e) {
            System.out.println("There is an issue");
        }

    }



}
