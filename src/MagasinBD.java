import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MagasinBD {
    
    ConnexionMySQL laConnexion;
	Statement st;

	MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    public Magasin rechercherMagasinParId(Integer id)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select * from MAGASIN where idmag =?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return new Magasin(rs.getInt(1), rs.getString(2), rs.getString(3));
        }
    	else{
            throw new SQLException("Magasin non trouvé");
        }
    }



    public List<Magasin> listeDesMagasins() throws SQLException{
		this.st = this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select * from MAGASIN");
		List<Magasin> magasins = new ArrayList<>();
		while(rs.next()){
			int id = rs.getInt(1);
			String nomM = rs.getString(2);
			String villeM = rs.getString(3);
			Magasin m = new Magasin(id,nomM, villeM);
			magasins.add(m);
		}
		rs.close();
		return magasins;
	}

    public List<String> listeDesNomDeMags() throws SQLException{
		this.st = this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select  from MAGASIN");
		List<String> magasins = new ArrayList<>();
		while(rs.next()){
			String nomM = rs.getString(1);
			magasins.add(nomM);
		}
		rs.close();
		return magasins;
	}

	public List<String> nombreLivresVenduParMagParAn(int annee) throws SQLException {
		PreparedStatement ps = this.laConnexion.prepareStatement(
		"Select nommag as Magasin, YEAR(datecom) as Annee, SUM(qte) as total " +
		"From MAGASIN Natural join COMMANDE Natural join DETAILCOMMANDE " +
		"Where YEAR(datecom) = ? GROUP BY nommag, YEAR(datecom)");
		ps.setInt(1, annee);
		ResultSet rs = ps.executeQuery();
		List<String> res = new ArrayList<>();
		while (rs.next()) {
			String chaine = rs.getString(1) + " " + rs.getInt(2) + " : " + rs.getInt(3);
			res.add(chaine);
		}
		rs.close();
		ps.close();
		return res;
	}

	public List<String> valeurStocksParMag()throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("Select nommag Magasin, sum(qte*prix) total From MAGASIN natural left join POSSEDER p natural left join LIVRE Group by nommag;");
		ResultSet rs = ps.executeQuery();
		List<String> res = new ArrayList<>();
		while (rs.next()) {
			String chaine = rs.getString(1) + ": " + rs.getInt(2);
			res.add(chaine);
		}
		rs.close();
		ps.close();
		return res;
	}
}
