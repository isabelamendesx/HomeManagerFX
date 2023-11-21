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

    public List<WeeklyTask> getHomeWTasks() {
        return homeWTasks;
    }

    public List<DailyTask> getHomeDTasks() {
        return homeDTasks;
    }

    public void printHomeTasks(){
        homeWTasks.forEach(System.out::println);
        homeDTasks.forEach(System.out::println);
    }

    public void addAllWeeklyTasks(List<WeeklyTask> weeklyTasks) {
        homeWTasks.addAll(weeklyTasks);
        HomeRepository.saveUserData();
    }

    public void addAllDailyTasks(List<DailyTask> dailyTasks) {
        homeDTasks.addAll(dailyTasks);
        HomeRepository.saveUserData();
    }

    public void addWeeklyTask(WeeklyTask weeklyTask) {
        homeWTasks.add(weeklyTask);
        HomeRepository.saveUserData();
    }

    public void addDailyTask(DailyTask dailyTask) {
        homeDTasks.add(dailyTask);
        HomeRepository.saveUserData();
    }

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

    public void distribuirTarefasSemanais() {
        if (membersList.isEmpty() || homeWTasks.isEmpty()) {
            System.out.println("Sem membros ou tarefas semanais para distribuir.");
            return;
        }

        // Calcula o número de tarefas que cada membro deve receber
        int tarefasPorMembro = homeWTasks.size() / membersList.size();
        int tarefasRestantes = homeWTasks.size() % membersList.size();

        List<WeeklyTask> tarefasDaCasaDisponivel = new ArrayList<>(homeWTasks);
        // Distribui tarefas para cada membro
        for (Member membro : membersList) {
            int tarefasMembro = tarefasPorMembro + (tarefasRestantes > 0 ? 1 : 0);
            tarefasRestantes--;

            // Embaralha a lista de tarefas semanais para distribuição aleatória
            Collections.shuffle(tarefasDaCasaDisponivel);

            // Remove as tarefas atuais do membro
            tarefasDaCasaDisponivel.removeAll(membro.getTarefasSemanais());

            List<WeeklyTask> tarefasDaCasaDisponivelCopy = new ArrayList<>(tarefasDaCasaDisponivel);
            int endIndex = Math.min(tarefasMembro, tarefasDaCasaDisponivelCopy.size());

            if(endIndex == tarefasDaCasaDisponivelCopy.size() && endIndex != tarefasMembro){
                tarefasRestantes++;
            }
            List<WeeklyTask> tarefasParaMembro = tarefasDaCasaDisponivelCopy.subList(0, endIndex);

            tarefasDaCasaDisponivel.addAll(membro.getTarefasSemanais());
            membro.removerTarefasSemanais();
            membro.adicionarTarefasSemanais(tarefasParaMembro);

            // Remove as tarefas que o membro pegou
            tarefasDaCasaDisponivel.removeAll(tarefasParaMembro);
        }
        HomeRepository.saveUserData();
    }

    public void distribuirTarefasDiarias() {
        if (membersList.isEmpty() || homeDTasks.isEmpty()) {
            System.out.println("Sem membros ou tarefas semanais para distribuir.");
            return;
        }

        // Calcula o número de tarefas que cada membro deve receber
        int tarefasPorMembro = homeDTasks.size() / membersList.size();
        int tarefasRestantes = homeDTasks.size() % membersList.size();

        List<DailyTask> tarefasDaCasaDisponivel = new ArrayList<>(homeDTasks);
        // Distribui tarefas para cada membro
        for (Member membro : membersList) {
            int tarefasMembro = tarefasPorMembro + (tarefasRestantes > 0 ? 1 : 0);
            tarefasRestantes--;

            // Embaralha a lista de tarefas semanais para distribuição aleatória
            Collections.shuffle(tarefasDaCasaDisponivel);

            // Remove as tarefas atuais do membro
            tarefasDaCasaDisponivel.removeAll(membro.getTarefasDiarias());

            List<DailyTask> tarefasDaCasaDisponivelCopy = new ArrayList<>(tarefasDaCasaDisponivel);
            int endIndex = Math.min(tarefasMembro, tarefasDaCasaDisponivelCopy.size());

            if(endIndex == tarefasDaCasaDisponivelCopy.size()){
                tarefasRestantes++;
            }
            List<DailyTask> tarefasParaMembro = tarefasDaCasaDisponivelCopy.subList(0, endIndex);

            tarefasDaCasaDisponivel.addAll(membro.getTarefasDiarias());
            membro.removerTarefasDiarias();
            membro.adicionarTarefasDiarias(tarefasParaMembro);

            // Remove as tarefas que o membro pegou
            tarefasDaCasaDisponivel.removeAll(tarefasParaMembro);
        }
        HomeRepository.saveUserData();
    }

    public void printTasks(){
        for (Member member : membersList) {
            System.out.println("Weekly Tasks for Member: " + member.getName());
            for (WeeklyTask weeklyTask : member.getTarefasSemanais()) {
                System.out.println(" - " + weeklyTask.getTaskName());
            }

            System.out.println("Daily Tasks for Member: " + member.getName());
            for (DailyTask dailyTask : member.getTarefasDiarias()) {
                System.out.println(" - " + dailyTask.getTaskName());
            }

            System.out.println(); // Adiciona uma linha em branco entre os membros
        }
    }

    @Override
    public String toString() {
        return "Home{" +
                "username='" + username + '\'' +
                ", membersList = " + membersList +
                ", weeklyList = " + homeWTasks +
                '}';
    }
}
