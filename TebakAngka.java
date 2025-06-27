import java.util.Random;
import java.util.Scanner;

public class TebakAngka {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();

        int angkaRahasia = random.nextInt(100) + 1; // angka 1 - 100
        int tebakan;
        int percobaan = 0;
        int batasPercobaan = 5;
        int salahInput = 0;
        boolean lanjutSetelahSalah = false;


        System.out.println("=== GAME TEBAK ANGKA ===");
        System.out.println("tebak angka antara 1 sampai 100!");
        


        while (percobaan < batasPercobaan) {
            System.out.println("Jawaban Kamu: ");
            tebakan = input.nextInt();


            if (tebakan < 1 || tebakan > 100) {
                salahInput++;

                if (lanjutSetelahSalah) {
                    System.out.println("yang bener goblok!");
                    System.exit(0);
                }


                System.out.println("Angka harus Antara 1 sampai 100.");

                if (salahInput >= 2) {
                    System.out.println("kamu sudah salah input 2x. mau lanjut? (y/n)");
                    input.nextLine();
                    String jawaban = input.nextLine();

                    if (!jawaban.equalsIgnoreCase("y")) {
                        System.out.println("Oke bye!");
                        break;
                    }else{
                        lanjutSetelahSalah = true;
                        salahInput = 0;
                    }
                }
                continue;
            }
            percobaan++;


            if (tebakan == angkaRahasia) {
                System.out.println("Selamat Kamu benar dalam " + percobaan + " percobaan");
                break;    
            } else if(tebakan < angkaRahasia) {
                System.out.println("Terlalu Kecil!");
            }else{
                System.out.println("Terlalu besar!");
            }

            if(percobaan == batasPercobaan){
                System.out.println("kamu gagal dalam " + percobaan + " percobaan");
                System.out.println("Jawabannya adalah " + angkaRahasia);
            }
        }
        input.close();
    }

}
