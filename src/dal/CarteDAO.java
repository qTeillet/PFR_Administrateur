package dal;

import bo.Carte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarteDAO {
	
		private static String url = System.getenv("FIL_ROUGE_URL");
		private static String username = System.getenv("FIL_ROUGE_USERNAME");
		private static String password = System.getenv("FIL_ROUGE_PASSWORD");
		
		public List<Carte> select() {
			List<Carte> cartes = new ArrayList<>();
			
			try {
				Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
	                    + url
	                    + ";databasename=PFR;username="
	                    + username
	                    + ";password="
	                    + password
	                    + ";trustservercertificate=true");
			
				if(!cnx.isClosed()) {
					PreparedStatement ps = cnx.prepareStatement("SELECT ca.id, ca.nom, ca.description, r.nom AS nom_restaurant"
							+ "	FROM cartes ca\r\n"
							+ "	LEFT JOIN restaurants r ON ca.id = r.id_carte;");
					ResultSet rs = ps.executeQuery(); // Pour exécuter un SELECT
				
					while (rs.next()) {
						cartes.add(convertResultSetToCarte(rs));
					}
				}
				cnx.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return cartes;
		}

	public Carte select(int id) {
		Carte carte = null;
		try {
			Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
					+ url
					+";databasename=PFR;username="
					+ username
					+ ";password="
					+ password
					+ ";trustservercertificate=true");
			
			if(!cnx.isClosed()) {
				
				PreparedStatement ps = cnx.prepareStatement("SELECT ca.id, ca.nom, ca.description, r.nom AS nom_restaurant"
						+ "	FROM cartes ca\r\n"
						+ "	LEFT JOIN restaurants r ON ca.id = r.id_carte "
						+  "WHERE ca.id = ?");
				ps.setInt(1, id);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					carte = convertResultSetToCarte(rs);
				}
			}
			cnx.close();
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		return carte;
	}
        public void insert(Carte carte){

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

        private Carte convertResultSetToCarte(ResultSet rs) throws SQLException {
    		Carte carte = new Carte();
    		
    		carte.setId(rs.getInt("id"));
    		carte.setNom(rs.getString("nom"));
    		
    		if (rs.getString("description") != null)
    			carte.setDescription(rs.getString("description"));
    		if (rs.getString("nom_restaurant") != null) 
    			carte.setNomRestaurant(rs.getString("nom_restaurant"));
    	    
    		return carte;
		}
}
