package controller;

import bll.CarteBLL;
import exceptions.CarteException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TestAdmin {
	private static Scanner scan;

	public static void main(String[] args) {
		scan = new Scanner(System.in);
		System.out.println("Bienvenue sur l'application de gestion de vos restaurants.\n");
		int choix;
		
		do {
			choix = afficherMenu();
			
			switch(choix) {
			case 1:
				break;
			case 2:
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
		}catch (CarteException e){
			System.out.println("Erreur lors de la création de la carte : " + e.getMessage());
		}


	}

}
