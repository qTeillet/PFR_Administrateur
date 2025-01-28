package bll;

import bo.Restaurant;
import dal.RestaurantDAO;
import exceptions.RestaurantException;



public class RestaurantBLL {
	
	private RestaurantDAO dao = new RestaurantDAO();
	
	public Restaurant insert(String nom, String adresse, String url_image) throws RestaurantException {
		Restaurant restaurant = new Restaurant(nom, adresse, url_image);
		checkRestaurant(restaurant);

		dao.insert(restaurant);
		
		return restaurant;
	}

	private void checkRestaurant(Restaurant restaurant) throws RestaurantException {
		if (restaurant.getNom() == null || restaurant.getNom().isBlank()) {
			throw new RestaurantException("Le nom du restaurant doit être renseigné. Entrée pour continuer");
		}
		
		if (restaurant.getAdresse() == null || restaurant.getAdresse().isBlank() ) {
			throw new RestaurantException("L'adresse du restaurant doit être renseignée. Entrée pour continuer");
		}
		
		if (restaurant.getNom().length() > 30) {
			throw new RestaurantException("Le nom doit faire au maximum 30 caractères. Entrée pour continuer");
		}
		
		if (restaurant.getAdresse().length() > 255) {
			throw new RestaurantException("L'adresse doit faire au maximum 255 caractères. Entrée pour continuer");
		}
		
		if (restaurant.getUrl_image().length() > 255) {
			throw new RestaurantException("L'URL de l'image doit faire au maximum 255 caractères. Entrée pour continuer");
		}
	}
}
