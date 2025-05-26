
import java.sql.*;
import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.Map;

public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;

	ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	int maxNumClient() throws SQLException{
		return -1;
	}


	int insererJoueur( Client c) throws  SQLException{
		throw new SQLException("méthode insererJoueur à implémenter");
	}


	void effacerJoueur(int num) throws SQLException {
		throw new SQLException("méthode effacerJoueur à implémenter");
	}

    void majJoueur(Client j)throws SQLException{
		throw new SQLException("méthode majJoueur à implémenter");
    }

    Client rechercherJoueurParNum(int num)throws SQLException{
    	throw new SQLException("méthode rechercherJoueurParNum à implémenter");
    }

	ArrayList<Client> listeDesJoueurs() throws SQLException{
		throw new SQLException("méthode listeDesJoueurs à implémenter");
	}
	
	String rapportMessage() throws SQLException{
		return "rapportMessage A faire";
	}
	
	String rapportMessageComplet() throws SQLException{
		return "rapportMessageComplet A faire";	
	}

	ArrayList<Map.Entry<String, Integer>> nbMsgParJour() throws SQLException{
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String, Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMsgParJour à implémenter");
	}
	ArrayList<Map.Entry<String, Integer>> nbMain() throws SQLException{
		// Pour créer une valeur pouvant être ajoutée à l'ArrayList<Map.Entry<String, Integer>>
		// faire un new AbstractMap.SimpleEntry<>("coucou",45)
		throw new SQLException("méthode nbMain à implémenter");
	}	
}
