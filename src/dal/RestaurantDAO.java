package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Carte;
import bo.Restaurant;


public class RestaurantDAO {
	private static final String SELECT = "SELECT r.id, r.nom, r.adresse, r.url_image, r.id_carte, ca.nom AS carte_nom, ca.description AS carte_description FROM restaurants r LEFT JOIN cartes ca ON r.id_carte = ca.id";
	private static final String SELECT_BY_ID = "SELECT r.id, r.nom, r.adresse, r.url_image, r.id_carte, ca.nom AS carte_nom, ca.description AS carte_description FROM restaurants r LEFT JOIN cartes ca ON r.id_carte = ca.id WHERE r.id = ?";
	private static final String INSERT = "INSERT INTO restaurants (nom, adresse, url_image, id_carte) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE restaurants SET nom = ?, adresse = ?, url_image = ?, id_carte = ? WHERE id = ?";
	private static final String DELETE = "DELETE FROM restaurants WHERE id = ?";
	
	private String url = System.getenv("FIL_ROUGE_URL");
	private String username = System.getenv("FIL_ROUGE_USERNAME");
	private String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	
	public List<Restaurant> select() {
		List<Restaurant> restaurants = new ArrayList<>();
		
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				
				PreparedStatement ps = cnx.prepareStatement(SELECT);
				ResultSet rs = ps.executeQuery(); 
				
				while (rs.next()) {
					restaurants.add(convertResultSetToRestaurant(rs));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		return restaurants;
	}
	
	public Restaurant select(int id) {
		Restaurant restaurant = null;
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				
				PreparedStatement ps = cnx.prepareStatement(SELECT_BY_ID);
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					restaurant = convertResultSetToRestaurant(rs);
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		return restaurant;
	}
	
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
				PreparedStatement ps = cnx.prepareStatement( INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, restaurant.getNom());
				ps.setString(2, restaurant.getAdresse());
				ps.setString(3, restaurant.getUrl_image());
				
				if (restaurant.getCarte() != null) {
					ps.setInt(4 ,restaurant.getCarte().getId());
				} else {
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				
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
	
	public void update(Restaurant restaurant) {
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(UPDATE);
				ps.setString(1, restaurant.getNom());
				ps.setString(2, restaurant.getAdresse());
				ps.setString(3, restaurant.getUrl_image());
				
				if (restaurant.getCarte() != null) {
					ps.setInt(4, restaurant.getCarte().getId());
				} else {
					ps.setNull(4, java.sql.Types.INTEGER);
				}
				ps.setInt(5, restaurant.getId());
				
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
	
	public void delete(int id) {
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement(DELETE);
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
	
	private Restaurant convertResultSetToRestaurant(ResultSet rs) throws SQLException {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(rs.getInt("id"));
		restaurant.setNom(rs.getString("nom"));
		restaurant.setAdresse(rs.getString("adresse"));
		restaurant.setUrl_image(rs.getString("url_image"));
		
		Carte carte = new Carte();
		carte.setId(rs.getInt("id_carte"));
		carte.setNom(rs.getString("carte_nom"));
		carte.setDescription(rs.getString("carte_description"));
		
		restaurant.setCarte(carte);
		restaurant.setHoraires(new ArrayList<>()); 
	   
		return restaurant;
	}
}
