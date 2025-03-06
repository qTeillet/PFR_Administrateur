package bo;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
	private int id;
	private String nom;
	private String  adresse;
	private String url_image;
	private Carte carte;
	private List<Table> tables = new ArrayList<Table>();
	private List<Horaire> horaires = new ArrayList<Horaire>();
	
	public Restaurant(int id, String nom, String adresse, String url_image, Carte carte, List<Table> tables,
			List<Horaire> horaires) {
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.url_image = url_image;
		this.carte = carte;
		this.tables = tables;
		this.horaires = horaires;
	}
	
	public Restaurant(String nom, String adresse, String url_image, Carte carte) {
		this.nom = nom;
		this.adresse = adresse;
		this.url_image = url_image;
		this.carte = carte;
	}
	
	public Restaurant() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public Carte getCarte() {
		return carte;
	}
	
	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public List<Horaire> getHoraires() {
		return horaires;
	}

	public void setHoraires(List<Horaire> horaires) {
		this.horaires = horaires;
	}

	@Override
	public String toString() {
		 String carteInfo;
		    if (carte == null || carte.getId() == 0) {
		        carteInfo = "Carte non associée";
		    } else {
		        carteInfo = carte.getNom() + " (n° " + carte.getId() + ")";
		    }
		
		    StringBuilder horairesString = new StringBuilder();
		    if (horaires != null && !horaires.isEmpty()) {
		        horairesString.append("  Horaires :\n");
		        for (Horaire horaire : horaires) {
		            horairesString.append(String.format("    %-10s : %-10s - %-10s\n", 
		                    horaire.getJour(), 
		                    horaire.getOuverture().toString(), 
		                    horaire.getFermeture().toString()));
		        }
		    } else {
		        horairesString.append("    Aucun horaire disponible.\n");
		    }
		
		    return String.format(
		            "\n=====================================================================\n" +
		            "| ID : %-3d  | %-52s |\n" +
		            "---------------------------------------------------------------------\n" +
		            "| %-64s |\n" +
		            "---------------------------------------------------------------------\n" +
		            "| Carte : %-56s |\n" +
		            "---------------------------------------------------------------------\n" +
		            "%s" +
		            "=====================================================================\n",
		            id,
		            nom,
		            adresse,
		            carteInfo,
		            horairesString.toString()
		        );
		    }
	
}
