
import java.util.*;

/**
 * The DeskSchedule class inserts available employees into 1 of 3 positions and parses a text file
 * to extract employee initials and hour ranges.
 * @author Lynn Yip
 */
public class DeskSchedule {
    private final ArrayList<String> DAYS = new ArrayList<>(Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"));
    private final ArrayList<Integer> HOURS = new ArrayList<>(Arrays.asList(9, 10, 11, 12, 1, 2, 3, 4, 5, 6, 7));
    private final ArrayList<String> POSITION = new ArrayList<>(Arrays.asList("Desk", "Backup", "Shelving"));
    private ArrayList<String> initials = new ArrayList<>();
    private ArrayList<String> schedule = new ArrayList<>();
    HashMap<String, String> parsedSched = new HashMap<>();


    /**
     * The default constructor
     */
    public DeskSchedule() {
    }

    /**
     * Returns the days of the week the business is open.
     * @return Returns the days of the week
     */
    public ArrayList<String> getDAYS() {
        return DAYS;
    }

    /**
     * Returns the hours the business is open.
     * @return Returns the hours the business is open.
     */
    public ArrayList<Integer> getHOURS() {
        return HOURS;
    }

    /**
     * Returns one of three possible positions.
     * @return A position
     */
    public ArrayList<String> getPOSITION() {
        return POSITION;
    }

    /**
     * Returns the employee's initials
     * @return The initials of an employee
     */
    public ArrayList<String> getInitials() {
        return initials;
    }

    /**
     * Sets an employee's initials
     * @param initials An arraylist of initials
     */
    public void setInitials(ArrayList<String> initials) {
        this.initials = initials;
    }

    /**
     * Gets the unparsed schedule file
     * @return A schedule
     */
    public ArrayList<String> getSchedule() {
        return schedule;
    }

    /**
     * Sets the schedule
     * @param schedule A schedule
     */
    public void setSchedule(ArrayList<String> schedule) {
        this.schedule = schedule;
    }

    /**
     * Returns a parsed schedule
     * @return A parsed schedule
     */
    public HashMap<String, String> getParsedSched() {
        return parsedSched;
    }

    /**
     * Sets the parsed schedule
     * @param parsedSched A parsed schedule
     */
    public void setParsedSched(HashMap<String, String> parsedSched) {
        this.parsedSched = parsedSched;
    }

    /**
     * Extracts the hours that an employee is available given a range separated by a dash
     * @param parsedSched A parsed schedule
     * @return A range of hours
     */
    public Map<String, ArrayList<Integer>> hourConversion(HashMap<String, String> parsedSched) {
        Map<String, ArrayList<Integer>> result = new HashMap<String, ArrayList<Integer>>();


        for (HashMap.Entry<String, String> entry : parsedSched.entrySet()) {
            ArrayList<Integer> hours = new ArrayList<>();
            int dashIndex = entry.getValue().indexOf("-");
            int firstHour = Integer.parseInt(entry.getValue().substring(0,dashIndex));
            int lastHour = Integer.parseInt(entry.getValue().substring(entry.getValue().length() - 1));
            hours.add(firstHour);
            while ((firstHour + 1 != lastHour + 12) && (firstHour + 1 != lastHour)) {
                int nextHour = firstHour + 1;
                if (nextHour > 12) {
                    nextHour = nextHour % 12;
                }
                firstHour = firstHour + 1;
                hours.add(nextHour);

            }

            result.put(entry.getKey(), hours);

        }
        return result;
    }

    /**
     * Returns a map of all hours an employee is available.
     * @param hourConversion The hours each employee is available
     * @return A map of all hours where the key is an hour and the values are employee initials.
     */
    public Map<Integer, ArrayList<String>> hourMap (Map<String, ArrayList<Integer>> hourConversion) {
          // Initials of peeps available that hour
        Map<Integer, ArrayList<String>> result = new HashMap<>();

        for (int hour : HOURS) {
            ArrayList<String> available = new ArrayList<>();
            for (Map.Entry<String, ArrayList<Integer>> entry : hourConversion.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getValue().get(i).equals(hour)) {
                        available.add(entry.getKey()); // Add that initial to the list
                    }
                }
            }
            result.put(hour, available);
        }

        return result;

    }

    /**
     * Returns the final desk schedule
     * @param hourMap A map containing each employee's availability for each hour
     * @return The final desk schedule.
     */
    public Map<Integer, ArrayList<String>> finalSchedule(Map<Integer, ArrayList<String>> hourMap) {
        Map<Integer, ArrayList<String>> result = new HashMap<>();
        int currentHour = 9;

        for (int i = 0; i < 11; i++) {
            ArrayList<String> available = new ArrayList<>();
            if (currentHour > 12) {
                currentHour = currentHour % 12;
            }

            for (int k = 0; k < hourMap.get(currentHour).size(); k++) {
                available.add(hourMap.get(currentHour).get(k));
            }

            if (available.size() <= 3) {
                result.put(currentHour, available);
            }

            else {
                Random r = new Random();
                ArrayList<String> newAvailable = new ArrayList<>();
                for (int j = 0; j < 3; j++) {
                    int randIndex = r.nextInt(available.size());
                    newAvailable.add(available.get(randIndex));
                    available.remove(newAvailable.get(j));
                }
                result.put(currentHour, newAvailable);
            }

            currentHour++;

        }

        return result;
    }

    /**
     * Neatly prints out the final schedule
     * @param finalSchedule The map of the final schedule
     * @return A tidy schedule
     */
    public String printFinalSchedule(Map<Integer, ArrayList<String>> finalSchedule) {
        StringBuilder result = new StringBuilder();
        int currentKey = 9;

        for (int i = 0; i < finalSchedule.keySet().size(); i++) {
            if (currentKey > 12) {
                currentKey = currentKey % 12;
            }

            result.append(currentKey).append(": ").append(finalSchedule.get(currentKey)).append("\n");
            currentKey++;
        }

        return result.toString();
    }

    /**
     * Returns a string describing the DeskSchedule class
     * @return A string.
     */
    @Override
    public String toString() {
        return "DeskSchedule{" +
                "DAYS=" + DAYS +
                ", HOURS=" + HOURS +
                ", POSITION=" + POSITION +
                '}';
    }
}
