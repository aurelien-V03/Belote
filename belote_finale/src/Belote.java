/*
Classe Belote : contient la partie (4 Joueurs + atout + couleur demandee) et des methodes pour gerer la partie 
 */
public class Belote {

    // 4 joueurs de la partie
    public Joueur joueurNord;
    public Joueur joueurOuest;
    public Joueur joueurSud;
    public Joueur joueurEst;

    public Couleur atout;
    // couleur jouee par le premier joueur (joueur Nord) a chaque tour
    public Couleur demandee;

    // constructeur : instanciation des 4 joueurs + MAJ de l'attribut atout
    public Belote(Joueur nord, Joueur ouest, Joueur sud, Joueur est) {
        this.joueurNord = nord;
        this.joueurOuest = ouest;
        this.joueurSud = sud;
        this.joueurEst = est;
        this.changeAtout();

    }

    // Distribue 1 carte pour chaque joueur
    // vérifie (une fois par joueur), que chaque joueur n'a pas une carte exactement pareille que l'un des joueurs précédents sinon on redonne une carte
    public void distribue() {

        // Joueur Nord recoit 1 carte
        this.joueurNord.donnerCarte(new Carte());

        // Joueur Est recoit 1 carte
        this.joueurEst.donnerCarte(new Carte());
        if (this.joueurEst.carte.egale(this.joueurNord.carte)) {
            this.joueurEst.donnerCarte(new Carte());
        }

        // Joueur Sud recoit 1 carte // Joueur Est recoit 1 carte
        this.joueurSud.donnerCarte(new Carte());
        if (this.joueurSud.carte.egale(this.joueurEst.carte) || this.joueurSud.carte.egale(this.joueurNord.carte)) {
            this.joueurSud.donnerCarte(new Carte());
        }

        // Joueur Ouest recoit 1 carte
        this.joueurOuest.donnerCarte(new Carte());
        if (this.joueurOuest.carte.egale(this.joueurEst.carte) || this.joueurOuest.carte.egale(this.joueurNord.carte) || this.joueurOuest.carte.egale(this.joueurSud.carte)) {
            this.joueurOuest.donnerCarte(new Carte());

        }

    }

    public Carte joueurJoue(Joueur joueur) {
        return joueur.carte;
    }

    // Determine qui remporte le tour entre les 4 joueurs
    // On compare 2 a 2 les joueurs grace a la methode remporte()
    public Joueur gagnant() {
        Joueur j;
        j = this.remporte(this.joueurNord, this.joueurEst);
        j = this.remporte(j, this.joueurSud);
        j = this.remporte(j, this.joueurOuest);
        return j;
    }
    // affiche dans la console les mains (nom des cartes) de chaque joueur

    public void afficheMains() {
        System.out.println("carte joueur nord : " + this.joueurNord.nom + this.joueurNord.carte.getNom() + "\n");
        System.out.println("carte joueur ouest : " + this.joueurOuest.nom + this.joueurOuest.carte.getNom() + "\n");
        System.out.println("carte joueur sud : " + this.joueurSud.nom + this.joueurSud.carte.getNom() + "\n");
        System.out.println("carte joueur est : " + this.joueurEst.nom + this.joueurEst.carte.getNom() + "\n");
    }

    // tirera une couleur au hasard pour mettre à jour l'attribut atout. 
    // Cette méthode ne sera appelée qu'une seule fois par partie, c'est-à-dire à la fin du constructeur de Belote.
    // 0.00 --> 0.24 = CARREAU
    // 0.25 --> 0.49 = COEUR
    // 0.50 --> 0.74 = PIQUE
    // 0.75 --> 1.00 = TREFLE
    public void changeAtout() {
        double atoutRandom = Math.random();
        if (atoutRandom < 0.25d) {
            this.atout = Couleur.CARREAU;
        } else if (atoutRandom < 0.5) {
            this.atout = Couleur.COEUR;
        } else if (atoutRandom < 75) {
            this.atout = Couleur.PIQUE;
        } else {
            this.atout = Couleur.TREFLE;
        }
    }

    // determine de joueur1 ou joueur2 a la carte la plus forte en tenant compte de l'atout et de la couleur demande
    public Joueur remporte(Joueur joueur1, Joueur joueur2) {
        Joueur aretourner;

        // les 2 cartes on la couleur de l'atout, on compare les valeurs de l'atout
        // ATTENTION : les cartes 7 et 8 ont la meme valeur ! le 8 est plus fort que le 7 
        if (joueur1.carte.c == this.atout && joueur2.carte.c == this.atout) {
            // Joueur1 = 7 OU 8 ET Joueur2 = 7 OU 8 MAIS ONT DES CARTES DIFFERENTES
            if ((joueur1.carte.v == Valeur.HUIT || joueur1.carte.v == Valeur.SEPT) && (joueur2.carte.v == Valeur.HUIT || joueur2.carte.v == Valeur.SEPT) && (joueur1.carte.v != joueur2.carte.v)) {
                aretourner = joueur1.carte.v.ordinal() > joueur2.carte.v.ordinal() ? joueur1 : joueur2;
            } else {
                if (joueur1.carte.v.valeurAtout > joueur2.carte.v.valeurAtout) {
                    aretourner = joueur1;
                } else {
                    aretourner = joueur2;
                }
            }

        } // joueur1 a atout mais pas joueur2
        else if (joueur1.carte.c == this.atout && joueur2.carte.c != this.atout) {
            aretourner = joueur1;
        } // joueur2  a atout mais pas joueur1
        else if (joueur1.carte.c != this.atout && joueur2.carte.c == this.atout) {
            aretourner = joueur2;
        } // les 2 cartes sont de la couleur DEMANDEE
        // ATTENTION : les cartes 7, 8 et 9 ont la meme valeur (0), cependant 7 < 8 < 9
        else if (joueur1.carte.c == this.demandee && joueur2.carte.c == this.demandee) {

            // Joueur1.carte = 7 OU 8 OU 9 ET Joueur2.carte = 7 OU 8 OU 9 ET CARTES DIFFERENTES !
            if ((joueur1.carte.v == Valeur.HUIT || joueur1.carte.v == Valeur.SEPT || joueur1.carte.v == Valeur.NEUF) && (joueur2.carte.v == Valeur.HUIT || joueur2.carte.v == Valeur.SEPT || joueur2.carte.v == Valeur.NEUF) && (joueur1.carte.v != joueur2.carte.v)) {
                aretourner = joueur1.carte.v.ordinal() > joueur2.carte.v.ordinal() ? joueur1 : joueur2;
                System.out.println(" 8 7 ET 9" + joueur1.carte.getNom() + joueur2.carte.getNom());
            }

            if (joueur1.carte.v.valeur > joueur2.carte.v.valeur) {
                aretourner = joueur1;
            } else {
                aretourner = joueur2;
            }
        }// carte de joueur1 est de la couleur DEMANDEE mais pas joueur2
        else if (joueur1.carte.c == this.demandee && joueur2.carte.c != this.demandee) {
            aretourner = joueur1;
        } else {
            aretourner = joueur2;
        }

        return aretourner;
    }

    // compte et renvoie les point du plis courant (somme des 4 cartes)
    public int points() {
        int pointsPlis = 0;

        int pointsJoueur1 = 0;
        int pointsJoueur2 = 0;
        int pointsJoueur3 = 0;
        int pointsJoueur4 = 0;

        // POINTS DE JOUEUR NORD
        if (this.joueurNord.carte.c == this.atout) {
            pointsJoueur1 = this.joueurNord.carte.v.valeurAtout;
        } // les points sont calculés avec la couleur demandee
        else {
            pointsJoueur1 = this.joueurNord.carte.v.valeur;
        }

        // POINTS DE JOUEUR EST   
        if (this.joueurEst.carte.c == this.atout) {
            pointsJoueur2 = this.joueurEst.carte.v.valeurAtout;
        } // les points sont calculés avec la couleur demandee
        else {
            pointsJoueur2 = this.joueurEst.carte.v.valeur;
        }

        // POINTS DE JOUEUR SUD
        if (this.joueurSud.carte.c == this.atout) {
            pointsJoueur3 = this.joueurSud.carte.v.valeurAtout;
        } // les points sont calculés avec la couleur demandee
        else {

            pointsJoueur3 = this.joueurSud.carte.v.valeur;
        }

        // POINTS DE JOUEUR OUEST
        if (this.joueurOuest.carte.c == this.atout) {
            pointsJoueur4 = this.joueurOuest.carte.v.valeurAtout;
        } // les points sont calculés avec la couleur demandee
        else {

            pointsJoueur4 = this.joueurOuest.carte.v.valeur;
        }

        pointsPlis = pointsJoueur1 + pointsJoueur2 + pointsJoueur3 + pointsJoueur4;
        return pointsPlis;
    }

    // renvois sous la forme d'un string la description de chaque tour (ATOUT + COULEUR DEMANDEE + DESCRIPTION DES 4 JOUEURS)
    public String mains() {
        String aretourner = "<html>";
        aretourner += "La couleur d'atout es " + this.atout + "<br/>";
        aretourner += "La couleur demandée est " + this.demandee + "</br>";
        aretourner += "Le joueur Nord " + this.joueurNord.nom + " a un(e) " + this.joueurNord.carte.getNom() + "</br>";
        aretourner += "Le joueur Ouest  " + this.joueurOuest.nom + " a un(e) " + this.joueurOuest.carte.getNom() + "</br>";
        aretourner += "Le joueur Sud " + this.joueurSud.nom + " a un(e) " + this.joueurSud.carte.getNom() + "</br>";
        aretourner += "Le joueur Est " + this.joueurEst.nom + " a un(e) " + this.joueurEst.carte.getNom() + "</br>";
        aretourner += "</html>";
        return aretourner;
    }

}
