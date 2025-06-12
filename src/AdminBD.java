import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBD {

    private ConnexionMySQL laConnexion;
	private Statement st;

	public AdminBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

    public String maxNumMagasin() throws SQLException{
		this.st=this.laConnexion.createStatement();
		ResultSet rs = this.st.executeQuery("Select IFNULL(max(idmag),0) from MAGASIN");
		if(rs.next()){
			String res = rs.getString(1);
			rs.close();
			return res;
		}
		else{
			throw new SQLException("Il y a un problème avec les magasins");
		}
		
	}

    public void insererLibrairie(Magasin m) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into MAGASIN(idmag, nommag, villemag) values(?, ?, ?)");
		String nouvNum = this.maxNumMagasin()+1;
		ps.setString(1, nouvNum);
		ps.setString(2, m.getNomMag());
		ps.setString(3, m.getVilleMag());
		ps.executeUpdate();
		ps.close();
	}

	public void supprimerLibrairie(String id) throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("delete from MAGASIN where idmag =?");
		ps.setString(1, id);
		int nb = ps.executeUpdate();
        if(nb == 0){
			throw new SQLException("La suppression de la librairie a échoué car aucune librairie n'a cet identifiant");
		} 
	}

}
