/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author valleta
 */
public enum Couleur {
    PIQUE,
    COEUR,
    CARREAU,
    TREFLE;
    
    public String getNom()
    {
        String aretourner = "";
        aretourner += this.toString().toLowerCase();
        return aretourner;
    }
}
