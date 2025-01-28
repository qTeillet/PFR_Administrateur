package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bo.Restaurant;


public class RestaurantDAO {
	
	String url = System.getenv("FIL_ROUGE_URL");
	String username = System.getenv("FIL_ROUGE_USERNAME");
	String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	public void insert(Restaurant restaurant) {
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(
						"INSERT INTO restaurants (nom, adresse, url_image) VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, restaurant.getNom());
				ps.setString(2, restaurant.getAdresse());
				ps.setString(3, restaurant.getUrl_image());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					restaurant.setId(rs.getInt(1));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
}
