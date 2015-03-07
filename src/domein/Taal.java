package domein;

import java.util.ResourceBundle;

/**
 * Deze klasse staat in voor het kiezen van een taal.
 * @author Michiel
 */
public class Taal
{
    private ResourceBundle resourceBundle;
    
    /**
     * methode om de taalkeuze in te stellen.
     * @param locale integer die gebruikt wordt om juiste taal te bepalen.
     */
    public void setTaalKeuze(int locale)
    {
    switch (locale) //resourcebundle selecteren op basis van genomen keuze
        {
            case 1: this.resourceBundle = ResourceBundle.getBundle("resources.Resources_nl_BE");
            break;
            case 2: this.resourceBundle = ResourceBundle.getBundle("resources.Resources_en_UK");
            break;
            case 3: this.resourceBundle = ResourceBundle.getBundle("resources.Resources_fr_BE");
            break;
            default: this.resourceBundle = ResourceBundle.getBundle("resources.Resources_nl_BE");
            break; 
        }
    }
    
    /**
     * Teruggeven van een string adhv een gekozen key.
     * @param key de key selecteert een vertaalde String uit de ingestelde taal.
     * @return geeft de string terug die overeenkomt met de key.
     */
    public String getStringUitBundle(String key)
    {
        return resourceBundle.getString(key);
    }
}