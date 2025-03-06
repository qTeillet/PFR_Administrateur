package controller;


import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import bll.PlatBLL;
import bll.RestaurantBLL;
import bo.Carte;

import bo.Categorie;
import bo.Horaire;
import bo.Plat;

import bo.Restaurant;
import exceptions.PlatException;
import exceptions.RestaurantException;

import bll.CarteBLL;
import bll.HoraireBLL;
import exceptions.CarteException;
import exceptions.HoraireException;


public class TestAdmin {
	private static Scanner scan;

	private static RestaurantBLL restaurantBLL = new RestaurantBLL();
	private static CarteBLL carteBLL = new CarteBLL();
	private static PlatBLL platBLL = new PlatBLL();
	private static HoraireBLL horaireBLL = new HoraireBLL();


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
				supprimerRestaurant();
				break;
			case 4:
				sousMenuCreationCarte();
				break;
			case 5: 
				sousMenuModificationCarte(); 
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
	
	// CASE 1
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
	         
	         List<Horaire> horaires = new ArrayList<>();
	         while (true) {
	             System.out.println("Voulez-vous ajouter un horaire ? (oui/non)");
	             String reponse = scan.nextLine();
	             if (reponse.equalsIgnoreCase("non")) {
	                 break;
	             } else if (reponse.equalsIgnoreCase("oui")) {
	                 System.out.println("Entrez le jour de la semaine (par exemple, Lundi) : ");
	                 String jour = scan.nextLine();
	                 if (estMenu(jour)) {
	                     return;
	                 }
	                 
	                 boolean jourExistant = horaires.stream().anyMatch(h -> h.getJour().equalsIgnoreCase(jour));
	                 if (jourExistant) {
	                     System.out.println("Ce jour est déjà enregistré pour un horaire. Veuillez choisir un autre jour.");
	                     continue; 
	                 }

	                 System.out.println("Entrez l'heure d'ouverture (format HH:mm) : ");
	                 String ouvertureStr = scan.nextLine();
	                 if (estMenu(ouvertureStr)) {
	                     return;
	                 }
	                 LocalTime ouverture = LocalTime.parse(ouvertureStr);

	                 System.out.println("Entrez l'heure de fermeture (format HH:mm) : ");
	                 String fermetureStr = scan.nextLine();
	                 if (estMenu(fermetureStr)) {
	                     return;
	                 }
	                 LocalTime fermeture = LocalTime.parse(fermetureStr);

	                 Horaire horaire = new Horaire(jour, ouverture, fermeture);
	                 try {
	                     horaireBLL.insert(horaire, restaurant.getId());
	                 } catch (HoraireException e) {
	                     e.printStackTrace();
	                 }
	                 horaires.add(horaire);
	             } else {
	                 System.out.println("Réponse non valide, veuillez entrer 'oui' ou 'non'.");
	             }
	         }
	         
	         restaurant.setHoraires(horaires);
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
	
	// CASE 2
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
		         
		         System.out.println("Carte actuelle : " + restaurant.getCarte().getId() + " - " + restaurant.getCarte().getNom() + ". \nEntrez l'ID de la nouvelle carte, ou passez avec Entrée : ");
			        String idCarte = scan.nextLine();
			        Carte carte = restaurant.getCarte();
			         
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
			       	
		            List<Horaire> horaires = horaireBLL.selectHorairesByRestaurantId(restaurant.getId());
		            System.out.println("Horaires actuels :");
		            if (horaires.isEmpty()) {
		                System.out.println("Aucun horaire enregistré.");
		            } else {
		                for (Horaire h : horaires) {
		                    System.out.println(h.getJour() + " : " + h.getOuverture() + " - " + h.getFermeture());
		                }
		            }

		            while (true) {
		                System.out.println("Souhaitez-vous ajouter ou modifier des horaires ? (ajouter/modifier/non)");
		                String reponse = scan.nextLine();
		                if (reponse.equalsIgnoreCase("non")) {
		                    break;
		                } else if (reponse.equalsIgnoreCase("ajouter")) {
		                    System.out.print("Entrez le jour de la semaine (par exemple, Lundi) : ");
		                    String jour = scan.nextLine();
		                    if (estMenu(jour)) {
		                        return;
		                    }

		                    boolean jourExistant = horaires.stream().anyMatch(h -> h.getJour().equalsIgnoreCase(jour));
		                    if (jourExistant) {
		                        System.err.println("Ce jour est déjà enregistré. Veuillez choisir un autre jour.");
		                        continue;
		                    }

		                    System.out.print("Entrez l'heure d'ouverture (format HH:mm) : ");
		                    String ouvertureStr = scan.nextLine();
		                    if (estMenu(ouvertureStr)) {
		                        return;
		                    }
		                    LocalTime ouverture = LocalTime.parse(ouvertureStr);

		                    System.out.print("Entrez l'heure de fermeture (format HH:mm) : ");
		                    String fermetureStr = scan.nextLine();
		                    if (estMenu(fermetureStr)) {
		                        return;
		                    }
		                    LocalTime fermeture = LocalTime.parse(fermetureStr);

		                    Horaire horaire = new Horaire(jour, ouverture, fermeture);
		                    try {
		                        horaireBLL.insert(horaire, restaurant.getId());
		                    } catch (Exception e) {
		                        e.printStackTrace();
		                    }
		                    horaires.add(horaire);
		                } else if (reponse.equalsIgnoreCase("modifier")) {
		                    System.out.print("Entrez le jour de la semaine à modifier (par exemple, Lundi) : ");
		                    String jourAModifier = scan.nextLine();
		                    if (estMenu(jourAModifier)) {
		                        return;
		                    }

		                    Horaire horaireAModifier = horaires.stream()
		                            .filter(h -> h.getJour().equalsIgnoreCase(jourAModifier))
		                            .findFirst()
		                            .orElse(null);

		                    if (horaireAModifier != null) {
		                        System.out.println("Horaire actuel : " + horaireAModifier.getOuverture() + " - " + horaireAModifier.getFermeture());

		                        System.out.print("Entrez la nouvelle heure d'ouverture (format HH:mm) : ");
		                        String nouvelleOuverture = scan.nextLine();
		                        if (estMenu(nouvelleOuverture)) {
		                            return;
		                        }
		                        LocalTime nouvelleOuvertureTime = LocalTime.parse(nouvelleOuverture);

		                        System.out.print("Entrez la nouvelle heure de fermeture (format HH:mm) : ");
		                        String nouvelleFermeture = scan.nextLine();
		                        if (estMenu(nouvelleFermeture)) {
		                            return;
		                        }
		                        LocalTime nouvelleFermetureTime = LocalTime.parse(nouvelleFermeture);

		                        horaireAModifier.setOuverture(nouvelleOuvertureTime);
		                        horaireAModifier.setFermeture(nouvelleFermetureTime);

		                        try {
		                            horaireBLL.update(horaireAModifier);
		                        } catch (Exception e) {
		                            e.printStackTrace();
		                        }
		                    } else {
		                        System.err.println("Aucun horaire trouvé pour ce jour.");
		                    }
		                } else {
		                    System.out.println("Réponse non valide, veuillez entrer 'ajouter', 'modifier' ou 'non'.");
		                }
		            }

		    restaurant.setHoraires(horaires);
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
    		List<Horaire> horaires = horaireBLL.selectHorairesByRestaurantId(current.getId());
            current.setHoraires(horaires); 
            System.out.println(current);
        }
    
    	
    }
	
	// CASE 3
	private static void supprimerRestaurant() {
		afficherRestaurants();
		System.out.println("Quel restaurant souhaitez-vous supprimer ? Saisissez son numéro : ");
		int id = scan.nextInt();
		
		Restaurant restaurant = restaurantBLL.select(id);
		scan.nextLine();	
		
		if(restaurant != null) {
			System.err.println("Confirmez-vous la suppression de ce restaurant? oui/non");
			String choix = scan.nextLine();
			
			switch (choix.toLowerCase()) {
            	case "oui":
            		restaurantBLL.delete(id);
            		System.out.println("Restaurant supprimé !");
            		System.out.println("Mise à jour de la liste des restaurants...");
            		afficherRestaurants();
            		break;

            	case "non":
            		System.out.println("Retour au menu principal... Entrée pour continuer.");
            		scan.nextLine();
            		break;

            	default:
            		System.out.println("Choix invalide, retour au menu principal.");

			}
		} else {
			System.err.println("Il n'y a pas de restaurant enregistré avec cet identifiant.");
		}
	}
	
	// CASE  4
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
	
	// CASE 5
	private static int sousMenuModificationCarte() {
		List<Carte> cartes = carteBLL.select();
		int choixCarte = -1;
		int choixMenuPlat;
		
		if (cartes.isEmpty()) {
			System.err.println("Aucun élément à afficher.");
			return -1;
		}
		
		
		
		do {
			System.out.println("\nListe des cartes :\n");
			for (Carte current : cartes) {
				System.out.println(current);
			}
			System.out.println("Quelle carte souhaitez-vous modifier ?");
			
	        if (scan.hasNextInt()) {
	            choixCarte = scan.nextInt(); 
	            scan.nextLine();
	            
	            boolean carteExiste = false;
	            // Vérification si l'ID existe dans la liste des cartes
	            for (Carte carte : cartes) {
	                if (carte.getId() == choixCarte) {
	                    carteExiste = true;
	                    break;
	                }
	            }
	            
	            if (carteExiste) {
	                afficherPlatsCarte(choixCarte);
	                do {
	                	choixMenuPlat = afficherMenuPlat(choixCarte);
	                	
	                	switch(choixMenuPlat) {
	                	case 1 : afficherMenuPlatAjout(choixCarte); break;
	                	case 2 : afficherMenuPlatModifier(); break;
	                	case 3 : afficherMenuPlatSuppression(); break;
	                	}
	                } while (choixMenuPlat != 4);
	            } else {
	                System.err.println("ID de carte invalide.");
	            }
	        } else {
	            System.err.println("Choix invalide. Veuillez entrer un nombre.");
	            scan.next();
	        } 
	    } while (choixCarte == -1);
	    
	    return choixCarte;  
	}
	
	private static void afficherPlatsCarte(int idCarte) {
	    List<Plat> plats = platBLL.select(idCarte); // Récupère les plats associés à la carte sélectionnée

	    if (plats.isEmpty()) {
	        System.out.println("Aucun plat trouvé pour cette carte.");
	    } else {
	        System.out.println("Plats de la carte sélectionnée :");
	        for (Plat plat : plats) {
	            System.out.println(plat); // Affiche chaque plat
	        }
	    }
	}
	
	private static int afficherMenuPlat(int choixCarte) {
		int choix;
		do {
			System.out.println();
			System.out.println("Quelle action souhaitez-vous réaliser ?");
			System.out.println("\t1. Ajouter un nouveau plat à la carte.");
			System.out.println("\t2. Modifier un plat de la carte.");
			System.out.println("\t3. Supprimer un plat de la carte.");
			System.out.println("\t4. Retourner au menu principal");
			
			try {
				choix = scan.nextInt();
			} catch (InputMismatchException e) {
				System.err.println("Choix invalide.");
				choix = -1;
			} finally {
				scan.nextLine();
			}
		} while (choix < 1 || choix > 4);
		return choix;
	}
	
	private static void afficherMenuPlatAjout(int choixCarte) {
		Plat platAAjouter = saisiePlat();
		CarteBLL carteBLL = new CarteBLL();
		Carte carte = carteBLL.select(choixCarte);
		carte.ajouterPlat(platAAjouter);
		try {
			PlatBLL platBLL = new PlatBLL();
			platBLL.insert(platAAjouter);
			platBLL.associerPlatCarte(platAAjouter, carte);
		} catch (PlatException | CarteException e){
			System.err.println("Erreur lors de la création du plat : " + e.getMessage());
		}
		System.out.println("Plat ajouté avec succès.");
		
	}
	
	private static void afficherMenuPlatModifier() {
		// TODO Auto-generated method stub
		
	}
	
	private static void afficherMenuPlatSuppression() {
		// TODO Auto-generated method stub
		
	}

	
	// GENERAL
	private static boolean estMenu(String input) {
		if (input.equalsIgnoreCase("Menu")) {
	        System.out.println("Retour au menu principal... Entrée pour continuer.");
	        return true; 
	    }
	    return false;
	}
}
