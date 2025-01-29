package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bo.Carte;
import bo.Plat;

public class PlatDAO {

	private static String url = System.getenv("FIL_ROUGE_URL");
    private static String username = System.getenv("FIL_ROUGE_USERNAME");
    private static String password = System.getenv("FIL_ROUGE_PASSWORD");
	
	public List<Plat> select(int idCarte) {
        
		List<Plat> plats = new ArrayList<>();
        

        try {

            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
                    + url
                    + ";databasename=PFR;username="
                    + username
                    + ";password="
                    + password
                    + ";trustservercertificate=true");


            
            if(!cnx.isClosed()){

                PreparedStatement ps = cnx.prepareStatement("SELECT p.id, p.nom, p.prix, p.description, c.libelle AS categorie_libelle " +
                        "FROM plats p " +
                        "INNER JOIN categories c ON p.id_categorie = c.id " +
                        "INNER JOIN asso_cartes_plats acp ON p.id = acp.id_plat " +
                        "INNER JOIN cartes ca ON acp.id_carte = ca.id " +
                        "WHERE ca.id = ?");
                ps.setInt(1, idCarte);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    plats.add(convertResultSetToPlat(rs));

                }

            }

            cnx.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return plats;
    
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


        

	public void insert(Plat plat) {
		 try {

	            Connection cnx = DriverManager.getConnection("jdbc:sqlserver://"
	                    + url
	                    + ";databasename=PFR;username="
	                    + username
	                    + ";password="
	                    + password
	                    + ";trustservercertificate=true");
	            
	            if(!cnx.isClosed()){

	                PreparedStatement ps = cnx.prepareStatement(
							"INSERT INTO plats (nom, prix, description, id_categorie) "
							+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
	                ps.setString(1, plat.getNom());
					ps.setFloat(2, plat.getPrix());
					ps.setString(3, plat.getDescription());
					ps.setInt(4, plat.getCategorie().getId());
					
					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						plat.setId(rs.getInt(1));
					}
	            }
	          cnx.close();
	      } catch (SQLException e) {
	    	  e.printStackTrace();
	      }
	}
	
	private Plat convertResultSetToPlat(ResultSet rs) throws SQLException {

		Plat plat = new Plat();
		plat.setId(rs.getInt("id"));
		plat.setNom(rs.getString("nom"));
		plat.setPrix(rs.getFloat("prix"));
		if (rs.getString("description") != null)
			plat.setDescription(rs.getString("description"));
		plat.setNomCategorie(rs.getString("categorie_libelle"));;
			
		return plat;
	}
	

}
