package bo;


public class Categorie {
	private int id;
	private String libelle;
	
	public Categorie(int id, String libelle) {
		this.id = id;
		this.libelle = libelle;
	}

	public Categorie(String libelle) {
		this.libelle = libelle;
	}

	public Categorie() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}


    @Override
    public String toString() {
        return libelle;
    }

}
