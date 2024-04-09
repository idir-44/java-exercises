public class Calculateur {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Calculateur <nombre1> <nombre2> <opérateur>");
            System.exit(1);
        }

        try {
            double nombre1 = Double.parseDouble(args[0]);
            double nombre2 = Double.parseDouble(args[1]);
            String operateur = args[2];

            double resultat = calculer(nombre1, nombre2, operateur);
            System.out.println("Résultat : " + resultat);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer des nombres valides.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public static double calculer(double nombre1, double nombre2, String operateur) {
        switch (operateur) {
            case "+":
                return nombre1 + nombre2;
            case "-":
                return nombre1 - nombre2;
            case "*":
                return nombre1 * nombre2;
            default:
                throw new IllegalArgumentException("Opérateur non supporté. Veuillez utiliser +, -, ou *.");
        }
    }
}