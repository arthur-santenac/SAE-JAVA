
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

	public String Connexion(String email, String mdp, AppLibrairie app) {
		try {
			st = laConnexion.createStatement();
			ResultSet set = st.executeQuery("select * from CONNEXION natural join CLIENT");
			while (set.next()) {
				if (email.equals(set.getString(2)) && mdp.equals(set.getString(3))) {
					app.setUtilisateur(new Client(set.getString(5), set.getString(6), set.getString(7), set.getInt(8), set.getString(9), set.getInt(1)));
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
