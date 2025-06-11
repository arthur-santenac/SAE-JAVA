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
		rs.next();
		int res = rs.getInt(1);
		rs.close();
		return res;
	}

    void insererLibrairie( Magasin m) throws  SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into Magasin(idmag, nommag, villemag) values(?, ?, ?,)");
		int nouvNum = this.maxNumMagasin()+1;
		ps.setInt(1, nouvNum);
		ps.setString(2, m.getNomMag());
		ps.setString(3, m.getVilleMag());
		ps.executeUpdate();
		ps.close();
	}

}
