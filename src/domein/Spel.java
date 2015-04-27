package domein;

/* IMPORTS */
import java.util.ArrayList;
import java.util.List;

/**
 * Staat in voor het starten en beheren van een Spel.
 *
 * @author Yves
 */
public class Spel
{

    private final List<Spelbord> spelborden;
    private final String spelNaam;
    private Spelbord huidigSpelbord;

    /**
     * Default-constructor initialiseert een ArrayList van Spelbord-objecten.
     *
     * @param spelNaam Naam van het spel.
     */
    public Spel(String spelNaam)
    {
        this.spelNaam = spelNaam;
        spelborden = new ArrayList<>();
    }

    /**
     * Constructor stelt een Spel in met een spelNaam en een List van Spelbord.
     *
     * @param spelNaam Naam van het spel.
     * @param spelborden List van Spelbord-objecten.
     */
    public Spel(String spelNaam, List<Spelbord> spelborden)
    {
        this.spelNaam = spelNaam;
        this.spelborden = spelborden;
    }
    
    /**
     * Deze methode loopt door de aanwezige spelborden en stelt het huidige
     * spelbord in als dit nog niet is voltooid.
     */
    public void start()
    {
        for (Spelbord bord : spelborden)
        {
            if (!bord.isVoltooid())
            {
                this.setHuidigSpelbord(bord);
                break;
            }
        }
    }

    public int geefAantalVoltooideBorden()
    {
        int aantal = 0;
        for (Spelbord bord : spelborden)
        {
            if (bord.isVoltooid())
            {
                aantal++;
            }
        }
        return aantal;
    }

    /**
     * Geeft de naam van het spel.
     *
     * @return Geeft de naam van het spel terug als een String.
     */
    public String getSpelNaam()
    {
        return this.spelNaam;
    }

    /**
     * Geeft het aantal spelborden.
     *
     * @return Het aantal spelborden als int.
     */
    public int geefAantalSpelborden()
    {
        return spelborden.size();
    }

    /**
     * Geeft het huidig ingestelde spelbord terug
     *
     * @return Huidig ingestelde Spelbord-object
     */
    public Spelbord getHuidigSpelbord()
    {
        return this.huidigSpelbord;
    }

    /**
     * Instellen van het huidige spelbord
     *
     * @param spelbord Spelbord-object dat in te stellen valt als huidig
     * spelbord.
     */
    private void setHuidigSpelbord(Spelbord spelbord)
    {
        this.huidigSpelbord = spelbord;
    }

    /**
     * Geeft het huidigSpelbord object terug als String.
     *
     * @return huidigSpelbord als String.
     */
    public String spelbordToString()
    {
        return huidigSpelbord.toString();
    }

    
    /*
    TOEGEVOEGD VOOR UC5!!!
    */
    public Spelbord maakLeegSpelbord()
    {
        return null;
    }

    public String geefSpelNaam()
    {
        // TODO - implement Spel.geefSpelNaam
        throw new UnsupportedOperationException();
    }
}
