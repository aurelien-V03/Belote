
/*
cette classe réprésente la valeur d'une carte sans atout et avec atout
*/
public enum Valeur {
    SEPT(0,0),
    HUIT(0,0),
    NEUF(9,14),
    DIX(10,10),
    VALET(2,20),
    DAME(3,3),
    ROI(4,4),
    AS(11,11);
  
    // valeur de la carte quand l'atout n'est pas celui de la carte
     public int valeur;
    // valeur de la carte quand la couleur de la carte est celle de l'atout
     public int valeurAtout;
    
     
     public String getNom()
    {
        String aretourner = "";
        aretourner += this.toString().toLowerCase();
        return aretourner;
    }
     // constructeur : mets a jour les 2 attributs 
     Valeur(int valeur, int valeurAtout)
     {
         this.valeur = valeur;
         this.valeurAtout = valeurAtout;
     }
 
}
