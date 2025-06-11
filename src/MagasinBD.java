import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MagasinBD {
    
    ConnexionMySQL laConnexion;
	Statement st;

	MagasinBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    Magasin rechercherMagasinParId(Integer id)throws SQLException{
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



    List<Magasin> listeDesMagasins() throws SQLException{
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

    ArrayList<String> listeDesNomDeMags() throws SQLException{
		throw new SQLException("méthode listeDesJoueurs à implémenter");
	}
}
