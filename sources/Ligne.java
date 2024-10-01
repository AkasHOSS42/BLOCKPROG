import java.awt.*;
import javax.swing.*;
/**
 * Les blocs simples vont etre mis sur une ligne pour qu'il n'y en ait qu'un par ligne.
 * */
public class Ligne extends JPanel{
	private BlockEdition content;
	public static final int l = 250; // largeur minimum d'une ligne

	public int getContentWidth(){return content.getWidth();}

	public Ligne(BlockEdition b, int wid){
		setPreferredSize(new Dimension(wid, b.getH()));
		setBackground(Color.black);
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		content=b;
		add(b);
		this.validate();
	}

	public void add(BlockEdition block, int posX){
		if(!(content instanceof Operator)) return;
		((Operator)content).add(block, posX-content.getX());
		validate();
	}
}
