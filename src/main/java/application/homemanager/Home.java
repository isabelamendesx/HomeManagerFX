package application.homemanager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Home {
    private String username;
    private char[] senhaHash;

    public Home(String username, char[] senha) {
        this.username = username;
        this.senhaHash = gerarSenhaHash(senha);
    }

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

}
