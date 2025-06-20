
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;

import javafx.scene.control.ListView;

public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;

	ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public int maxNum() throws SQLException{
		this.st=this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select IFNULL(max(idcli),0) from CLIENT");
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
	}

	public Client rechercherVendeurParId(Integer id)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select * from CLIENT where idcli =?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new Client(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(1));
        }
    	else{
            throw new SQLException("Magasin non trouvé");
        }
    }

	public void insererVendeur(Client c, String email, String mdp, int idMag)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into CLIENT(idcli, nomcli, prenomcli, adressecli, codepostal, villecli ) values(?, ?, ?, ?, ?, ?)");
		ps.setInt(1, c.getIdCli());
		ps.setString(2, c.getNom());
		ps.setString(3, c.getPrenom());
		ps.setString(4, c.getAdresse());
		ps.setString(5, c.getCodePostal());
		ps.setString(6, c.getVille());
		ps.executeUpdate();
		ps.close();
		PreparedStatement ps2 = this.laConnexion.prepareStatement("insert into CONNEXION(adresseemail, motdepasse, idcli, compte) values(?, ?, ?, ?)");
		ps2.setString(1, email);
		ps2.setString(2, mdp);
		ps2.setInt(3, c.getIdCli());
		ps2.setString(4, "vendeur"+idMag);
		ps2.executeUpdate();
		ps2.close();
	}

	List<Client> listeDesVendeurs() throws SQLException {
		this.st = this.laConnexion.createStatement();
		// Utilisez la requête recommandée avec INNER JOIN
		ResultSet rs = this.st.executeQuery(
			"Select c.* from CLIENT c INNER JOIN CONNEXION co ON c.idcli = co.idcli Where co.compte Like 'vendeur%'");
		List<Client> clients = new ArrayList<>();
		while (rs.next()) {
			int id = rs.getInt(1);
			String nomV = rs.getString(2);
			String prenomV = rs.getString(3);
			String adresseV = rs.getString(4);
			String codePostalV = rs.getString(5);
			String villeV = rs.getString(6);
			Client c = new Client(nomV, prenomV, adresseV, codePostalV, villeV, id);
			clients.add(c);
		}
		rs.close();
		return clients;
	}

	public void supprimerVendeur(Integer id)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("delete from CONNEXION where idcli =?");
		ps.setInt(1, id);
		ps.executeUpdate();
		PreparedStatement ps2 = this.laConnexion.prepareStatement("delete from CLIENT where idcli =?");
		ps2.setInt(1, id);
		int nb = ps2.executeUpdate();
        if(nb ==0 ){
			throw new SQLException("La suppression de la librairie a échoué car aucune librairie n'a cet identifiant");
		}
		ps.close();
		ps2.close();
	}


	public List<Livre> getLivresCommandesParClient(int idClient) throws SQLException {
		List<Livre> livreCommander = new ArrayList<>();

		String requete =
			"SELECT DISTINCT LIVRE.isbn, LIVRE.titre, LIVRE.nbpages, LIVRE.datepubli, LIVRE.prix, " +
			"CLASSIFICATION.iddewey, CLASSIFICATION.nomclass " +
			"FROM CLIENT " +
			"JOIN COMMANDE ON CLIENT.idcli = COMMANDE.idcli " +
			"JOIN DETAILCOMMANDE ON COMMANDE.numcom = DETAILCOMMANDE.numcom " +
			"JOIN LIVRE ON DETAILCOMMANDE.isbn = LIVRE.isbn " +
			"LEFT JOIN THEMES ON LIVRE.isbn = THEMES.isbn " +
			"LEFT JOIN CLASSIFICATION ON THEMES.iddewey = CLASSIFICATION.iddewey " +
			"WHERE CLIENT.idcli = ?";

		PreparedStatement pst = this.laConnexion.prepareStatement(requete);
		pst.setInt(1, idClient);

		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String isbn      = rs.getString("isbn");
			String titre     = rs.getString("titre");
			int    nbpages   = rs.getInt("nbpages");
			int    datepubli = rs.getInt("datepubli");
			double prix      = rs.getDouble("prix");
			livreCommander.add(new Livre(isbn, titre, nbpages, datepubli, prix));
		}

		return livreCommander;
	}


	public List<Livre> onVousRecommande(int idClient) throws SQLException {
		List<Livre> recommandations = new ArrayList<>();

		String requete =
			"SELECT DISTINCT LIVRE.isbn, LIVRE.titre, LIVRE.nbpages, LIVRE.datepubli, LIVRE.prix " +
			"FROM THEMES " +
			"JOIN LIVRE ON LIVRE.isbn = THEMES.isbn " +
			"WHERE THEMES.iddewey IN ( " +
			"        SELECT DISTINCT THEMES.iddewey " +
			"        FROM COMMANDE " +
			"        JOIN DETAILCOMMANDE ON DETAILCOMMANDE.numcom = COMMANDE.numcom " +
			"        JOIN THEMES ON THEMES.isbn = DETAILCOMMANDE.isbn " +
			"        WHERE COMMANDE.idcli = ? ) " +
			"AND LIVRE.isbn NOT IN ( " +
			"        SELECT DETAILCOMMANDE.isbn " +
			"        FROM COMMANDE " +
			"        JOIN DETAILCOMMANDE ON DETAILCOMMANDE.numcom = COMMANDE.numcom " +
			"        WHERE COMMANDE.idcli = ? ) " +
			"ORDER BY LIVRE.datepubli DESC";

		try (PreparedStatement pst = this.laConnexion.prepareStatement(requete)) {
			pst.setInt(1, idClient);
			pst.setInt(2, idClient);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					recommandations.add(new Livre(
						rs.getString("isbn"),
						rs.getString("titre"),
						rs.getInt("nbpages"),
						rs.getInt("datepubli"),
						rs.getDouble("prix")));
				}
			}
		}

		return recommandations;
	}

	public ListView<Livre> onVousRecommandeIHM(int idClient) throws SQLException {
		ListView<Livre> recommandations = new ListView<>();

		String requete =
			"SELECT DISTINCT LIVRE.isbn, LIVRE.titre, LIVRE.nbpages, LIVRE.datepubli, LIVRE.prix " +
			"FROM THEMES " +
			"JOIN LIVRE ON LIVRE.isbn = THEMES.isbn " +
			"WHERE THEMES.iddewey IN ( " +
			"        SELECT DISTINCT THEMES.iddewey " +
			"        FROM COMMANDE " +
			"        JOIN DETAILCOMMANDE ON DETAILCOMMANDE.numcom = COMMANDE.numcom " +
			"        JOIN THEMES ON THEMES.isbn = DETAILCOMMANDE.isbn " +
			"        WHERE COMMANDE.idcli = ? ) " +
			"AND LIVRE.isbn NOT IN ( " +
			"        SELECT DETAILCOMMANDE.isbn " +
			"        FROM COMMANDE " +
			"        JOIN DETAILCOMMANDE ON DETAILCOMMANDE.numcom = COMMANDE.numcom " +
			"        WHERE COMMANDE.idcli = ? ) " +
			"ORDER BY LIVRE.datepubli DESC";

		try (PreparedStatement pst = this.laConnexion.prepareStatement(requete)) {
			pst.setInt(1, idClient);
			pst.setInt(2, idClient);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					recommandations.getItems().add(new Livre(
						rs.getString("isbn"),
						rs.getString("titre"),
						rs.getInt("nbpages"),
						rs.getInt("datepubli"),
						rs.getDouble("prix")));
				}
			}
		}

		return recommandations;
	}

	public ListView<Livre> getCatalogue(String theme, String filtre) throws SQLException{
		st = laConnexion.createStatement();
		String rajout1 = "";
		String rajout2 = "";
		if (filtre.equals("Prix croissant")) {
			rajout2 = "order by prix";
		} else if (filtre.equals("Prix décroissant")) {
			rajout2 = "order by prix DESC";
		} else if (filtre.equals("Popularité")) {
			rajout2 = "";
		}
		if (theme.equals("Arts et Loisirs")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 7";
		} else if (theme.equals("Histoire et Géographie")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 9";
		} else if (theme.equals("Informatique, généralités")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 0";
		} else if (theme.equals("Langues")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 4";
		} else if (theme.equals("Littérature")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 8";
		} else if (theme.equals("Philosophie et psychologie")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 1";
		} else if (theme.equals("Religion")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 2";
		} else if (theme.equals("Science naturelles et mathématiques")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 5";
		} else if (theme.equals("Sciences sociales")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 3";
		} else if (theme.equals("Technologie et sciences appliqués")) {
			rajout1 = "natural join THEMES natural join CLASSIFICATION where LEFT(iddewey, 1) = 6";
		}
		String requete = "select * from LIVRE " + rajout1 + " " + rajout2;
        ResultSet set = st.executeQuery(requete);
        ListView<Livre> listeLivre = new ListView<>();
        while (set.next()) {
			listeLivre.getItems().add(new Livre(set.getString("isbn"), set.getString("titre"), set.getInt("nbpages"), set.getInt("datepubli"), set.getDouble("prix")));
		}
		return listeLivre;
	}

	public ListView<Livre> getRecherche(String recherche) throws SQLException{
		ListView<Livre> listeLivre = new ListView<>();
		if (recherche.length() > 0) {
			st = laConnexion.createStatement();
			String requete = "select * from LIVRE";
			ResultSet set = st.executeQuery(requete);
			while (set.next()) {
				if (set.getString("titre").contains(recherche)) {
					listeLivre.getItems().add(new Livre(set.getString("isbn"), set.getString("titre"), set.getInt("nbpages"), set.getInt("datepubli"), set.getDouble("prix")));
				}
			}
		}
		return listeLivre;
	}


}
