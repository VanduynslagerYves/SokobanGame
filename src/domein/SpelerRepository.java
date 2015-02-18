package domein;

import persistentie.*;

/**
 * Staat in voor het beheren, toevoegen en verwijderen van spelers.
 * @author Yves
 */
public class SpelerRepository
{
    SpelerMapper spelerMapper;

    public SpelerRepository()
    {
        spelerMapper = new SpelerMapper();
    }
    /**
     *
     * @param gebruikersnaam gebruikersnaam van de speler
     * @param wachtwoord wachtwoord van de speler
     * @return gevuld Speler-object met waarden uit gewenste record uit databank.
     */
    public Speler geefSpeler(String gebruikersnaam, String wachtwoord)
    {
        return spelerMapper.zoek(gebruikersnaam, wachtwoord);
    }
}
