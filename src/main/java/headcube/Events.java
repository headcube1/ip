package headcube;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with a start and end time.
 */
public class Events extends Task{
    protected LocalDateTime start;
    protected LocalDateTime end;

    /**
     * Constructor of an Events task with the specified description, start time, and end time.
     * The dates are parsed and stored as LocalDateTime. If the parsing fails, defaults
     * to a start time of now and an end time of 24 hours from now.
     *
     * @param description The description of the event.
     * @param start The start time of the event in "yyyy-MM-dd HH:mm" or "MMM dd yyyy HH:mm" format.
     * @param end The end time of the event in "MMM dd yyyy HH:mm" format.
     */
    public Events(String description, String start, String end) {
        super(description);
        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm");
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            this.start = LocalDateTime.parse(start, originalFormatter);
            this.end = LocalDateTime.parse(end, originalFormatter);
        } catch (DateTimeParseException e) {
            try {
                this.start = LocalDateTime.parse(start, targetFormatter);
                this.end = LocalDateTime.parse(end, targetFormatter);
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid date and time format. Setting 24 hours from now");
                this.start = LocalDateTime.now();
                this.end = this.start.plusHours(24);
            }
        }
    }

    /**
     * Returns a string representation of the event task, including its type, status,
     * description, start time, and end time.
     *
     * @return A string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + start.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"))
                + " to: " + end.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }

    /**
     * Returns the file format representation of the event task,
     * useful for saving the task to a file.
     *
     * @return A string formatted for file saving.
     */
    @Override
    public String toFileFormat() {
        return "E | " + super.toFileFormat() + " | " +
                "(from: " + start.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) +
                " to: " + end.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm")) + ")";
    }
}
