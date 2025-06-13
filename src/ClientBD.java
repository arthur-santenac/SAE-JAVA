
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap;
import java.util.Map;

public class ClientBD {
	ConnexionMySQL laConnexion;
	Statement st;

	ClientBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

	public int maxNum() throws SQLException{
		this.st=this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select IFNULL(max(idcli),1) from CLIENT");
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

	public void insererVendeur(Client c, String email, String mdp)throws SQLException{
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
		ps2.setString(4, "vendeur");
		ps2.executeUpdate();
		ps2.close();
	}

	List<Client> listeDesVendeurs() throws SQLException {
		this.st = this.laConnexion.createStatement();
		// Utilisez la requête recommandée avec INNER JOIN
		ResultSet rs = this.st.executeQuery(
			"SELECT c.* FROM CLIENT c INNER JOIN CONNEXION co ON c.idcli = co.idcli WHERE co.compte = 'vendeur'");
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
		PreparedStatement ps = this.laConnexion.prepareStatement("delete from CLIENT where idcli =?");
		ps.setInt(1, id);
		int nb = ps.executeUpdate();
		PreparedStatement ps2 = this.laConnexion.prepareStatement("delete from CONNEXION where idcli =?");
		ps2.setInt(1, id);
		int nb2 = ps2.executeUpdate();
        if(nb ==0 || nb2 ==0){
			throw new SQLException("La suppression de la librairie a échoué car aucune librairie n'a cet identifiant");
		} 
	}

	public String Connexion(String email, String mdp, AppLibrairie app) {
		try {
			st = laConnexion.createStatement();
			ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");
			while (set.next()) {
				if (email.equals(set.getString(2)) && mdp.equals(set.getString(3))) {
					app.setUtilisateur(new Client(set.getString(5), set.getString(6), set.getString(7), set.getString(8), set.getString(9), set.getInt(1)));
					return set.getString(4);
				}
			}
			return "mauvaisMdp";
		} catch (SQLException e) {
			System.out.println("erreur sql");
			return "mauvaisMdp";
		}
	}

	public List<Livre> getLivresCommandesParClient(int idClient) throws SQLException {
		List<Livre> livreCommander = new ArrayList<>();

		String requete = "SELECT DISTINCT LIVRE.isbn, LIVRE.titre, LIVRE.nbpages, LIVRE.datepubli, LIVRE.prix, " +
		"CLASSIFICATION.iddewey, CLASSIFICATION.nomclass " +
		"FROM CLIENT " +
		"JOIN COMMANDE ON CLIENT.idcli = COMMANDE.idcli " +
		"JOIN LIGNECOMMANDE ON COMMANDE.numcom = LIGNECOMMANDE.numcom " +
		"JOIN LIVRE ON LIGNECOMMANDE.idlivre = LIVRE.isbn " +
		"LEFT JOIN THEMES ON LIVRE.isbn = THEMES.isbn " +
		"LEFT JOIN CLASSIFICATION ON THEMES.iddewey = CLASSIFICATION.iddewey " +
		"WHERE CLIENT.idcli = ?";

		PreparedStatement pst = this.laConnexion.prepareStatement(requete);
		pst.setInt(1, idClient);

		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
		int isbn = rs.getInt("isbn");
		String titre = rs.getString("titre");
		int nbpages = rs.getInt("nbpages");
		String datepubli = rs.getString("datepubli");
		double prix = rs.getDouble("prix");

		}

		return livreCommander;
	}

	public List<Livre> onVousRecommande(int idClient) throws SQLException {
		List<Livre> recommandations = new ArrayList<>();

		String requete = 
			"WITH ThemesClient AS ( " +
			"    SELECT iddewey " +
			"    FROM COMMANDE " +
			"    NATURAL JOIN LIGNECOMMANDE " +
			"    NATURAL JOIN THEMES " +
			"    WHERE idcli = ? " +
			"), " +
			"ClientsSimilaires AS ( " +
			"    SELECT DISTINCT idcli " +
			"    FROM COMMANDE " +
			"    NATURAL JOIN LIGNECOMMANDE " +
			"    NATURAL JOIN THEMES " +
			"    WHERE iddewey IN (SELECT iddewey FROM ThemesClient) " +
			"      AND idcli <> ? " +
			"), " +
			"LivresPotentiels AS ( " +
			"    SELECT idlivre, COUNT(*) AS frequence " +
			"    FROM COMMANDE " +
			"    NATURAL JOIN LIGNECOMMANDE " +
			"    WHERE idcli IN (SELECT idcli FROM ClientsSimilaires) " +
			"    GROUP BY idlivre " +
			") " +
			"SELECT LIVRE.isbn, LIVRE.titre, LIVRE.nbpages, LIVRE.datepubli, LIVRE.prix " +
			"FROM LivresPotentiels " +
			"JOIN LIVRE ON LivresPotentiels.idlivre = LIVRE.isbn " +
			"WHERE isbn NOT IN ( " +
			"    SELECT idlivre " +
			"    FROM COMMANDE " +
			"    NATURAL JOIN LIGNECOMMANDE " +
			"    WHERE idcli = ? " +
			") " +
			"ORDER BY frequence DESC, datepubli DESC";

		PreparedStatement pst = this.laConnexion.prepareStatement(requete);
		pst.setInt(1, idClient);
		pst.setInt(2, idClient);
		pst.setInt(3, idClient);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			int isbn = rs.getInt("isbn");
			String titre = rs.getString("titre");
			int nbpages = rs.getInt("nbpages");
			String datepubli = rs.getString("datepubli");
			double prix = rs.getDouble("prix");

		}

		return recommandations;
	}

}
