package dal;

import bo.Carte;
import bo.Restaurant;

import java.sql.*;

public class CarteDAO {

    /*Je mets le restaurant auquel on ajoute une carte en parametre
    parce qu'on a besoin d'ajouter son id à la carte dans la BDD
    (colonne id_restaurant)
    */
    public void insert(Carte carte, Restaurant restaurant){

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
                        "INSERT INTO cartes (nom, type, id_restaurant)" +
                                "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, carte.getNom());
                ps.setString(2, carte.getDescription());
                //ps.setString(3, restaurant.getId()); //ID DU RESTAURANT DONT ON CREE LA CARTE

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
