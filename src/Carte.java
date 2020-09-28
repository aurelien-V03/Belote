/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author valleta
 */
public class Carte {
    public Couleur c;
    public Valeur v;
    
    public Carte(Couleur c, Valeur v)
    {
        this.c = c;
        this.v = v;
    }
    public Carte()
    {
      double valeur = Math.random();
      double couleur = Math.random();

      //System.out.println(valeur + "\n" + couleur);
      
      if(couleur <= 0.25d )     /* COULEUR */ 
      {
          c = Couleur.CARREAU;
      }
      else if( couleur <= 0.50d)
      {
          c = Couleur.COEUR;
      }
      else if(couleur <= 0.75d)
      {
          c = Couleur.PIQUE;
      }
      else 
          c = Couleur.TREFLE;
      
      if(valeur <= 0.125d)  /* VALEUR */ 
      {
          v = Valeur.AS;
      }
      else if(valeur <= 0.25d)
      {
          v = Valeur.DAME;
      }
      else if(valeur <= 0.375d)
      {
          v = Valeur.DIX;
      }
      else if(valeur <= 0.5d)
      {
          v = Valeur.HUIT;
      }
      else if(valeur <= 0.625d)
      {
       v = Valeur.NEUF; 
      }
      else if (valeur <= 0.75)
      {
          v = Valeur.ROI;
      }
      else if( valeur <= 0.875)
      {
          v = Valeur.SEPT;      
      }
      else
          v = Valeur.VALET;
      
      
      
    }
    
    
    public String getNom()
    {
        return  this.v.getNom() + "-de-" + this.c.getNom() ;
    }
    
    public boolean egale(Carte c)
    {
        boolean aretourner = false;
        /*
        if(this.c.equals(c))
            aretourner = true;
        */
        if(this.c == c.c && this.v == c.v)
            aretourner = true;
        
        
        return aretourner;
    }
    
    public String getFichierImage()
    {
        return this.getNom() + ".png";
    }
   
}
