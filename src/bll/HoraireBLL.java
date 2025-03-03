package bll;

import java.time.LocalTime;
import java.util.List;

import bo.Horaire;
import dal.HoraireDAO;
import exceptions.HoraireException;

public class HoraireBLL {
	
	private HoraireDAO dao = new HoraireDAO();
	
	public List<Horaire> select() {
		return dao.select();
	}
	
	public Horaire insert(String jour, LocalTime ouverture, LocalTime fermeture) throws HoraireException {
			Horaire horaire = new Horaire(jour, ouverture, fermeture);
			checkHoraireInsert(horaire);
			dao.insert(horaire);
			return horaire; 
	}
	
	public void update(Horaire horaire) throws HoraireException {
		checkHoraireUpdate(horaire);
		dao.update(horaire);
	}

	private void checkHoraireInsert(Horaire horaire) throws HoraireException {
		// TO DO
	}
	
	private void checkHoraireUpdate(Horaire horaire) throws HoraireException {
		// TO DO	
	}
	
	public void delete(int id) {
		dao.delete(id);
	}
}
