import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login_Sistem Login_Sistem = new Login_Sistem();
        Mahasiswa mahasiswa = new Mahasiswa();
        Login_Sistem.addPengguna(mahasiswa);
        Admin admin = new Admin("admin", "admin");
        Login_Sistem.addPengguna(admin);
        boolean exit = false;

        while (!exit) {
            System.out.println("====library sistem===");
            System.out.println("1. login as student");
            System.out.println("2. login as admin");
            System.out.println("3. exit");
            System.out.print("choose option (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("enter your NIM: ");
                    String nimInput = scanner.nextLine();
                    if (Login_Sistem.authenticate(nimInput)) {
                        System.out.println("successful login as student");
                    } else {
                        System.out.println("Pengguna not found");
                    }
                    break;
                case 2:
                    System.out.print("enter your Penggunaname(admin): ");
                    String adminPenggunaname = scanner.nextLine();
                    System.out.print("enter your password (admin): ");
                    String adminPassword = scanner.nextLine();
                    if (Login_Sistem.authenticate(adminPenggunaname + ":" + adminPassword)) {
                        System.out.println("successful login as admin");
                    } else {
                        System.out.println("admin Pengguna not found!!");
                    }
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
        scanner.close();
    }
}

class Login_Sistem {
    private Map<String, Pengguna> Penggunas = new HashMap<>();

    public void addPengguna(Pengguna Pengguna) {
        Penggunas.put(Pengguna.getClass().getSimpleName(), Pengguna);
    }

    public boolean authenticate(String input) {
        String[] parts = input.split(":");
        String Penggunaname = parts[0];
        String password = parts.length > 1 ? parts[1] : "";
        Pengguna Pengguna = Penggunas.get("Admin");
        if (Pengguna != null && Pengguna.login(Penggunaname + ":" + password)) {
            return true;
        }
        Pengguna = Penggunas.get("Mahasiswa");
        if (Pengguna != null && Pengguna.login(Penggunaname)) {
            return true;
        }
        return false;
    }
}

interface Pengguna {
    boolean login(String input);
}

class Mahasiswa implements Pengguna {
    @Override
    public boolean login(String input) {
        return input.length() == 15;
    }
}

class Admin implements Pengguna {
    private String Penggunaname;
    private String password;

    public Admin(String Penggunaname, String password) {
        this.Penggunaname = Penggunaname;
        this.password = password;
    }

    @Override
    public boolean login(String input) {
        if (input.startsWith(Penggunaname + ":")) {
            String inputPassword = input.substring(Penggunaname.length() + 1);
            return inputPassword.equals(password);
        }
        return false;
    }
}