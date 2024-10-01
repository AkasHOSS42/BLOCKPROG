import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 * Bloc n'ayant rien de special, il indique juste la fin d'une boucle visuellement
 * */
public class FinBlock extends BlockEdition{
	private String label;

	public FinBlock (int l, int h, Color c, String label, boolean isExpr){
		super(l,h,c,null, isExpr, null);
		this.label = label;
		this.add(new JLabel(label));

	}

	@Override
	public FinBlock copie (MouseAdapter m){
		return new FinBlock(longueur, hauteur, couleur, label, isExpr);
	}
}
