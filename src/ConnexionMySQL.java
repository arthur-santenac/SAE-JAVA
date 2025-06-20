import java.sql.*;

public class ConnexionMySQL {
	private Connection mysql=null;
	private boolean connecte=false;
	
	public ConnexionMySQL() throws ClassNotFoundException{
		this.mysql = null;
		this.connecte = false;
		Class.forName("org.mariadb.jdbc.Driver");
	}

	public void connecter( String nomLogin, String motDePasse, String nomServeur, String nomBase) throws SQLException {
		// this.mysql=DriverManager.getConnection("jdbc:mysql://" + nomServeur + ":3306/" + nomBase, nomLogin, motDePasse);
		String url = "jdbc:mariadb://" + nomServeur + ":3306/" + nomBase; // Pour notre machine perso
		this.mysql = DriverManager.getConnection(url, nomLogin, motDePasse);
		this.connecte = true;
		// si tout s'est bien passé la connexion n'est plus nulle
		this.connecte=this.mysql!=null;
	}

	public void close() throws SQLException {
		// fermer la connexion
		this.connecte=false;
	}

    public boolean isConnecte() {
		return this.connecte;
	}

	public Statement createStatement() throws SQLException {
		return this.mysql.createStatement();
	}

	public PreparedStatement prepareStatement(String requete) throws SQLException{
		return this.mysql.prepareStatement(requete);
	}
	
}
