import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Quiz implements ActionListener {
    // Konstanty pro nastavení hry
    private static final int NUMBER_OF_OPTIONS = 4; // Počet možných odpovědí
    private static final int FLAG_WIDTH = 650;    // Šířka obrázku vlajky
    private static final int FLAG_HEIGHT = 200;   // Výška obrázku vlajky
    private static final String FLAGS_FOLDER = "C:\\Users\\evast\\IdeaProjects\\Flags\\Flags"; // Cesta ke složce s vlajkami
    private static final String KODY_CSV_PATH = "C:\\Users\\evast\\IdeaProjects\\Flags\\Flags\\Kody zemi\\kody.csv"; // Cesta k souboru s kódy zemí

    // Proměnné pro ukládání dat
    private List<String> flagFileNames; // Názvy souborů s vlajkami
    private Map<String, String> countryCodes; // Mapování kódů zemí na jejich názvy
    private List<String> countryNames;
    private String correctCountryName; // Název správné země pro aktuální vlajku
    private int correct_guesses = 0; // Počet správných odpovědí
    private int total_questions; // Celkový počet otázek
    private int index = 0; // Index aktuální otázky
    private int result;    // Procentuální výsledek
    private int seconds = 30; // Čas na odpověď
    private char[] correctAnswers = new char[NUMBER_OF_OPTIONS];

    // Komponenty grafického rozhraní
    private JFrame frame = new JFrame();
    private JTextField textfield = new JTextField();
    private JLabel flagLabel = new JLabel();
    private JLabel time_label = new JLabel();
    private JLabel seconds_left = new JLabel();
    private JTextField number_right = new JTextField();
    private JTextField percentage = new JTextField();
    private JButton[] buttons = new JButton[NUMBER_OF_OPTIONS];
    private JLabel[] answerLabels = new JLabel[NUMBER_OF_OPTIONS];
    private Timer timer;
    private Random random = new Random();

    public Quiz() {
        // Inicializace GUI
        initializeGUI();
        // Načtení dat
        loadFlagFileNames();
        loadCountryCodes();
        // Nastavení celkového počtu otázek
        total_questions = 10;//flagFileNames.size();
        // Zobrazení první otázky
        nextQuestion();
    }

    private void initializeGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 750); // Zmenšeno, aby se vešly 4 odpovědi pod vlajku
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(null);
        frame.setResizable(false);

        textfield.setBounds(0, 0, 650, 50);
        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 30));
        textfield.setBorder(BorderFactory.createBevelBorder(1));
        textfield.setHorizontalAlignment(JTextField.CENTER);
        textfield.setEditable(false);
        textfield.setText("Which country does this flag belong to?"); // Otázka se nyní nastavuje zde a nemění se

        flagLabel.setBounds(0, 50, FLAG_WIDTH, FLAG_HEIGHT);
        flagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(flagLabel);

        int buttonHeight = 50; // Zmenšeno výška tlačítek
        int buttonWidth = 100;
        int startY = FLAG_HEIGHT + 60; // Posunuto za vlajku + mezera
        int xPosition = 0;
        int labelX = buttonWidth + 10; // Popisky vpravo od tlačítek

        for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 20)); // Zmenšeno font
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setText(String.valueOf((char) ('A' + i))); // A, B, C, D
            buttons[i].setBounds(xPosition, startY + i * buttonHeight, buttonWidth, buttonHeight);
            frame.add(buttons[i]);

            answerLabels[i] = new JLabel();
            answerLabels[i].setBackground(new Color(50, 50, 50));
            answerLabels[i].setForeground(new Color(25, 255, 0));
            answerLabels[i].setFont(new Font("MV Boli", Font.PLAIN, 20)); // Zmenšeno font
            answerLabels[i].setBounds(labelX, startY + i * buttonHeight, 500, buttonHeight); // Popisky vpravo
            frame.add(answerLabels[i]);
        }

        time_label.setBounds(535, 610, 100, 25); // Přesunuto dolů
        time_label.setBackground(new Color(50, 50, 50));
        time_label.setForeground(new Color(255, 0, 0));
        time_label.setFont(new Font("MV Boli", Font.PLAIN, 16));
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("Time Left:");

        seconds_left.setBounds(535, 635, 100, 50); // Pod time_label
        seconds_left.setBackground(new Color(25, 25, 25));
        seconds_left.setForeground(new Color(255, 0, 0));
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 30)); // Zmenšeno font
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds));

        number_right.setBounds(225, 610, 200, 30); // Přesunuto dolů a zmenšeno
        number_right.setBackground(new Color(25, 25, 25));
        number_right.setForeground(new Color(25, 255, 0));
        number_right.setFont(new Font("Ink Free", Font.BOLD, 20)); // Zmenšeno font
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setHorizontalAlignment(JTextField.CENTER);
        number_right.setEditable(false);

        percentage.setBounds(225, 640, 200, 30); // Pod number_right
        percentage.setBackground(new Color(25, 25, 25));
        percentage.setForeground(new Color(25, 255, 0));
        percentage.setFont(new Font("Ink Free", Font.BOLD, 20)); // Zmenšeno font
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);

        frame.add(time_label);
        frame.add(seconds_left);
        frame.add(number_right);
        frame.add(percentage);
        frame.add(textfield); //textfield s otázkou
        frame.setVisible(true);

        timer = new Timer(1000, new ActionListener() { // Timer po sekundách
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds--;
                seconds_left.setText(String.valueOf(seconds));
                if (seconds <= 0) {
                    displayAnswer();
                }
            }
        });
    }

    private void loadFlagFileNames() {
        File folder = new File(FLAGS_FOLDER);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (listOfFiles != null) {
            flagFileNames = new ArrayList<>(); // Používáme ArrayList
            for (File file : listOfFiles) {
                flagFileNames.add(file.getName());
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Chyba: Nenalezeny žádné soubory .png ve složce s vlajkami.", "Chyba", JOptionPane.ERROR_MESSAGE);
            flagFileNames = new ArrayList<>();
        }
    }

    private void loadCountryCodes() {
        countryCodes = new HashMap<>();
        countryNames = new ArrayList<>();
        File file = new File(KODY_CSV_PATH);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    String code = parts[1].trim().toLowerCase(); // Kódy zemí jsou lowercase
                    String name = parts[0].trim();
                    countryCodes.put(code, name);
                    countryNames.add(name); // Přidáme i do seznamu jmen
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Chyba: Nepodařilo se načíst soubor s kódy zemí.", "Chyba", JOptionPane.ERROR_MESSAGE);
            countryCodes = new HashMap<>();
            countryNames = new ArrayList<>();
        }
    }

    private String getCountryNameForFlag(String flagFileName) {
        // Odstraníme příponu ".png" a získáme kód země
        String countryCode = flagFileName.replace(".png", "").toLowerCase();
        return countryCodes.get(countryCode);
    }

    public void nextQuestion() {
        seconds = 30;
        seconds_left.setText(String.valueOf(seconds));
        timer.start();

        if (index >= total_questions) {
            results();
            return;
        }

        if (flagFileNames.isEmpty()) {
            flagLabel.setText("Chyba: Žádné vlajky k zobrazení.");
            flagLabel.setIcon(null);
            return;
        }

        // Náhodný výběr vlajky
        int randomIndex = random.nextInt(flagFileNames.size());
        String selectedFlagFileName = flagFileNames.get(randomIndex);
        String flagFilePath = FLAGS_FOLDER + "\\" + selectedFlagFileName;
        correctCountryName = getCountryNameForFlag(selectedFlagFileName); // Získání názvu správné země

        if (correctCountryName == null) {
            correctCountryName = "Unknown";
        }
        ImageIcon flagIcon = new ImageIcon(flagFilePath);
        Image scaledImage = flagIcon.getImage().getScaledInstance(FLAG_WIDTH, FLAG_HEIGHT, Image.SCALE_SMOOTH);
        ImageIcon scaledFlagIcon = new ImageIcon(scaledImage);
        flagLabel.setIcon(scaledFlagIcon);
        flagLabel.setText("");

        // Generování náhodných odpovědí
        List<String> answers = generateRandomAnswers(correctCountryName);

        // Nastavení odpovědí do popisků
        for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
            answerLabels[i].setText(answers.get(i));
        }
        //povolíme buttony
        for (JButton button : buttons) {
            button.setEnabled(true);
        }
    }

    private List<String> generateRandomAnswers(String correctCountryName) {
        List<String> answers = new ArrayList<>();
        answers.add(correctCountryName); // Přidáme správnou odpověď

        // Náhodně vybereme 3 různé nesprávné odpovědi
        while (answers.size() < NUMBER_OF_OPTIONS) {
            String randomCountry = countryNames.get(random.nextInt(countryNames.size()));
            if (!answers.contains(randomCountry)) { // Kontrola duplicity
                answers.add(randomCountry);
            }
        }
        Collections.shuffle(answers); // Zamícháme odpovědi
        return answers;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Zablokujeme tlačítka
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        timer.stop();

        String selectedAnswer = ""; // Inicializace
        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                selectedAnswer = answerLabels[i].getText();
                break;
            }
        }

        if (selectedAnswer.equals(correctCountryName)) {
            correct_guesses++;
        }
        displayAnswer();
    }

    public void displayAnswer() {
        timer.stop();
        for (JButton button : buttons) {
            button.setEnabled(false);
        }

        for (int i = 0; i < NUMBER_OF_OPTIONS; i++) {
            if (answerLabels[i].getText().equals(correctCountryName)) {
                answerLabels[i].setForeground(new Color(0, 255, 0)); // Správná odpověď zeleně
            } else {
                answerLabels[i].setForeground(new Color(255, 0, 0)); // Ostatní červeně
            }
        }

        Timer pause = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetAnswerLabels();
                index++;
                nextQuestion();
            }
        });
        pause.setRepeats(false);
        pause.start();
    }

    private void resetAnswerLabels() {
        for (JLabel label : answerLabels) {
            label.setForeground(new Color(25, 255, 0)); // Reset na původní barvu
        }
    }

    public void results() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        timer.stop();
        result = (int) ((correct_guesses / (double) total_questions) * 100);
        textfield.setText("RESULTS!");
        flagLabel.setIcon(null); // Odstraníme vlajku
        for (JLabel label : answerLabels) { // Odstraníme texty odpovědí
            label.setText("");
        }
        number_right.setText("(" + correct_guesses + "/" + total_questions + ")");
        percentage.setText(result + "%");
        frame.add(number_right);
        frame.add(percentage);
    }

    public static void main(String[] args) {
        new Quiz();
    }
}
