import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileCalculateur {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage : java OperateurFichier <chemin_dossier>");
            return;
        }

        File dossier = new File(args[0]);
        File[] fichiersOp = dossier.listFiles((d, name) -> name.endsWith(".op"));

        if (fichiersOp != null) {
            for (File fichier : fichiersOp) {
                traiterFichier(fichier);
            }
        }
    }

    private static void traiterFichier(File fichier) {
        String nomFichierRes = fichier.getPath().replace(".op", ".res");

        try (Scanner scanner = new Scanner(fichier);
                PrintWriter writer = new PrintWriter(nomFichierRes)) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                try {
                    double resultat = OperateurMathematique.calculer(ligne);
                    writer.println(resultat);
                } catch (Exception e) {
                    writer.println("ERROR");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + fichier.getPath());
        }
    }
}

class OperateurMathematique {
    public static double calculer(String input) throws SyntaxeException, OperationException {
        String[] parts = input.split(";");
        if (parts.length != 3) {
            throw new SyntaxeException("Syntaxe incorrecte");
        }

        double num1 = Double.parseDouble(parts[0]);
        double num2 = Double.parseDouble(parts[1]);
        String operateur = parts[2];

        switch (operateur) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            default:
                throw new OperationException("Opération non supportée");
        }
    }
}

class SyntaxeException extends Exception {
    public SyntaxeException(String message) {
        super(message);
    }
}

class OperationException extends Exception {
    public OperationException(String message) {
        super(message);
    }
}
