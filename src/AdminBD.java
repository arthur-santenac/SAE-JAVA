import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    void insererLibrairie( Magasin m) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into MAGASIN(idmag, nommag, villemag) values(?, ?, ?)");
		int nouvNum = this.maxNumMagasin()+1;
		ps.setInt(1, nouvNum);
		ps.setString(2, m.getNomMag());
		ps.setString(3, m.getVilleMag());
		ps.executeUpdate();
		ps.close();
	}

	void supprimerLibrairie(Magasin m)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("delete from MAGASIN(idmag, nommag, villemag) values(?, ?, ?)");
		ps.setInt(1, m.getIdMag());
		ps.setString(2, m.getNomMag());
		ps.setString(3, m.getVilleMag());
		int nb = ps.executeUpdate();
        if(nb ==0){
			throw new SQLException("La suppression de la librairie a échoué car aucune librairie n'a cet identifiant");
		} 
	}

}
