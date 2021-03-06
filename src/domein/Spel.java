package domein;

/* IMPORTS */
import exceptions.OngeldigAantalDoelenException;
import exceptions.OngeldigAantalKistenException;
import exceptions.OngeldigAantalMannetjesException;
import exceptions.OngelijkAantalDoelenKistenException;
import java.util.ArrayList;
import java.util.List;

/**
 * Staat in voor het starten en beheren van een Spel.
 *
 * @author Yves
 */
public final class Spel
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

    /**
     * Geeft een lijst terug met daarin alle spelborden voor dit spel.
     * @return List met alle spelborden in dit spel.
     */
    public List<Spelbord> geefSpelborden()
    {
        return spelborden;
    }
    
    /**
     * Geeft het aantal voltooide spelborden terug.
     * @return aantal voltooide spelborden.
     */
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
     * Geeft de naam van het spel terug.
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
     * Initialiseert een nieuw Spelbord-object en stelt dit in als het huidige spelbord.     * 
     */
    public void maakLeegSpelbord()
    {
        Spelbord spelbord = new Spelbord();
        this.setHuidigSpelbord(spelbord);
    }

    /**
     * Voegt het huidige spelbord toe aan dit spel.
     * 
     * @throws exceptions.OngeldigAantalMannetjesException Minder dan 1 mannetje op spelbord.
     * @throws exceptions.OngelijkAantalDoelenKistenException Aantal doelen en kisten is niet gelijk.
     * @throws exceptions.OngeldigAantalKistenException Minder dan 1 kist op spelbord.
     * @throws exceptions.OngeldigAantalDoelenException Minder dan 1 doel op spelbord.
     */
    public void registreerSpelbord() throws OngeldigAantalMannetjesException, OngelijkAantalDoelenKistenException,
            OngeldigAantalKistenException, OngeldigAantalDoelenException
    {
        huidigSpelbord.controleerGeldigheid();
        spelborden.add(huidigSpelbord);
    }
}