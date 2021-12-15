import java.util.Scanner;
import java.util.*;

/**
 * Created by zulupero on 24/09/2021.
 */
public class Jeton {
    static final Scanner input = new Scanner(System.in);
    public static String[] state;
    static final int NCASES = 21;
    static final int NLIGNES = 6; 
    static final String[] COULEURS = {"B", "R"};

    static boolean estOui(char reponse) {
        return "yYoO".indexOf(reponse) != -1;
    }
    
    public static void main(String[] args) throws InterruptedException {

        boolean newDeal;
        int scoreBleus = 0;
        int scoreRouges = 0;

        do {
            System.out.println("Jouer seul ? ");
            char reponse = input.next().charAt(0);
            boolean single = estOui(reponse);

            initJeu();
            afficheJeu(state);
 
            int val = 1;
            int idCaseJouee;

            /*

                le code de votre partie ici

            */

            for (int i=0 ; i < NCASES/2 ; i++){
                do {
                System.out.println("Veuillez entrer la position du jeton Bleu : ");
                idCaseJouee = input.nextInt();
                }
                while (jouer(COULEURS[0], val, idCaseJouee)!=true);
                afficheJeu(state);
                do {
                System.out.println("Veuillez entrer la position du jeton Rouge : ");
                idCaseJouee = input.nextInt();
                }
                while (jouer(COULEURS[1], val, idCaseJouee)!=true);
                afficheJeu(state);
                val++;
            }

            // ---------------------------\\

            int sumR = sommeVoisins("R");
            int sumB = sommeVoisins("B");

            if ( sumB < sumR)
                System.out.println("Les bleus gagnent par "+sumB+" à "+sumR);
            else if (sumB == sumR)
                System.out.println("Égalité : "+sumB+" partout !");
            else
                System.out.println("Les rouges gagnent par "+sumR+" à "+sumB);

            System.out.println("Nouvelle partie ? ");
            reponse = input.next().charAt(0);
            newDeal = estOui(reponse);
        } while (newDeal);
        System.out.println("Bye Bye !");
        System.exit(0);

    }

    /**

     * Initialise le jeu avec un double/triple underscore à chaque case, signifiant 'case vide'

     */
    public static void initJeu() {
        state = new String[NCASES];
        for (int i = 0; i<state.length ; i++) {
            state[i]="___";}
    }

    /**

     * Affiche le plateau de jeu en mode texte

     */
    public static void afficheJeu(String[] state){
        int count=0;
        int x=0;

        for (int i=0; i<NLIGNES; ++i) {
            System.out.print(x + "\t" + ":");
            for (int j=0; j<NLIGNES - i; j++) {
                System.out.print("  ");
            }
            for (int k=0; k<=i; k++){
                System.out.print(state[count]+ " ");
                count=count+1; 
                x++;
            }
            System.out.println();
        }
    }

    /**
     * Place un jeton sur le plateau, si possible.
     * @param couleur couleur du jeton : COULEURS[0] ou COULEURS[1]
     * @param val valeur faciale du jeton
     * @param pos position (indice) de l'emplacement où placer le jeton
     * @return true si le jeton a pu être posé, false sinon.
     */
    public static boolean jouer(String couleur, int val, int pos){
        if (state[pos]=="___"){
            state[pos] = couleur + String.valueOf(val);
            return true;
        } else 
            return false;
    }

    /**
     * Trouve la ligne du jeton joué
     * @param pos position (indice) de l'emplacement où placer le jeton
     * @return la ligne du jeton joué
     */
    public static int getLigne(int pos){
        int idLigne = 1;
        int count = 0;
        int limitInf = 0;
        int limitSup = 0;

        for (int i = 0; i<NLIGNES ; i++){
            limitInf = count*(count+1)/2;
            limitSup = (count+1)*((count+1)+1)/2;
            if (pos == 0 || pos >= limitInf && pos < limitSup) {
                return idLigne;
            } else
                idLigne+=1;
                count++;
        }
        return idLigne;
    }

    /**
     * Retourne l'indice de la case débutant la ligne idLigne
     * @param idLigne indice de la ligne. La première ligne est la ligne #0.
     * @return l'indice de la case la plus à gauche de la ligne
     */
    public static int idDebutLigne(int idLigne){
        int indice = idLigne-1;
        indice = indice*(indice+1)/2;
        return indice;
    }

    /**
     * Retourne l'indice de la case terminant la ligne idLigne
     * @param idLigne indice de la ligne. La première ligne est la ligne #0.
     * @return l'indice de la case la plus à droite de la ligne
     */
    public static int idFinLigne(int idLigne){
        int limit = idDebutLigne(idLigne);
        int indice = idLigne-1;
        for ( int i=0; i<limit; i++)
            indice+=1;
        return indice;
    }

    /**
     * Renvoie la position du jeton manquant
     * @return l'indice de la case non occupée
     */
    public static int getIdVide(){
        int i=0;
        while (state[i]!="___"){
            i++;
        }
        return i;
    }

    /**
     * fait la somme des poids des voisins de couleur col
     * (6 voisins au maximum)
     *
     * @param col couleur des voisins considérés
     * @return somme des poids
     */

    public static int sommeVoisins(String col){
        List<Integer> tabIdProche = new ArrayList<>();
        int idVide = getIdVide();
        int idLigne = getLigne(idVide);
        int sommeTab = 0;
        int somme = 0;

        String getVal = "";
        String val = "";
        String color;

        if (idVide==0){
            tabIdProche.add(idVide+idLigne);
            tabIdProche.add(idVide+(idLigne+1));

        } else if (idLigne==NLIGNES && idVide==idDebutLigne(idLigne)){
            tabIdProche.add(idVide+1);
            tabIdProche.add(idVide-(idLigne-1));

        } else if (idLigne==NLIGNES && idVide==idFinLigne(idLigne)){
            tabIdProche.add(idVide-1);
            tabIdProche.add(idVide-idLigne);
        
        } else if (idLigne==NLIGNES && idVide!=idDebutLigne(idLigne) && idVide!=idFinLigne(idLigne)){
            tabIdProche.add(idVide-1);
            tabIdProche.add(idVide+1);
            tabIdProche.add(idVide-idLigne);
            tabIdProche.add(idVide-(idLigne-1));

        } else if (idLigne!=NLIGNES && idVide==idDebutLigne(idLigne) && idVide!=idFinLigne(idLigne)){
            tabIdProche.add(idVide+1);
            tabIdProche.add(idVide+idLigne);
            tabIdProche.add(idVide-(idLigne-1));
            tabIdProche.add(idVide+(idLigne+1));

        } else if ( idLigne!=NLIGNES && idVide!=idDebutLigne(idLigne) && idVide==idFinLigne(idLigne)){
            tabIdProche.add(idVide-1);
            tabIdProche.add(idVide-idLigne);
            tabIdProche.add(idVide+idLigne);
            tabIdProche.add(idVide+(idLigne+1));

        } else {
            tabIdProche.add(idVide-1);
            tabIdProche.add(idVide+1);
            tabIdProche.add(idVide-idLigne);
            tabIdProche.add(idVide+idLigne);
            tabIdProche.add(idVide-(idLigne-1));
            tabIdProche.add(idVide+(idLigne+1));
        }

        System.out.println("");
        System.out.println("sommeVoisins(col : " + col + ")");
        System.out.println("");

        for(int i=0; i<tabIdProche.size(); i++){
            getVal = state[tabIdProche.get(i)];
            val = getVal.substring(1);
            color = getVal.substring(0,1);

            // System.out.println("Couleur:" + color +"beeboo");
            // System.out.println("tabIdProche.get(" + i + ") : " + tabIdProche.get(i) + " , val : " + val);
            // System.out.println("");

            sommeTab = Integer.parseInt(val);
            // System.out.println("SommeTab : " + sommeTab);
            // System.out.println("");
            System.out.println(color);
            System.out.println(col);
            System.out.println("Test " + (col == color));
            if (col == color)
                somme+=sommeTab;
        }
        // System.out.println("========== Somme : " + somme + " ==========");
        // System.out.println("");
        return somme;


        /*
        for(int i=0; i<tabIdProche.length; i++){
            sommeTab = Integer.parseInt(state[tabIdProche[i]].substring(1));

            if (col == COULEURS[1])
                somme+=sommeTab;
        }
        return somme;
        */


        /*
        for(int i=0; i<tabIdProche.length; i++){

            // System.out.println(tabIdProche[i]);

            tabSplit = state[tabIdProche[i]].split(".");
            test = tabSplit[1];
            sommeTab = Integer.parseInt(test);

            if (col == COULEURS[1])
                somme+=sommeTab;
        }
        return somme;
        */
    }

    /**
     * Renvoie le prochain coup à jouer pour les rouges
     * Algo naïf = la première case dispo
     * @return id de la case
     */
    public static int iaRouge(){
	/*
		Écire un véritable code sachant jouer.
		La ligne du return ci-dessous doit donc naturellement aussi être ré-écrite.
		Cette version ne permet que de reproduire le fonctionnement à 2 joueurs 
		tout en conservant l'appel à la fonction,
		cela peut s'avérer utile lors du développement.
	*/
        return Integer.parseInt(input.next());
    }
}
