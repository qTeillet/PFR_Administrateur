package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Categorie;

public class CategorieDAO {

	String url = System.getenv("FIL_ROUGE_URL");
    String username = System.getenv("FIL_ROUGE_USERNAME");
    String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	public List<Categorie> select() {
		List<Categorie> categories = new ArrayList<>();
		
		 try {

            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
                    + url
                    + ";databasename=PFR;username="
                    + username
                    + ";password="
                    + password
                    + ";trustservercertificate=true");

            
            if(!cnx.isClosed()){

                PreparedStatement ps = cnx.prepareStatement("SELECT * FROM categories");
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    categories.add(convertResultSetToCategorie(rs));
                }

            }

            cnx.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

	
	private Categorie convertResultSetToCategorie(ResultSet rs) throws SQLException {
		Categorie categorie = new Categorie();
		categorie.setId(rs.getInt("id"));
		categorie.setLibelle(rs.getString("libelle"));
		return categorie;
	}
}

