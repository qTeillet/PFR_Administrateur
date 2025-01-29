package bo;

import java.util.ArrayList;
import java.util.List;

public class Carte {

    private int id;
    private String nom;
    private String description;
    private String nomRestaurant;
    private List<Plat> plats = new ArrayList<>();

    public Carte(int id, String nom, String description, List<Plat> plats) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.plats = plats;
    }

    public Carte(String nom, String description, List<Plat> plats) {
        this.nom = nom;
        this.description = description;
        this.plats = plats;
    }

    public Carte(String nom, String description) {
        this.nom = nom;
        this.description = description;
    }

    public Carte() {
    }

    public void ajouterPlat(Plat plat){
        plats.add(plat);
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public void setPlats(List<Plat> plats) {
        this.plats = plats;
    }
	
    public String getNomRestaurant() {
		return nomRestaurant;
	}

	public void setNomRestaurant(String nomRestaurant) {
		this.nomRestaurant = nomRestaurant;
	}
	
	@Override
	public String toString() {
		return String.format("| %-4d | %-20s | %-100s | %-20s |",
				id,
				nom,
				description, 
				nomRestaurant); 
	}


    
    
}
