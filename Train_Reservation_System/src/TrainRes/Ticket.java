package TrainRes;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.time.LocalDate;
public class Ticket {
	    private LocalDate date ;
	    private int tickID;
	    private String from;
	    private String to;
	    private int tickAuth;
	    private int coach;
	    private int seat;
	    private int coachCapacity;
	    private int coaches;
	    private int totalSeats;
	    private double tickPrice;
	    private  int seatCounter = 0; 
	    private  int reservedCounter = 0;
	    private  int coachCounter = 1; 
	    private boolean airCondition;
	    private static double totalIncome = 0;
	    private boolean isFull = false;

	    public Ticket(Train train, int tickID, String from, String to, double tickPrice) {
	        this.tickID = tickID;
	        this.from = from;
	        this.to = to;
	        this.tickPrice = tickPrice;
	        this.coachCapacity = train.coachCapacity;
	        this.coaches = train.coaches;
	        this.totalSeats = coachCapacity * coaches;
	        this.airCondition = train.airCondition;
	      
	        writeTicketToFile("Tickets.txt");
	    }
	    public Ticket(Train train, int tickID, int coachCapacity, int coaches, double tickPrice) {
	    	this.tickID = tickID;
	    	this.coachCapacity = train.coachCapacity;
	    	this.coaches = coaches;
	    	this.totalSeats = coachCapacity * coaches;
	    	this.airCondition = train.airCondition;
	    	this.tickPrice = tickPrice;
	    }
	    
	    public static double getTotalIncome() {
	    	return totalIncome;
	    }
	    public void chooseSeat() {
	        if (reservedCounter < totalSeats) {
	            ++reservedCounter;
	            ++seatCounter;
	        } else {
	            System.err.println("All seats are reserved.");
	            isFull = true;
	        }
	    }

	    public int chooseCoach() {
	        // Reset the seat counter if a coach is full and move to the next coach
	        if (seatCounter >= coachCapacity) {
	            seatCounter = 0; // Reset seat counter for the next coach
	            ++coachCounter;
	        }
	        return coachCounter;
	    }


	    private int generateTickAuth() {
	        return (this.seatCounter + this.coachCounter + this.tickID) * 392343;
	    }

	    @Override
	    public String toString() {
	    	if(!isFull) {
	        return 
	        		
	               "Coach: " + coachCounter +
	               "\nSeat: " + seatCounter +
	               "\nTicket Authorization: " + generateTickAuth() +
	        	   "\nAir Condition: " + airCondition;
	    	}
	    	else {
	    		return "All Tickets Are Reserved Book Another Ticket";
	    	}

	    }
	    
	    public String toString1() {
            this.date = LocalDate.now();
	        return 
	            "Ticket ID: " + tickID +
	            "\nFrom: " + from +
	            "\nTo: " + to +
	            "\nDate: " + date +
	            "\nTicket Price: $" + tickPrice + 
	        	"\nAir Condition: " + airCondition;
	                
	    }
	    public static String displayTicketsFile() {
	        StringBuilder fileContent = new StringBuilder("Available Tickets:\n");
	        try (BufferedReader reader = new BufferedReader(new FileReader("Tickets.txt"))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                fileContent.append(line).append("\n");
	            }
	        } catch (IOException e) {
	            fileContent.append("Error reading the tickets file: ").append(e.getMessage());
	        }
	        return fileContent.toString();
	    }
	    void writeTicketToFile(String filename) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
	            writer.write(this.toString1()  +  "\n");
	            writer.newLine();
	        } catch (IOException e) {
	            System.out.println("Error writing to file: " + e.getMessage());
	        }
	    }
	    public void bookTicketByID(int ticketID) {
	        if (reservedCounter >= totalSeats) {
	            System.err.println("ALL SEATS HAVE BEEN RESERVED");
	            isFull = true;
	            return;
	        }

	        try (BufferedReader reader = new BufferedReader(new FileReader("Tickets.txt"))) {
	            String line;
	            boolean ticketFound = false;
	            StringBuilder ticketRecord = new StringBuilder(); // To store the entire ticket details

	            while ((line = reader.readLine()) != null) {
	                if (line.contains("Ticket ID: " + ticketID)) {
	                    // Start capturing the ticket record when the ticket ID is found
	                    ticketFound = true;
	                    ticketRecord.append(line).append("\n"); // Append the ticket ID line

	                    // Continue reading lines until we hit an empty line or end of file
	                    while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
	                        ticketRecord.append(line).append("\n"); // Append the rest of the ticket details
	                    }
	                    break;
	                }
	            }

	            if (ticketFound) {
	                // Increment seat and coach counters appropriately
	                chooseCoach();
	                chooseSeat();
	                // Append the ticket seating information
	                ticketRecord.append(this.toString());

	                // Print the found ticket record
	                System.out.println("\nSelected Ticket:\n" + ticketRecord.toString());

	                // Save the found ticket record to Reserved.txt
	                saveToReservedFile(ticketRecord.toString());
	            } else {
	                System.out.println("No ticket found with Ticket ID: " + ticketID);
	            }
	        } catch (IOException e) {
	            System.out.println("Error processing the tickets file: " + e.getMessage());
	        }
	    }
	    public void restoreData() {
	    	readTotalIncome();
	        int lastSeat = 0;
	        int lastCoach = 0;

	        // Check if the Reserved.txt file is empty before processing
	        File reservedFile = new File("Reserved.txt");
	        if (reservedFile.length() == 0) {
	            System.out.println("Reserved file is empty. No data to restore.");
	            return; // Exit the method if the file is empty
	        }

	        try (BufferedReader reader = new BufferedReader(new FileReader(reservedFile))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("Coach: ")) {
	                    // Extract Coach and Seat from the respective lines
	                    String coachLine = line.trim(); // Coach: 1
	                    String seatLine = reader.readLine().trim(); // Seat: 1

	                    // Extracting the actual Coach and Seat values
	                    int coach = Integer.parseInt(coachLine.split(": ")[1].trim());
	                    int seat = Integer.parseInt(seatLine.split(": ")[1].trim());

	                    // Update the last coach and seat as we go
	                    lastCoach = coach;
	                    lastSeat = seat;
	                }
	            }

	            // Update the counters for the next ticket
	            this.coachCounter = lastCoach;
	            this.seatCounter = lastSeat;
	            this.reservedCounter = (coachCapacity * coachCounter) + seatCounter - 1; 

	            // Print the restored seat and coach values
	            System.out.println("Data restored: Last Coach: " + lastCoach + ", Last Seat: " + lastSeat);

	        } catch (IOException e) {
	            System.out.println("Error reading the Reserved file: " + e.getMessage());
	        }
	    }


	    
	    private void saveToReservedFile(String ticketRecord) {
	    	date =  LocalDate.now();
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reserved.txt", true))) {
	        	totalIncome += this.tickPrice;
	            writer.write(ticketRecord +"\nDate: " + date + "\nTotal Income: " + totalIncome +"\n"); // Write the entire ticket record to the file
	            writer.newLine(); // Ensure the ticket details are separated by a new line
	            System.out.println("Ticket successfully reserved and saved to " + "Reserved.txt");
	        } catch (IOException e) {
	            System.out.println("Error saving to Reserved file: " + e.getMessage());
	        }
	    }
	    public void resetReservedFile() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Reserved.txt"))) {
	            // Clear the Reserved.txt file
	            writer.write("");

	            // Reset instance counters
	            totalIncome = 0; // If totalIncome is instance-level
	            seatCounter = 0;
	            reservedCounter = 0;
	            coachCounter = 1;
	            isFull = false;

	            System.out.println("Reserved.txt file has been reset. Counters have been reset.");
	        } catch (IOException e) {
	            System.out.println("Error resetting Reserved.txt: " + e.getMessage());
	        }
	    }


	    private void readTotalIncome() {
	        double totalIncomeFromFile = 0.0;

	        try (BufferedReader reader = new BufferedReader(new FileReader("Reserved.txt"))) {
	            String line;
	            while ((line = reader.readLine()) != null) {
	                if (line.startsWith("Total Income: ")) {
	                    // Extract the total income value
	                    totalIncomeFromFile = Double.parseDouble(line.split(": ")[1].trim());
	                }
	            }
	        } catch (IOException e) {
	            System.out.println("Error reading the Reserved file: " + e.getMessage());
	        } catch (NumberFormatException e) {
	            System.out.println("Error parsing Total Income value: " + e.getMessage());
	        }

	        Ticket.totalIncome = totalIncomeFromFile;
	    }
	    public void displayTicket(String ticketRecord) {
	    	System.out.println("Selected Ticket: " + ticketRecord);
	    }
	    
		public static String displayReservedFile() {
	    StringBuilder fileContent = new StringBuilder("Reserved Tickets:\n");
	    try (BufferedReader reader = new BufferedReader(new FileReader("Reserved.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            fileContent.append(line).append("\n");
	        }
	    } catch (IOException e) {
	        fileContent.append("Error reading the reserved tickets file: ").append(e.getMessage());
	    }
	    return fileContent.toString();
	}
}

