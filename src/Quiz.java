import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import javax.swing.JOptionPane;
import java.util.Random;

public class Quiz implements ActionListener {
    String[] questions = {
            "Which state does this flag belong to??",
            "Which year was Java created?",
            "What was Java originally called?",
            "Who is credited with creating Java?"
    };

    String[][] options = {
            {"Sun Microsystems", "Starbucks", "Microsoft", "Alphabet"},
            {"1989", "1996", "1972", "1492"},
            {"Apple", "Latte", "Oak", "Koffing"},
            {"Steve Jobs", "Bill Gates", "James Gosling", "Mark Zuckerburg"}
    };

    private int numberOfOptions = 4; // počet možných odpovědí na otázku

    char[] correctAnswers = {
            'A',
            'B',
            'C',
            'C'
    };

    char answer;
    int index;
    int correct_guesses = 0;
    int total_questions = questions.length;
    int result;
    int seconds = 30;

    JFrame frame = new JFrame();
    JTextField textfield = new JTextField();
    JTextArea textarea = new JTextArea();
    JLabel flagLabel = new JLabel();
    private String[] flagFileNames; // Proměnná pro uložení názvů souborů s vlajkami
    JButton buttonA = new JButton();
    JButton buttonB = new JButton();
    JButton buttonC = new JButton();
    JButton buttonD = new JButton();

    JButton[] buttons = new JButton[numberOfOptions];
    JLabel[] answerLabels = new JLabel[numberOfOptions];
    JLabel answer_labelA = new JLabel();
    JLabel answer_labelB = new JLabel();
    JLabel answer_labelC = new JLabel();
    JLabel answer_labelD = new JLabel();
    JLabel time_label = new JLabel();
    JLabel seconds_left = new JLabel();
    JTextField number_right = new JTextField();
    JTextField percentage = new JTextField();

    private Random random = new Random();
    Timer timer = new Timer(3000, new ActionListener() {


        @Override

        public void actionPerformed(ActionEvent e) {

            seconds--;
            seconds_left.setText(String.valueOf(seconds));
            if (seconds <= 0) {
                displayAnswer();
            }
        }
    });

    public Quiz() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 1050);
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
        textarea.setBounds(0, 350, 650, 50);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setBackground(new Color(25, 25, 25));
        textarea.setForeground(new Color(25, 255, 0));
        textarea.setFont(new Font("MV Boli", Font.BOLD, 25));
        textarea.setBorder(BorderFactory.createBevelBorder(1));
        textarea.setEditable(false);
        flagLabel.setBounds(0, 50, 650, 200); // Nastavíš pozici a rozměry (výšku si uprav podle potřeby)
        flagLabel.setHorizontalAlignment(SwingConstants.CENTER); // Zarovnání obrázku na střed
        frame.add(flagLabel);
        flagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(flagLabel);
        loadFlagFileNames(); // Načti názvy souborů

        int buttonHeight = 100;
        int buttonWidth = 100;
        int startY = 100;
        int xPosition = 0;

        for (int i = 0; i < numberOfOptions; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 35));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setText(String.valueOf((char) ('A' + i)));
            buttons[i].setBounds(xPosition, startY + i * buttonHeight, buttonWidth, buttonHeight);
            frame.add(buttons[i]);

            answerLabels[i] = new JLabel();
            answerLabels[i].setBackground(new Color(50, 50, 50));
            answerLabels[i].setForeground(new Color(25, 255, 0));
            answerLabels[i].setFont(new Font("MV Boli", Font.PLAIN, 35));
            answerLabels[i].setBounds(125, startY + i * buttonHeight, 500, buttonHeight);
            frame.add(answerLabels[i]);
        }

       /* buttonA.setBounds(0, 100, 100, 100);
        buttonA.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonA.setFocusable(false);
        buttonA.addActionListener(this);
        buttonA.setText("A");
        buttonB.setBounds(0, 200, 100, 100);
        buttonB.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonB.setFocusable(false);
        buttonB.addActionListener(this);
        buttonB.setText("B");
        buttonC.setBounds(0, 300, 100, 100);
        buttonC.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonC.setFocusable(false);
        buttonC.addActionListener(this);
        buttonC.setText("C");
        buttonD.setBounds(0, 400, 100, 100);
        buttonD.setFont(new Font("MV Boli", Font.BOLD, 35));
        buttonD.setFocusable(false);
        buttonD.addActionListener(this);
        buttonD.setText("D");
        answer_labelA.setBounds(125, 100, 500, 100);
        answer_labelA.setBackground(new Color(50, 50, 50));
        answer_labelA.setForeground(new Color(25, 255, 0));
        answer_labelA.setFont(new Font("MV Boli", Font.PLAIN, 35));
        answer_labelB.setBounds(125, 200, 500, 100);
        answer_labelB.setBackground(new Color(50, 50, 50));
        answer_labelB.setForeground(new Color(25, 255, 0));
        answer_labelB.setFont(new Font("MV Boli", Font.PLAIN, 35));
        answer_labelC.setBounds(125, 300, 500, 100);
        answer_labelC.setBackground(new Color(50, 50, 50));
        answer_labelC.setForeground(new Color(25, 255, 0));
        answer_labelC.setFont(new Font("MV Boli", Font.PLAIN, 35));
        answer_labelD.setBounds(125, 400, 500, 100);
        answer_labelD.setBackground(new Color(50, 50, 50));
        answer_labelD.setForeground(new Color(25, 255, 0));
        answer_labelD.setFont(new Font("MV Boli", Font.PLAIN, 35)); */
        seconds_left.setBounds(535, 510, 100, 100);
        seconds_left.setBackground(new Color(25, 25, 25));
        seconds_left.setForeground(new Color(255, 0, 0));
        seconds_left.setFont(new Font("Ink Free", Font.BOLD, 60));
        seconds_left.setBorder(BorderFactory.createBevelBorder(1));
        seconds_left.setOpaque(true);
        seconds_left.setHorizontalAlignment(JTextField.CENTER);
        seconds_left.setText(String.valueOf(seconds));
        time_label.setBounds(535, 475, 100, 25);
        time_label.setBackground(new Color(50, 50, 50));
        time_label.setForeground(new Color(255, 0, 0));
        time_label.setFont(new Font("MV Boli", Font.PLAIN, 16));
        time_label.setHorizontalAlignment(JTextField.CENTER);
        time_label.setText("timer >:D");
        number_right.setBounds(225, 225, 200, 100);
        number_right.setBackground(new Color(25, 25, 25));
        number_right.setForeground(new Color(25, 255, 0));
        number_right.setFont(new Font("Ink Free", Font.BOLD, 50));
        number_right.setBorder(BorderFactory.createBevelBorder(1));
        number_right.setHorizontalAlignment(JTextField.CENTER);
        number_right.setEditable(false);
        percentage.setBounds(225, 325, 200, 100);
        percentage.setBackground(new Color(25, 25, 25));
        percentage.setForeground(new Color(25, 255, 0));
        percentage.setFont(new Font("Ink Free", Font.BOLD, 50));
        percentage.setBorder(BorderFactory.createBevelBorder(1));
        percentage.setHorizontalAlignment(JTextField.CENTER);
        percentage.setEditable(false);
        frame.add(time_label);
        frame.add(seconds_left);
        frame.add(textarea);
        frame.add(textfield);
        frame.setVisible(true);
        nextQuestion();
    }

    private void loadFlagFileNames() {
        File folder = new File("C:\\Users\\evast\\OneDrive\\Documents\\Moje\\Java\\Quiz game\\Quiz game\\Flags");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

        if (listOfFiles != null) {
            flagFileNames = new String[listOfFiles.length];
            for (int i = 0; i < listOfFiles.length; i++) {
                flagFileNames[i] = listOfFiles[i].getName();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Chyba: Nenalezeny žádné soubory .png ve složce s vlajkami.", "Chyba", JOptionPane.ERROR_MESSAGE);
            flagFileNames = new String[0]; // Nastavíme prázdné pole, abychom předešli null pointer exception
        }
    }

    public void nextQuestion() {

        if (index >= total_questions) {
            results();
        } else {
            textfield.setText("Question " + (index + 1));
            textarea.setText(questions[index]);
            answer_labelA.setText(options[index][0]);
            answer_labelB.setText(options[index][1]);
            answer_labelC.setText(options[index][2]);
            answer_labelD.setText(options[index][3]);
            if (flagFileNames != null && flagFileNames.length > 0) {
                int randomIndex = random.nextInt(flagFileNames.length);
                String selectedFlagFileName = flagFileNames[randomIndex];
                String flagFilePath = "C:\\Users\\evast\\OneDrive\\Documents\\Moje\\Java\\Quiz game\\Quiz game\\Flags\\" + selectedFlagFileName;

                ImageIcon flagIcon = new ImageIcon(flagFilePath);

                // Pro lepší škálování vlajky na velikost Labelu (volitelné)
                Image scaledImage = flagIcon.getImage().getScaledInstance(flagLabel.getWidth(), flagLabel.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledFlagIcon = new ImageIcon(scaledImage);

                flagLabel.setIcon(scaledFlagIcon);
                flagLabel.setText(""); // Ujisti se, že text v labelu je prázdný
            } else {
                flagLabel.setText("Chyba: Žádné vlajky k zobrazení.");
                flagLabel.setIcon(null);
            }
            timer.start();
        }
    }

    @Override

    @Override
    public void actionPerformed(ActionEvent e) {
        // Zablokujeme všechna tlačítka, dokud se nezobrazí další otázka
        for (JButton button : buttons) {
            button.setEnabled(false);
        }

        char answer = ' '; // Inicializujeme proměnnou answer

        for (int i = 0; i < buttons.length; i++) {
            if (e.getSource() == buttons[i]) {
                answer = (char) ('A' + i); // Určíme, které písmeno odpovídá stisknutému tlačítku
                if (answer == correctAnswers[index]) {
                    correct_guesses++;
                }
                break; // Jakmile najdeme stisknuté tlačítko, můžeme cyklus ukončit
            }
        }
        displayAnswer();
    }

    public void displayAnswer() {

        timer.stop();
        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);
        if (correctAnswers[index] != 'A')
            answer_labelA.setForeground(new Color(255, 0, 0));
        if (correctAnswers[index] != 'B')
            answer_labelB.setForeground(new Color(255, 0, 0));
        if (correctAnswers[index] != 'C')
            answer_labelC.setForeground(new Color(255, 0, 0));
        if (correctAnswers[index] != 'D')
            answer_labelD.setForeground(new Color(255, 0, 0));
        Timer pause = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                answer_labelA.setForeground(new Color(25, 255, 0));
                answer_labelB.setForeground(new Color(25, 255, 0));
                answer_labelC.setForeground(new Color(25, 255, 0));
                answer_labelD.setForeground(new Color(25, 255, 0));
                answer = ' ';
                seconds = 10;
                seconds_left.setText(String.valueOf(seconds));
                for (JButton button : buttons) {
                    button.setEnabled(true);
                }
                index++;
                nextQuestion();
            }
        });
        pause.setRepeats(false);
        pause.start();
    }

    public void results() {
        for (JButton button : buttons) {
            button.setEnabled(false);
        }
        result = (int) ((correct_guesses / (double) total_questions) * 100);
        textfield.setText("RESULTS!");
        textarea.setText("");
        answer_labelA.setText("");
        answer_labelB.setText("");
        answer_labelC.setText("");
        answer_labelD.setText("");
        number_right.setText("(" + correct_guesses + "/" + total_questions + ")");
        percentage.setText(result + "%");
        frame.add(number_right);
        frame.add(percentage);
    }
}