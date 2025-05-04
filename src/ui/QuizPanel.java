package ui;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class QuizPanel extends JPanel {

    private JLabel flagLabel;
    private JButton[] buttons;
    private JLabel[] answerLabels;
    private JLabel timeLabel;
    private JLabel secondsLeftLabel;
    private JTextField numberRightField;
    private JTextField percentageField;

    public QuizPanel() {
        setLayout(null);
        setBackground(new Color(50, 50, 50)); // Stejná barva pozadí

        flagLabel = new JLabel();
        flagLabel.setBounds(0, 50, 650, 200);
        flagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(flagLabel);

        int numberOfOptions = 4;
        buttons = new JButton[numberOfOptions];
        answerLabels = new JLabel[numberOfOptions];
        int buttonHeight = 50;
        int buttonWidth = 100;
        int startY = 260; // Posunuto za vlajku
        int xPosition = 0;
        int labelX = buttonWidth + 10;

        for (int i = 0; i < numberOfOptions; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 20));
            buttons[i].setFocusable(false);
            buttons[i].setText(String.valueOf((char) ('A' + i)));
            buttons[i].setBounds(xPosition, startY + i * buttonHeight, buttonWidth, buttonHeight);
            add(buttons[i]);

            answerLabels[i] = new JLabel();
            answerLabels[i].setBackground(new Color(50, 50, 50));
            answerLabels[i].setForeground(new Color(25, 255, 0));
            answerLabels[i].setFont(new Font("MV Boli", Font.PLAIN, 20));
            answerLabels[i].setBounds(labelX, startY + i * buttonHeight, 500, buttonHeight);
            add(answerLabels[i]);
        }

        timeLabel = new JLabel("Time Left:");
        timeLabel.setBounds(535, 510, 100, 25);
        timeLabel.setBackground(new Color(50, 50, 50));
        timeLabel.setForeground(new Color(255, 0, 0));
        timeLabel.setFont(new Font("MV Boli", Font.PLAIN, 16));
        timeLabel.setHorizontalAlignment(JTextField.CENTER);
        add(timeLabel);

        secondsLeftLabel = new JLabel();
        secondsLeftLabel.setBounds(535, 535, 100, 50);
        secondsLeftLabel.setBackground(new Color(25, 25, 25));
        secondsLeftLabel.setForeground(new Color(255, 0, 0));
        secondsLeftLabel.setFont(new Font("Ink Free", Font.BOLD, 30));
        secondsLeftLabel.setBorder(BorderFactory.createBevelBorder(1));
        secondsLeftLabel.setOpaque(true);
        secondsLeftLabel.setHorizontalAlignment(JTextField.CENTER);
        add(secondsLeftLabel);

        numberRightField = new JTextField();
        numberRightField.setBounds(225, 510, 200, 30);
        numberRightField.setBackground(new Color(25, 25, 25));
        numberRightField.setForeground(new Color(25, 255, 0));
        numberRightField.setFont(new Font("Ink Free", Font.BOLD, 20));
        numberRightField.setBorder(BorderFactory.createBevelBorder(1));
        numberRightField.setHorizontalAlignment(JTextField.CENTER);
        numberRightField.setEditable(false);
        add(numberRightField);

        percentageField = new JTextField();
        percentageField.setBounds(225, 540, 200, 30);
        percentageField.setBackground(new Color(25, 25, 25));
        percentageField.setForeground(new Color(25, 255, 0));
        percentageField.setFont(new Font("Ink Free", Font.BOLD, 20));
        percentageField.setBorder(BorderFactory.createBevelBorder(1));
        percentageField.setHorizontalAlignment(JTextField.CENTER);
        percentageField.setEditable(false);
        add(percentageField);
    }

    public JLabel getFlagLabel() {
        return flagLabel;
    }

    public JButton[] getButtons() {
        return buttons;
    }

    public JLabel[] getAnswerLabels() {
        return answerLabels;
    }

    public JLabel getSecondsLeftLabel() {
        return secondsLeftLabel;
    }

    public JTextField getNumberRightField() {
        return numberRightField;
    }

    public JTextField getPercentageField() {
        return percentageField;
    }
}
