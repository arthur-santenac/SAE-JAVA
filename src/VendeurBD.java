
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
		this.st = this.laConnexion.createStatement();
		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "';");

		if (!verif.next()) {
			this.st.executeUpdate(
					"INSERT INTO POSSEDER(idmag, isbn, qte) VALUES ('" + idmag + "', '" + isbn + "', " + qte + ");");
			Menu.vendeurModifReussi();
			System.console().readLine();
			return true;
		} else {

			Menu.vendeurErreurAjout();
			System.console().readLine();
			return false;
		}
	}

	public boolean majQte(int idmag, String isbn, int qte, boolean afficheMenu) throws SQLException {
		this.st = this.laConnexion.createStatement();
		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "';");

		if (verif.next()) {
			this.st.executeUpdate(
					"UPDATE POSSEDER SET qte = qte + " + qte +
							" WHERE isbn = '" + isbn + "' AND idmag = " + idmag + " AND qte + " + qte + " >= 0;");
			if (afficheMenu) {
				Menu.vendeurModifReussi();
				System.console().readLine();
			}

			return true;
		} else {
			if (afficheMenu) {
				Menu.vendeurErreurModif();
				System.console().readLine();
			}

			return false;
		}
	}

	public boolean dispo(int idmag, String isbn, int quantite, boolean afficheMenu) throws SQLException {
		this.st = this.laConnexion.createStatement();

		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "' AND (qte >= " + quantite
						+ ");");

		if (verif.next()) {
			if (afficheMenu) {
				Menu.vendeurDispo();
				System.console().readLine();
			}

			return true;
		} else {
			if (afficheMenu) {
				Menu.vendeurPasDispo(quantite);
				System.console().readLine();
			}

			return false;
		}
	}

	public boolean transfer(int idMag, int idMagDestination, String isbn, int quantite) throws SQLException {
		this.st = this.laConnexion.createStatement();

		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idMag + "' AND isbn = '" + isbn + "' AND (qte >= " + quantite
						+ ");");

		if (verif.next()) {
			if (!dispo(idMagDestination, isbn, quantite, false)) {
				ajouteLivre(idMagDestination, isbn, quantite);
			} else {
				majQte(idMagDestination, isbn, quantite, false);
			}
			majQte(idMag, isbn, -quantite, false);
			Menu.tranfertReussi();
			System.console().readLine();
			return true;
		} else {
			Menu.transferFail();
			System.console().readLine();
			return false;
		}
	}

}
