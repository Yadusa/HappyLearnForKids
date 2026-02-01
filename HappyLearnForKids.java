//Yadusa Balakrishnan 1221205086
//Keishav Rao Bala Subramaniam  1221207387

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;

// AWT classes
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

// IO classes
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

//Inheritance: Superclass
class ModulePanel extends JPanel {
    protected JLabel tLabel;
    public ModulePanel(String title) {
        setLayout(new BorderLayout());
        tLabel = new JLabel(title, SwingConstants.CENTER);
        tLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(tLabel, BorderLayout.NORTH);
    }
}

//WELCOME PANEL (NEW)
class WelcomePanel extends JPanel {
    public WelcomePanel(Runnable onStart) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to Happy Learn!", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));

        JButton startBtn = new JButton("START");
        startBtn.setFont(new Font("Arial", Font.BOLD, 22));
        startBtn.setPreferredSize(new Dimension(200, 60));

        startBtn.addActionListener(e -> onStart.run());

        JPanel center = new JPanel(new FlowLayout());
        center.add(startBtn);

        add(title, BorderLayout.CENTER);
        add(center, BorderLayout.SOUTH);
    }
}

class UserDetailsPanel extends ModulePanel {
    private JTextField nameField, ageField, schoolField, gradeField;

    public UserDetailsPanel(Runnable onProceed) {
        super("Tell us about yourself!");
        
        // Grid layout with 5 rows (Name, Age, School, Grade, Button)
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        nameField = new JTextField();
        ageField = new JTextField();
        schoolField = new JTextField();
        gradeField = new JTextField();

        form.add(new JLabel("Full Name:"));
        form.add(nameField);
        form.add(new JLabel("Age:"));
        form.add(ageField);
        form.add(new JLabel("School Name:"));
        form.add(schoolField);
        form.add(new JLabel("Grade/Standard:"));
        form.add(gradeField);

        JButton submitBtn = new JButton("Save & Proceed");
        submitBtn.addActionListener(e -> {
            if (saveDetails()) {
                // Personalized greeting
                String name = nameField.getText().trim();
                JOptionPane.showMessageDialog(this, "Let's get started, " + name + "!");
                onProceed.run();
            }
        });

        add(form, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }

    private boolean saveDetails() {
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();
        String school = schoolField.getText().trim();
        String grade = gradeField.getText().trim();

        if (name.isEmpty() || age.isEmpty() || school.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all details!");
            return false;
        }

        // Saving to your userDetails.txt file
        try (java.io.FileWriter fw = new java.io.FileWriter("userDetails.txt", true);
             java.io.PrintWriter out = new java.io.PrintWriter(fw)) {
            out.println("Name: " + name + " | Age: " + age + " | School: " + school + " | Grade: " + grade);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving details.");
            return false;
        }
    }
}

//MODULE MENU PANEL 
class ModuleMenuPanel extends ModulePanel {
    public ModuleMenuPanel(Runnable openAlpha, Runnable openMedium, Runnable openAdvanced) {
        super("Choose Your Learning Level");

        JPanel grid = new JPanel(new GridLayout(3, 1, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton begBtn = new JButton("Beginner (Alphabets, Numbers & Colours)");
        JButton medBtn = new JButton("Medium (Arithmetic Operations)");
        JButton advBtn = new JButton("Advanced (Coming Soon!)");
        
        JButton exitBtn = new JButton("Exit App");
        exitBtn.setBackground(Color.LIGHT_GRAY);
        exitBtn.addActionListener(e -> {
          JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
          System.exit(0);
        });

        Font f = new Font("Arial", Font.BOLD, 18);
        
        // Setting button colors
        begBtn.setBackground(Color.GREEN);
        begBtn.setForeground(Color.WHITE); // White text for better contrast on Red
        
        medBtn.setBackground(Color.YELLOW);
        medBtn.setForeground(Color.BLACK); // Black text for Yellow
        
        advBtn.setBackground(Color.RED);
        advBtn.setForeground(Color.WHITE);

        // Required for color to show on some operating systems
        begBtn.setOpaque(true);
        begBtn.setBorderPainted(false);
        medBtn.setOpaque(true);
        medBtn.setBorderPainted(false);
        advBtn.setOpaque(true);
        advBtn.setBorderPainted(false);

        begBtn.setFont(f);
        medBtn.setFont(f);
        advBtn.setFont(f);

        begBtn.addActionListener(e -> openAlpha.run());
        medBtn.addActionListener(e -> openMedium.run());
        advBtn.addActionListener(e -> openAdvanced.run());

        grid.add(begBtn);
        grid.add(medBtn);
        grid.add(advBtn);

        add(grid, BorderLayout.CENTER);
    }
}

class BeginnerMenuPanel extends ModulePanel {
    public BeginnerMenuPanel(Runnable openAlpha, Runnable openNumbers, Runnable openColors, Runnable goBack) {
        super("Beginner Modules");

        JPanel grid = new JPanel(new GridLayout(3, 1, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton alphaBtn = new JButton("Learn Alphabets");
        JButton numBtn = new JButton("Learn Numbers");
        JButton colorBtn = new JButton("Learn Colors");
        JButton backBtn = new JButton("Back to Levels");

        Font f = new Font("Arial", Font.BOLD, 18);
        alphaBtn.setFont(f);
        numBtn.setFont(f);
        colorBtn.setFont(f);

        alphaBtn.addActionListener(e -> openAlpha.run());
        numBtn.addActionListener(e -> openNumbers.run());
        colorBtn.addActionListener(e -> openColors.run());
        backBtn.addActionListener(e -> goBack.run());

        grid.add(alphaBtn);
        grid.add(numBtn);
        grid.add(colorBtn);

        add(grid, BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);
    }
}

class MediumMathModule extends ModulePanel {
    private JTextField numField1, numField2;
    private JLabel resultLabel;

    public MediumMathModule(Runnable goMenu) {
        super("Medium Level: Math Fun!");

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        numField1 = new JTextField();
        numField2 = new JTextField();
        resultLabel = new JLabel("Enter numbers and pick an operation!", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

        inputPanel.add(new JLabel("First Number:"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Second Number:"));
        inputPanel.add(numField2);

        JPanel opPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("+");
        JButton subBtn = new JButton("-");
        JButton mulBtn = new JButton("x");
        JButton divBtn = new JButton("÷");

        addBtn.addActionListener(e -> calculate('+'));
        subBtn.addActionListener(e -> calculate('-'));
        mulBtn.addActionListener(e -> calculate('*'));
        divBtn.addActionListener(e -> calculate('/'));

        opPanel.add(addBtn); opPanel.add(subBtn);
        opPanel.add(mulBtn); opPanel.add(divBtn);

        JButton backBtn = new JButton("Back to Levels");
        backBtn.addActionListener(e -> goMenu.run());

        JPanel container = new JPanel(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(opPanel, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);
        add(backBtn, BorderLayout.PAGE_END);
    }

    private void calculate(char op) {
        try {
            double n1 = Double.parseDouble(numField1.getText());
            double n2 = Double.parseDouble(numField2.getText());
            double res = 0;
            String sym = "";

            if (op == '+') { res = n1 + n2; sym = " + "; }
            else if (op == '-') { res = n1 - n2; sym = " - "; }
            else if (op == '*') { res = n1 * n2; sym = " x "; }
            else if (op == '/') { 
                if (n2 == 0) { JOptionPane.showMessageDialog(this, "Cannot divide by 0!"); return; }
                res = n1 / n2; sym = " ÷ "; 
            }

            resultLabel.setText("Result: " + n1 + sym + n2 + " = " + String.format("%.2f", res));
            resultLabel.setForeground(new Color(0, 128, 0));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers!");
        }
    }
}

//ALPHABETS MODULE 
class AlphabetModule extends ModulePanel {

    private final String[] letters = {
            "A","B","C","D","E","F","G","H",
            "I","J","K","L","M","N","O","P",
            "Q","R","S","T","U","V","W","X",
            "Y","Z"
    };

    private int pageIndex = 0;            
    private final int pageSize = 8;

    private JPanel gridPanel;
    private JButton backBtn, nextBtn;

    private Runnable goMenu;
    private Runnable goNumbers;

    public AlphabetModule(Runnable goMenu, Runnable goNumbers) {
        super("Learn Your Alphabets!");
        this.goMenu = goMenu;
        this.goNumbers = goNumbers;

        // center grid like your drawing
        gridPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // bottom navigation (Back / Next)
        JPanel nav = new JPanel(new BorderLayout());
        backBtn = new JButton("Back");
        nextBtn = new JButton("Next");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.addActionListener(e -> {
          // We can use a callback or reach the main frame
          int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
             JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
             System.exit(0);
           }
        });

        nav.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        nav.add(backBtn, BorderLayout.WEST);
        nav.add(nextBtn, BorderLayout.CENTER);
        nav.add(exitBtn, BorderLayout.EAST);

        backBtn.addActionListener(e -> onBack());
        nextBtn.addActionListener(e -> onNext());

        add(gridPanel, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);

        refreshGrid();
    }

    private void refreshGrid() {
        gridPanel.removeAll();

        int start = pageIndex * pageSize;
        for (int i = 0; i < pageSize; i++) {
            int idx = start + i;

            if (idx < letters.length) {
                String letter = letters[idx];
                JButton btn = new JButton(letter);
                btn.setFont(new Font("Arial", Font.BOLD, 22));
                btn.setPreferredSize(new Dimension(80, 80));

                btn.addActionListener(e -> showPopup(letter));
                gridPanel.add(btn);
            } else {
                JButton empty = new JButton("");
                empty.setEnabled(false);
                gridPanel.add(empty);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void showPopup(String letter) {
    String imagePath = "";
    String description = "";

    try {
        // Points to the 'txt' folder shown in your screenshot
        File myFile = new File("txt/alphabet.txt");
        Scanner reader = new Scanner(myFile);
        
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            // Assuming your format is: A,image/apple.jpg,A is for Apple!
            String[] parts = line.split(",");
            
            if (parts[0].equalsIgnoreCase(letter)) {
                imagePath = parts[1]; // e.g. "image/apple.jpg"
                description = parts[2]; // e.g. "A is for Apple!"
                break;
            }
        }
        reader.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: Could not read txt/alphabets.txt");
        return; 
    }

    // Create the label with text
    JLabel lbl = new JLabel(description, SwingConstants.CENTER);
    lbl.setFont(new Font("Arial", Font.BOLD, 18));
    lbl.setHorizontalTextPosition(SwingConstants.CENTER);
    lbl.setVerticalTextPosition(SwingConstants.BOTTOM);

    // Retrieve the image from the 'image' folder shown in your screenshot
    File imgFile = new File(imagePath);
    if (imgFile.exists()) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        lbl.setIcon(new ImageIcon(img));
    } else {
        lbl.setText(description + " (Image not found in image folder)");
    }

    JOptionPane.showMessageDialog(this, lbl, "Alphabet Learning", JOptionPane.PLAIN_MESSAGE);
   }

    private void onBack() {
        if (pageIndex == 0) {
            goMenu.run();
        } else {
            pageIndex--;
            refreshGrid();
        }
    }

    private void onNext() {
        int maxPage = (int)Math.ceil(letters.length / (double)pageSize) - 1;
        if (pageIndex >= maxPage) {
            goNumbers.run();
        } else {
            pageIndex++;
            refreshGrid();
        }
    }
}

//NUMBERS MODULE
class NumbersModule extends ModulePanel {
    private Runnable goAlpha;
    private Runnable goMath;

    public NumbersModule(Runnable goAlpha, Runnable goMath) {
        super("Learn Numbers 1 to 10!");
        this.goAlpha = goAlpha;
        this.goMath = goMath;

        JPanel grid = new JPanel(new GridLayout(2, 5, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        Font f = new Font("Arial", Font.BOLD, 22);

        for (int i = 1; i <= 10; i++) {
            String num = String.valueOf(i);
            JButton btn = new JButton(num);
            btn.setFont(f);
            btn.setPreferredSize(new Dimension(80, 80));
            // When clicked, search the text file
            btn.addActionListener(e -> showPopup(num));
            grid.add(btn);
        }

        JPanel nav = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("Back");
        JButton nextBtn = new JButton("Next");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
        
        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.addActionListener(e -> {
          // We can use a callback or reach the main frame
          int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
            System.exit(0);
        }
      });
        
        nav.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        nav.add(backBtn, BorderLayout.WEST);
        nav.add(nextBtn, BorderLayout.CENTER);
        nav.add(exitBtn, BorderLayout.EAST);

        backBtn.addActionListener(e -> goAlpha.run());
        nextBtn.addActionListener(e -> goMath.run());

        add(grid, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);
    }

    private void showPopup(String num) {
        String imagePath = "";
        String description = "";

        // 1. Read the numbers.txt file
        try {
            File myFile = new File("txt/numbers.txt");
            Scanner reader = new Scanner(myFile);
            
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                
                // If the first part matches the number (e.g., "1")
                if (parts[0].equals(num)) {
                    imagePath = parts[1]; // Gets "image/1.jpg"
                    description = parts[2]; // Gets "Number One!"
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Could not read txt/numbers.txt");
            return;
        }

        // 2. Build the Popup UI
        JLabel lbl = new JLabel(description, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Load the image from the folder
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(img));
        } else {
            lbl.setText(description + " (Image not found at: " + imagePath + ")");
        }

        JOptionPane.showMessageDialog(this, lbl, "Number Learning", JOptionPane.PLAIN_MESSAGE);
    }
}

class ColorModule extends ModulePanel {
    private final String[] colors = {"Red", "Blue", "Green", "Yellow", "Orange", "Pink", "Brown", "Purple"};
    private Runnable goBack;
    private Runnable goNext; // Added to make the Next button work

    public ColorModule(Runnable goBack, Runnable goNext) {
        super("Learn Your Colors!");
        this.goBack = goBack;
        this.goNext = goNext;

        // 1. Setup the Color Grid (Center)
        JPanel grid = new JPanel(new GridLayout(2, 4, 15, 15)); // Changed to 2x4 to fit 8 colors
        grid.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        for (String c : colors) {
            JButton btn = new JButton(c);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(e -> showPopup(c));
            grid.add(btn);
        }

        // 2. Setup the Navigation Panel (Bottom)
        JPanel nav = new JPanel(new GridLayout(1, 3, 20, 0)); // 1 row, 3 columns for equal spacing
        nav.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton backBtn = new JButton("Back");
        JButton exitBtn = new JButton("Exit");

        // Style fonts
        Font btnFont = new Font("Arial", Font.BOLD, 16);
        backBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);

        // Button Actions
        backBtn.addActionListener(e -> goBack.run());
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
                System.exit(0);
            }
        });

        // Add buttons to nav panel
        nav.add(backBtn);
        nav.add(exitBtn);

        // 3. Add panels to the Main ModulePanel
        add(grid, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);
    }

    private void showPopup(String colorName) {
        String imagePath = "";
        String description = "";

        try {
            File myFile = new File("txt/colors.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(colorName)) {
                    imagePath = parts[1];
                    description = parts[2];
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading txt/colors.txt");
            return;
        }

        JLabel lbl = new JLabel(description, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            lbl.setIcon(new ImageIcon(img));
        }
        JOptionPane.showMessageDialog(this, lbl, "Color Fun", JOptionPane.PLAIN_MESSAGE);
    }
}

//MATH MODULE
class MathModule extends ModulePanel {
    private JTextField numField1, numField2;
    private JLabel resultLabel;
    private JButton calculateBtn;

    public MathModule(Runnable goNumbers, Runnable goMenu) {
        super("Multiplication Fun!");

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        numField1 = new JTextField();
        numField2 = new JTextField();
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        calculateBtn = new JButton("Multiply!");
        calculateBtn.setBackground(Color.CYAN);

        inputPanel.add(new JLabel("Enter first number (1-10):"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Enter second number (1-10):"));
        inputPanel.add(numField2);
        inputPanel.add(calculateBtn);

        calculateBtn.addActionListener(e -> performMultiplication());

        JPanel nav = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("Back");
        JButton nextBtn = new JButton("Next");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        nav.add(backBtn, BorderLayout.WEST);
        nav.add(nextBtn, BorderLayout.EAST);

        backBtn.addActionListener(e -> goNumbers.run());
        nextBtn.addActionListener(e -> goMenu.run());

        add(inputPanel, BorderLayout.CENTER);
        add(resultLabel, BorderLayout.SOUTH);

        JPanel southWrap = new JPanel(new BorderLayout());
        southWrap.add(resultLabel, BorderLayout.CENTER);
        southWrap.add(nav, BorderLayout.SOUTH);
        remove(resultLabel);
        add(southWrap, BorderLayout.SOUTH);
    }

    private void performMultiplication() {
        try {
            int n1 = Integer.parseInt(numField1.getText());
            int n2 = Integer.parseInt(numField2.getText());

            if (n1 < 1 || n1 > 10 || n2 < 1 || n2 > 10) {
                JOptionPane.showMessageDialog(this, "Please use numbers between 1 and 10!");
                return;
            }

            int result = n1 * n2;
            resultLabel.setText("Result: " + n1 + " x " + n2 + " = " + result);
            resultLabel.setForeground(new Color(0, 128, 0));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Oops! Please enter valid numbers.",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            resultLabel.setText("Try again!");
            resultLabel.setForeground(Color.RED);
        }
    }
}

// MAIN FRAME (HappyLearn)
public class HappyLearnForKids extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public HappyLearnForKids() {
        setTitle("Kids Fun Learning System");
        setSize(500, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Modules");
        JMenuItem welcomeItem = new JMenuItem("Welcome");
        JMenuItem menuItem = new JMenuItem("Menu");
        JMenuItem alphaItem = new JMenuItem("Alphabets");
        JMenuItem numbersItem = new JMenuItem("Numbers 1-10");
        JMenuItem mathItem = new JMenuItem("Multiplication");

        menu.add(welcomeItem);
        menu.add(menuItem);
        menu.add(alphaItem);
        menu.add(numbersItem);
        menu.add(mathItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Navigation Runnables (so panels can switch screens)
        Runnable showWelcome = () -> cardLayout.show(mainContainer, "Welcome");
        Runnable showDetails = () -> cardLayout.show(mainContainer, "Details");
        Runnable showBeginnerMenu = () -> cardLayout.show(mainContainer, "BeginnerMenu");
        Runnable showColors = () -> cardLayout.show(mainContainer, "Colors");
        Runnable showMediumMenu = () -> cardLayout.show(mainContainer, "MediumMenu");
        Runnable showMenu = () -> cardLayout.show(mainContainer, "Menu");
        Runnable showAlpha = () -> cardLayout.show(mainContainer, "Alpha");
        Runnable showNumbers = () -> cardLayout.show(mainContainer, "Numbers");
        Runnable showMath = () -> cardLayout.show(mainContainer, "Maths");

        // Menu item actions
        welcomeItem.addActionListener(e -> showWelcome.run());
        menuItem.addActionListener(e -> showMenu.run());
        alphaItem.addActionListener(e -> showAlpha.run());
        numbersItem.addActionListener(e -> showNumbers.run());
        mathItem.addActionListener(e -> showMath.run());

        // Screens
        mainContainer.add(new WelcomePanel(showDetails), "Welcome");
        mainContainer.add(new UserDetailsPanel(showMenu), "Details");
        mainContainer.add(new ModuleMenuPanel(showBeginnerMenu, showMediumMenu, showWelcome), "Menu"); // The missing link
        mainContainer.add(new BeginnerMenuPanel(showAlpha, showNumbers, showColors, showMenu), "BeginnerMenu");
        mainContainer.add(new AlphabetModule(showBeginnerMenu, showNumbers), "Alpha");
        mainContainer.add(new NumbersModule(showBeginnerMenu, showColors), "Numbers");
        mainContainer.add(new ColorModule(showBeginnerMenu, showMath), "Colors");
        mainContainer.add(new MathModule(showNumbers, showMenu), "Maths");

        add(mainContainer);

        // default screen
        cardLayout.show(mainContainer, "Welcome");
        
        loadUserProgress();
    }

    private void loadUserProgress() {
        try (BufferedReader br = new BufferedReader(new FileReader("progress.txt"))) {
            System.out.println("Last session: " + br.readLine());
        } catch (IOException e) {
            System.out.println("No previous progress found.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HappyLearnForKids().setVisible(true));
    }
    
    private void exitProgram() {
    JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!", 
                                  "Goodbye", JOptionPane.INFORMATION_MESSAGE);
    System.exit(0);
}
}
