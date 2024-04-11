
import java.io.*;
import java.util.*;
import java.util.stream.*;

// Classe principale pour le traitement des fichiers
public class FileCalcMvn {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java FileCalcMvn <chemin_dossier>");
            return;
        }

        File dossier = new File(args[0]);
        Optional.ofNullable(dossier.listFiles((d, name) -> name.endsWith(".op")))
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .forEach(FileCalcMvn::traiterFichier);
    }

    private static void traiterFichier(File fichier) {
        String nomFichierRes = fichier.getPath().replace(".op", ".res");

        try (Scanner scanner = new Scanner(fichier);
                PrintWriter writer = new PrintWriter(nomFichierRes)) {
            Stream.generate(scanner::nextLine)
                    .takeWhile(x -> scanner.hasNextLine())
                    .map(OperateurMathematique::calculer)
                    .forEach(resultat -> writer.println(resultat.orElse("ERROR")));
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + fichier.getPath());
        }
    }
}

// Classe pour effectuer les opérations mathématiques
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
