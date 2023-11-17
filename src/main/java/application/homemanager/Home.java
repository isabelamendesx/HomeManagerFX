package application.homemanager;

import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Home implements Serializable {
    private String username;
    private char[] senhaHash;

    private List<Member> membersList;

    private List<WeeklyTask> homeWTasks;
    private List<DailyTask> homeDTasks;


    public Home(String username, char[] senha) {
        this.username = username;
        this.senhaHash = gerarSenhaHash(senha);
        this.membersList = new ArrayList<>();
        this.homeDTasks = new ArrayList<>();
        this.homeWTasks = new ArrayList<>();
    }

    public void printHomeTasks(){
        homeWTasks.forEach(System.out::println);
        homeDTasks.forEach(System.out::println);
    }


    public void addAllWeeklyTasks(List<WeeklyTask> weeklyTasks) {
        homeWTasks.addAll(weeklyTasks);
    }

    public void addAllDailyTasks(List<DailyTask> dailyTasks) {
        homeDTasks.addAll(dailyTasks);
    }

    public void addWeeklyTask(WeeklyTask weeklyTask) {homeWTasks.add(weeklyTask); }

    public void addDailyTask(DailyTask dailyTask) {homeDTasks.add(dailyTask); }

    public void printMembersList(){
        membersList.forEach(System.out::println);
    }

    public void addMember(Member member){membersList.add(member); }

    public static char[] gerarSenhaHash(char[] senha) {
        try {
            // Use SHA-256 para hashing (ou outro algoritmo mais seguro)
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(new String(senha).getBytes());

            // Converte bytes para caracteres hexadecimais
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Retorna o hash como um array de caracteres
            return hexString.toString().toCharArray();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // Em um ambiente real, você lidaria com essa exceção de maneira mais apropriada
            return null;
        }
    }

    public boolean verificarSenha(char[] senha) {
        char[] senhaHashDigitada = gerarSenhaHash(senha);
        return Arrays.equals(senhaHash, senhaHashDigitada);
    }

    public String getUsername() {
        return username;
    }


    @Override
    public String toString() {
        return "Home{" +
                "username='" + username + '\'' +
                ", membersList = " + membersList +
                '}';
    }
}
