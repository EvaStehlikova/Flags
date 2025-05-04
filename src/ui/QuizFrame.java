package ui;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.BorderFactory;

public class QuizFrame extends JFrame {

    private JTextField titleField; // Přesunuli jsme titleField sem

    public QuizFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 750);
        getContentPane().setBackground(new Color(50, 50, 50));
        setLayout(null);
        setResizable(false);

        titleField = new JTextField(); // Inicializace titleField
        titleField.setBounds(0, 0, 650, 50);
        titleField.setBackground(new Color(25, 25, 25));
        titleField.setForeground(new Color(25, 255, 0));
        titleField.setFont(new Font("Ink Free", Font.BOLD, 30));
        titleField.setBorder(BorderFactory.createBevelBorder(1));
        titleField.setHorizontalAlignment(JTextField.CENTER);
        titleField.setEditable(false);
        add(titleField); // Přidáme titleField do frame
    }

    public JTextField getTitleField() { // Getter pro získání instance titleField
        return titleField;
    }
}




