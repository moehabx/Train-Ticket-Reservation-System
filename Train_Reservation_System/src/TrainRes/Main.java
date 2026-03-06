package TrainRes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Initialize the GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TrainTicketSystemGUI(); // Create the TrainTicketSystemGUI instance to initialize the system
            }
        });
    }
}

class TrainTicketSystemGUI extends JFrame {
    private Train train1, train2, train3, train4; // Train objects for different routes
    private Ticket ticketa, ticketb, ticketc, ticketg; // Ticket objects for the different trains
    private JFrame frame; // Frame for displaying content

    public TrainTicketSystemGUI() {
        // Set up trains and tickets with initial values
        train1 = new Train(101, "10 A.M", new String[]{"Alexandria", "Cairo"}, 5, 5, true);
        train1.saveToFile(); // Save the train data to a file
        train2 = new Train(102, "12 A.M", new String[]{"Alexandria", "Tanta"}, 5, 5, true);
        train2.saveToFile();
        train3 = new Train(103, "2 P.M", new String[]{"Alexandria", "Matrouh"}, 5, 5, true);
        train3.saveToFile();
        train4 = new Train(104, "4 P.M", new String[]{"Alexandria", "Aswan"}, 5, 5, true);
        train4.saveToFile();

        // Initialize ticket objects for each train
        ticketa = new Ticket(train1, 101, 5, 5, 60);
        ticketb = new Ticket(train2, 102, 5, 5, 70);
        ticketc = new Ticket(train3, 103, 5, 5, 90);
        ticketg = new Ticket(train4, 104, 5, 5, 370);
        ticketa.restoreData();
        ticketb.restoreData();
        ticketc.restoreData();
        ticketg.restoreData();

        // Create and configure the JFrame (main window)
        setTitle("Train Ticket System");
        setSize(400, 400); // Set the size of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure application exits on closing
        setLayout(new BorderLayout()); // Use BorderLayout for main window layout
        frame = new JFrame("Train Reservation System"); // Create the frame

        // Set the frame to fullscreen and undecorated (without border)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);//Removes window borders (such as the title bar)

        // Background Panel with image as background
        BackgroundPanel backgroundPanel = new BackgroundPanel("background.jpg");
        backgroundPanel.setLayout(new BorderLayout()); // Use BorderLayout inside background panel
        add(backgroundPanel, BorderLayout.CENTER); // Add background panel to the main window

        // Title label
        JLabel title = new JLabel("Train Ticket Reservation System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40)); // Set title font
        title.setForeground(Color.BLACK); // Set title color to black
        title.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // Add padding around the title
        backgroundPanel.add(title, BorderLayout.NORTH); // Add title at the top of the background panel

        // Panel to hold the buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // Set the panel layout with 5 rows, 1 column
        panel.setOpaque(false);  // Make the panel transparent
        backgroundPanel.add(panel, BorderLayout.CENTER); // Add the panel to the center of the background

        // Buttons for different actions
        JButton displayTicketsButton = new JButton("Display Tickets");
        JButton bookTicketButton = new JButton("Book a Ticket");
        JButton displayTrainsButton = new JButton("Display Available Trains");
        JButton displayReservedFile = new JButton("Display Reserved Tickets");
        JButton totalIncomeButton = new JButton("Display Total Income");
        JButton restoreData = new JButton("Restore Data");
        JButton resetReserved = new JButton("Reset Reserved File");
        JButton exitButton = new JButton("Exit");
        // Add buttons to an array for easy manipulation
        JButton[] buttons = {displayTrainsButton, displayTicketsButton, displayReservedFile, bookTicketButton, totalIncomeButton, resetReserved, restoreData, exitButton};
        for (JButton button : buttons) {
            // Set modern font
            button.setFont(new Font("Roboto", Font.BOLD, 30));

            // Set transparent background and text color
            button.setBackground(new Color(255, 165, 0));  // Orange color
            button.setForeground(new Color(255, 255, 255)); // White text color by default

            button.setFocusPainted(true); // Disable button's focus painting

            // Add a thin border around the button
            button.setBorder(BorderFactory.createLineBorder(new Color(255, 140, 0), 1)); // Thin 1-pixel border

            // Add padding for a modern, clean look
            button.setMargin(new Insets(10, 20, 10, 20)); // Consistent padding

            // Transparent background with hover effect on text
            button.setContentAreaFilled(false); // Disables default background fill
            button.setOpaque(false); // Makes the button visually transparent

            // Add hover effect for text color
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (button == exitButton) {
                        button.setForeground(new Color(255, 0, 0)); // Change text to red for exit button
                    } else {
                        button.setForeground(new Color(0, 255, 0)); // Change text to green for other buttons
                    }
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setForeground(new Color(255, 255, 255)); // Revert text to white
                }
            });

            // Add the button to the panel
            panel.add(button);
        }

        // Action listeners for buttons
        displayTicketsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, Ticket.displayTicketsFile(), "Available Tickets", JOptionPane.INFORMATION_MESSAGE);
                Ticket.displayTicketsFile(); // Displays the manually saved tickets
            }
        });
        
        displayReservedFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the reserved tickets as a string
                String reservedTickets = Ticket.displayReservedFile();

                // Create a JTextArea to display the tickets
                JTextArea textArea = new JTextArea(reservedTickets);
                textArea.setEditable(false); // Make it read-only
                textArea.setLineWrap(true);  // Wrap long lines
                textArea.setWrapStyleWord(true); // Wrap at word boundaries for better readability

                // Add the text area to a scroll pane
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(400, 300)); // Set the size of the scrollable area

                // Show the scrollable dialog
                JOptionPane.showMessageDialog(frame, scrollPane, "Reserved Tickets", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a dialog for ticket booking options
                String[] options = {"Alexandria to Cairo", "Alexandria to Tanta", "Alexandria to Matrouh", "Alexandria to Aswan"};
                String choice = (String) JOptionPane.showInputDialog(null, "Choose a route:", "Book a Ticket",
                        JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (choice != null) {
                    // Book the selected ticket
                    switch (choice) {
                        case "Alexandria to Cairo":
                            ticketa.bookTicketByID(101);
                            JOptionPane.showMessageDialog(frame, ticketa.toString(), "Trains", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case "Alexandria to Tanta":
                            ticketb.bookTicketByID(102);
                            JOptionPane.showMessageDialog(frame, ticketb.toString(), "Trains", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case "Alexandria to Matrouh":
                            ticketc.bookTicketByID(103);
                            JOptionPane.showMessageDialog(frame, ticketc.toString(), "Trains", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        case "Alexandria to Aswan":
                            ticketg.bookTicketByID(104);
                            JOptionPane.showMessageDialog(frame, ticketg.toString(), "Trains", JOptionPane.INFORMATION_MESSAGE);
                            break;
                    }
                }
            }
        });

        displayTrainsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, Train.displayTrainsFile(), "Available Trains", JOptionPane.INFORMATION_MESSAGE);
                Train.displayTrainsFile();
            }
        });

        totalIncomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Total Income: " + Ticket.getTotalIncome());
            }
        });
        restoreData.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		ticketa.restoreData();
                ticketb.restoreData();
                ticketc.restoreData();
                ticketg.restoreData();
        		JOptionPane.showMessageDialog(null, "Data is Restored to Avoid Losing Data Again Shutdown the System Correctly:");
        	}
        });
        resetReserved.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
                // Show a confirmation dialog\
                int choice = JOptionPane.showConfirmDialog(
                        frame, 
                        "Are you sure you want to Reset The File?", 
                        "Confirm Reset", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE
                );

                // Check the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                	ticketa.resetReservedFile();
                	ticketb.resetReservedFile();
                	ticketc.resetReservedFile();
                	ticketg.resetReservedFile();
                    JOptionPane.showMessageDialog(frame, 
                                                  "File Has Been Reset", 
                                                  "Successfull", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show a confirmation dialog
                int choice = JOptionPane.showConfirmDialog(
                        frame, 
                        "Are you sure you want to exit?", 
                        "Confirm Exit", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE
                );

                // Check the user's choice
                if (choice == JOptionPane.YES_OPTION) {
                    // Show "All rights reserved" message
                    JOptionPane.showMessageDialog(frame, 
                                                  "All Rights Reserved To SU Programming Team", 
                                                  "Exiting", 
                                                  JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // Exit the program
                }
            }
        });


        // Explicitly trigger repaint to refresh the UI
        backgroundPanel.revalidate();
        backgroundPanel.repaint();

        // Set frame visibility to true to show the window
        setVisible(true);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            // Use double backslashes or a forward slash for file paths
            backgroundImage = new ImageIcon(imagePath).getImage();
            System.out.println("Background image loaded successfully.");
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    // Override the paintComponent method to draw the background
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Print to check if this method is being called
        System.out.println("paintComponent called");
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Draw the background image
        } else {
            System.out.println("Background image is null."); // Error message if the image wasn't loaded
        }
    }
}
