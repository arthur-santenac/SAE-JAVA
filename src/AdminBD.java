import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminBD {
    
    ConnexionMySQL laConnexion;
	Statement st;

	AdminBD(ConnexionMySQL laConnexion){
		this.laConnexion=laConnexion;
	}

}
