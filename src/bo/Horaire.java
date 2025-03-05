package bo;

import java.time.LocalTime;

public class Horaire {
	private int id;
	private String jour;
	private LocalTime ouverture;
	private LocalTime fermeture;
	
	public Horaire(int id, String jour, LocalTime ouverture, LocalTime fermeture) {
		this.id = id;
		this.jour = jour;
		this.ouverture = ouverture;
		this.fermeture = fermeture;
	}

	public Horaire(String jour, LocalTime ouverture, LocalTime fermeture) {
		this.jour = jour;
		this.ouverture = ouverture;
		this.fermeture = fermeture;
	}
	
	public Horaire() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public LocalTime getOuverture() {
		return ouverture;
	}

	public void setOuverture(LocalTime ouverture) {
		this.ouverture = ouverture;
	}

	public LocalTime getFermeture() {
		return fermeture;
	}

	public void setFermeture(LocalTime fermeture) {
		this.fermeture = fermeture;
	}
	
	@Override
	public String toString() {
		return "Horaire : " + jour + " de " + ouverture + " à " + fermeture;
	}
	
	
	
}
