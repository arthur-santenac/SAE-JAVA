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

    public static String editerFacture(Connection bd, int idClient) {
        final String REQ_DATE = "SELECT DATE_FORMAT(MAX(com.datecom), '%Y-%m') AS date_max " +
                "FROM commande com " +
                "JOIN client c ON c.idcli = com.idcli " +
                "WHERE c.idcli = ?";

        String moisAnnee;
        try (PreparedStatement ps = bd.prepareStatement(REQ_DATE)) {
            ps.setInt(1, idClient);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || rs.getString("date_max") == null) {
                    return "Aucune commande trouvée pour ce client : aucune facture à éditer.\n";
                }
                moisAnnee = rs.getString("date_max"); // ex: "2025-06"
            }
        } catch (SQLException e) {
            return "Erreur lors de la récupération de la date : " + e.getMessage();
        }

        final String REQ_FACTURE = "SELECT m.nommag AS nommag, " +
                "       c.idcli AS idcli, " +
                "       c.nomcli AS nomcli, " +
                "       c.prenomcli AS prenomcli, " +
                "       c.adressecli AS adressecli, " +
                "       c.codepostal AS codepostal, " +
                "       c.villecli AS villecli, " +
                "       com.numcom AS numcom, " +
                "       com.datecom AS datecom, " +
                "       l.isbn AS isbn, " +
                "       l.titre AS titre, " +
                "       dc.qte AS qte, " +
                "       dc.prixvente AS prixvente " +
                "FROM   commande com " +
                "JOIN   client c ON c.idcli = com.idcli " +
                "JOIN   magasin m ON m.idmag = com.idmag " +
                "JOIN   detailcommande dc ON dc.numcom = com.numcom " +
                "JOIN   livre l ON l.isbn = dc.isbn " +
                "WHERE  c.idcli = ? AND DATE_FORMAT(com.datecom, '%Y-%m') = ? " +
                "ORDER  BY m.nommag, com.numcom, l.titre";

        StringBuilder res = new StringBuilder("Facture du ").append(moisAnnee).append(" pour client ").append(idClient)
                .append('\n');

        String ancienMag = null;
        int ligneIdx = 0, totalClient = 0;
        int nbFacturesMag = 0, nbLivresMag = 0;
        int CA = 0, nbLivresGlobaux = 0;

        try (PreparedStatement ps = bd.prepareStatement(REQ_FACTURE)) {
            ps.setInt(1, idClient);
            ps.setString(2, moisAnnee);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    return "Aucune facture pour ce client en " + moisAnnee + ".\n";
                }

                while (rs.next()) {
                    String nomMag = rs.getString("nommag");
                    if (ancienMag == null || !ancienMag.equals(nomMag)) {
                        if (ancienMag != null) {
                            res.append(
                                    "---------------------------------------------------------------------------------\n")
                                    .append(nbFacturesMag).append(" factures éditées\n")
                                    .append(nbLivresMag).append(" livres vendus\n")
                                    .append("**\n");
                        }
                        ancienMag = nomMag;
                        res.append("Edition des factures du magasin ").append(nomMag).append('\n');
                        nbFacturesMag = 0;
                        nbLivresMag = 0;
                    }

                    if (ligneIdx == 0) {
                        res.append(
                                "---------------------------------------------------------------------------------\n")
                                .append(rs.getString("nomcli")).append(' ')
                                .append(rs.getString("prenomcli")).append('\n')
                                .append(rs.getString("adressecli")).append('\n')
                                .append(rs.getString("codepostal")).append(' ')
                                .append(rs.getString("villecli")).append('\n');
                    }

                    if (ligneIdx == 0 || rs.getInt("numcom") != ligneIdx) {
                        res.append("                         commande n°")
                                .append(rs.getInt("numcom")).append(" du ")
                                .append(rs.getDate("datecom")).append('\n')
                                .append("         ISBN                    Titre                      qte    prix    total\n");
                        nbFacturesMag++;
                    }

                    int qte = rs.getInt("qte");
                    int prix = rs.getInt("prixvente");
                    nbLivresGlobaux += qte;
                    nbLivresMag += qte;
                    ligneIdx++;
                    totalClient += qte * prix;
                    CA += qte * prix;

                    String ligne = ligneIdx + " " + rs.getString("isbn") + " " + rs.getString("titre");
                    if (ligne.length() > 60)
                        ligne = ligne.substring(0, 60);
                    ligne = String.format("%-60s", ligne);

                    res.append(ligne)
                            .append(String.format("%3d", qte))
                            .append(String.format("%8d", prix))
                            .append(String.format("%9d%n", qte * prix));
                }

                res.append(String.format("%80s%n", "--------"))
                        .append(String.format("%71s%9d%n", "Total", totalClient));

                res.append("---------------------------------------------------------------------------------\n")
                        .append(nbFacturesMag).append(" factures éditées\n")
                        .append(nbLivresMag).append(" livres vendus\n");

                res.append("*********************************************************************************\n")
                        .append("Chiffre d'affaire global: ").append(CA).append('\n')
                        .append("Nombre de livres vendus: ").append(nbLivresGlobaux);
            }

        } catch (SQLException e) {
            res.append("\nErreur lors de l'édition de la facture : ")
                    .append(e.getMessage());
        }

        return res.toString();
    }

}
