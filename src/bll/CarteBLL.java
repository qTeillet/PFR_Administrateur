package bll;

import java.util.List;

import bo.Carte;
import dal.CarteDAO;
import exceptions.CarteException;

public class CarteBLL {
	private CarteDAO dao = new CarteDAO();
	
	
	public Carte select(int id) {
		CarteDAO dao = new CarteDAO();
		return dao.select(id);	
	}
	
	public List<Carte> select() {
		return dao.select();
	}
	
    public Carte insert(String nom, String description) throws CarteException {

        Carte carte = new Carte(nom, description);

        checkCarte(carte);

        dao.insert(carte);
        return carte;
    }

    public void checkCarte(Carte carte) throws CarteException {
        if(carte.getNom() == null || carte.getNom().isBlank()){
            throw new CarteException("Le nom de la carte doit être renseigné !");
        }

        if (carte.getNom().length() > 30){
            throw new CarteException("Le nom de la carte doit faire moins de 30 caractères !");
        }

        if(carte.getDescription() != null && carte.getDescription().length() > 255){
            throw new CarteException("La longueur de la description doit faire moins de 255 caractères !");
        }
    }

}
