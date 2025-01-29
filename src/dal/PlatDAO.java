package dal;

import bo.Carte;
import bo.Plat;

import java.sql.*;

public class PlatDAO {

    public void insert(Plat plat){

        String url = System.getenv("FIL_ROUGE_URL");
        String username = System.getenv("FIL_ROUGE_USERNAME");
        String password = System.getenv("FIL_ROUGE_PASSWORD");

        try {

            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
                    + url
                    + ";databasename=PFR;username="
                    + username
                    + ";password="
                    + password
                    + ";trustservercertificate=true");


            if(! cnx.isClosed()){

                PreparedStatement ps = cnx.prepareStatement(
                        "INSERT INTO plats (nom, prix, description, id_categorie)" +
                                "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, plat.getNom());
                ps.setFloat(2, plat.getPrix());
                ps.setString(3, plat.getDescription());
                ps.setInt(4, plat.getCategorie().getId());

                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    plat.setId(rs.getInt(1));
                }

            }

            cnx.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void associerPlatCarte(Plat plat, Carte carte){

        String url = System.getenv("FIL_ROUGE_URL");
        String username = System.getenv("FIL_ROUGE_USERNAME");
        String password = System.getenv("FIL_ROUGE_PASSWORD");

        try {

            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
                    + url
                    + ";databasename=PFR;username="
                    + username
                    + ";password="
                    + password
                    + ";trustservercertificate=true");


            if(! cnx.isClosed()){

                PreparedStatement ps = cnx.prepareStatement(
                        "INSERT INTO asso_cartes_plats (id_carte, id_plat)" +
                                "VALUES (?, ?)");
                ps.setInt(1, carte.getId());
                ps.setInt(2, plat.getId());

                ps.executeUpdate();

            }

            cnx.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
