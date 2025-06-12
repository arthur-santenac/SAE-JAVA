import java.sql.*;

public class CommandeBD {
        ConnexionMySQL laConnexion;
        Statement st;

        public CommandeBD(ConnexionMySQL laConnexion) {
            this.laConnexion = laConnexion;
        }

        public int maxNumCom() throws SQLException{
            this.st = this.laConnexion.createStatement();
            ResultSet rs = this.st.executeQuery("Select IFNULL(max(numcom),1) from COMMANDE");
            rs.next();
            int res = rs.getInt(1);
            rs.close();
            return res;
        }

        public void insererCommande(int numcom, char enLigne, char livraison, int idcli, String idmag) throws SQLException{
            PreparedStatement ps = laConnexion.prepareStatement("insert into COMMANDE values (?,CURDATE(),?,?,?,?)");
            ps.setInt(1, numcom);
            ps.setString(2, String.valueOf(enLigne));
            ps.setString(3, String.valueOf(livraison));
            ps.setInt(4, idcli);
            ps.setString(5, idmag);
            ps.executeUpdate();
        }

        public void insererDetailCommande(int numcom, int numlig, int qte, double prixvente, String isbn) throws SQLException{
            PreparedStatement ps = laConnexion.prepareStatement("insert into DETAILCOMMANDE values (?,?,?,?,?)");
            ps.setInt(1, numcom);
            ps.setInt(2, numlig);
            ps.setInt(3, qte);
            ps.setDouble(4, prixvente);
            ps.setString(5, isbn);
            ps.executeUpdate();
        }

        public static String editerFacture(String requete, int mois, int annee, Connection bd) throws SQLException {
        String res = "Facture du " + mois + "/" + annee + "\n";
        try {
        PreparedStatement ps = bd.prepareStatement(requete);
        ps.setInt(1, mois);
        ps.setInt(2, annee);
        ResultSet rs = ps.executeQuery();
        String ancienMag = null;
        String ancienClient = null;
        int cpt = 0;
        int totalClient = 0;
        int cptFactures = 0;
        int cptLivres = 0;
        int CA = 0;
        int nbVentes = 0;
        while (rs.next()) {
            String nomMag = rs.getString("nommag");
            String idClient = rs.getString("idcli");
            if (ancienMag == null || !ancienMag.equals(nomMag)) {
                if (ancienMag != null) {
                    res += "---------------------------------------------------------------------------------\n";
                    res += cptFactures + " factures éditées\n";
                    res += cptLivres + " livres vendus\n";
                    res += "**\n";
                }
                ancienMag = nomMag;
                res += "Edition des factures du magasin " + nomMag + "\n";
                cptLivres = 0;
                cptFactures = 0;
            }
            if (ancienClient == null || !ancienClient.equals(idClient)) {
                if (ancienClient != null) {
                    res += String.format("%80s\n", "--------");
                    res += String.format("%71s%9d\n", "Total", totalClient);
                }
                ancienClient = idClient;
                res += "---------------------------------------------------------------------------------\n";
                res += rs.getString("nomcli") + " " + rs.getString("prenomcli") + "\n";
                res += rs.getString("adressecli") + "\n";
                res += rs.getString("codepostal") + " " + rs.getString("villecli") + "\n";
                res += "                         commande n°" + rs.getInt("numcom") + " du " + rs.getDate("datecom")
                        + "\n";
                res += "         ISBN                    Titre                      qte    prix    total\n";
                cpt = 0;
                totalClient = 0;
                cptFactures++;
            }
            int qte = rs.getInt("qte");
            int prix = rs.getInt("prixvente");
            nbVentes += qte;
            cptLivres += qte;
            cpt++;
            totalClient += qte * prix;
            CA += qte * prix;
            String isbn = rs.getString("isbn");
            String titre = rs.getString("titre");
            String ligne = cpt + " " + isbn + " " + titre;
            if (ligne.length() > 60) {
                ligne = ligne.substring(0, 60);
            }
            ligne = String.format("%-60s", ligne);
            res += ligne
                    + String.format("%3d", qte)
                    + String.format("%8d", prix)
                    + String.format("%9d", qte * prix)
                    + "\n";
        }
        res += String.format("%80s\n", "--------");
        res += String.format("%71s%9d\n", "Total", totalClient);

        res += "---------------------------------------------------------------------------------\n";
        res += cptFactures + " factures éditées\n";
        res += cptLivres + " livres vendus\n";
        res += "*********************************************************************************\n";
        res += "Chiffre d'affaire global: " + CA + "\n";
        res += "Nombre de livres vendus: " + nbVentes;

        rs.close();
        ps.close();

    }
    catch (SQLException error) {
        res += "\nErreur lors de l'édition de la facture : " + error.getMessage();
    }
    return res;
    }
}
