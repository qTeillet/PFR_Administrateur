package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import bo.Horaire;

public class HoraireDAO {
	
	private static String url = System.getenv("FIL_ROUGE_URL");
    private static String username = System.getenv("FIL_ROUGE_USERNAME");
    private static String password = System.getenv("FIL_ROUGE_PASSWORD");
	
    public List<Horaire> select() {
		List<Horaire> horaires = new ArrayList<>();
		
		 try {
            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
                    + url
                    + ";databasename=PFR;username="
                    + username
                    + ";password="
                    + password
                    + ";trustservercertificate=true");
            
            if(!cnx.isClosed()){
                PreparedStatement ps = cnx.prepareStatement("SELECT * FROM horaires");
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    horaires.add(convertResultSetToHoraires(rs));
                    }
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return horaires;
    }
    
    public List<Horaire> selectHorairesByRestaurantId(int restaurantId) {
        List<Horaire> horaires = new ArrayList<>();
        
        try {
            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
                    + url
                    + ";databasename=PFR;username="
                    + username
                    + ";password="
                    + password
                    + ";trustservercertificate=true");

            if (!cnx.isClosed()) {
                PreparedStatement ps = cnx.prepareStatement("SELECT * FROM horaires WHERE id_restaurant = ?");
                ps.setInt(1, restaurantId); 
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    horaires.add(convertResultSetToHoraires(rs));
                }
            }
            cnx.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horaires;
    }
    
    public void insert(Horaire horaire, int idRestaurant){
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("INSERT INTO horaires (id_restaurant, jour, ouverture, fermeture) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setInt(1, idRestaurant);
				ps.setString(2, horaire.getJour());
				ps.setTime(3, Time.valueOf(horaire.getOuverture()));
				ps.setTime(4, Time.valueOf(horaire.getFermeture()));
				
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					horaire.setId(rs.getInt(1));
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
	}
  
    public void update(Horaire horaire) {
    	try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				PreparedStatement ps = cnx.prepareStatement("UPDATE horaires SET jour = ?, ouverture = ?, fermeture = ? WHERE id = ?");
				ps.setString(1, horaire.getJour());
				ps.setTime(2, Time.valueOf(horaire.getOuverture()));
				ps.setTime(3, Time.valueOf(horaire.getFermeture()));
				ps.setInt(4, horaire.getId());
				
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
				PreparedStatement ps = cnx.prepareStatement("DELETE FROM horaires WHERE id = ?");
				ps.setInt(1, id);
				ps.executeUpdate();
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    private Horaire convertResultSetToHoraires(ResultSet rs) throws SQLException {
		Horaire horaire = new Horaire();
		horaire.setId(rs.getInt("id"));
		horaire.setJour(rs.getString("jour"));
		horaire.setOuverture(rs.getTime("ouverture").toLocalTime());
		horaire.setFermeture(rs.getTime("fermeture").toLocalTime());
		return horaire;
	}
}
