
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class BeloteInterface {

    static final String PATH_TO_IMAGES = "src/images/";

    JFrame frameJeuDeBelotte;

    String nomNord;
    String nomSud;
    String nomEst;
    String nomOuest;

    Belote belote;

    JLabel labelJoueurSud;
    JLabel labelJoueurEst;
    JLabel labelJoueurOuest;
    JLabel labelJoueurNord;

    JLabel commentaire;
    JLabel carteJoueurNordLabel;
    JLabel carteJoueurSudLabel;
    JLabel carteJoueurEstLabel;
    JLabel carteJoueurOuestLabel;

    BeloteInterface(String nomNord, String nomSud, String nomEst, String nomOuest) {
        this.belote = new Belote(new Joueur(nomNord), new Joueur(nomSud), new Joueur(nomEst), new Joueur(nomOuest));
        this.nomNord = this.belote.joueurNord.nom;
        this.nomEst = this.belote.joueurEst.nom;
        this.nomSud = this.belote.joueurSud.nom;
        this.nomOuest = this.belote.joueurOuest.nom;
        initFrame();
    }

    void jouer() {

        // On distribue les cartes aux 4 joueurs
        this.belote.distribue();

        // A chaque tour la couleur DEMANDEE devient celle du joueur NORD
        this.belote.demandee = this.belote.joueurNord.joue().c;

        this.belote.afficheMains();
        commentaire.setText(belote.mains());

        // On dévoile les cartes des joueurs
        afficheCarteJoueur(JoueurPosition.NORD, this.belote.joueurNord.carte);
        afficheCarteJoueur(JoueurPosition.EST, this.belote.joueurEst.carte);
        afficheCarteJoueur(JoueurPosition.SUD, this.belote.joueurSud.carte);
        afficheCarteJoueur(JoueurPosition.OUEST, this.belote.joueurOuest.carte);
    }

    // methode appellée apres la méthode jouer, elle verifie que la methode jouer a été appeler en vérifiant que les cartes ne valent pas null
    void remporter() {

        if (this.belote.joueurNord.carte != null && this.belote.joueurEst.carte != null && this.belote.joueurSud.carte != null && this.belote.joueurOuest.carte != null) {

            Joueur gagnant = belote.gagnant();
            int points = belote.points();
            gagnant.remporte(points);

            String msg = "<html>";
            msg += "C'est " + gagnant.nom + " avec son " + gagnant.carte.getNom();
            msg += "<br/> qui remporte " + points + " points!<br/>";
            msg += "</html>";
            commentaire.setText(msg);

            setJoueurPoints(JoueurPosition.NORD, belote.joueurNord.nom, belote.joueurNord.points);
            setJoueurPoints(JoueurPosition.EST, belote.joueurEst.nom, belote.joueurEst.points);
            setJoueurPoints(JoueurPosition.SUD, belote.joueurSud.nom, belote.joueurSud.points);
            setJoueurPoints(JoueurPosition.OUEST, belote.joueurOuest.nom, belote.joueurOuest.points);

            // on enleve les cartes de la table
            afficheCarteJoueur(JoueurPosition.NORD, null);
            afficheCarteJoueur(JoueurPosition.EST, null);
            afficheCarteJoueur(JoueurPosition.SUD, null);
            afficheCarteJoueur(JoueurPosition.OUEST, null);

            // les cartes de chaque joueurs valent null afin d'eviter que le joueur puisse appurer sur le bouton "REMPORTER" plusieurs fois de suite et cumuler les points
            this.belote.joueurNord.carte = null;
            this.belote.joueurEst.carte = null;
            this.belote.joueurSud.carte = null;
            this.belote.joueurOuest.carte = null;
        }
    }

    // Methode qui affiche une carte sur l'interface a une position précise (NORD/EST/SUD/OUEST)
    // si la carte est nulle on affiche le dos de carte
    void afficheCarteJoueur(JoueurPosition position, Carte carte) {

        String fileName = "";
        // AFFICHAGE CARTE : NORD
        if (position == position.NORD) {
            if (carte != null) {
                fileName = BeloteInterface.PATH_TO_IMAGES + carte.getFichierImage();
                this.carteJoueurNordLabel.setIcon(new ImageIcon(fileName));
            } else {
                this.carteJoueurNordLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
            }
            // AFFICHAGE CARTE : EST
        } else if (position == position.EST) {

            if (carte != null) {
                fileName = BeloteInterface.PATH_TO_IMAGES + carte.getFichierImage();
                this.carteJoueurEstLabel.setIcon(new ImageIcon(fileName));
            } else {
                this.carteJoueurEstLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
            }
            // AFFICHAGE CARTE : SUD
        } else if (position == position.SUD) {
            if (carte != null) {
                fileName = BeloteInterface.PATH_TO_IMAGES + carte.getFichierImage();
                this.carteJoueurSudLabel.setIcon(new ImageIcon(fileName));
            } else {
                this.carteJoueurSudLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
            }
            // PAR DEFAUT : OUEST
        } else {
            if (carte != null) {
                fileName = BeloteInterface.PATH_TO_IMAGES + carte.getFichierImage();
                this.carteJoueurOuestLabel.setIcon(new ImageIcon(fileName));
            } else {
                this.carteJoueurOuestLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
            }
        }
    }

    // Methode qui met a jour les points du gagnant sur l'interface
    void setJoueurPoints(JoueurPosition position, String nom, int points) {

        if (position != null && nom != null) {
            if (position == position.NORD) {
                this.labelJoueurNord.setText(nom + " (" + points + ")");
            } else if (position == position.EST) {
                this.labelJoueurEst.setText(nom + " (" + points + ")");
            } else if (position == position.SUD) {
                this.labelJoueurSud.setText(nom + " (" + points + ")");
            } else {
                this.labelJoueurOuest.setText(nom + " (" + points + ")");
            }
        }
    }

    void initFrame() {
        // Dimensions
        int xmin = 100;
        int xmax = 900;
        int ymin = 100;
        int ymax = 900;
        Dimension totalDimension = new Dimension(xmax - xmin, ymax - ymin);
        Dimension carteDimension = new Dimension(240, 360);
        Dimension cartePanelDimension = new Dimension(150, 150);
        Dimension joueurLabelDimensionNS = new Dimension(200, 50);
        Dimension joueurLabelDimensionEO = new Dimension(50, 200);
        Dimension joueurPanelDimension = new Dimension(250, 250);

        // Coueurs
        Color couleurTapis = new Color(0, 100, 0);
        Color couleurBGLabelJoueurNS = Color.BLUE;
        Color couleurBGLabelJoueurEO = Color.ORANGE;
        Color couleurLabelJoueurNS = Color.ORANGE;
        Color couleurLabelJoueurEO = Color.BLUE;
        Color couleurCoin = couleurTapis;
        Color couleurCommentaire = new Color(255, 255, 240);

        frameJeuDeBelotte = new JFrame();
        frameJeuDeBelotte.getContentPane().setMinimumSize(totalDimension);
        frameJeuDeBelotte.getContentPane().setPreferredSize(totalDimension);
        frameJeuDeBelotte.setTitle("Jeu de Belotte");
        frameJeuDeBelotte.getContentPane().setBackground(new Color(0, 100, 0));
        frameJeuDeBelotte.getContentPane()
                .setLayout(new BoxLayout(frameJeuDeBelotte.getContentPane(), BoxLayout.Y_AXIS));

        // Jeu
        // Joueurs
        JPanel jeuPanel = new JPanel();
        frameJeuDeBelotte.getContentPane().add(jeuPanel);
        GridLayout joueursLayout = new GridLayout(3, 3, 1, 1);
        jeuPanel.setLayout(joueursLayout);

        // Coin supérieur gauche
        JPanel coinGauchePanel = new JPanel();
        coinGauchePanel.setBackground(couleurCoin);
        coinGauchePanel.setLayout(new BoxLayout(coinGauchePanel, BoxLayout.X_AXIS));

        JLabel coinGaucheLabel = new JLabel("0, 0");
        coinGaucheLabel.setForeground(couleurCoin);
        coinGaucheLabel.setPreferredSize(joueurPanelDimension);
        coinGaucheLabel.setMinimumSize(joueurPanelDimension);
        coinGaucheLabel.setMaximumSize(joueurPanelDimension);
        coinGauchePanel.add(coinGaucheLabel);

        jeuPanel.add(coinGauchePanel, 0, 0);

        // Joueur Sud
        JPanel joueurSudPanel = new JPanel();
        joueurSudPanel.setBackground(couleurBGLabelJoueurNS);
        joueurSudPanel.setMinimumSize(joueurPanelDimension);
        joueurSudPanel.setLayout(new BoxLayout(joueurSudPanel, BoxLayout.Y_AXIS));

        JPanel joueurSudCartesPanel = new JPanel();
        joueurSudCartesPanel.setPreferredSize(cartePanelDimension);
        joueurSudCartesPanel.setMinimumSize(cartePanelDimension);
        joueurSudCartesPanel.setBackground(couleurTapis);
        joueurSudCartesPanel.setLayout(new GridLayout(1, 0, 20, 0));
        carteJoueurSudLabel = new JLabel();
        carteJoueurSudLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carteJoueurSudLabel.setPreferredSize(carteDimension);
        carteJoueurSudLabel.setMaximumSize(carteDimension);
        carteJoueurSudLabel.setMinimumSize(carteDimension);
        carteJoueurSudLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
        joueurSudCartesPanel.add(carteJoueurSudLabel);
        joueurSudPanel.add(joueurSudCartesPanel);

        JPanel labelJoueurSudPanel = new JPanel();
        labelJoueurSudPanel.setBackground(couleurBGLabelJoueurNS);
        labelJoueurSudPanel.setMaximumSize(joueurLabelDimensionNS);
        labelJoueurSudPanel.setLayout(new BoxLayout(labelJoueurSudPanel, BoxLayout.X_AXIS));
        labelJoueurSud = new JLabel(nomSud);
        labelJoueurSud.setHorizontalAlignment(JLabel.CENTER);
        labelJoueurSud.setPreferredSize(joueurLabelDimensionNS);
        labelJoueurSud.setForeground(couleurLabelJoueurNS);
        labelJoueurSudPanel.add(labelJoueurSud);
        joueurSudPanel.add(labelJoueurSudPanel);

        jeuPanel.add(joueurSudPanel, 0, 1);

        // Coin supérieur droit
        JPanel coinDroitPanel = new JPanel();
        coinDroitPanel.setBackground(couleurCoin);
        coinDroitPanel.setLayout(new BoxLayout(coinDroitPanel, BoxLayout.X_AXIS));

        JLabel coinDroitLabel = new JLabel("0, 2");
        coinDroitLabel.setForeground(couleurCoin);
        coinDroitLabel.setPreferredSize(joueurPanelDimension);
        coinDroitLabel.setMinimumSize(joueurPanelDimension);
        coinDroitLabel.setMaximumSize(joueurPanelDimension);
        coinDroitPanel.add(coinDroitLabel);

        jeuPanel.add(coinDroitPanel, 0, 2);

        // Joueur Est
        JPanel joueurEstPanel = new JPanel();
        joueurEstPanel.setBackground(couleurBGLabelJoueurEO);
        joueurEstPanel.setMinimumSize(joueurPanelDimension);
        joueurEstPanel.setLayout(new BoxLayout(joueurEstPanel, BoxLayout.X_AXIS));

        JPanel labelJoueurEstPanel = new JPanel();
        labelJoueurEstPanel.setBackground(couleurBGLabelJoueurEO);
        labelJoueurEstPanel.setMaximumSize(joueurLabelDimensionEO);
        labelJoueurEstPanel.setLayout(new BoxLayout(labelJoueurEstPanel, BoxLayout.X_AXIS));
        labelJoueurEst = new JLabel(nomEst);
        labelJoueurEst.setForeground(couleurLabelJoueurEO);
        labelJoueurEst.setHorizontalAlignment(JLabel.CENTER);
        labelJoueurEst.setPreferredSize(joueurLabelDimensionEO);
        labelJoueurEst.setUI(new VerticalLabelUI(false));
        labelJoueurEstPanel.add(labelJoueurEst);
        joueurEstPanel.add(labelJoueurEstPanel);

        JPanel joueurEstCartesPanel = new JPanel();
        joueurEstCartesPanel.setPreferredSize(cartePanelDimension);
        joueurEstCartesPanel.setMinimumSize(cartePanelDimension);
        joueurEstCartesPanel.setBackground(couleurTapis);
        joueurEstCartesPanel.setLayout(new GridLayout(0, 1, 20, 0));
        carteJoueurEstLabel = new JLabel();
        carteJoueurEstLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carteJoueurEstLabel.setPreferredSize(carteDimension);
        carteJoueurEstLabel.setMaximumSize(carteDimension);
        carteJoueurEstLabel.setMinimumSize(carteDimension);
        carteJoueurEstLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
        carteJoueurEstLabel.setUI(new VerticalLabelUI(false));
        joueurEstCartesPanel.add(carteJoueurEstLabel);
        joueurEstPanel.add(joueurEstCartesPanel);

        jeuPanel.add(joueurEstPanel, 1, 0);

        // Centre
        JPanel centrePanel = new JPanel();
        centrePanel.setBackground(couleurCoin);
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
        centrePanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        centrePanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JButton btnRJouer = new JButton("Jouer");
        btnRJouer.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnRJouer.setAlignmentY(JButton.CENTER_ALIGNMENT);
        btnRJouer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jouer();
            }
        });
        centrePanel.add(btnRJouer);

        JButton btnRemporter = new JButton("Remporter");
        btnRemporter.setAlignmentX(JButton.CENTER_ALIGNMENT);
        btnRemporter.setAlignmentY(JButton.CENTER_ALIGNMENT);
        btnRemporter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remporter();
            }
        });
        centrePanel.add(btnRemporter);

        commentaire = new JLabel("Commentaire");
        commentaire.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        commentaire.setAlignmentY(JLabel.CENTER_ALIGNMENT);
        commentaire.setForeground(couleurCommentaire);
        commentaire.setHorizontalAlignment(SwingConstants.CENTER);
        centrePanel.add(commentaire);

        jeuPanel.add(centrePanel, 1, 1);

        // Joueur Ouest
        JPanel joueurOuestPanel = new JPanel();
        joueurOuestPanel.setBackground(couleurBGLabelJoueurEO);
        joueurOuestPanel.setMinimumSize(joueurPanelDimension);
        joueurOuestPanel.setLayout(new BoxLayout(joueurOuestPanel, BoxLayout.X_AXIS));

        JPanel joueurOuestCartesPanel = new JPanel();
        joueurOuestCartesPanel.setPreferredSize(cartePanelDimension);
        joueurOuestCartesPanel.setMinimumSize(cartePanelDimension);
        joueurOuestCartesPanel.setBackground(couleurTapis);
        joueurOuestCartesPanel.setLayout(new GridLayout(0, 1, 20, 0));
        carteJoueurOuestLabel = new JLabel();
        carteJoueurOuestLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carteJoueurOuestLabel.setPreferredSize(carteDimension);
        carteJoueurOuestLabel.setMaximumSize(carteDimension);
        carteJoueurOuestLabel.setMinimumSize(carteDimension);
        carteJoueurOuestLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
        carteJoueurOuestLabel.setUI(new VerticalLabelUI(false));
        joueurOuestCartesPanel.add(carteJoueurOuestLabel);
        joueurOuestPanel.add(joueurOuestCartesPanel);

        JPanel labelJoueurOuestPanel = new JPanel();
        labelJoueurOuestPanel.setBackground(couleurBGLabelJoueurEO);
        labelJoueurOuestPanel.setMaximumSize(joueurLabelDimensionEO);
        labelJoueurOuestPanel.setLayout(new BoxLayout(labelJoueurOuestPanel, BoxLayout.X_AXIS));
        labelJoueurOuest = new JLabel(nomOuest);
        labelJoueurOuest.setForeground(couleurLabelJoueurEO);
        labelJoueurOuest.setHorizontalAlignment(JLabel.CENTER);
        labelJoueurOuest.setPreferredSize(joueurLabelDimensionEO);
        labelJoueurOuest.setUI(new VerticalLabelUI(true));
        labelJoueurOuestPanel.add(labelJoueurOuest);
        joueurOuestPanel.add(labelJoueurOuestPanel);

        jeuPanel.add(joueurOuestPanel, 1, 2);

        // Coin inférieur gauche
        JPanel coinBasGauchetPanel = new JPanel();
        coinBasGauchetPanel.setBackground(couleurCoin);
        coinBasGauchetPanel.setLayout(new BoxLayout(coinBasGauchetPanel, BoxLayout.X_AXIS));

        JLabel coinBasGaucheLabel = new JLabel("2, 0");
        coinBasGaucheLabel.setForeground(couleurCoin);
        coinBasGaucheLabel.setPreferredSize(joueurPanelDimension);
        coinBasGaucheLabel.setMinimumSize(joueurPanelDimension);
        coinBasGaucheLabel.setMaximumSize(joueurPanelDimension);
        coinBasGauchetPanel.add(coinBasGaucheLabel);

        jeuPanel.add(coinBasGauchetPanel, 2, 0);

        // Joueur Nord
        JPanel joueurNordPanel = new JPanel();
        joueurNordPanel.setBackground(couleurBGLabelJoueurNS);
        joueurNordPanel.setMinimumSize(joueurPanelDimension);
        joueurNordPanel.setLayout(new BoxLayout(joueurNordPanel, BoxLayout.Y_AXIS));

        JPanel labelJoueurNordPanel = new JPanel();
        labelJoueurNordPanel.setBackground(couleurBGLabelJoueurNS);
        labelJoueurNordPanel.setMaximumSize(joueurLabelDimensionNS);
        labelJoueurNordPanel.setLayout(new BoxLayout(labelJoueurNordPanel, BoxLayout.X_AXIS));
        labelJoueurNord = new JLabel(nomNord);
        labelJoueurNord.setForeground(couleurLabelJoueurNS);
        labelJoueurNord.setHorizontalAlignment(JLabel.CENTER);
        labelJoueurNord.setPreferredSize(joueurLabelDimensionNS);
        labelJoueurNordPanel.add(labelJoueurNord);
        joueurNordPanel.add(labelJoueurNordPanel);

        JPanel joueurNordCartesPanel = new JPanel();
        joueurNordCartesPanel.setPreferredSize(cartePanelDimension);
        joueurNordCartesPanel.setMinimumSize(cartePanelDimension);
        joueurNordCartesPanel.setBackground(couleurTapis);
        joueurNordCartesPanel.setLayout(new GridLayout(1, 0, 20, 0));

        carteJoueurNordLabel = new JLabel();
        carteJoueurNordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        carteJoueurNordLabel.setPreferredSize(carteDimension);
        carteJoueurNordLabel.setMaximumSize(carteDimension);
        carteJoueurNordLabel.setMinimumSize(carteDimension);
        carteJoueurNordLabel.setIcon(new ImageIcon(PATH_TO_IMAGES + "dos-de-carte.png"));
        joueurNordCartesPanel.add(carteJoueurNordLabel);
        joueurNordPanel.add(joueurNordCartesPanel);

        jeuPanel.add(joueurNordPanel, 2, 1);

        // Coin inférieur droit
        JPanel coinBasDroitPanel = new JPanel();
        coinBasDroitPanel.setBackground(couleurCoin);
        coinBasDroitPanel.setLayout(new BoxLayout(coinBasDroitPanel, BoxLayout.X_AXIS));

        JLabel coinBasDroitLabel = new JLabel("2, 2");
        coinBasDroitLabel.setForeground(couleurCoin);
        coinBasDroitLabel.setPreferredSize(joueurPanelDimension);
        coinBasDroitLabel.setMinimumSize(joueurPanelDimension);
        coinBasDroitLabel.setMaximumSize(joueurPanelDimension);
        coinBasDroitPanel.add(coinBasDroitLabel);

        jeuPanel.add(coinBasDroitPanel, 2, 2);

        frameJeuDeBelotte.setBounds(xmin, ymin, xmax, ymax);
        frameJeuDeBelotte.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
