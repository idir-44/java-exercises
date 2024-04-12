import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBCalc {

    private static final String DB_URL = "jdbc:postgresql://SG-hetic-mt4-java-5275-pgsql-master.servers.mongodirector.com:5432/TP";
    private static final String USER = "etudiant";
    private static final String PASS = "MT4@hetic2324";

    public static void main(String[] args) {

        String sql = "SELECT PARAM1, PARAM2, OPERATEUR FROM FICHIER WHERE TYPE = 'OP'";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER,
                PASS);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int param1 = rs.getInt("PARAM1");
                int param2 = rs.getInt("PARAM2");
                String operateur = rs.getString("OPERATEUR");
                String operation = param1 + ";" + param2 + ";" + operateur;

                Optional<String> resultat = OperateurMathematique.calculer(operation);
                System.out.println(resultat.orElse("ERROR"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    class OperateurMathematique {
        public static Optional<String> calculer(String input) {
            String[] parts = input.split(";");
            if (parts.length != 3) {
                return Optional.empty();
            }

            try {
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[1]);
                String operateur = parts[2];
                double resultat = switch (operateur) {
                    case "+" -> num1 + num2;
                    case "-" -> num1 - num2;
                    case "*" -> num1 * num2;
                    default -> throw new IllegalArgumentException("Opération non supportée");
                };
                return Optional.of(String.valueOf(resultat));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        }
    }
}
