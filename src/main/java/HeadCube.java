import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class HeadCube {
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        greet();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String input = scanner.nextLine();
                String[] split = input.split(" ",2);

                if (input.equals("bye")) {
                    exit();
                    break;
                } else if (input.equals("list")) {
                    list();
                } else if (split[0].equals("mark")){
                    mark(Integer.parseInt(split[1]));

                } else if (split[0].equals("delete")) {
                    delete(Integer.parseInt(split[1]));
                } else {
                    add(input);
                }
            } catch (HeadCubeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void greet() {
        System.out.println("Hello! I'm HeadCube\n" + "What can I do for you?\n");
    }

    public static void exit() {
        System.out.println("Bye. Hope to see you again soon!\n");
    }

    public static void add(String task) throws HeadCubeException{
        String[] split = task.split(" ",2 );
        String event = split[0];
        String description;

        if (event.equals("todo")) {
            if (split.length < 2 || split[1].isBlank()) {
                throw new HeadCubeException("Todo cannot be empty!!");
            }
            tasks.add(new ToDos(split[1]));
        } else if (event.equals("deadline")) {
            String[] parts = split[1].split(" /by ",2);
            description = parts[0];
            String by = parts[1];
            tasks.add(new Deadlines(description,by));
        } else if (event.equals("event")) {
            String[] parts = split[1].split(" /from ", 2);
            description = parts[0];
            String[] times = parts[1].split(" /to ", 2);
            String start = times[0].trim();
            String end = times[1].trim();
            tasks.add(new Events(description, start, end));
        } else {
            throw new HeadCubeException("I do not understand what that means!!");
        }

        System.out.println("Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.\n");
    }

    public static void list() {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        System.out.println();
    }

    public static void mark(int taskNumber) {
        Task task = tasks.get(taskNumber - 1);
        task.done();
        System.out.println("Nice! I've marked this task as done:\n  " + task);
    }

    public static void delete(int taskNumber) {
        Task removedTask = tasks.remove(taskNumber - 1);
        System.out.println("Noted. I've removed this task:\n  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.\n");
    }

}