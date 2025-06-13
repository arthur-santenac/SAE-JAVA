
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

	public void insererVendeur(Client c)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into CLIENT(nom, prenom, adresse, codePostal, ville, idCli) values(?, ?, ?, ?, ?, ?)");
		int nouvNum = this.maxNum()+1;
		ps.setString(1, c.getNom());
		ps.setString(2, c.getPrenom());
		ps.setString(3, c.getAdresse());
		ps.setString(4, c.getCodePostal());
		ps.setString(5, c.getVille());
		ps.setInt(6, c.getIdCli());
		ps.executeUpdate();
		ps.close();
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

	

}
