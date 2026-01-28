
//Yadusa Baalkrishnan 1221205086
//Keishav Rao Bala Subramaniam  1221207387

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;  
import javax.swing.JFrame; 
import javax.swing.JTextField; 
import javax.swing.JButton; 
import javax.swing.JLabel; 
import javax.swing.JPanel; 
import java.awt.BorderLayout; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;  

// 1. Inheritance: Superclass
class ModulePanel extends JPanel {
    protected JLabel tLabel;
    public ModulePanel(String title) {
        setLayout(new BorderLayout());
        tLabel = new JLabel(title, SwingConstants.CENTER);
        tLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(tLabel, BorderLayout.NORTH);
    }
}

// 2. Inheritance: Subclass for Alphabets
class AlphabetModule extends ModulePanel {
    private JComboBox<String> alphaBets;
    private JLabel imageDisplay;

    public AlphabetModule() {
        super("Learn Your Alphabets!");
        
        // JComponents & Layout
        JPanel centerPanel = new JPanel(new FlowLayout());
        String[] letters = {"A", "B", "C"}; // Extend to Z
        alphaBets = new JComboBox<>(letters);
        imageDisplay = new JLabel("Select a letter");

        // Event Handling
        alphaBets.addActionListener(e -> {
            String selected = (String) alphaBets.getSelectedItem();
            updateContent(selected);
        });

        centerPanel.add(new JLabel("Choose a Letter: "));
        centerPanel.add(alphaBets);
        add(centerPanel, BorderLayout.CENTER);
        add(imageDisplay, BorderLayout.SOUTH);
    }

    private void updateContent(String letter) {
        // ImageIcon Requirement
        if (letter.equals("A")) {
            imageDisplay.setIcon(new ImageIcon("apple.jpg")); 
            imageDisplay.setText("A is for Apple!");
        }
        // Add other letters here...
    }
}

class MathModule extends ModulePanel {
    private JTextField numField1, numField2;
    private JLabel resultLabel;
    private JButton calculateBtn;

    public MathModule() {
        super("Multiplication Fun!");

        // 1. Layout Manager: Using GridLayout for the input area
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        // 2. JComponents: JTextFields and JLabels
        numField1 = new JTextField();
        numField2 = new JTextField();
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        calculateBtn = new JButton("Multiply!");

        // styling for kids
        calculateBtn.setBackground(Color.CYAN);
        
        inputPanel.add(new JLabel("Enter first number (1-10):"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Enter second number (1-10):"));
        inputPanel.add(numField2);
        inputPanel.add(calculateBtn);

        // 3. Event Handling: ActionListener
        calculateBtn.addActionListener(e -> performMultiplication());

        add(inputPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
    }

    private void performMultiplication() {
        // 4. Exception Handling: try-catch for NumberFormatException
        try {
            int n1 = Integer.parseInt(numField1.getText());
            int n2 = Integer.parseInt(numField2.getText());

            // Simple validation for the 1-10 range
            if (n1 < 1 || n1 > 10 || n2 < 1 || n2 > 10) {
                // Additional Component: JOptionPane
                JOptionPane.showMessageDialog(this, "Please use numbers between 1 and 10!");
                return;
            }

            int result = n1 * n2;
            resultLabel.setText("Result: " + n1 + " x " + n2 + " = " + result);
            resultLabel.setForeground(new Color(0, 128, 0)); // Helper Class: Color

        } catch (NumberFormatException ex) {
            // Requirement: Handle appropriate exception
            JOptionPane.showMessageDialog(this, "Oops! Please enter valid numbers.", 
                                          "Input Error", JOptionPane.ERROR_MESSAGE);
            resultLabel.setText("Try again!");
            resultLabel.setForeground(Color.RED);
        }
    }
}

public class HappyLearn extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public HappyLearn() {
        setTitle("Happy Learn for Kids");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        

        // Additional Components: JMenuBar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Modules");
        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem alphaItem = new JMenuItem("Alphabets");
        JMenuItem mathItem = new JMenuItem("Multiplication");
                
        menu.add(homeItem);
        menu.add(alphaItem);
        menu.add(mathItem);
        setJMenuBar(menuBar);
        menuBar.add(menu);
        
        alphaItem.addActionListener(e -> cardLayout.show(mainContainer, "Alpha"));
        mathItem.addActionListener(e -> cardLayout.show(mainContainer, "Maths"));
        
        homeItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Welcome to Happy Learn!"));

        // Add Screens
        mainContainer.add(new AlphabetModule(), "Alpha");
        mainContainer.add(new MathModule(), "Maths");
        add(mainContainer);

        // File I/O: Reading (Simplified)
        loadUserProgress();
    }

    private void loadUserProgress() {
        try (BufferedReader br = new BufferedReader(new FileReader("progress.txt"))) {
            System.out.println("Last session: " + br.readLine());
        } catch (IOException e) {
            // Exception Handling requirement
            System.out.println("No previous progress found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HappyLearn().setVisible(true));
    }
}
