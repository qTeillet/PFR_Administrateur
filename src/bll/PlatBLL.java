package bll;

import java.util.List;

import bo.Carte;
import bo.Plat;
import dal.PlatDAO;
import exceptions.CarteException;
import exceptions.PlatException;

public class PlatBLL {
	
	private PlatDAO dao = new PlatDAO();
	
	public List<Plat> select(int idCarte) {
		return dao.select(idCarte);
	}
	

    //insert directement avec un plat parce que c'est plus simple
    public void insert(Plat plat) throws PlatException {

        checkPlat(plat);

        dao.insert(plat);
    }

    public void associerPlatCarte (Plat plat, Carte carte) throws PlatException, CarteException {

        checkPlat(plat);
        CarteBLL carteBll = new CarteBLL();

        carteBll.checkCarte(carte);

        PlatDAO dao = new PlatDAO();
        dao.associerPlatCarte(plat, carte);
    }


    public void checkPlat(Plat plat) throws PlatException {
        if(plat.getNom() == null || plat.getNom().isBlank()){
            throw new PlatException("Le nom doit être renseigné !");
        }

        if(plat.getNom().length() > 30){
            throw new PlatException("Le nom du plat ne doit pas faire plus de 30 caractères !");
        }

        if (plat.getDescription().length() > 255){
            throw new PlatException("La description du plat ne doit pas faire plus de 255 caractères !");
        }
    }

}
