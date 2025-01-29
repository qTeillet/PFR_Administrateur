package dal;

import bo.Carte;

import java.sql.*;

public class CarteDAO {

    String url = System.getenv("FIL_ROUGE_URL");
    String username = System.getenv("FIL_ROUGE_USERNAME");
    String password = System.getenv("FIL_ROUGE_PASSWORD");
		
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
				
				PreparedStatement ps = cnx.prepareStatement("SELECT * FROM cartes WHERE id = ?");
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
    		carte.setDescription(rs.getString("description"));
    		
    		return carte;
    	}
}
