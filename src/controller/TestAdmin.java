package controller;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import bll.RestaurantBLL;
import bo.Carte;
import bo.Restaurant;
import exceptions.RestaurantException;

import bll.CarteBLL;
import exceptions.CarteException;



public class TestAdmin {
	private static Scanner scan;

	private static RestaurantBLL restaurantBLL = new RestaurantBLL();
	private static CarteBLL carteBLL = new CarteBLL();

	public static void main(String[] args)  {
		scan = new Scanner(System.in);
		System.out.println("Bienvenue sur l'application de gestion de vos restaurants.\n");
		int choix;
		
		do {
			choix = afficherMenu();
			
			switch(choix) {
			case 1: 
				ajouterRestaurant();
				break;
			case 2:
				modifierRestaurant();
				break;
			case 3:
				break;
			case 4:
				sousMenuCreationCarte();
				break;
			case 5: 
				break;
			}
		} while (choix != 6);
		System.out.println("À bientot !");
		scan.close();
	}

	private static int afficherMenu() {
		int choix;
		do {
	        System.out.println("                  Menu Principal                  ");
	        System.out.println("-".repeat(50) + "\n");
	        System.out.format(" %-7s %s\n", "1.", "Ajouter un restaurant\n");
	        System.out.format(" %-7s %s\n", "2.", "Modifier un restaurant existant\n");
	        System.out.format(" %-7s %s\n", "3.", "Supprimer un restaurant\n");
	        System.out.format(" %-7s %s\n", "4.", "Créer une carte\n");
	        System.out.format(" %-7s %s\n", "5.", "Modifier une carte\n");
	        System.out.format(" %-7s %s\n", "6.", "Quitter l'application\n");
	        System.out.println("-".repeat(50) + "\n");
	        System.out.println("Entrez votre choix (1-6): ");
	        
	        try {
	        	choix = scan.nextInt();
	        } catch (InputMismatchException e) {
	        	System.err.println("Choix invalide.");
	        	choix = -1;
	        } finally {
				scan.nextLine();
			}
		} while (choix < 1 || choix > 6);
		return choix;
	}
	
	public static void ajouterRestaurant() {
		 try {
			System.out.println("Pour créer un nouveau restaurant, saisissez les informations suivantes : ");
	        System.out.println("Pour retourner au menu à tout moment, saissisez 'Menu'");
	            
	        System.out.println("Nom du restaurant : ");
	        String nom = scan.nextLine();
	        if (estMenu(nom)) {
	        	 return;
	        }
	            
	        System.out.println("Adresse du restaurant : ");
	        String adresse = scan.nextLine();
	        if (estMenu(adresse)) {
	        	 return;
	        }
	            
	        System.out.println("Ajouter une image du restaurant (URL) ou passez avec Entrée : ");
	        String url_image = scan.nextLine();
	        if (estMenu(url_image)) {
	        	 return;
	        }
	         
	        System.out.println("Entrez l'ID de la carte du restaurant à associer, ou passez avec Entrée : ");
	        String idCarte = scan.nextLine();
	        Carte carte = null;
	         
	       	while (!idCarte.isBlank()) {
	        	 if (estMenu(idCarte)) {
		        	 return;
		         } else {
		        	 try {
			        	 int id_carte = Integer.parseInt(idCarte);
			        	 carte = carteBLL.select(id_carte);
			        	 
			        	 if(carte == null ) {
			        		 System.err.println("Il n'y a pas de carte enregistrée avec cet identifiant. Réessayez ou passez avec Entrée.");
			                 idCarte = scan.nextLine();
			        	 } else {
			        		 break;
			        	 }
		        	 } catch (NumberFormatException e) {
		        		 System.err.println("L'ID de la carte doit être un nombre valide. Réessayez ou passez avec Entrée.");
		                 idCarte = scan.nextLine();
		                 break;
		        	 }
		         }
	         }
	            
	         Restaurant restaurant = restaurantBLL.insert(nom, adresse, url_image, carte);
		     System.out.println("Ajout du restaurant réussi !");
		     System.out.println(restaurant);
		     System.out.println("Entrée pour retourner au menu principal.");

		 } catch (RestaurantException e) {
			 System.err.println("La création du restaurant à échoué :");
			 System.err.println(e.getMessage());
	     } finally {
	    	 scan.nextLine();
		}
	}
	
	public static void modifierRestaurant()  {
		try {
			afficherRestaurants();
			System.out.println("Quel restaurant souhaitez-vous modifier ? Saisissez son numéro : ");
			int id = scan.nextInt();
			Restaurant restaurant = restaurantBLL.select(id);
			scan.nextLine();
			
			if(restaurant != null) {
				System.out.println("Pour retourner au menu à tout moment, saissisez 'Menu'");
				
				System.out.print("Nom actuel : " + restaurant.getNom()+". \nSaisissez un nouveau nom ou passez avec Entrée :");
	            String nom = scan.nextLine();
		         if (estMenu(nom)){;
		        	 return;
		         }
	            
		            
		         System.out.println("Adresse actuelle : " + restaurant.getAdresse()+". \nSaisissez une nouvelle adresse ou passez avec Entrée :");
		         String adresse = scan.nextLine();
		         if (estMenu(adresse)) {
		        	 return;
		         }
		            
		         System.out.println("Image actuelle : " + restaurant.getUrl_image() + ". \nAjoutez une nouvelle image du restaurant (URL) ou passez avec Entrée : ");
		         String url_image = scan.nextLine();
		         if (estMenu(url_image)) {
		        	 return;
		         }
		         
		         System.out.println("Entrez l'ID de la carte du restaurant à associer, ou passez avec Entrée : ");
			        String idCarte = scan.nextLine();
			        Carte carte = null;
			         
			       	while (!idCarte.isBlank()) {
			        	 if (estMenu(idCarte)) {
				        	 return;
				         } else {
				        	 try {
					        	 int id_carte = Integer.parseInt(idCarte);
					        	 carte = carteBLL.select(id_carte);
					        	 
					        	 if(carte == null ) {
					        		 System.err.println("Il n'y a pas de carte enregistrée avec cet identifiant. Réessayez ou passez avec Entrée.");
					                 idCarte = scan.nextLine();
					        	 } else {
					        		 break;
					        	 	}
				        	 } catch (NumberFormatException e) {
				        		 System.err.println("L'ID de la carte doit être un nombre valide. Réessayez ou passez avec Entrée.");
				                 idCarte = scan.nextLine();
				                 break;
				        	 }
				         }
			       	}
			       	
			       	if(!nom.isBlank()){
	                    restaurant.setNom(nom);
	                }
			       	if(!adresse.isBlank()){
	                    restaurant.setAdresse(adresse);
	                }
			       	if(!url_image.isBlank()){
	                    restaurant.setUrl_image(url_image);
	                }
			       	if(carte != null){
	                    restaurant.setCarte(carte);
	                }
			       	
			 
			restaurantBLL.update(restaurant);
			System.out.println("Restaurant mis à jour !");
			System.out.println("Mise à jour de la liste des restaurants...");
			afficherRestaurants();
			System.out.println("Entrée pour retourner au menu principal.");
			}
			} catch (RestaurantException e) {
				 System.err.println("La création du restaurant à échoué :");
				 System.err.println(e.getMessage());
		     } finally {
		    	 scan.nextLine();
			}
	}
	
    public static void afficherRestaurants() {
    	List<Restaurant> restaurants = restaurantBLL.select();
    	System.out.println("Liste des restaurants :");
    	for (Restaurant current : restaurants) {
    		System.out.println(current);
    	}
    }
  
	private static void sousMenuCreationCarte(){
		String nom;
		String description;

		System.out.println("Nom de la carte :");
		nom = scan.nextLine();
		System.out.println("Description de la carte :");
		description = scan.nextLine();

		CarteBLL bll = new CarteBLL();
		try{
			bll.insert(nom, description);
			System.out.println("Carte créée avec succès !");
		} catch (CarteException e){
			System.out.println("Erreur lors de la création de la carte : " + e.getMessage());
		}
	}
	
	private static boolean estMenu(String input) {
		if (input.equalsIgnoreCase("Menu")) {
	        System.out.println("Retour au menu principal... Entrée pour continuer.");
	        return true; 
	    }
	    return false;
	}
}