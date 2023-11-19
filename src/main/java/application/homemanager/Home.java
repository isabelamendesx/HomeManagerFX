package application.homemanager;

import javafx.scene.layout.VBox;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public List<Member> getMembersList(){
        return membersList;
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

    private List<WeeklyTask> getNewTasks(Member membro) {
        return homeWTasks.stream()
                .filter(tarefa -> !membro.getTarefasSemanais().contains(tarefa))
                .collect(Collectors.toList());
    }

    public void distribuirTarefasSemanais() {
        if (membersList.isEmpty() || homeWTasks.isEmpty()) {
            System.out.println("Sem membros ou tarefas semanais para distribuir.");
            return;
        }

        // Embaralha a lista de tarefas semanais para distribuição aleatória
        Collections.shuffle(homeWTasks);

        // Calcula o número de tarefas que cada membro deve receber
        int tarefasPorMembro = homeWTasks.size() / membersList.size();
        int tarefasRestantes = homeWTasks.size() % membersList.size();


        int indiceTarefa = 0;
        // Distribui tarefas para cada membro
        for (Member membro : membersList) {
            // Verifica e remove as tarefas atuais do membro
            membro.removerTarefasSemanais();

            int tarefasMembro = tarefasPorMembro + (tarefasRestantes > 0 ? 1 : 0);
            tarefasRestantes--;


            // Adiciona novas tarefas, garantindo que não sejam as mesmas da semana anterior
            List<WeeklyTask> novasTarefas = getNewTasks(membro).subList(indiceTarefa, indiceTarefa + tarefasMembro);;
            membro.adicionarTarefasSemanais(novasTarefas);
        }
    }

    @Override
    public String toString() {
        return "Home{" +
                "username='" + username + '\'' +
                ", membersList = " + membersList +
                '}';
    }
}
