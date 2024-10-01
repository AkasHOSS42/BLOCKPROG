import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/**
 *Un bloc de la zone edition pour une variable. Il possede un champ de texte qui est le nom de la variable.
 *@see BlockLabel*/
public abstract class BlockVar extends BlockCST_string{
	/**
	 *Dans le cas ou ce bloc est le membre de gauche d'une affectation,
	 *on ne souhaite pas le rendre deplacable (ou ecrasable). Cet attribut permet de verifier
	 *si this doit etre deplacable.*/
	protected boolean cliquable;

	/**
	 *@param enabled Si on veut rendre this deplacable.
	 *@param h La hauteur du bloc.
	 *@param m Le controleur du bloc.
	 *@param arg Le nom de depart de la variable.
	 *@param c La couleur du bloc.*/
	public BlockVar(int h, MouseAdapter m, String arg, Color c, boolean enabled){
		super(h, m, arg);
		cliquable=enabled;
		if(!enabled){//on le rend indeplacable
			removeMouseListener(m);
			removeMouseMotionListener(m);
		}
		setBackground(c);
		center.setBackground(c);
		label.setBackground(c);
		couleur=c;
		for(ActionListener a : label.getActionListeners())
			label.removeActionListener(a);
	}

	/*Une variable a besoin d'avoir acces a son propre programme pour pouvoir appeler
      sa methode update des qu'on change son nom. Sinon on ne pourrait pas verifier
      que les variables utilisees soient bien initialisees.*/
	public void setProg(Fenetre.Vue.Edition.Programme p){prog=p;}

	/**Le programme qui contient ce bloc.*/
	protected Fenetre.Vue.Edition.Programme prog;
}

/*On fait une classe differente pour chaque type, car l'AST n'est pas le meme
  selon le type.*/

/**Un bloc de la zone edition pour une variable entiere.*/
class BlockVarI extends BlockVar{
	public BlockVarI(int h, MouseAdapter m, String arg, boolean enabled){
		super(h, m, arg, Block.varInt, enabled);
		ast=new IntVariable(arg);
		label.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String cmd=e.getActionCommand();
				((IntVariable)ast).setKey(cmd);
				setText(cmd);
				prog.update();
			}
		});
	}

	public BlockVarI copie(MouseAdapter m){
		BlockVarI copie = new BlockVarI(hauteur, m, ((IntVariable)ast).getKey(), cliquable);
		return copie;
	}
}

/**Un bloc de la zone edition pour une variable de type String.*/
class BlockVarS extends BlockVar{
	public BlockVarS(int h, MouseAdapter m, String arg, boolean enabled){
		super(h, m, arg, Block.varString, enabled);
		ast=new StringVariable(arg);
		label.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String cmd=e.getActionCommand();
				((StringVariable)ast).setKey(cmd);
				setText(cmd);
				prog.update();
			}
		});
	}

	public BlockVarS copie(MouseAdapter m){
		BlockVarS copie = new BlockVarS(hauteur, m, ((StringVariable)ast).getKey(), cliquable);
		return copie;
	}
}

/**Un bloc de la zone edition pour une variable booleene.*/
class BlockVarB extends BlockVar{
	public BlockVarB(int h, MouseAdapter m, String arg, boolean enabled){
		super(h, m, arg, Block.varBool, enabled);
		ast=new BooleanVariable(arg);
		label.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String cmd=e.getActionCommand();
				int i=Integer.parseInt(cmd);
				((BooleanVariable)ast).setKey(cmd);
				setText(cmd);
				prog.update();
			}
		});
	}

	public BlockVarB copie(MouseAdapter m){
		BlockVarB copie = new BlockVarB(hauteur, m, ((BooleanVariable)ast).getKey(), cliquable);
		return copie;
	}
}
