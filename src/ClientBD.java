
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
		ResultSet rs = this.st.executeQuery("Select IFNULL(max(idcli),0) from CLIENT");
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
	}

	int maxNumJoueur() throws SQLException{
		this.st=this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select IFNULL(max(numJoueur),0) from JOUEUR");
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
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

            livreCommander.add(new Livre(isbn, titre, nbpages, datepubli, prix));
        }

        return livreCommander;
    }

	/* int insererJoueur( Joueur j) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into JOUEUR(numJoueur, pseudo, motdepasse, main, abonne, niveau) values(?, ?, ?, ?, ?, ?)");
		int nouvNum = maxNumJoueur()+1;
		ps.setInt(1, nouvNum);
		ps.setString(2, j.getPseudo());
		ps.setString(3, j.getMotdepasse());
		ps.setString(4, ""+j.getMain());
		String abo;
		if(j.isAbonne()){
			abo="O";
		}
		else{
			abo="N";
		}
		ps.setString(5, abo);
		ps.setInt(6, j.getNiveau());
		ps.executeUpdate();
		ps.close();
		return nouvNum;
	} */


	void effacerJoueur(int num) throws SQLException {
		PreparedStatement ps = this.laConnexion.prepareStatement("delete from JOUEUR where numJoueur=?");
		ps.setInt(1,num);
		int nb = ps.executeUpdate();
		if(nb ==0) throw new SQLException("La suppression du joueur a échoué car aucun joueur n'a ce numéro");
	}

    /* void majJoueur(Joueur j)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("update JOUEUR set numJoueur =?, pseudo=?, motdepasse=?, main=?, abonne=?, niveau=? where numJoueur= ?");
		ps.setInt(1,j.getIdentifiant());
		ps.setString(2,j.getPseudo());
		ps.setString(3, j.getMotdepasse());
		ps.setString(4, j.getMain()+"");
		String abo;
		if( j.isAbonne()){
			abo = "O";
		}else{	
			abo = "N";
		}
		ps.setString(5,abo);
		ps.setInt(6, j.getNiveau());
		ps.setInt(7, j.getIdentifiant());
		int nb = ps.executeUpdate();
		if(nb == 0){
			throw new SQLException("La mise à jour a échoué car aucun joueur ne porte le numéro "+j.getIdentifiant());
		}
		
    } */

    /* Joueur rechercherJoueurParNum(int num)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select * from JOUEUR where numJoueur =?");
		ps.setInt(1, num);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return new Joueur(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getBoolean(5), rs.getString(4).charAt(0), rs.getInt(6));
		}
		else{
			throw new SQLException("Joueur non trouvé");
		}
    } */

	/* ArrayList<Joueur> listeDesJoueurs() throws SQLException{
		this.st = this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select * from JOUEUR");
		List<Joueur> joueurs = new ArrayList<>();
		while(rs.next()){
			int num = rs.getInt(1);
			String ps = rs.getString(2);
			String mp = rs.getString(3);
			Boolean abo ;
			String main = rs.getString(5);
			int niv = rs.getInt(6);
			if(rs.getString(5).equals("O")){
				abo = true;
			}
			else{
				abo =false;
			}
			Joueur j = new Joueur(num, ps, mp, abo, main.charAt(0), niv);
			joueurs.add(j);
		}
		rs.close();
		return (ArrayList<Joueur>) joueurs;
	} */
	

}
