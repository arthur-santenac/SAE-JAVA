import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminBD {
    ConnexionMySQL laConnexion;
	Statement st;

	AdminBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    int maxNumMagasin() throws SQLException{
		this.st=this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select IFNULL(max(idmag),0) from MAGASIN");
		if(rs.next()){
			int res = rs.getInt(1);
			rs.close();
			return res;
		}
		else{
			throw new SQLException("Il y a un problème avec les magasins");
		}
		
	}


    public void insererLibrairie(Magasin m) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into MAGASIN(idmag, nommag, villemag) values(?, ?, ?)");
		int nouvNum = this.maxNumMagasin()+1;
		ps.setInt(1, nouvNum);
		ps.setString(2, m.getNomMag());
		ps.setString(3, m.getVilleMag());
		ps.executeUpdate();
		ps.close();
	}

	public void supprimerLibrairie(Integer id)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("delete from MAGASIN where idmag =?");
		ps.setInt(1, id);
		int nb = ps.executeUpdate();
        if(nb ==0){
			throw new SQLException("La suppression de la librairie a échoué car aucune librairie n'a cet identifiant");
		} 
	}

	public List<String> afficherStocks() throws SQLException{
		Statement st = this.laConnexion.createStatement();
        ResultSet rs = st.executeQuery("Select isbn, titre, nbpages, prix, SUM(qte) as stockTotal From LIVRE Natural join POSSEDER Natural join MAGASIN "+
            						"Group by isbn, titre, nbpages, prix");
        List<String> listeStock = new ArrayList<>();
        while (rs.next()) {
			String titre = rs.getString(2);
			if (titre.length() > 20) {
				titre = titre.substring(0, 17) + "...";
			}
            String chaine = rs.getString(1)+" "+titre+" | "+rs.getInt(3)+" pages | "+rs.getInt(4)+"€ |"+rs.getInt(5);
            listeStock.add(chaine);
        }
		return listeStock;
	}

	public List<String> afficherStocksLib(int id) throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select isbn, titre, nbpages, prix, SUM(qte) as stockTotal From LIVRE Natural join POSSEDER Natural join MAGASIN "+
            						"Where idmag = ? Group by isbn, titre, nbpages, prix");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
        List<String> listeStock = new ArrayList<>();
        while (rs.next()) {
			String titre = rs.getString(2);
			if (titre.length() > 20) {
				titre = titre.substring(0, 17) + "...";
			}
            String chaine = rs.getString(1)+" "+titre+" | "+rs.getInt(3)+" pages | "+rs.getInt(4)+"€ |"+rs.getInt(5);
            listeStock.add(chaine);
        }
		return listeStock;
	}

	public List<String> palmares()throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("with vue as(" +
						" Select YEAR(datecom) annee,idauteur, nomauteur, sum(qte) total " + 
						" From AUTEUR natural join ECRIRE natural join LIVRE natural join DETAILCOMMANDE natural join COMMANDE " + //
						" Group by YEAR(datecom),idauteur, nomauteur )"+"\n" +
						"\n" +
						"Select annee , nomauteur , total\n" + 
						"From vue v1 Where total = ( Select max(total) From vue v2  Where annee <> 2025 and v1.annee = v2.annee)\n" +
						"Group by annee, nomauteur");
		ResultSet rs = ps.executeQuery();
		List<String> res = new ArrayList<>();
		while (rs.next()) {
			String chaine = rs.getString(2)+" " + rs.getInt(1)+" "+rs.getInt(3);
			res.add(chaine);
		}
		rs.close();
		ps.close();
		return res;
	}


	public List<String> meilleursEdit()throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select nomedit Editeur , count(distinct idauteur) nbauteurs "+ 
																 "From EDITEUR natural join EDITER natural join LIVRE natural join ECRIRE natural join AUTEUR "+
																 "Group by nomedit Order by nbauteurs desc limit 10");
		ResultSet rs = ps.executeQuery();
		List<String> res = new ArrayList<>();
		while (rs.next()) {
			String chaine = rs.getString(1)+" : "+rs.getInt(2);
			res.add(chaine);
		}
		rs.close();
		ps.close();
		return res;
	}


	public Map<String, Double> caParTheme(int annee) throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select  nomclass Theme , SUM(prixvente * qte) Montant " + 
						"From CLASSIFICATION natural join THEMES natural join LIVRE natural join DETAILCOMMANDE natural join COMMANDE " + 
						"Where YEAR(datecom) = ? " + 
						"Group by LEFT(iddewey, 1)");
		ps.setInt(1, annee);
		ResultSet rs = ps.executeQuery();
		Map<String, Double > res = new HashMap<>();
		while(rs.next()){
			res.put(rs.getString(1), rs.getDouble(2));
		}
		rs.close();
		ps.close();
		return res;
	}




}
