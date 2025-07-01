import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TebakAngkaGUI {

    // Variabel game
    private static int angkaRahasia;
    private static int percobaan = 0;
    private static final int batasPercobaan = 5;
    private static int batasAtas = 100;
    static int batasInput = 0;
    static int notint = 0;
    private static FileWriter logFile;

    public static void main(String[] args) {
        Random random = new Random();

        JFrame frame = new JFrame("Game Tebak Angka");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        JLabel labelJudul = new JLabel("Tebak Angka", SwingConstants.CENTER);

        String[] levelOptions = {"Easy (1-50)", "Medium (1-100)", "Hard (1-200)"};
        JComboBox<String> comboLevel = new JComboBox<>(levelOptions);
        String selected = (String) comboLevel.getSelectedItem();
        if (levelOptions.equals("Easy")) batasAtas = 50;
        else if (selected.contains("Hard")) batasAtas = 200;
        else batasAtas = 100;
            
        JTextField inputTebakan = new JTextField();
        JButton tombolTebak = new JButton("Tebak");
        JLabel labelHasil = new JLabel("", SwingConstants.CENTER);
        JButton tombolReset = new JButton("Main Lagi");

        frame.add(labelJudul);
        frame.add(comboLevel);
        frame.add(inputTebakan);
        frame.add(tombolTebak);
        frame.add(labelHasil);
        frame.add(tombolReset);

        try {
            logFile = new FileWriter("game_log.txt", true);
            logFile.write("\n=== Permainan Baru ===\n");
            logFile.write("Waktu: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        } catch (IOException ex) {
            System.out.println("Gagal membuat log file");
            }

        // Atur angka rahasia default
        angkaRahasia = random.nextInt(100) + 1;


        

        tombolTebak.addActionListener(e -> {
            String input = inputTebakan.getText();
            try {
                percobaan++;
                int tebakan = Integer.parseInt(input);

                try {
                    logFile.write("Tebakan ke-" + percobaan + ": " + input + "\n");
                } catch (IOException ex) {
                        System.out.println("Gagal menulis ke log.");
                }

                if (tebakan < 1 || tebakan > batasAtas) {
                    batasInput++;
                    labelHasil.setText("Angka harus 1 - " + batasAtas);
                }else if (tebakan == angkaRahasia) {
                    logFile.write("Berhasil dalam " + percobaan + " percobaan\n");
                    labelHasil.setText("🎉 Selamat! Benar dalam " + percobaan + " percobaan.");
                    tombolTebak.setEnabled(false);
                } else if (tebakan < angkaRahasia) {
                    labelHasil.setText("Terlalu kecil!");
                } else {
                    labelHasil.setText("Terlalu besar!");
                }

                if (percobaan >= batasPercobaan && tebakan != angkaRahasia) {
                    logFile.write("Gagal. Jawaban: " + angkaRahasia + "\n");
                    labelHasil.setText("😞 Gagal! Jawaban: " + angkaRahasia);
                    tombolTebak.setEnabled(false);
                }
                if (batasInput >= 2) {
                    JOptionPane.showMessageDialog(frame, "goblok!!");
                    logFile.write("si tolol jawab " + tebakan);
                    System.exit(0);
                }

            } catch (NumberFormatException ex) {
                notint++;
                labelHasil.setText("Input tidak valid!");

                if (notint >= 2) {
                    JOptionPane.showMessageDialog(frame, "goblok!!");
                    System.exit(0);
                }
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
        });

        tombolReset.addActionListener(e -> {
            String reselected = (String) comboLevel.getSelectedItem();
            if (reselected.contains("Easy")) batasAtas = 50;
            else if (reselected.contains("Hard")) batasAtas = 200;
            else batasAtas = 100;

            angkaRahasia = random.nextInt(batasAtas) + 1;
            percobaan = 0;
            labelHasil.setText("");
            inputTebakan.setText("");
            tombolTebak.setEnabled(true);
        });

        frame.setVisible(true);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
        try {
         if (logFile != null) {
                logFile.close();
            }
        } catch (IOException e) {
            System.out.println("Gagal menutup log.");
            }
        }));
    }
}
