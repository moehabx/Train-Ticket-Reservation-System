package TrainRes;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDate;
public class Train {
	private int tNumber;
    private String schedule;
    private String[] tDestinations;
    int coaches;
    int coachCapacity;
    private int totalSeats;
    boolean airCondition;
    
    public Train(int tNumber, String schedule, String[] tDestinations, int coaches, int coachCapacity, boolean airCondition) {
        if (tNumber <= 0 || coaches <= 0 || coachCapacity <= 0) {
            throw new IllegalArgumentException("Train number, coaches, and coachCapacity must be positive.");
        }
        this.tNumber = tNumber;
        this.schedule = schedule;
        this.tDestinations = tDestinations;
        this.coaches = coaches;
        this.airCondition = airCondition;
        this.coachCapacity = coachCapacity;
        this.totalSeats = coachCapacity * coaches;
    }

    // Getters
    public int getTrainNumber() {
        return tNumber;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getCoachCapacity() {
        return coachCapacity;
    }

    @Override
    public String toString() {
        return "Train Number: " + tNumber +
               "\nSchedule: " + schedule +
               "\nDestinations: " + Arrays.toString(tDestinations) +
               "\nCoaches: " + coaches +
               "\nSeats per Coach: " + coachCapacity +
               "\nTotal Seats: " + totalSeats +
               "\nAirCondition: " + airCondition;
    }

    // Save train details to a file
    public void saveToFile() {
        if (!doesTrainExist("Trains.txt")) {
            writeTrainToFile("Trains.txt");
            System.out.println("Train successfully added to the file.");
        } else {
            System.out.println("Train with number " + tNumber + " already exists in the file.");
        }
    }

    private boolean doesTrainExist(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("Train Number: " + tNumber)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // File does not exist; it's fine to add the train
        }
        return false;
    }

    private void writeTrainToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(this.toString() + "\n");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static String displayTrainsFile() {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("Trains.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            fileContent.append("Error reading the trains file: ").append(e.getMessage());
        }
        return fileContent.toString();
    }
    
}


