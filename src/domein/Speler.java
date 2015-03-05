package domein;

/**
 * @author Yves De gebruiker die een spel speelt wordt bewaard in een
 * Speler-object
 */
public class Speler
{
    private String gebruikersnaam, wachtwoord, adminrechten, voornaam, achternaam;

    public Speler()
    {
    }

    /**
     *
     * @param gebruikersnaam gebruikersnaam van de speler
     * @param wachtwoord wachtwoord van de speler
     * @param achternaam achternaam van de speler
     * @param voornaam voornaam van de speler
     * @param adminrechten "ja" als speler adminrechten heeft, "nee" als speler
     * geen adminrechten heeft
     */
    public Speler(String gebruikersnaam, String wachtwoord, String achternaam, String voornaam, String adminrechten) //Contructor gebruikt voor registreren.
    {
        this.setEnControleerGebruikersnaam(gebruikersnaam);
        this.setEnControleerWachtwoord(wachtwoord);
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.adminrechten = adminrechten;
    }

    /**
     * Instellen van de gebruikersnaam van de speler
     * @param gebruikersnaam gebruikersnaam van de aan te melden speler
     */
    public void setGebruikersnaam(String gebruikersnaam)
    {
        this.gebruikersnaam = gebruikersnaam;
    }
    
    /**
     * instellen van het wachtwoord van de speler
     * @param wachtwoord wachtwoord van de aan te melden speler
     */
    public void setWachtwoord(String wachtwoord)
    {
        this.wachtwoord = wachtwoord;
    }
    /**
     * Controleren en instellen van de gebruikersnaam van de speler bij registratie
     *
     * @param gebruikersnaam gebruikersnaam van de speler
     */
    private void setEnControleerGebruikersnaam(String gebruikersnaam) throws IllegalArgumentException
    {
        if (!(gebruikersnaam.length() >= 8))
        {
            throw new IllegalArgumentException();
        }
        this.gebruikersnaam = gebruikersnaam;
    }

    /**
     * Controleren en instellen van het wachtwoord van de speler bij registratie
     *
     * @param wachtwoord wachtwoord van de speler
     */
    private void setEnControleerWachtwoord(String wachtwoord)
    {
        if (!this.geldigWachtwoord(wachtwoord))
        {
            throw new IllegalArgumentException();
        }

        this.wachtwoord = wachtwoord;
    }

    public void setVoornaam(String voornaam)
    {
        this.voornaam = voornaam;
    }

    public void setAchternaam(String achternaam)
    {
        this.achternaam = achternaam;
    }

    public boolean geldigWachtwoord(String wachtwoord)
    {
        int numOfUpperLetters = 0; // initialiseren aantal lowerCase letters
        int numOfLowerLetters = 0; // initialiseren aantal upperCase letters
        int numOfDigits = 0; // initialiseren aantal cijfers

        byte[] bytes = wachtwoord.getBytes();
        for (byte tempByte : bytes)
        {
            char tempChar = (char) tempByte;
            if (Character.isDigit(tempChar))
            {
                numOfDigits++;
            }

            if (Character.isUpperCase(tempChar))
            {
                numOfUpperLetters++;
            }

            if (Character.isLowerCase(tempChar))
            {
                numOfLowerLetters++;
            }
        }
        // indien aan alles werd voldaan: resultaat >= 1; anders 0
        if (numOfDigits * numOfUpperLetters * numOfLowerLetters * (wachtwoord.length() / 8) >= 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Instellen van de adminrechten van de speler
     *
     * @param adminrechten adminrechten van de speler
     */
    public void setAdminrechten(String adminrechten)
    {
        if (adminrechten.equals("ja") || adminrechten.equals("nee"))
        {
            this.adminrechten = adminrechten;
        }
        else
        {
            this.adminrechten = "nee";
        }
    }

    public String getGebruikersnaam()
    {
        return this.gebruikersnaam;
    }

    public String getWachtwoord()
    {
        return this.wachtwoord;
    }

    public String getAdminrechten()
    {
        return this.adminrechten;
    }

    public String getVoornaam()
    {
        return this.voornaam;
    }

    public String getAchternaam()
    {
        return this.achternaam;
    }
}
