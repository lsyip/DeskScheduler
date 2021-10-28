import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException {
        DeskSchedule ds = new DeskSchedule();
        File file = new File("src/small_desk_test.txt");
        // Input day schedule
        //Let's do Monday first
        if (!file.exists()) {
            System.out.println("File not found!");
            System.exit(0);
        }
        Scanner inputFile = new Scanner(file);

        while (inputFile.hasNext()) {
            ds.getSchedule().add(inputFile.useDelimiter(",\r\n").next());
        }

        // Parse initials and hours
        for (String item : ds.getSchedule()) {
            ds.getInitials().add(item.substring(0,2));
            ds.getParsedSched().put(item.substring(0,2), item.substring(3));
        }

        inputFile.close();

        System.out.println(ds.parsedSched);

        // For each hour, figure out who's available - map <int hour, ArrayList String>?
        System.out.println(ds.hourMap(ds.hourConversion(ds.parsedSched)));

        // Put into another multimap with final sched
        System.out.println(ds.printFinalSchedule(ds.finalSchedule(ds.hourMap(ds.hourConversion(ds.parsedSched)))));
        // Insert into table

        /*
        TODO - Constraints:
        1. 4 hours = 15 minute break, 6 hour = 30 minute break, 8 hour = 1 hour break
        2. No desk more than twice per 4 hour shift
        3. schedule 4 hour employees first, then manager
         */


    }
}
