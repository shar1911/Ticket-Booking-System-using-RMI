import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class GUIClient {
    private static int availableTickets = 10;
    private static JLabel ticketLabel;
    private static JTextArea logArea;
    private static Map<String, Integer> transactions = new HashMap<>();
    private static Map<String, Integer> destinationPrices;

    public static void main(String[] args) {
        destinationPrices = new HashMap<>();
        destinationPrices.put("Delhi", 300);
        destinationPrices.put("Mumbai", 500);
        destinationPrices.put("Bangalore", 700);
        destinationPrices.put("Chennai", 650);
        destinationPrices.put("Kolkata", 550);

        JFrame frame = new JFrame("Train Ticket Booking System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel("Enter Tickets:");
        JTextField ticketField = new JTextField(5);
        JComboBox<String> destinationBox = new JComboBox<>(destinationPrices.keySet().toArray(new String[0]));
        JButton bookButton = new JButton("Book Ticket");
        JButton cancelButton = new JButton("Cancel Ticket");
       
        ticketLabel = new JLabel("Available Tickets: " + availableTickets);

        topPanel.add(label);
        topPanel.add(ticketField);
        topPanel.add(new JLabel("Destination:"));
        topPanel.add(destinationBox);
        topPanel.add(bookButton);
        topPanel.add(cancelButton);
        topPanel.add(ticketLabel);

        logArea = new JTextArea(10, 40);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        bookButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int tickets;
                try {
                    tickets = Integer.parseInt(ticketField.getText());
                } catch (NumberFormatException ex) {
                    logMessage("Invalid input! Please enter a number.");
                    return;
                }
               
                String destination = (String) destinationBox.getSelectedItem();
                int ticketPrice = destinationPrices.get(destination);
               
                if (tickets > 0 && tickets <= availableTickets) {
                    availableTickets -= tickets;
                    updateLabel();
                    String pnrNumber = generatePNRNumber();
                    transactions.put(pnrNumber, tickets);
                    int totalCost = tickets * ticketPrice;
                    logTransaction("Booked", tickets, pnrNumber, destination, totalCost);
                    logMessage("PNR: " + pnrNumber + "\nBooked " + tickets + " tickets to " + destination + "\nCost: " + totalCost + " INR");
                    JOptionPane.showMessageDialog(frame, "PNR: " + pnrNumber + "\nBooked " + tickets + " tickets to " + destination + "\nCost: " + totalCost + " INR");
                } else {
                    logMessage("Invalid number of tickets!");
                    JOptionPane.showMessageDialog(frame, "Invalid number of tickets!");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pnrNumber = JOptionPane.showInputDialog(frame, "Enter PNR Number to Cancel:");
                if (pnrNumber == null || !transactions.containsKey(pnrNumber)) {
                    logMessage("Invalid PNR Number!");
                    JOptionPane.showMessageDialog(frame, "Invalid PNR Number!");
                    return;
                }
               
                int bookedTickets = transactions.get(pnrNumber);
                int ticketsToCancel;
                try {
                    ticketsToCancel = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter number of tickets to cancel:"));
                } catch (NumberFormatException ex) {
                    logMessage("Invalid input! Please enter a number.");
                    return;
                }
               
                if (ticketsToCancel > 0 && ticketsToCancel <= bookedTickets) {
                    availableTickets += ticketsToCancel;
                    if (ticketsToCancel == bookedTickets) {
                        transactions.remove(pnrNumber);
                    } else {
                        transactions.put(pnrNumber, bookedTickets - ticketsToCancel);
                    }
                    updateLabel();
                    logMessage("PNR: " + pnrNumber + "\nCancelled " + ticketsToCancel + " tickets.");
                    JOptionPane.showMessageDialog(frame, "PNR: " + pnrNumber + "\nCancelled " + ticketsToCancel + " tickets.");
                } else {
                    logMessage("Invalid cancellation!");
                    JOptionPane.showMessageDialog(frame, "Invalid cancellation!");
                }
            }
        });
       
        frame.setVisible(true);
    }

    private static void updateLabel() {
        ticketLabel.setText("Available Tickets: " + availableTickets);
    }

    private static void logTransaction(String type, int tickets, String pnrNumber, String destination, int totalCost) {
        try (FileWriter writer = new FileWriter("transactions.log", true)) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logEntry = timestamp + " - PNR: " + pnrNumber + " - " + type + " " + tickets + " tickets to " + destination + " - Amount: " + totalCost + " INR\n";
            writer.write(logEntry);
            logMessage(logEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logMessage(String message) {
        logArea.append(message + "\n");
    }

    private static String generatePNRNumber() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder pnr = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            pnr.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return pnr.toString();
    }
}
