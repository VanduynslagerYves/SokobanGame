package exceptions;

/**
 * Klasse die erft van Exception, gebruikt bij registreren van een custom spelbord in het spel.
 *
 * @author Yves
 */
public final class OngeldigAantalKistenException extends Exception
{
    /**
     * Default constructor
     */
    public OngeldigAantalKistenException()
    {
    }

    /**
     * Geeft een bericht mee aan de exception.
     *
     * @param message Bericht als String.
     */
    public OngeldigAantalKistenException(String message)
    {
        super(message);
    }

    /**
     * Geeft een bericht en object mee met info over de code die de exception
     * heeft veroorzaakt.
     *
     * @param message Bericht als String.
     * @param cause object met info over de exception.
     */
    public OngeldigAantalKistenException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Geet een object mee met info over de code die de exception heeft
     * veroorzaakt.
     *
     * @param cause object met info over de exception.
     */
    public OngeldigAantalKistenException(Throwable cause)
    {
        super(cause);
    }
}