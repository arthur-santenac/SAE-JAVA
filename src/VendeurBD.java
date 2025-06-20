
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

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
			
			// mode terminal : System.console().readLine();
			return true;
		} else {

			
			// mode terminal : System.console().readLine();
			new Alert(Alert.AlertType.INFORMATION, "Modification echec").showAndWait();
			return false;
		}
	}

	public boolean majQte(int idmag, String isbn, int qte, boolean afficheMenu)
			throws SQLException, NumberFormatException {
		this.st = this.laConnexion.createStatement();
		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "';");
		if (verif.next()) {
			this.st.executeUpdate(
					"UPDATE POSSEDER SET qte = qte + " + qte +
							" WHERE isbn = '" + isbn + "' AND idmag = " + idmag + " AND qte + " + qte + " >= 0;");
			return true;
		} else {
			return false;
		}
	}

	public boolean dispo(int idmag, String isbn, int quantite, boolean afficheMenu) throws SQLException {
		this.st = this.laConnexion.createStatement();

		ResultSet verif = this.st.executeQuery(
				"SELECT qte FROM POSSEDER WHERE idmag = '" + idmag + "' AND isbn = '" + isbn + "' AND (qte >= "
						+ quantite
						+ ");");

		if (verif.next()) {
			return true;
		} else {
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
			return true;
		} else {
			return false;
		}
	}

	public boolean existe(String isbn) throws SQLException {
		this.st = this.laConnexion.createStatement();

		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM LIVRE WHERE isbn = '" + isbn + "';");

		if (verif.next()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean estDansUneLibrairie(String isbn) throws SQLException {
		this.st = this.laConnexion.createStatement();

		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE isbn = '" + isbn + "';");

		if (verif.next()) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> librairiesPossedent(String isbn, int qte) throws SQLException {
		this.st = this.laConnexion.createStatement();
		List<String> res = new ArrayList<>();

		ResultSet verif = this.st.executeQuery(
				"select distinct * from MAGASIN natural join POSSEDER where qte >= " + qte + " AND isbn = '" + isbn
						+ "';");

		while (verif.next()) {
			res.add(verif.getInt("idmag") + " " + verif.getString("nommag") + ", stock : " + verif.getInt("qte"));
		}
		return res;

	}

	public ListView<String> getRecherche(String recherche, int idMag) throws SQLException {
		ListView<String> listeLivre = new ListView<>();
		if (recherche.length() > 0) {
			st = laConnexion.createStatement();
			String requete = "select * from LIVRE natural join POSSEDER where idmag = " + idMag;
			ResultSet set = st.executeQuery(requete);
			while (set.next()) {
				if (set.getString("titre").contains(recherche)) {
					Livre livre = new Livre(
							set.getString("isbn"),
							set.getString("titre"),
							set.getInt("nbpages"),
							set.getInt("datepubli"),
							set.getDouble("prix"),
							set.getInt("qte"));
					listeLivre.getItems().add(livre.toStringVendeur());
				}
			}
		}
		return listeLivre;
	}

	public Livre getLivreBD(String isbn, int idMag) throws SQLException {
		this.st = this.laConnexion.createStatement();
		Livre res = null;

		ResultSet verif = this.st.executeQuery(
				"SELECT * FROM POSSEDER WHERE isbn = '" + isbn + "' AND idmag = "+ idMag);

		if (verif.next()) {
			res = new Livre(isbn, verif.getString(4), verif.getInt(5), verif.getInt(6), verif.getDouble(7));
		}
		return res;
	}
}
