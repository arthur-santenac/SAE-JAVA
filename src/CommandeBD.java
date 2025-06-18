import java.sql.*;

public class CommandeBD {
    ConnexionMySQL laConnexion;
    Statement st;

    public CommandeBD(ConnexionMySQL laConnexion) {
        this.laConnexion = laConnexion;
    }

    public int maxNumCom() throws SQLException {
        this.st = this.laConnexion.createStatement();
        ResultSet rs = this.st.executeQuery("Select IFNULL(max(numcom),1) from COMMANDE");
        rs.next();
        int res = rs.getInt(1);
        rs.close();
        return res;
    }

    public void insererCommande(int numcom, char enLigne, char livraison, int idcli, int idmag) throws SQLException {
        PreparedStatement ps = laConnexion.prepareStatement("insert into COMMANDE values (?,CURDATE(),?,?,?,?)");
        ps.setInt(1, numcom);
        ps.setString(2, String.valueOf(enLigne));
        ps.setString(3, String.valueOf(livraison));
        ps.setInt(4, idcli);
        ps.setInt(5, idmag);
        ps.executeUpdate();
    }

    public void insererDetailCommande(int numcom, int numlig, int qte, double prixvente, String isbn)
            throws SQLException {
        PreparedStatement ps = laConnexion.prepareStatement("insert into DETAILCOMMANDE values (?,?,?,?,?)");
        ps.setInt(1, numcom);
        ps.setInt(2, numlig);
        ps.setInt(3, qte);
        ps.setDouble(4, prixvente);
        ps.setString(5, isbn);
        ps.executeUpdate();
    }

    public String faireFactures(Connection conn, int mois, int annee) throws SQLException {
        String sql = "SELECT nommag, idcli, nomcli, prenomcli, adressecli, codepostal, villecli, " +
                "       numcom, datecom, qte, prixvente, isbn, titre " +
                "  FROM MAGASIN " +
                "  NATURAL JOIN COMMANDE " +
                "  NATURAL JOIN CLIENT " +
                "  NATURAL JOIN DETAILCOMMANDE " +
                "  NATURAL JOIN LIVRE " +
                " WHERE MONTH(datecom) = ? " +
                "   AND YEAR(datecom)  = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, mois);
            ps.setInt(2, annee);

            try (ResultSet rs = ps.executeQuery()) {
                StringBuilder res = new StringBuilder();
                res.append("Facture du ").append(mois).append("/").append(annee).append("\n");

                String ancienMag = null;
                int ancienPers = -1;
                int cptFac = 0;
                int cptLivre = 0;
                int cpt = 0;
                int nbVente = 0;
                double totalPers = 0.0;
                double caTot = 0.0;

                while (rs.next()) {
                    String nomMag = rs.getString("nommag");
                    if (ancienMag == null || !ancienMag.equals(nomMag)) {
                        if (ancienMag != null) {
                            res.append(
                                    "---------------------------------------------------------------------------------\n")
                                    .append(cptFac).append(" factures éditées\n")
                                    .append(cptLivre).append(" livres vendus\n")
                                    .append("*********************************************************************************\n");
                        }
                        ancienMag = nomMag;
                        res.append("Edition des factures du magasin ").append(nomMag).append("\n");
                        cptLivre = 0;
                        cptFac = 0;
                    }

                    int idCli = rs.getInt("idcli");
                    if (ancienPers == -1 || ancienPers != idCli) {
                        if (ancienPers != -1) {
                            res.append(String.format("%80s\n", "--------"))
                                    .append(String.format("%71s%9.2f\n", "Total", totalPers));
                        }
                        ancienPers = idCli;
                        res.append(
                                "---------------------------------------------------------------------------------\n")
                                .append(rs.getString("nomcli")).append(" ").append(rs.getString("prenomcli"))
                                .append("\n")
                                .append(rs.getString("adressecli")).append("\n")
                                .append(rs.getString("codepostal")).append(" ").append(rs.getString("villecli"))
                                .append("\n")
                                .append("                         commande n°")
                                .append(rs.getInt("numcom"))
                                .append(" du ")
                                .append(rs.getDate("datecom"))
                                .append("\n")
                                .append("         ISBN                    Titre                      qte    prix    total\n");
                        cpt = 0;
                        totalPers = 0.0;
                        cptFac++;
                    }
                    int qte = rs.getInt("qte");
                    double prix = rs.getDouble("prixvente");
                    double total = qte * prix;

                    nbVente += qte;
                    cptLivre += qte;
                    cpt++;
                    totalPers += total;
                    caTot += total;

                    String ligneIsbn = cpt + " " + rs.getString("isbn") + " " + rs.getString("titre");
                    if (ligneIsbn.length() > 60) {
                        ligneIsbn = ligneIsbn.substring(0, 60);
                    }

                    res.append(String.format("%-60s%3d%8.2f%9.2f\n", ligneIsbn, qte, prix, total));
                }
                if (ancienPers != -1) {
                    res.append(String.format("%80s\n", "--------"))
                            .append(String.format("%71s%9.2f\n", "Total", totalPers));
                }
                if (ancienMag != null) {
                    res.append("---------------------------------------------------------------------------------\n")
                            .append(cptFac).append(" factures éditées\n")
                            .append(cptLivre).append(" livres vendus\n")
                            .append("*********************************************************************************\n");
                }
                res.append("Chiffre d'affaire global: ").append(caTot).append("\n")
                        .append("Nombre de livres vendus: ").append(nbVente);

                return res.toString();
            }
        }
    }
}
