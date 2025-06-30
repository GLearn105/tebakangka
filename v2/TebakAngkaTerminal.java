import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;

public class TebakAngkaTerminal {
    static Scanner input = new Scanner(System.in);
    static Random random = new Random();
    static FileWriter logFile;
    static int batasAtas = 100;
    static int level;
    static int angkaRahasia;
    static int tebakan;
    static int percobaan = 0;
    static int batasPercobaan = 5;
    static int salahInput = 0;
    static int batasInput = 2;
    static boolean lanjutSetelahSalah = false;
    static String jawaban;


    public static void main(String[] args) throws IOException{
        logFile = new FileWriter("game_Log", true);

        System.out.println("====================");
        System.out.println("  GAME TEBAK ANGKA  ");
        System.out.println("====================");
        System.out.println("1. Easy (1-50)");
        System.out.println("2. Medium (1-100)");
        System.out.println("3. Hard (1-200)");
        System.out.println("Pilihan Level : ");

        while (true) {
        
           if (!input.hasNextInt()) {
            salahInput++;
            input.next();

                if (salahInput >= batasInput) {
            System.out.println("cah tolol!!!");
            System.exit(0);
                } else {
            System.out.println("⚠️  Input tidak valid. Coba lagi:");
            continue;
                }
            }
            salahInput = 0;
        

            level = input.nextInt();

            if (level == 1) {
                batasAtas = 50;
                break;
            } else if (level == 2) {
                batasAtas = 100;
                break;
            } else if (level == 3) {
                batasAtas = 200;
                break;
            } else {
                System.out.println("Pilihan level tidak tersedia. Silakan pilih 1, 2, atau 3.");
            }

        }

        angkaRahasia = random.nextInt(batasAtas) + 1;

        logFile.write("\n=== Permainan Baru ===\n");
        logFile.write("Waktu: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
        logFile.write("Level: " + level + ", Rentang: 1-" + batasAtas + "\n");
        System.out.println("Tebak angka antara 1 sampai " + batasAtas);


        if (lanjutSetelahSalah) {
            logFile.write("yang bener goblok!");
            System.out.println("yang bener goblok!");
            logFile.close();
            System.exit(0);
        }


        while (percobaan < batasPercobaan) {
            
            System.out.println("Jawaban Kamu: ");
            tebakan = input.nextInt();
            logFile.write("Percobaan " + percobaan + ": " + tebakan + "\n");


            if (tebakan < 1 || tebakan > 100) {
                salahInput++;

                if (lanjutSetelahSalah) {
                    logFile.write("yang bener goblok!");
                    System.out.println("yang bener goblok!");
                    logFile.close();
                    System.exit(0);
                }


                System.out.println("Angka harus Antara 1 sampai " + batasAtas);

                if (salahInput >= 2) {
                    System.out.println("kamu sudah salah input 2x. mau lanjut? (y/n)");
                    input.nextLine();
                    jawaban = input.nextLine();

                    if (!jawaban.equalsIgnoreCase("y")) {
                        logFile.close();
                        return;
                    }else{
                        lanjutSetelahSalah = true;
                        salahInput = 0;
                    }
                }
                continue;
            }
            percobaan++;
            logFile.write("Percobaan " + percobaan + ": " + tebakan + "\n");


            if (tebakan == angkaRahasia) {
                System.out.println("Selamat Kamu benar dalam " + percobaan + " percobaan");
                logFile.write("Berhasil dalam " + percobaan + " percobaan\n");
                break;    
            } else if(tebakan < angkaRahasia) {
                System.out.println("Terlalu Kecil!");
            }else{
                System.out.println("Terlalu besar!");
            }

            if(percobaan == batasPercobaan){
                System.out.println("kamu gagal dalam " + percobaan + " percobaan");
                System.out.println("Jawabannya adalah " + angkaRahasia);
                logFile.write("BEGO!!");
            }
        }
        logFile.close();
        input.close();
    }

}
