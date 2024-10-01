import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 *Un bloc de la zone choix, a deposer dans la zone edition. Il possede un label qui indique de quoi il s'agit.
 *@see Block*/
public abstract class BlockChoix extends Block{
	private String label;

	public BlockChoix(int l, int h, Color c, MouseAdapter m, String label){
		super(l, h, c, m);
		this.label=label;
		add(new JLabel(label));
	}

	/**
	 *	@return le BlockEdition correspondant a l'instance
	 * */
	public abstract BlockEdition getEd();

	@Override
	public BlockChoix copie(MouseAdapter controleur){
		return new BlockChoix(longueur, hauteur, couleur, controleur, label){
			public BlockEdition getEd(){
				return BlockChoix.this.getEd();
			}
		};
	}
}
