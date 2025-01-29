package controller;


import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import bll.CategorieBLL;
import bll.PlatBLL;
import bll.RestaurantBLL;
import bo.Carte;

import bo.Categorie;
import bo.Plat;

import bo.Restaurant;
import dal.PlatDAO;
import exceptions.PlatException;
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
  
	private static void sousMenuCreationCarte() {
		String nom;
		String description;
		int choix;

		System.out.println("Nom de la carte :");
		nom = scan.nextLine();
		System.out.println("Description de la carte :");
		description = scan.nextLine();

		CarteBLL bll = new CarteBLL();
		try {
			Carte carte = bll.insert(nom, description);
			System.out.println("Carte créée avec succès !");
			do{
				System.out.println("Voulez-vous ajouter un plat à la carte ?");
				System.out.format(" %-7s %s\n", "1.", "Oui\n");
				System.out.format(" %-7s %s\n", "2.", "Non\n");
				try{
					choix = scan.nextInt();
					if (choix == 2){
						continue;
					}
				} catch (InputMismatchException e) {
					System.err.println("Choix invalide.");
					choix = -1;
				} finally {
					scan.nextLine();
				}

				Plat plat = saisiePlat();
				carte.ajouterPlat(plat);
				try {
					PlatBLL platBLL = new PlatBLL();
					platBLL.insert(plat);
					platBLL.associerPlatCarte(plat, carte);
				} catch (PlatException e){
					System.err.println("Erreur lors de la création du plat : " + e.getMessage());
				}
				System.out.println("Plat ajouté avec succès.");

			}while(choix != 2);
			System.out.println("les plats ont tous bien été ajoutés");
		} catch (CarteException e) {
			System.out.println("Erreur lors de la création de la carte : " + e.getMessage());
		}
	}

	private static Plat saisiePlat(){
		Plat plat = new Plat();
		String nom;
		String description;
		float prix;
		int choix;

		System.out.println("Nom du plat à ajouter : ");
		nom = scan.nextLine();
		plat.setNom(nom);

		System.out.println("Description du plat à ajouter : ");
		description = scan.nextLine();
		plat.setDescription(description);

		System.out.println("Prix du plat à ajouter : ");
		prix = scan.nextFloat();
		scan.nextLine();
		plat.setPrix(prix);

		do {
			System.out.println("Catégorie du plat à ajouter : ");
			System.out.format(" %-7s %s\n", "1.", "Entrée\n");
			System.out.format(" %-7s %s\n", "2.", "Plat\n");
			System.out.format(" %-7s %s\n", "3.", "Dessert\n");
			System.out.format(" %-7s %s\n", "4.", "Boisson\n");
			try {
				choix = scan.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide.");
				choix = -1;
			} finally {
				scan.nextLine();
			}
		} while (choix < 1 || choix > 4);

		switch (choix) {
			case 1:
				plat.setCategorie(new Categorie(1, "Entrée"));
				break;
			case 2:
				plat.setCategorie(new Categorie(2, "Plat"));
				break;
			case 3:
				plat.setCategorie(new Categorie(3, "Dessert"));
				break;
			case 4:
				plat.setCategorie(new Categorie(4, "Boisson"));
				break;
		}

		return plat;
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
