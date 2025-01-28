package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import bll.RestaurantBLL;
import bo.Restaurant;
import exceptions.RestaurantException;


public class TestAdmin {
	private static Scanner scan;
	private static RestaurantBLL restaurantBLL = new RestaurantBLL();

	public static void main(String[] args) {
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
				break;
			case 3:
				break;
			case 4:
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
	            if (nom.equalsIgnoreCase("Menu")) {
	            	System.out.println("Retour au menu principal... Entrée pour continuer.");
	            	return;
	            }
	            
	            System.out.println("Adresse du restaurant : ");
	            String adresse = scan.nextLine();
	            if (adresse.equalsIgnoreCase("Menu")) {
	            	System.out.println("Retour au menu principal... Entrée pour continuer.");
	            	return;
	            }
	            
	            System.out.println("Ajouter une image du restaurant (URL) ou passez avec Entrée : ");
	            String url_image = scan.nextLine();
	            if (url_image.equalsIgnoreCase("Menu")) {
	            	System.out.println("Retour au menu principal... Entrée pour continuer.");
	            	return;
	            }
	            
	            Restaurant restaurant = restaurantBLL.insert(nom, adresse, url_image);
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

}