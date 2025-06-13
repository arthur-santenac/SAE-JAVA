
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

	public void insererVendeur(Client c, String email, String mdp)throws SQLException{
		PreparedStatement ps = this.laConnexion.prepareStatement("insert into CLIENT(idcli, nomcli, prenomcli, adressecli, codepostal, villecli ) values(?, ?, ?, ?, ?, ?)");
		int nouvNum = this.maxNum()+1;
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
		ps2.setInt(3, nouvNum);
		ps2.setString(4, "vendeur");
		ps2.executeUpdate();
		ps2.close();
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
