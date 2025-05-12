package Exceptions;
public class MauvaisMotDePasseExeption extends Exception {
    public MauvaisMotDePasseExeption() {
        System.out.println("╭───────────────────────────────────────────────────────────────────────────╮");
        System.out.println("│ Votre Identifiant ou votre Mot de passe est incorrect, veuillez réessayer │");
        System.out.println("╰───────────────────────────────────────────────────────────────────────────╯");    
        System.console().readLine();
    }
}