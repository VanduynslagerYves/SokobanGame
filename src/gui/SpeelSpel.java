package gui;

import domein.DomeinController;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Yves
 */
public class SpeelSpel
{
    DomeinController controller;

    public SpeelSpel(DomeinController controller)
    {
        this.controller = controller;
    }

    public void startSpelSpelUI()
    {
        List<String> spelnamen = controller.geefSpelNamen();
        Scanner scanner = new Scanner(System.in);
        int verderSpelenKeuze, keuzeBeweging, spelKeuze;

        do
        {
            System.out.println(controller.getString("speelspel.keuze"));

            int index = 0;
            for (String spelnaam : spelnamen)
            {
                ++index;
                System.out.print("[" + index + "] " + spelnaam + "\n");
            }

            System.out.print(controller.getString("keuze"));
            System.out.println();
            spelKeuze = scanner.nextInt();

            String keuzeString = null;
            switch (spelKeuze)
            {
                case 1:
                    keuzeString = spelnamen.get(--spelKeuze);
                    break;
                case 2:
                    keuzeString = "makkelijk"; //spelnamen.get(--keuze);
                    break;
                case 3:
                    keuzeString = spelnamen.get(--spelKeuze);
            }

            controller.selecteerSpel(keuzeString); //selecteert spel
            controller.startVolgendSpelbord(); //start het volgende spelbord

            do
            {
                while (!controller.huidigSpelbordVoltooid()) //Wordt uitgevoerd als het spelbord nog niet alle kisten op de doelen heeft staan.
                {
                    System.out.println(controller.spelbordToString());
                    System.out.println(controller.getString("speelspel.beweging"));
                    System.out.println(controller.getString("speelspel.omhoog"));
                    System.out.println(controller.getString("speelspel.omlaag"));
                    System.out.println(controller.getString("speelspel.links"));
                    System.out.println(controller.getString("speelspel.rechts"));
                    System.out.print(controller.getString("keuze"));

                    keuzeBeweging = scanner.nextInt();
                    beweeg(keuzeBeweging);
                }
                
                /* Wordt uitgevoerd vanaf het spelbord alle kisten op de doelen heeft staan */
                System.out.println(controller.spelbordToString());
                
                System.out.println(controller.getString("speelspel.gewonnen1") + controller.getAantalZetten() + " " + controller.getString("speelspel.gewonnen2"));
                System.out.println("Aantal spelborden voltooid: " + controller.geefAantalVoltooideBorden() + " van " + controller.geefAantalSpelborden()
                        + " spelborden.");
                System.out.println(controller.getString("speelspel.verderspelen"));
                System.out.print(controller.getString("keuze"));
                verderSpelenKeuze = scanner.nextInt();
                System.out.println();
                
                if (verderSpelenKeuze == 1)
                {
                    controller.startVolgendSpelbord();
                }
            }
            while (verderSpelenKeuze != 2);
            
            System.out.println("Nog een spel? ");
            System.out.println("[1] Ja");
            System.out.println("[2] Nee");
            System.out.println("Uw keuze: ");
            verderSpelenKeuze = scanner.nextInt();
        }
        while (verderSpelenKeuze != 2);
    }

    private void beweeg(int keuzeBeweging)
    {
        controller.beweeg(keuzeBeweging - 1);
    }
}