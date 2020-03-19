/*
Classe Joueur : est utilisée dans la classe Belote pour représenter les 4 joueurs de la partie.

*/
public class Joueur {
    
    // Nom d'un joueur
    public String nom;
    // Carte du joueur
    public Carte carte;
    // Points du joueur (il s'accumulent au fur et a mesure des tous de la parties)
    public int points;
    
    // Constructeur : 
    public Joueur(String nom)
    {
        this.nom = nom;
        this.points = 0;
    }
    public void donnerCarte(Carte carte)
    {
        this.carte = carte;
    }
    
    public Carte joue()
    {
      return this.carte;
    }
    
    // Ajoute aux point courants les points gagnés lors d'un pli
    public void remporte(int points) 
    {
        if(points >= 0){
                    this.points += points;
        }
    }
    
}
