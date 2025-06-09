import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBD {

    ConnexionMySQL laConnexion;
	Statement st;

	AdminBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
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

}