package persistentie;

import domein.Kist;
import domein.Mannetje;
import domein.Muur;
import domein.Spel;
import domein.Spelbord;
import domein.Element;
import domein.Veld;
import exceptions.SpelNaamBestaatException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Staat in voor het ophalen van een spel en bijhorende spelborden uit de
 * databank en deze op te slaan als een Spel-object.
 *
 * @author Yves
 */
public final class SpelMapper
{
    private final int VELDEN_ARRAY_GROOTTE = 10;
    private Mannetje mannetje;

    /**
     * Geeft de eerste ID terug van een spelbord binnen een spel. Adhv deze id
     * zal er geloopt worden door de borden van een spel in de
     * geefSpel()-methode.
     *
     * @param spelNaam De naam van het spel waar gecontroleerd moet op worden.
     * @return het eerste spelbordID binnen het spel.
     */
    private int geefEersteSpelbordIdVanSpel(String spelNaam)
    {
        int eersteID = 0;
        String sqlEerste = "SELECT MIN(spelbord.spelbordID) FROM spelbord JOIN spel ON spelbord.Spel_spelID = spel.spelID "
                + "WHERE spel.spelNaam = '" + spelNaam + "';";

        Connectie.start();
        PreparedStatement stmtEersteID;

        try
        {
            stmtEersteID = Connectie.getDatabaseConnectie().prepareStatement(sqlEerste);
            ResultSet rs = stmtEersteID.executeQuery();

            while (rs.next())
            {
                eersteID = rs.getInt(1);
            }
            stmtEersteID.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout in geefEersteSpelbordID: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        finally
        {
            Connectie.sluit();
        }

        return eersteID;
    }

    /**
     * Geeft het laatste spelbordID terug.
     *
     * @return laatste spelbordID
     */
    private int geefLaatsteSpelbordID()
    {
        int laatsteID = 0;

        String sqlLaatste = "SELECT MAX(spelbordID) FROM spelbord;";

        PreparedStatement stmtLaatsteID;

        try
        {
            stmtLaatsteID = Connectie.getDatabaseConnectie().prepareStatement(sqlLaatste);
            ResultSet rs = stmtLaatsteID.executeQuery();

            while (rs.next())
            {
                laatsteID = rs.getInt(1);
            }
            stmtLaatsteID.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout in geefLaatsteSpelbordID: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        return laatsteID;
    }

    /**
     * Geeft het laatste spelID terug.
     *
     * @return laatste spelID
     */
    private int geefLaatsteSpelID()
    {
        int laatsteID = 0;

        String sqlLaatste = "SELECT MAX(spelID) FROM spel;";

        PreparedStatement stmtLaatsteID;

        try
        {
            stmtLaatsteID = Connectie.getDatabaseConnectie().prepareStatement(sqlLaatste);
            ResultSet rs = stmtLaatsteID.executeQuery();

            while (rs.next())
            {
                laatsteID = rs.getInt(1);
            }
            stmtLaatsteID.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout in geefLaatsteSpelID: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        return laatsteID;
    }

    /**
     * Haalt de nodige pelgegevens uit de databank op basis van meegegeven parameter.
     *
     * @param naam van het spel dat moet opgehaald worden.
     * @return Spel-object met alle spelborden van dat spel.
     */
    public Spel geefSpel(String naam)
    {
        Element[][] velden;
        final String[] ELEMENTEN =
        {
            "veld", "doel", "mannetje", "kist"
        };
        int eersteSpelbordID = this.geefEersteSpelbordIdVanSpel(naam);
        int aantalBorden = this.geefAantalSpelborden(naam) + eersteSpelbordID;

        List<Spelbord> borden = new ArrayList();

        Connectie.start();

        for (int spelbordID = eersteSpelbordID; spelbordID < aantalBorden; spelbordID++)
        {
            velden = new Element[VELDEN_ARRAY_GROOTTE][VELDEN_ARRAY_GROOTTE];
            for (String element : ELEMENTEN)
            {
                PreparedStatement stmtBordenOphalen;

                String sqlBordenOphalen = "SELECT Element.positieX, Element.positieY "
                        + "FROM " + element + " Element JOIN spelbord Spelbord ON Element.Spelbord_spelbordID = Spelbord.spelbordID "
                        + "JOIN spel Spel ON Spelbord.Spel_spelID = Spel.spelID "
                        + "WHERE Spel.spelNaam = '" + naam + "' AND Spelbord.spelbordID = " + spelbordID + ";";

                try
                {
                    int x, y;

                    stmtBordenOphalen = Connectie.getDatabaseConnectie().prepareStatement(sqlBordenOphalen);
                    ResultSet rs = stmtBordenOphalen.executeQuery();

                    while (rs.next())
                    {
                        x = rs.getInt(1);
                        y = rs.getInt(2);

                        switch (element)
                        {
                            case "veld":
                                velden[x][y] = new Veld(x, y, false);
                                break;
                            case "doel":
                                velden[x][y] = new Veld(x, y, true);
                                break;
                            case "mannetje":
                                mannetje = new Mannetje(x, y, false);
                                velden[x][y] = new Mannetje(x, y, false);
                                break;
                            case "kist":
                                velden[x][y] = new Kist(x, y, false);
                                break;
                        }
                    }
                    stmtBordenOphalen.close();
                }
                catch (SQLException sqlEx)
                {
                    System.err.println("SQL fout in geefSpel: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
                }
            }

            /*
             for-loop om te controleren op null-waarden in de velden array en die dan te vullen met een muur.
             Ik doe dit bewust met een gewone for-loop zodat ik de teller kan gebruiken in de declaratie van de Muur.
             */
            for (int rij = 0; rij < 10; rij++)
            {
                for (int kolom = 0; kolom < 10; kolom++)
                {
                    if (velden[rij][kolom] == null)
                    {
                        velden[rij][kolom] = new Muur(rij, kolom);
                    }
                }
            }

            borden.add(new Spelbord(velden, mannetje));
        }

        Connectie.sluit();

        Spel spel = new Spel(naam, borden);
        return spel;
    }

    /**
     * Deze methode geeft het aantal spelborden terug die 1 enkel gekozen spel
     * bevat
     *
     * @param naam De naam van het spel waarvan het aantal spelborden geweten
     * moet zijn.
     * @return Het aantal spelborden van het gekozen spel.
     */
    private int geefAantalSpelborden(String naam)
    {
        int aantal = 0;
        String sqlCount = "SELECT COUNT(spelbord.spelbordID) FROM spelbord JOIN spel ON spelbord.Spel_spelID = spel.spelID "
                + "WHERE spel.spelNaam = '" + naam + "';";

        Connectie.start();
        PreparedStatement stmtAantalBorden;

        try
        {
            stmtAantalBorden = Connectie.getDatabaseConnectie().prepareStatement(sqlCount);
            ResultSet rs = stmtAantalBorden.executeQuery();

            while (rs.next())
            {
                aantal = rs.getInt(1);
            }
            stmtAantalBorden.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout in geefAantalSpelborden: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        finally
        {
            Connectie.sluit();
        }

        return aantal;
    }

    /**
     * Geeft alle spelnamen terug uit de databank.
     * @return Lijst van spelnamen.
     */
    public List<String> geefSpelNamen()
    {
        List<String> spelNamen = new ArrayList();
        String sqlSpelNamen = "SELECT spelNaam FROM spel ORDER BY spelID;";

        Connectie.start();
        PreparedStatement stmtSpelNamen;

        try
        {
            stmtSpelNamen = Connectie.getDatabaseConnectie().prepareStatement(sqlSpelNamen);
            ResultSet rs = stmtSpelNamen.executeQuery();

            while (rs.next())
            {
                spelNamen.add(rs.getString(1));
            }
            stmtSpelNamen.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout in geefSpelNamen: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        finally
        {
            Connectie.sluit();
        }
        return spelNamen;
    }

    /**
     * Voegt een nieuwe spelNaam toe aan de databank.
     * @param spelNaam in te voeren spelnaam.
     * @throws SpelNaamBestaatException 
     */
    private void voegSpelToe(String spelNaam) throws SpelNaamBestaatException
    {
        String sqlInsertSpelNaam = "INSERT INTO sokobandatabase.spel(spelNaam) "
                + "VALUES(?);";

        PreparedStatement stmtInsertSpelNaam;

        try
        {
            if (bestaatSpelNaam(spelNaam))
            {
                throw new SpelNaamBestaatException();
            }
            stmtInsertSpelNaam = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertSpelNaam);
            stmtInsertSpelNaam.setString(1, spelNaam);
            stmtInsertSpelNaam.executeUpdate();
            stmtInsertSpelNaam.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout in voegSpelToe: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
    }

    /**
     * Voegt nieuwe spelbordID's toe aan de databank.
     * 
     * @param spelborden lijst van spelborden.
     */
    private void voegSpelbordenToe(List<Spelbord> spelborden)
    {
        String sqlInsertSpelbord = "INSERT INTO sokobandatabase.spelbord(Spel_spelID) "
                + "VALUES(?);";

        PreparedStatement stmtInsertSpelbord;

        for (Spelbord spelbord : spelborden)
        {
            int spelbordVreemdeSleutel = this.geefLaatsteSpelID();

            try
            {
                stmtInsertSpelbord = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertSpelbord);
                stmtInsertSpelbord.setInt(1, spelbordVreemdeSleutel);
                stmtInsertSpelbord.executeUpdate();
                stmtInsertSpelbord.close();
            }
            catch (SQLException sqlEx)
            {
                System.err.println("SQL fout in voegSpelbordenToe: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
            }

            voegElementenToe(spelbord.geefVelden());
        }
    }

    /**
     * Voegt alle elementen uit een spelbord toe aan de databank.
     * 
     * @param velden 2d-array met daarin alle elementen.
     */
    private void voegElementenToe(Element[][] velden)
    {
        String sqlInsertElementen = "";
        PreparedStatement stmtInsertElementen;

        int elementVreemdeSleutel = this.geefLaatsteSpelbordID();

        for (Element[] rij : velden)
        {
            for (Element element : rij)
            {
                if (!(element instanceof Muur))
                {
                    if (element instanceof Veld && element.isDoel())
                    {
                        sqlInsertElementen = "INSERT INTO sokobandatabase.doel(positieX, positieY, Spelbord_spelbordID) "
                                + "VALUES(?, ?, ?);";
                    }
                    else
                    {
                        if (element instanceof Veld && !element.isDoel())
                        {
                            sqlInsertElementen = "INSERT INTO sokobandatabase.veld(positieX, positieY, Spelbord_spelbordID) "
                                    + "VALUES(?, ?, ?);";
                        }
                        else
                        {
                            if (element instanceof Kist)
                            {
                                sqlInsertElementen = "INSERT INTO sokobandatabase.kist(positieX, positieY, Spelbord_spelbordID) "
                                        + "VALUES(?, ?, ?);";
                            }
                            else
                            {
                                if (element instanceof Mannetje)
                                {
                                    sqlInsertElementen = "INSERT INTO sokobandatabase.mannetje(positieX, positieY, Spelbord_spelbordID) "
                                            + "VALUES(?, ?, ?);";
                                }
                            }
                        }
                    }

                    try
                    {
                        stmtInsertElementen = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertElementen);

                        stmtInsertElementen.setInt(1, element.getKolomPositie());
                        stmtInsertElementen.setInt(2, element.getRijPositie());
                        stmtInsertElementen.setInt(3, elementVreemdeSleutel);

                        stmtInsertElementen.executeUpdate();
                        stmtInsertElementen.close();
                    }
                    catch (SQLException sqlEx)
                    {
                        System.err.println("SQL fout in voegElementenToe: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
                    }
                }
            }
        }
    }

    /**
     * Voegt een Spel-object toe aan de databank.
     * 
     * @param customSpel Spel-object met bijhorende Spelborden en Elementen.
     * @throws SpelNaamBestaatException Spelnaam bestaat al in databank.
     */
    public void voegToe(Spel customSpel) throws SpelNaamBestaatException
    {
        Connectie.start();
        voegSpelToe(customSpel.getSpelNaam());
        voegSpelbordenToe(customSpel.geefSpelborden());
        Connectie.sluit();
    }

    /**
     * Controleert of de spelnaam al bestaat in de tabel spel.
     * @param spelNaam Te controleren spelnaam.
     * @return true als spelnaam bestaat, false als spelnaam niet bestaat.
     */
    private boolean bestaatSpelNaam(String spelNaam)
    {
        String sqlString = "SELECT spelNaam FROM spel WHERE spelNaam = '" + spelNaam + "'";

        PreparedStatement sqlStatement;

        String opgehaaldeSpelNaam = null;

        try
        {
            sqlStatement = Connectie.getDatabaseConnectie().prepareStatement(sqlString);
            ResultSet rs = sqlStatement.executeQuery();

            while (rs.next())
            {
                opgehaaldeSpelNaam = rs.getString(1);
            }
            sqlStatement.close();
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout" + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }

        return opgehaaldeSpelNaam != null; //string moet null zijn om onbestaand spel voor te stellen
    }
}
