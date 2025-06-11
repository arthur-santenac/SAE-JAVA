
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;

public class VendeurBD {
	ConnexionMySQL laConnexion;
	Statement st;

	VendeurBD(ConnexionMySQL laConnexion) {
		this.laConnexion = laConnexion;
	}

	public boolean ajouteLivre(int idmag, String isbn, int qte)
			throws SQLException {
		System.out.println("test1");
		this.st = this.laConnexion.createStatement();
		System.out.println("test2");
		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "';");

		if (!verif.next()) {
			System.out.println("test3");
			this.st.executeUpdate(
					"INSERT INTO POSSEDER(idmag, isbn, qte) VALUES ('" + idmag + "', '" + isbn + "', " + qte + ");");
			System.out.println("Le livre a bien été ajouté");
			return true;
		} else {

			System.out.println("Vous possédez déjà ce livre, mettez la quantité à jour");
			return false;
		}
	}

	public boolean majQte(int idmag, String isbn, int qte) throws SQLException {
		this.st = this.laConnexion.createStatement();
		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "';");

		if (verif.next()) {
			this.st.executeUpdate(
					"UPDATE POSSEDER SET qte = qte + " + qte +
							" WHERE isbn = '" + isbn + "' AND idmag = '" + idmag + "';");
			System.out.println("Quantité mise à jour");
			return true;
		} else {
			System.out.println("Vous ne possédez pas ce livre, ajoutez-le");
			return false;
		}
	}

	public boolean dispo(int idmag, String isbn, int quantite) throws SQLException {
		this.st = this.laConnexion.createStatement();

		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "' AND (qte >= " + quantite
						+ ");");

		if (verif.next()) {
			System.out.println("Le livre est disponible !");
			return true;
		} else {
			System.out.println("Nous n'avons pas " + quantite + " exemplaire(s) du livre voulu en réserve");
			return false;
		}
	}

}
