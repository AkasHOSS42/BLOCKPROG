import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 *Un block est juste un JPanel rectangulaire colore.*/
public class Block extends JPanel {
	protected int longueur, hauteur;
	protected Color couleur;

	//definition des couleurs pour chaque type de blocs.
	public static final Color stringColor=Color.green;
	public static final Color intColor=new Color (127, 127, 255);//Color.blue est trop sombre
	public static final Color booleanColor=Color.red;
	public static final Color voidColor=new Color(127, 127, 127);//gris
	public static final Color jokerColor=Color.white;
	public static final Color gripColor=new Color(192, 0, 255);
	public static final Color varInt=new Color(175,238,238);//
	public static final Color varString=new Color(190, 245, 116); //vert pistache
	public static final Color varBool=new Color(152, 87, 23);//

	public Block (int longueur, int hauteur, Color couleur, MouseAdapter controleur){
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.couleur = couleur;
		this.setBackground(couleur);
		this.setPreferredSize(new Dimension(longueur, hauteur));
		this.addMouseListener(controleur);
		this.addMouseMotionListener(controleur);
	}

	/**
	 * @param controleur
	 * @return une copie de l'instance avec un autre controleur 
	 * Les instances des classes qui etendent Block seront cliquables.
	 * On veut donc pouvoir les copier en changeant leur controleur.*/
	public Block copie(MouseAdapter controleur){
		Block copie = new Block(longueur, hauteur,couleur, controleur);
		return copie;
	}

	/**
	 * @param nouveau
	 * modifie le controleur de l'instance
	 */
	public void setControleur(MouseAdapter nouveau) {
		this.removeMouseListener(this.getMouseListeners()[0]);
		this.removeMouseMotionListener(this.getMouseMotionListeners()[0]);
		this.addMouseListener(nouveau);
		this.addMouseMotionListener(nouveau);
	}

	/**
	 * @return la longueur de l'instance
	 * */
	public int getW(){return longueur;}

	/**
	 * @return la hauteur de l'instance
	 * */
	public int getH(){return hauteur;}

	/**
	 * @return la couleur de l'instance
	 * */
	public Color getC(){return couleur;}
}
