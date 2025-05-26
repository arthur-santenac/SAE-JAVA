import java.sql.*;
import java.util.ArrayList;

public class MagasinBD {
    
    ConnexionMySQL laConnexion;
	Statement st;

	MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    Magasin rechercherJoueurParNom(String nom)throws SQLException{
    	throw new SQLException("méthode rechercherJoueurParNum à implémenter");
    }

    ArrayList<Magasin> listeDesMagasins() throws SQLException{
		throw new SQLException("méthode listeDesJoueurs à implémenter");
	}

    ArrayList<String> listeDesNomDeMags() throws SQLException{
		throw new SQLException("méthode listeDesJoueurs à implémenter");
	}
}
