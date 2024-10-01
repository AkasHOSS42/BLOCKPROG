import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**Un bloc de la zone edition pour une operation. Visuellement, il va "englober" ses arguments.*/
public abstract class Operator extends BlockEdition{
	public Operator(int l, int h, Color c, MouseAdapter m, boolean b, Operation n){
		super(l, h, c, m, b, n);
	}

	@Override
	public abstract Operator copie(MouseAdapter m);

	/**
	 * Ajoute le bloc b a la droite de l'operateur.
	 * @param b : le blockEdition a ajouter a droite
	 */
	public abstract void addD(BlockEdition b);

	/**
	 * Ajoute le bloc b a la gauche de l'operateur.
	 * @param b : le blockEdition a ajouter a gauche
	 */
	public abstract void addG(BlockEdition b);

	public abstract void add(BlockEdition b, int posX);
}
