import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GuiNext {

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
        JPanel panelUtama = new JPanel();
        panelUtama.setLayout(new BoxLayout(panelUtama, BoxLayout.Y_AXIS));
        panelUtama.setBackground(Color.DARK_GRAY);
        panelUtama.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        frame.add(panelUtama);

        JLabel labelJudul = new JLabel("🎮 Game Tebak Angka", SwingConstants.CENTER);
        labelJudul.setFont(new Font("Arial", Font.BOLD, 20));
        labelJudul.setForeground(Color.WHITE);
        labelJudul.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelUtama.add(labelJudul);


        String[] levelOptions = {"Mudah (1-50)", "Sedang (1-100)", "Sulit (1-200)"};
        JComboBox<String> comboLevel = new JComboBox<>(levelOptions);
        levelOptions[0] = "Mudah (1-50)";
        levelOptions[1] = "Sedang (1-100)";
        levelOptions[2] = "Sulit (1-200)";
        comboLevel.setMaximumSize(new Dimension(200, 30));
        comboLevel.setSelectedIndex(1); // Default to "Sedang (1-100)"
        comboLevel.setBackground(Color.LIGHT_GRAY);
        comboLevel.setForeground(Color.BLACK);
        String selected = (String) comboLevel.getSelectedItem();
        if (selected.contains("Mudah")) batasAtas = 50;
        else if (selected.contains("Sulit")) batasAtas = 200;
        else batasAtas = 100;

        JTextField inputTebakan = new JTextField();
        inputTebakan.setMaximumSize(new Dimension(200, 30));
        JButton tombolTebak = new JButton("Tebak");
        tombolTebak.setMaximumSize(new Dimension(200, 30));
        tombolTebak.setAlignmentX(Component.CENTER_ALIGNMENT);
        tombolTebak.setBackground(Color.ORANGE);
        tombolTebak.setForeground(Color.BLACK);
        tombolTebak.setFocusPainted(false);

        JTextField labelHasil = new JTextField("", SwingConstants.CENTER);
        labelHasil.setMaximumSize(new Dimension(200, 30));
        labelHasil.setFont(new Font("Arial", Font.PLAIN, 10));
        labelHasil.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelHasil.setMaximumSize(new Dimension(200, 30));
        labelHasil.setBackground(Color.WHITE);
        labelHasil.setForeground(Color.BLACK);
        JButton tombolReset = new JButton("Main Lagi");
        tombolReset.setMaximumSize(new Dimension(200, 30));
        tombolReset.setAlignmentX(Component.CENTER_ALIGNMENT);
        tombolReset.setBackground(Color.GREEN);
        tombolReset.setForeground(Color.BLACK);
        tombolReset.setFocusPainted(false);


    
        frame.setContentPane(panelUtama);
        panelUtama.add(labelJudul);
        panelUtama.add(comboLevel);
        panelUtama.add(inputTebakan);
        panelUtama.add(tombolTebak);
        panelUtama.add(labelHasil);
        panelUtama.add(tombolReset);

        try {
            logFile = new FileWriter("game_log.txt", true);
            logFile.write("\n=== Permainan Baru ===\n");
            logFile.write("Waktu: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        } catch (IOException ex) {
            System.out.println("Gagal membuat file log");
        }

        // Atur angka rahasia default
        angkaRahasia = random.nextInt(100) + 1;

        tombolTebak.addActionListener(_ -> {
            String input = inputTebakan.getText();
            try {
                percobaan++;
                int tebakan = Integer.parseInt(input);

                try {
                    logFile.write("Tebakan ke-" + percobaan + ": " + input + "\n");
                } catch (IOException ex) {
                    System.out.println("Gagal menulis ke file log.");
                }

                if (tebakan < 1 || tebakan > batasAtas) {
                    batasInput++;
                    labelHasil.setText("Angka harus antara 1 sampai " + batasAtas);
                } else if (tebakan == angkaRahasia) {
                    logFile.write("Berhasil menebak dalam " + percobaan + " percobaan\n");
                    labelHasil.setText("🎉 Selamat! Jawaban Anda benar dalam " + percobaan + " percobaan.");
                    tombolTebak.setEnabled(false);
                } else if (tebakan < angkaRahasia) {
                    labelHasil.setText("Terlalu kecil!");
                } else {
                    labelHasil.setText("Terlalu besar!");
                }

                if (percobaan >= batasPercobaan && tebakan != angkaRahasia) {
                    logFile.write("Gagal menebak. Jawaban yang benar: " + angkaRahasia + "\n");
                    labelHasil.setText("😞 Kesempatan habis! Jawaban yang benar: " + angkaRahasia);
                    tombolTebak.setEnabled(false);
                }
                if (batasInput >= 2) {
                    JOptionPane.showMessageDialog(frame, "Anda sudah dua kali memasukkan angka di luar batas!");
                    logFile.write("Pengguna memasukkan angka di luar batas sebanyak dua kali.\n");
                    System.exit(0);
                }

            } catch (NumberFormatException ex) {
                notint++;
                labelHasil.setText("Input tidak valid! Masukkan angka bulat.");

                if (notint >= 2) {
                    JOptionPane.showMessageDialog(frame, "Anda sudah dua kali memasukkan input tidak valid!");
                    System.exit(0);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        tombolReset.addActionListener(_ -> {
            String reselected = (String) comboLevel.getSelectedItem();
            if (reselected.contains("Mudah")) batasAtas = 50;
            else if (reselected.contains("Sulit")) batasAtas = 200;
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
                System.out.println("Gagal menutup file log.");
            }
        }));

        frame.setContentPane(panelUtama);
        frame.setVisible(true);
    }
}
