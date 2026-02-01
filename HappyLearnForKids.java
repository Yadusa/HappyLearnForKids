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

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class ModulePanel extends JPanel {
    protected JLabel tLabel;
    public ModulePanel(String title) {
        setLayout(new BorderLayout());
        tLabel = new JLabel(title, SwingConstants.CENTER);
        tLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(tLabel, BorderLayout.NORTH);
    }
}

class WelcomePanel extends JPanel {
    
    private final String fontName = "Lucida Handwriting";
    private final Color backgroundColor = Color.white;
    private final Color titleColor = Color.orange;

    public WelcomePanel(Runnable onStart) {
        
        setBackground(backgroundColor);
        setLayout(new BorderLayout());

       
        JLabel title = new JLabel("Welcome to Happy Learn!", SwingConstants.CENTER);
        title.setFont(new Font(fontName, Font.BOLD, 25));
        title.setForeground(titleColor); 
        title.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));

        
        JButton startBtn = new JButton("START");
        startBtn.setFont(new Font("Arial", Font.BOLD, 15));
        startBtn.setBackground(Color.white);
        startBtn.setForeground(Color.blue); 
        startBtn.setPreferredSize(new Dimension(100, 50));
        
        startBtn.setFocusPainted(false);

        startBtn.addActionListener(e -> onStart.run());

        JPanel buttonContainer = new JPanel(new FlowLayout());
        buttonContainer.setBackground(backgroundColor); // Keep color consistent
        buttonContainer.add(startBtn);

        add(title, BorderLayout.CENTER);
        add(buttonContainer, BorderLayout.SOUTH);
    }
}

class UserDetailsPanel extends ModulePanel {
    private JTextField nameField, ageField, schoolField, gradeField;
    
    private String fontName = "Arial"; 
    private Font labelFont = new Font(fontName, Font.BOLD, 16);
    private Color themeColor = new Color(255,255,191);

    public UserDetailsPanel(Runnable onProceed) {
        super("Tell us about yourself!");
        this.setBackground(themeColor);

        
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 20));
        form.setBackground(themeColor);
        form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        
        form.add(createStyledLabel("Full Name:"));
        nameField = createStyledField();
        form.add(nameField);

        form.add(createStyledLabel("Age:"));
        ageField = createStyledField();
        form.add(ageField);

        form.add(createStyledLabel("School Name:"));
        schoolField = createStyledField();
        form.add(schoolField);

        form.add(createStyledLabel("Grade/Standard:"));
        gradeField = createStyledField();
        form.add(gradeField);

       
        JButton submitBtn = new JButton("Save & Proceed");
        submitBtn.setFont(new Font(fontName, Font.BOLD, 18));
        submitBtn.setBackground(new Color(239,178,97)); 
        submitBtn.setForeground(Color.WHITE);
        
        submitBtn.addActionListener(e -> {
            if (saveDetails()) {
                String name = nameField.getText().trim();
                JOptionPane.showMessageDialog(this, "Let's get started, " + name + "!");
                onProceed.run();
            }
        });

        add(form, BorderLayout.CENTER);
        add(submitBtn, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(labelFont);
        label.setForeground(new Color(25, 25, 112)); 
        return label;
    }

    // Helper method to style text fields
    private JTextField createStyledField() {
        JTextField field = new JTextField();
        field.setFont(new Font(fontName, Font.PLAIN, 16));
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        return field;
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

class ModuleMenuPanel extends ModulePanel {
    // Styling inspired by Lab 9 constants
    private final String fontName = "Lucida Handwriting"; //
    private final Color bgColor = new Color(255,255,191); //

    public ModuleMenuPanel(Runnable openAlpha, Runnable openMedium, Runnable openAdvanced) {
        super("Choose Your Learning Level");
        this.setBackground(bgColor); // Set the main panel background

        // Create the grid for buttons
        JPanel grid = new JPanel(new GridLayout(3, 1, 20, 20));
        grid.setBackground(bgColor); //
        grid.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Create buttons
        JButton begBtn = new JButton("Beginner");
        JButton medBtn = new JButton("Medium");
        JButton advBtn = new JButton("Advanced");

        // Styling buttons using Lab 9 logic
        styleButton(begBtn, Color.green, Color.white); // Magenta text, Cyan back
        styleButton(medBtn, Color.yellow, Color.white);   // Blue text
        styleButton(advBtn, Color.red, Color.white);   // Red text like "GONG XI FA CAI"

        // Functionality
        begBtn.addActionListener(e -> openAlpha.run());
        medBtn.addActionListener(e -> openMedium.run());
        advBtn.addActionListener(e -> openAdvanced.run());

        grid.add(begBtn);
        grid.add(medBtn);
        grid.add(advBtn);

        add(grid, BorderLayout.CENTER);

        // Exit button at the bottom
        JButton exitBtn = new JButton("Exit App");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 14));
        exitBtn.setForeground(Color.RED); 
        exitBtn.setBackground(Color.WHITE);
        exitBtn.addActionListener(e -> System.exit(0));
        add(exitBtn, BorderLayout.SOUTH);
    }

    private void styleButton(JButton btn, Color fore, Color back) {
        btn.setFont(new Font(fontName, Font.BOLD, 18)); 
        btn.setForeground(fore); 
        btn.setBackground(back); 
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(fore, 2));
    }
}

class BeginnerMenuPanel extends ModulePanel {
   
    private final String fontName = "Lucida Handwriting"; //
    private final Color bgColor = new Color(255,255,191); //

    public BeginnerMenuPanel(Runnable openAlpha, Runnable openNumbers, Runnable openColors, Runnable goBack) {
        super("Beginner Modules");
        this.setBackground(bgColor); 

        
        JPanel grid = new JPanel(new GridLayout(3, 1, 20, 20));
        grid.setBackground(bgColor); 
        grid.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton alphaBtn = new JButton("Learn Alphabets");
        JButton numBtn = new JButton("Learn Numbers");
        JButton colorBtn = new JButton("Learn Colors");
        JButton backBtn = new JButton("Back to Levels");

        // Applying styles: Foreground/Background logic from Lab 9
        styleBeginnerButton(alphaBtn, Color.magenta, Color.white); 
        styleBeginnerButton(numBtn, Color.blue, Color.white); 
        styleBeginnerButton(colorBtn, new Color(0, 128, 0), Color.white); 


        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        backBtn.setForeground(Color.RED); // 
        backBtn.setBackground(Color.white);
        backBtn.setFocusPainted(false);

    
        alphaBtn.addActionListener(e -> openAlpha.run());
        numBtn.addActionListener(e -> openNumbers.run());
        colorBtn.addActionListener(e -> openColors.run());
        backBtn.addActionListener(e -> goBack.run());

        grid.add(alphaBtn);
        grid.add(numBtn);
        grid.add(colorBtn);

        add(grid, BorderLayout.CENTER);
        
      
        JPanel navPanel = new JPanel(new FlowLayout());
        navPanel.setBackground(bgColor);
        navPanel.add(backBtn);
        add(navPanel, BorderLayout.SOUTH);
    }

    
    private void styleBeginnerButton(JButton btn, Color fore, Color back) {
        btn.setFont(new Font(fontName, Font.BOLD, 20)); 
        btn.setForeground(fore);
        btn.setBackground(back); 
        btn.setBorder(BorderFactory.createLineBorder(fore, 3)); // Bold border matching text
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

        gridPanel = new JPanel(new GridLayout(2, 4, 15, 15));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        JPanel nav = new JPanel(new BorderLayout());
        backBtn = new JButton("Back");
        nextBtn = new JButton("Next");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.BOLD, 16));
        exitBtn.addActionListener(e -> {
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
            File myFile = new File("txt/alphabet.txt");
            Scanner reader = new Scanner(myFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts[0].equalsIgnoreCase(letter)) {
                    imagePath = parts[1];
                    description = parts[2];
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Could not read txt/alphabets.txt");
            return;
        }

        JLabel lbl = new JLabel(description, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);

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

        try {
            File myFile = new File("txt/numbers.txt");
            Scanner reader = new Scanner(myFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                if (parts[0].equals(num)) {
                    imagePath = parts[1];
                    description = parts[2];
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: Could not read txt/numbers.txt");
            return;
        }

        JLabel lbl = new JLabel(description, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);

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
    private Runnable goNext;

    public ColorModule(Runnable goBack, Runnable goNext) {
        super("Learn Your Colors!");
        this.goBack = goBack;
        this.goNext = goNext;

        JPanel grid = new JPanel(new GridLayout(2, 4, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        for (String c : colors) {
            JButton btn = new JButton(c);
            btn.setFont(new Font("Arial", Font.BOLD, 18));
            btn.addActionListener(e -> showPopup(c));
            grid.add(btn);
        }

        JPanel nav = new JPanel(new GridLayout(1, 3, 20, 0));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton backBtn = new JButton("Back");
        JButton exitBtn = new JButton("Exit");

        Font btnFont = new Font("Arial", Font.BOLD, 16);
        backBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);

        backBtn.addActionListener(e -> goBack.run());
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
                System.exit(0);
            }
        });

        nav.add(backBtn);
        nav.add(exitBtn);

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

class MediumMenuPanel extends ModulePanel {
    public MediumMenuPanel(Runnable add, Runnable sub, Runnable mul, Runnable div, Runnable goBack) {
        super("Medium Level: Math Operations");

        JPanel grid = new JPanel(new GridLayout(2, 2, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton addBtn = new JButton("Addition");
        JButton subBtn = new JButton("Subtraction");
        JButton mulBtn = new JButton("Multiplication");
        JButton divBtn = new JButton("Division");
        JButton backBtn = new JButton("Back to Levels");

        addBtn.addActionListener(e -> add.run());
        subBtn.addActionListener(e -> sub.run());
        mulBtn.addActionListener(e -> mul.run());
        divBtn.addActionListener(e -> div.run());
        backBtn.addActionListener(e -> goBack.run());

        grid.add(addBtn); grid.add(subBtn);
        grid.add(mulBtn); grid.add(divBtn);

        add(grid, BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);
    }
}

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

class AdditionModule extends ModulePanel {
    private JTextField numField1, numField2;
    private JLabel resultLabel;
    private Runnable goBack;
    private Runnable goNext;

    public AdditionModule(Runnable goBack, Runnable goNext) {
        super("Addition Fun!");
        this.goBack = goBack;
        this.goNext = goNext;

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        numField1 = new JTextField();
        numField2 = new JTextField();
        numField1.setFont(new Font("Arial", Font.PLAIN, 18));
        numField2.setFont(new Font("Arial", Font.PLAIN, 18));

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton calcBtn = new JButton("Calculate Addition");
        calcBtn.setFont(new Font("Arial", Font.BOLD, 16));
        calcBtn.setBackground(Color.CYAN);

        inputPanel.add(new JLabel("Enter First Number:"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Enter Second Number:"));
        inputPanel.add(numField2);
        inputPanel.add(new JLabel("Click to Solve:"));
        inputPanel.add(calcBtn);

        calcBtn.addActionListener(e -> {
            try {
                double n1 = Double.parseDouble(numField1.getText());
                double n2 = Double.parseDouble(numField2.getText());
                double sum = n1 + n2;

                resultLabel.setText("Result: " + n1 + " + " + n2 + " = " + sum);
                resultLabel.setForeground(new Color(0, 128, 0));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers to calculate!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel nav = new JPanel(new GridLayout(1, 3, 20, 0));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton backBtn = new JButton("Back");
        JButton nextBtn = new JButton("Next Level");
        JButton exitBtn = new JButton("Exit");

        Font btnFont = new Font("Arial", Font.BOLD, 16);
        backBtn.setFont(btnFont);
        nextBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);

        backBtn.addActionListener(e -> goBack.run());
        nextBtn.addActionListener(e -> goNext.run());
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
                System.exit(0);
            }
        });

        nav.add(backBtn);
        nav.add(nextBtn);
        nav.add(exitBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);
    }
}

class SubtractionModule extends ModulePanel {
    private JTextField numField1, numField2;
    private JLabel resultLabel;
    private Runnable goBack;
    private Runnable goNext;

    public SubtractionModule(Runnable goBack, Runnable goNext) {
        super("Subtraction Fun!");
        this.goBack = goBack;
        this.goNext = goNext;

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        numField1 = new JTextField();
        numField2 = new JTextField();
        numField1.setFont(new Font("Arial", Font.PLAIN, 18));
        numField2.setFont(new Font("Arial", Font.PLAIN, 18));

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton calcBtn = new JButton("Calculate Subtraction");
        calcBtn.setFont(new Font("Arial", Font.BOLD, 16));
        calcBtn.setBackground(new Color(255, 182, 193));

        inputPanel.add(new JLabel("Number to subtract from:"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Amount to take away:"));
        inputPanel.add(numField2);
        inputPanel.add(new JLabel("Click to Solve:"));
        inputPanel.add(calcBtn);

        calcBtn.addActionListener(e -> {
            try {
                double n1 = Double.parseDouble(numField1.getText());
                double n2 = Double.parseDouble(numField2.getText());
                double difference = n1 - n2;

                resultLabel.setText("Result: " + n1 + " - " + n2 + " = " + difference);
                resultLabel.setForeground(new Color(220, 20, 60));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel nav = new JPanel(new GridLayout(1, 3, 20, 0));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton backBtn = new JButton("Back");
        JButton nextBtn = new JButton("Next Level");
        JButton exitBtn = new JButton("Exit");

        Font btnFont = new Font("Arial", Font.BOLD, 16);
        backBtn.setFont(btnFont);
        nextBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);

        backBtn.addActionListener(e -> goBack.run());
        nextBtn.addActionListener(e -> goNext.run());
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
                System.exit(0);
            }
        });

        nav.add(backBtn);
        nav.add(nextBtn);
        nav.add(exitBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);
    }
}

class DivisionModule extends ModulePanel {
    private JTextField numField1, numField2;
    private JLabel resultLabel;
    private Runnable goBack;
    private Runnable goNext;

    public DivisionModule(Runnable goBack, Runnable goNext) {
        super("Division Fun!");
        this.goBack = goBack;
        this.goNext = goNext;

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        numField1 = new JTextField();
        numField2 = new JTextField();
        numField1.setFont(new Font("Arial", Font.PLAIN, 18));
        numField2.setFont(new Font("Arial", Font.PLAIN, 18));

        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton calcBtn = new JButton("Calculate Division");
        calcBtn.setFont(new Font("Arial", Font.BOLD, 16));
        calcBtn.setBackground(new Color(144, 238, 144));

        inputPanel.add(new JLabel("Number to be divided:"));
        inputPanel.add(numField1);
        inputPanel.add(new JLabel("Divide by (divisor):"));
        inputPanel.add(numField2);
        inputPanel.add(new JLabel("Click to Solve:"));
        inputPanel.add(calcBtn);

        calcBtn.addActionListener(e -> {
            try {
                double n1 = Double.parseDouble(numField1.getText());
                double n2 = Double.parseDouble(numField2.getText());

                if (n2 == 0) {
                    resultLabel.setText("Result: Cannot divide by zero!");
                    resultLabel.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(this, "Oops! You cannot divide a number by zero.", "Math Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    double quotient = n1 / n2;
                    resultLabel.setText("Result: " + n1 + " ÷ " + n2 + " = " + String.format("%.2f", quotient));
                    resultLabel.setForeground(new Color(0, 0, 139));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel nav = new JPanel(new GridLayout(1, 3, 20, 0));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton backBtn = new JButton("Back");
        JButton nextBtn = new JButton("Next Level");
        JButton exitBtn = new JButton("Exit");

        Font btnFont = new Font("Arial", Font.BOLD, 16);
        backBtn.setFont(btnFont);
        nextBtn.setFont(btnFont);
        exitBtn.setFont(btnFont);

        backBtn.addActionListener(e -> goBack.run());
        nextBtn.addActionListener(e -> goNext.run());
        exitBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Thank you for using Happy Learn!");
                System.exit(0);
            }
        });

        nav.add(backBtn);
        nav.add(nextBtn);
        nav.add(exitBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);
    }
}

class ArithmeticModule extends ModulePanel {
    // Question data: {Question, Answer}
    private final String[][] questions = {
        {"15 + 7 = ?", "22"},
        {"12 * 3 = ?", "36"},
        {"100 - 45 = ?", "55"},
        {"48 / 6 = ?", "8"},
        {"9 + 14 = ?", "23"}
    };

    private int index = 0;
    private int score = 0;
    private JLabel questionLabel, scoreLabel;
    private JTextField answerField;
    private Runnable goBack;

    public ArithmeticModule(Runnable goBack) {
        super("Math Challenge");
        this.goBack = goBack;

        // Layout setup (Similar to your spelling module for consistency)
        JPanel center = new JPanel(new GridLayout(2, 1));
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Serif", Font.BOLD, 32));
        
        center.add(scoreLabel);
        center.add(questionLabel);

        answerField = new JTextField();
        answerField.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> checkMathAnswer());

        add(center, BorderLayout.CENTER);
        JPanel south = new JPanel(new BorderLayout());
        south.add(answerField, BorderLayout.CENTER);
        south.add(submitBtn, BorderLayout.EAST);
        add(south, BorderLayout.SOUTH);

        loadMathQuestion();
    }

    private void loadMathQuestion() {
        if (index < questions.length) {
            questionLabel.setText(questions[index][0]);
            answerField.setText("");
            scoreLabel.setText("Score: " + score + " / " + questions.length);
        } else {
            showFinalScore();
        }
    }

    private void checkMathAnswer() {
        String input = answerField.getText().trim();
        if (input.equals(questions[index][1])) {
            score++;
            JOptionPane.showMessageDialog(this, "Correct! Well done.");
        } else {
            JOptionPane.showMessageDialog(this, "Not quite! The answer was " + questions[index][1]);
        }
        index++;
        loadMathQuestion();
    }

    private void showFinalScore() {
        JOptionPane.showMessageDialog(this, "Math Quiz Over! Total: " + score + "/" + questions.length);
        goBack.run();
    }
}

public class HappyLearnForKids extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public HappyLearnForKids() {
        setTitle("Happy Learn For Kids");
        setSize(500, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Modules");
        JMenuItem welcomeItem = new JMenuItem("Welcome");
        JMenuItem menuItem = new JMenuItem("Menu");
        JMenuItem alphaItem = new JMenuItem("Alphabets");
        JMenuItem numbersItem = new JMenuItem("Numbers 1-10");
        JMenuItem mathItem = new JMenuItem("Multiplication");
        JMenuItem advancedItem = new JMenuItem("Advanced (Maths Quiz)");

        menu.add(welcomeItem);
        menu.add(menuItem);
        menu.add(alphaItem);
        menu.add(numbersItem);
        menu.add(mathItem);
        menu.add(advancedItem);

        menuBar.add(menu);
        setJMenuBar(menuBar);

        Runnable showWelcome = () -> cardLayout.show(mainContainer, "Welcome");
        Runnable showDetails = () -> cardLayout.show(mainContainer, "Details");
        Runnable showBeginnerMenu = () -> cardLayout.show(mainContainer, "BeginnerMenu");
        Runnable showColors = () -> cardLayout.show(mainContainer, "Colors");
        Runnable showMediumMenu = () -> cardLayout.show(mainContainer, "MediumMenu");
        Runnable showMenu = () -> cardLayout.show(mainContainer, "Menu");
        Runnable showAlpha = () -> cardLayout.show(mainContainer, "Alpha");
        Runnable showNumbers = () -> cardLayout.show(mainContainer, "Numbers");
        Runnable showMath = () -> cardLayout.show(mainContainer, "Multiplication");
        Runnable showAdd = () -> cardLayout.show(mainContainer, "Addition");
        Runnable showSub = () -> cardLayout.show(mainContainer, "Subtraction");
        Runnable showDiv = () -> cardLayout.show(mainContainer, "Division");
        Runnable showMathChallenge = () -> cardLayout.show(mainContainer, "MathChallenge");

        welcomeItem.addActionListener(e -> showWelcome.run());
        menuItem.addActionListener(e -> showMenu.run());
        alphaItem.addActionListener(e -> showAlpha.run());
        numbersItem.addActionListener(e -> showNumbers.run());
        mathItem.addActionListener(e -> showMath.run());
        JMenuItem mathChallengeItem = new JMenuItem("Math Challenge");
        menu.add(mathChallengeItem);
        mathChallengeItem.addActionListener(e -> showMathChallenge.run());

        mainContainer.add(new WelcomePanel(showDetails), "Welcome");
        mainContainer.add(new UserDetailsPanel(showMenu), "Details");
        mainContainer.add(new ModuleMenuPanel(showBeginnerMenu, showMediumMenu, showMathChallenge), "Menu");
        mainContainer.add(new BeginnerMenuPanel(showAlpha, showNumbers, showColors, showMenu), "BeginnerMenu");
        mainContainer.add(new AlphabetModule(showBeginnerMenu, showNumbers), "Alpha");
        mainContainer.add(new NumbersModule(showBeginnerMenu, showColors), "Numbers");
        mainContainer.add(new ColorModule(showBeginnerMenu, showMath), "Colors");
        mainContainer.add(new MediumMenuPanel(showAdd, showSub, showMath, showDiv, showMenu), "MediumMenu");
        mainContainer.add(new MathModule(showMediumMenu, showMediumMenu), "Multiplication");
        mainContainer.add(new AdditionModule(showMediumMenu, showMediumMenu), "Addition");
        mainContainer.add(new SubtractionModule(showMediumMenu, showMediumMenu), "Subtraction");
        mainContainer.add(new DivisionModule(showMediumMenu, showMediumMenu), "Division");
        mainContainer.add(new ArithmeticModule(showMenu), "MathChallenge");
        

        add(mainContainer);

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
