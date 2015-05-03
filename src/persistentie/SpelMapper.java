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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Staat in voor het ophalen van een spel en bijhorende spelborden uit de
 * databank en deze op te slaan als een Spel-object.
 *
 * @author Yves
 */
public final class SpelMapper
{
    private final int VELDEN_ARRAY_GROOTTE = 10;
    //private Element[][] velden;
    private Mannetje mannetje;

    /**
     * Geeft de eerste ID terug van een spelbord binnen een spel. Adhv deze id
     * zal er geloopt worden door de borden van een spel in de
     * geefSpel()-methode.
     *
     * @param spelNaam
     * @return
     */
    private int geefEersteSpelbordID(String spelNaam)
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
            System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        finally
        {
            Connectie.sluit();
        }

        return eersteID;
    }

    /**
     *
     * @return
     */
    private int geefLaatsteSpelbordID()
    {
        int laatsteID = 0;

        String sqlLaatste = "SELECT MAX(spelbordID) FROM spelbord;";

        //Connectie.start();
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
            System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        return laatsteID;
    }

    /**
     *
     * @return
     */
    private int geefLaatsteSpelID()
    {
        int laatsteID = 0;

        String sqlLaatste = "SELECT MAX(spelID) FROM spel;";

        //Connectie.start();
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
            System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        return laatsteID;
    }

    /**
     *
     * @param naam makkelijk, gemiddeld, of moeilijk
     * @return
     */
    public Spel geefSpel(String naam)
    {
        Element[][] velden;
        String spelbordNaam = "";
        final String[] ELEMENTEN =
        {
            "veld", "doel", "mannetje", "kist"
        };
        int eersteSpelbordID = this.geefEersteSpelbordID(naam);
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
                    System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
                }
            }
            //for-loop om te controleren op null-waarden in de velden array en die dan te vullen met een muur.
            //Ik doe dit bewust met een gewone for-loop zodat ik de teller kan gebruiken in de declaratie van de Muur.
            for (int x = 0; x < 10; x++)
            {
                for (int y = 0; y < 10; y++)
                {
                    if (velden[x][y] == null)
                    {
                        velden[x][y] = new Muur(x, y);
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
            System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        finally
        {
            Connectie.sluit();
        }

        return aantal;
    }

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
            System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
        finally
        {
            Connectie.sluit();
        }
        return spelNamen;
    }

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
        }
        catch (SQLException sqlEx)
        {
            System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
        }
    }

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
            }
            catch (SQLException sqlEx)
            {
                System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
            }

            voegElementenToe(spelbord.geefVelden());
        }
    }

    
    /*
    HIER NOG FOUT!!!!!!
    */
    private void voegElementenToe(Element[][] velden)
    {
        String sqlInsertElementen = "";

        PreparedStatement stmtInsertElementen;

        int elementVreemdeSleutel = this.geefLaatsteSpelbordID();

        for (Element[] velden1 : velden)
        {
            for (Element element : velden1)
            {
                if (element instanceof Veld && element.isDoel())
                {
                    sqlInsertElementen = "INSERT INTO sokobandatabase.doel(positieX, positieY, Spelbord_spelbordID) "
                            + "VALUES(?, ?, ?); ";
                }
                if (element instanceof Veld && !element.isDoel())
                {
                    sqlInsertElementen = "INSERT INTO sokobandatabase.veld(positieX, positieY, Spelbord_spelbordID) "
                            + "VALUES(?, ?, ?); ";
                }
                if (element instanceof Kist)
                {
                    sqlInsertElementen = "INSERT INTO sokobandatabase.kist(positieX, positieY, Spelbord_spelbordID) "
                            + "VALUES(?, ?, ?); ";
                }
                if (element instanceof Mannetje)
                {
                    sqlInsertElementen = "INSERT INTO sokobandatabase.mannetje(positieX, positieY, Spelbord_spelbordID) "
                            + "VALUES(?, ?, ?); ";
                }
                try
                {
                    stmtInsertElementen = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertElementen);

                    stmtInsertElementen.setInt(1, element.getxPositie());
                    stmtInsertElementen.setInt(2, element.getyPositie());
                    stmtInsertElementen.setInt(3, elementVreemdeSleutel);

                    stmtInsertElementen.executeUpdate();
                }
                catch (SQLException sqlEx)
                {
                    System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
                }
            }
        }
    }

    /*
     public void voegToe(Spel customSpel) throws SpelNaamBestaatException
     {
     String sqlInsertElementen = "";
     String sqlInsertSpelNaam = "INSERT INTO sokobandatabase.spelbord(spelNaam) "
     + "VALUES(?);";
     String sqlInsertSpelbord = "INSERT INTO sokobandatabase.spelbord(spel_spelID) "
     + "VALUES(?);";
     PreparedStatement stmtInsertElementen;
     PreparedStatement stmtInsertSpelNaam;
     PreparedStatement stmtInsertSpelbord;

     try
     {
     if (bestaatSpelNaam(customSpel.getSpelNaam()))
     {
     throw new SpelNaamBestaatException();
     }
     stmtInsertSpelNaam = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertSpelNaam);
     stmtInsertSpelNaam.setString(1, customSpel.getSpelNaam());
     stmtInsertSpelNaam.executeUpdate();
     }
     catch (SQLException sqlEx)
     {
     System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
     }

     for (Spelbord spelbord : customSpel.geefSpelborden())
     {
     int elementVreemdeSleutel = this.geefLaatsteSpelbordID() + 1;
     int spelbordVreemdeSleutel = this.geefLaatsteSpelID() + 1;

     try
     {
     stmtInsertSpelbord = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertSpelbord);
     stmtInsertSpelbord.setInt(1, spelbordVreemdeSleutel);
     stmtInsertSpelbord.executeUpdate();
     }
     catch (SQLException sqlEx)
     {
     System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
     }

     Element velden[][] = spelbord.geefVelden();
     for (Element[] velden1 : velden)
     {
     for (Element element : velden1)
     {
     if (element instanceof Veld && element.isDoel())
     {
     sqlInsertElementen = "INSERT INTO sokobandatabase.doel(positieX, positieY, Spelbord_spelbordID) "
     + "VALUES(?, ?, ?); ";
     }
     if (element instanceof Veld && !element.isDoel())
     {
     sqlInsertElementen = "INSERT INTO sokobandatabase.veld(positieX, positieY, Spelbord_spelbordID) "
     + "VALUES(?, ?, ?); ";
     }
     if (element instanceof Kist)
     {
     sqlInsertElementen = "INSERT INTO sokobandatabase.kist(positieX, positieY, Spelbord_spelbordID) "
     + "VALUES(?, ?, ?); ";
     }
     if (element instanceof Mannetje)
     {
     sqlInsertElementen = "INSERT INTO sokobandatabase.mannetje(positieX, positieY, Spelbord_spelbordID) "
     + "VALUES(?, ?, ?); ";
     }
     try
     {
     stmtInsertElementen = Connectie.getDatabaseConnectie().prepareStatement(sqlInsertElementen);

     stmtInsertElementen.setInt(1, element.getxPositie());
     stmtInsertElementen.setInt(2, element.getyPositie());
     stmtInsertElementen.setInt(3, elementVreemdeSleutel);

     stmtInsertElementen.executeUpdate();
     }
     catch (SQLException sqlEx)
     {
     System.err.println("SQL fout: " + sqlEx.getMessage() + "\n" + sqlEx.getSQLState());
     }
     finally
     {
     Connectie.sluit();
     }
     }
     }
     }
     }*/
    public void voegToe(Spel customSpel) throws SpelNaamBestaatException
    {
        Connectie.start();
        voegSpelToe(customSpel.getSpelNaam());
        voegSpelbordenToe(customSpel.geefSpelborden());
        Connectie.sluit();
    }

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
