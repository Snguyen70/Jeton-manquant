import java.util.Scanner;

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

            // Inititalisation des variables

            String couleur = "";
            int pos = 0;

            int count = 1;
            int resultat = 0;
            int count2 = count + 1;
            int resultat2 = 0;
            int idLigne = 0;

            

            // MODIFIE LE 10 BAKA \\
            for (int i=0 ; i<10 ; i++){

                do {
                couleur = COULEURS[0];
                System.out.println("Veuillez entrer la position du jeton Bleu : ");
                pos = input.nextInt();
                }
                while (jouer(couleur, val, pos)!=true);

                // idLigne TEST \\
                if (pos == 0){
                    idLigne=0;

                } else if (pos >= 1 && pos < 3){
                    idLigne=1;

                } else if (pos >= 3 && pos < 6){
                    idLigne=2;

                } else if (pos >= 6 && pos < 10){
                    idLigne=3;

                } else if (pos >= 10 && pos < 15){
                    idLigne=4;

                } else
                    idLigne=5;

                // idDebutLigne TEST \\
                idDebutLigne(idLigne);
                System.out.println("L'idDebutLigne : " + idDebutLigne(idLigne));

                
                // idFinLigne TEST \\
                idFinLigne(idLigne);
                System.out.println("L'IdFinLigne : " + idFinLigne(idLigne));
                
                afficheJeu(state);

                do {
                couleur = COULEURS[1];
                System.out.println("Veuillez entrer la position du jeton Rouge : ");
                pos = input.nextInt();
                }
                while (jouer(couleur, val, pos)!=true);

                // idLigne TEST \\
                if (pos == 0){
                    idLigne=0;

                } else if (pos >= 1 && pos < 3){
                    idLigne=1;

                } else if (pos >= 3 && pos < 6){
                    idLigne=2;

                } else if (pos >= 6 && pos < 10){
                    idLigne=3;

                } else if (pos >= 10 && pos < 15){
                    idLigne=4;

                } else
                    idLigne=5;

                // idDebutLigne TEST \\
                idDebutLigne(idLigne);
                System.out.println("L'idDebutLigne : " + idDebutLigne(idLigne));

                
                // idFinLigne TEST \\
                idFinLigne(idLigne);
                System.out.println("L'IdFinLigne : " + idFinLigne(idLigne));
                
                afficheJeu(state);

                val++;
                
            }

            System.out.println("la position du jeton manquant : " + getIdVide());

            
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
            System.out.print(x + "\t" + " :");
            for (int j=0; j<NLIGNES - i; j++) {
                System.out.print("  ");
            }
            for (int k=0; k<=i;k++){
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
     * Retourne l'indice de la case débutant la ligne idLigne
     * @param idLigne indice de la ligne. La première ligne est la ligne #0.
     * @return l'indice de la case la plus à gauche de la ligne
     */
    public static int idDebutLigne(int idLigne){
        idLigne = idLigne*(idLigne+1)/2;
        return idLigne;
    }

    /**
     * Retourne l'indice de la case terminant la ligne idLigne
     * @param idLigne indice de la ligne. La première ligne est la ligne #0.
     * @return l'indice de la case la plus à droite de la ligne
     */
    public static int idFinLigne(int idLigne){
        int length = 0;
        length = idDebutLigne(idLigne);
        for ( int i=0; i<length+1; i++)
            idLigne+=i;
        return idLigne;
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
        throw new java.lang.UnsupportedOperationException("à compléter");                
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
