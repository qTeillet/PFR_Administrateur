package dal;

import bo.Carte;
import bo.Restaurant;

import java.sql.*;

public class CarteDAO {

        public void insert(Carte carte){

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
                        "INSERT INTO cartes (nom, description)" +
                                "VALUES (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, carte.getNom());
                ps.setString(2, carte.getDescription());

                ps.executeUpdate(); //pour executer un insert, un update, ou un delete
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    carte.setId(rs.getInt(1));
                }

            }

            cnx.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
