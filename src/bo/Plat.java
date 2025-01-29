package bo;

public class Plat {

	private int id;
	private String nom;
	private float prix;
	private String description;
	private Categorie categorie;
	private String nomCategorie;
	
	public Plat(int id, String nom, float prix, String description, Categorie categorie) {
		this.id = id;
		this.nom = nom;
		this.prix = prix;
		this.description = description;
		this.categorie = categorie;
	}

	public Plat(String nom, float prix, String description, Categorie categorie) {
		this.nom = nom;
		this.prix = prix;
		this.description = description;
		this.categorie = categorie;
	}

	public Plat() {
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

	public float getPrix() {
		return prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String getNomCategorie() {
		return nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

	@Override
	public String toString() {
		return String.format("| %-4d | %-20s | %-20s | %-100s | %-20s |",
				id,
				nom,
				prix,
				description, 
				nomCategorie); 
	}
	
	
}

