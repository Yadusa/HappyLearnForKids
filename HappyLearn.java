//Yadusa Balakrishnan 1221205086
//Keishav Rao Bala Subramaniam  1221207387

import javax.swing.*;
import java.awt.*;
import java.io.*;

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

//MODULE MENU PANEL 
class ModuleMenuPanel extends ModulePanel {
    public ModuleMenuPanel(Runnable openAlpha, Runnable openNumbers, Runnable openMaths) {
        super("Choose a Module");

        JPanel grid = new JPanel(new GridLayout(2, 2, 15, 15));
        grid.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton alphaBtn = new JButton("Alphabets");
        JButton numBtn = new JButton("Numbers 1-10");
        JButton mathBtn = new JButton("Multiplication");
        JButton exitBtn = new JButton("Exit");

        Font f = new Font("Arial", Font.BOLD, 18);
        alphaBtn.setFont(f);
        numBtn.setFont(f);
        mathBtn.setFont(f);
        exitBtn.setFont(f);

        alphaBtn.addActionListener(e -> openAlpha.run());
        numBtn.addActionListener(e -> openNumbers.run());
        mathBtn.addActionListener(e -> openMaths.run());
        exitBtn.addActionListener(e -> System.exit(0));

        grid.add(alphaBtn);
        grid.add(numBtn);
        grid.add(mathBtn);
        grid.add(exitBtn);

        add(grid, BorderLayout.CENTER);
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

        nav.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        nav.add(backBtn, BorderLayout.WEST);
        nav.add(nextBtn, BorderLayout.EAST);

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
        JLabel content = buildPopupLabel(letter);
        JOptionPane.showMessageDialog(this, content, "Alphabet", JOptionPane.PLAIN_MESSAGE);
    }

    private JLabel buildPopupLabel(String letter) {

        String path = "";
        String text = "";

        if (letter.equals("A")) { path="assets/apple.jpg"; text="A is for Apple!"; }
        else if (letter.equals("B")) { path="assets/ball.jpg"; text="B is for Ball!"; }
        else if (letter.equals("C")) { path="assets/cat.jpg"; text="C is for Cat!"; }
        else if (letter.equals("D")) { path="assets/dog.jpg"; text="D is for Dog!"; }
        else if (letter.equals("E")) { path="assets/elephant.jpg"; text="E is for Elephant!"; }
        else if (letter.equals("F")) { path="assets/fish.jpg"; text="F is for Fish!"; }
        else if (letter.equals("G")) { path="assets/goat.jpg"; text="G is for Goat!"; }
        else if (letter.equals("H")) { path="assets/hat.jpg"; text="H is for Hat!"; }
        else if (letter.equals("I")) { path="assets/icecream.jpg"; text="I is for Ice Cream!"; }
        else if (letter.equals("J")) { path="assets/jug.jpg"; text="J is for Jug!"; }
        else if (letter.equals("K")) { path="assets/kite.jpg"; text="K is for Kite!"; }
        else if (letter.equals("L")) { path="assets/lion.jpg"; text="L is for Lion!"; }
        else if (letter.equals("M")) { path="assets/monkey.jpg"; text="M is for Monkey!"; }
        else if (letter.equals("N")) { path="assets/nest.jpg"; text="N is for Nest!"; }
        else if (letter.equals("O")) { path="assets/orange.jpg"; text="O is for Orange!"; }
        else if (letter.equals("P")) { path="assets/parrot.jpg"; text="P is for Parrot!"; }
        else if (letter.equals("Q")) { path="assets/queen.jpg"; text="Q is for Queen!"; }
        else if (letter.equals("R")) { path="assets/rabbit.jpg"; text="R is for Rabbit!"; }
        else if (letter.equals("S")) { path="assets/sun.jpg"; text="S is for Sun!"; }
        else if (letter.equals("T")) { path="assets/tiger.jpg"; text="T is for Tiger!"; }
        else if (letter.equals("U")) { path="assets/umbrella.jpg"; text="U is for Umbrella!"; }
        else if (letter.equals("V")) { path="assets/van.jpg"; text="V is for Van!"; }
        else if (letter.equals("W")) { path="assets/watch.jpg"; text="W is for Watch!"; }
        else if (letter.equals("X")) { path="assets/xylophone.jpg"; text="X is for Xylophone!"; }
        else if (letter.equals("Y")) { path="assets/yoyo.jpg"; text="Y is for Yo-Yo!"; }
        else if (letter.equals("Z")) { path="assets/zebra.jpg"; text="Z is for Zebra!"; }
        else { text = "Select a letter"; }

        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 18));
        lbl.setHorizontalTextPosition(SwingConstants.CENTER);
        lbl.setVerticalTextPosition(SwingConstants.BOTTOM);

        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
        lbl.setIcon(new ImageIcon(img));

        return lbl;
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
            btn.addActionListener(e -> showPopup(num));
            grid.add(btn);
        }

        JPanel nav = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("Back");
        JButton nextBtn = new JButton("Next");
        backBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nextBtn.setFont(new Font("Arial", Font.BOLD, 16));
        nav.setBorder(BorderFactory.createEmptyBorder(10, 30, 20, 30));
        nav.add(backBtn, BorderLayout.WEST);
        nav.add(nextBtn, BorderLayout.EAST);

        backBtn.addActionListener(e -> goAlpha.run());
        nextBtn.addActionListener(e -> goMath.run());

        add(grid, BorderLayout.CENTER);
        add(nav, BorderLayout.SOUTH);
    }

    private void showPopup(String num) {

    String path = "assets/" + num + ".jpg";
    File f = new File(path);
    if (!f.exists()) {
    JOptionPane.showMessageDialog(this, "Image not found: " + path);
    return;
    }
    
    String text = "Number " + num + "!";
    JLabel lbl = new JLabel(text, SwingConstants.CENTER);
    lbl.setFont(new Font("Arial", Font.BOLD, 18));
    lbl.setHorizontalTextPosition(SwingConstants.CENTER);
    lbl.setVerticalTextPosition(SwingConstants.BOTTOM);

    ImageIcon icon = new ImageIcon(path);
    Image img = icon.getImage().getScaledInstance(220, 220, Image.SCALE_SMOOTH);
    lbl.setIcon(new ImageIcon(img));

    JOptionPane.showMessageDialog(this, lbl, "Numbers", JOptionPane.PLAIN_MESSAGE);
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
public class HappyLearn extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainContainer = new JPanel(cardLayout);

    public HappyLearn() {
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
        mainContainer.add(new WelcomePanel(showMenu), "Welcome");
        mainContainer.add(new ModuleMenuPanel(showAlpha, showNumbers, showMath), "Menu");
        mainContainer.add(new AlphabetModule(showMenu, showNumbers), "Alpha");
        mainContainer.add(new NumbersModule(showAlpha, showMath), "Numbers");
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
        SwingUtilities.invokeLater(() -> new HappyLearn().setVisible(true));
    }
}
