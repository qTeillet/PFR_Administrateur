package bll;

import java.util.List;

import bo.Horaire;
import dal.HoraireDAO;
import exceptions.HoraireException;

public class HoraireBLL {
	
	private HoraireDAO dao = new HoraireDAO();
	
	public List<Horaire> select() {
		return dao.select();
	}
	
	public List<Horaire> selectHorairesByRestaurantId(int restaurantId) {
		return dao.selectHorairesByRestaurantId(restaurantId);
	}
	
	public Horaire insert(Horaire horaire, int idRestaurant) throws HoraireException {
			checkHoraire(horaire);
			dao.insert(horaire, idRestaurant);
			return horaire; 
	}
	
	public void update(Horaire horaire) throws HoraireException {
		checkHoraire(horaire);
		dao.update(horaire);
	}

	private void checkHoraire(Horaire horaire) throws HoraireException {
		if (horaire.getOuverture().isAfter(horaire.getFermeture())) {
			throw new HoraireException("L'heure d'ouverture ne peut pas être après l'heure de fermeture.");
		}
	}
	
	public void delete(int id) {
		dao.delete(id);
	}
}
