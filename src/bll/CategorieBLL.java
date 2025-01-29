package bll;
import java.util.List;

import bo.Categorie;
import dal.CategorieDAO;
import exceptions.CategorieException;

public class CategorieBLL {

	private List<Categorie> categoriesBLL;
	
	public CategorieBLL(CategorieDAO categorieDAO) {
		this.categoriesBLL = categorieDAO.select();
	}
	
	public Categorie checkCategorie(Categorie categorie) throws CategorieException {
		
		
		if (categorie.getLibelle() == null || categorie.getLibelle().isBlank()) {
            throw new CategorieException("Le libellé de la catégorie ne peut pas être vide.");
        }
		for (Categorie current : categoriesBLL) {
            if (categorie.getLibelle().equalsIgnoreCase(current.getLibelle())) {
                return current;
            }
        }
		
		throw new CategorieException(
            "La catégorie doit être une des valeurs suivantes : " + categoriesBLL + ". Valeur saisie : " + categorie.getLibelle()
        );
	}
}

