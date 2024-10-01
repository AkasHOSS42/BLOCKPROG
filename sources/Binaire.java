import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 *La classe d'un bloc de la zone edition dont l'ast est une operation binaire.
 * @see Operator */ 
public class Binaire extends Operator{
	/**
	 *Le mot algebrique pour representer l'operateur. Exemple : "+" pour une addition.*/
	protected String label;

	/**
	 *La couleur des arguments. Rappelons que la couleur d'un bloc est avant tout son type de retour, qui n'est
	 *pas forcement le meme que le type des arguments.*/
	protected Color args;

	/**
	 *Le membre gauche de l'operation, au niveau de l'interface graphique.*/
	protected JPanel GP=new JPanel();

	/**
	 *Le membre droit de l'operation, au niveau de l'interface graphique.*/
	protected JPanel DP=new JPanel();

	/**
	 *Le JPanel qui contient le label.*/
	protected JPanel CP=new JPanel();

	public Binaire(int l, int h, Color c, MouseAdapter m, String label, Color args, boolean isExpr, Operation n){
		super(l, h, c, m, isExpr, n);
		this.label = label;
		this.args = args;
		setLayout(new BorderLayout(0, 0));

		GP.setPreferredSize(new Dimension((l-20)/2, h));
		DP.setPreferredSize(new Dimension((l-20)/2, h));
		CP.setPreferredSize(new Dimension(20, h));
		add(GP, BorderLayout.LINE_START);
		add(CP, BorderLayout.CENTER);
		add(DP, BorderLayout.LINE_END);

		GP.setBackground(args);
		DP.setBackground(args);
		CP.setBackground(Color.pink);
		CP.add(new JLabel(label));
	}

	/**
	 * @param m Le controleur de la copie.
	 * @return une copie de l'instance
	 */
	@Override
	public Binaire copie(MouseAdapter m){
		Binaire copie = new Binaire(longueur,hauteur,couleur,m, label, args, isExpr, (Operation)ast);


		return copie;
	}

	/**
	 *Essaie d'affecter le noeud comme valeur de gauche de l'AST de this.
	 *@return true si l'affectation c'est bien passee (donc le typage est correct). false sinon, et dans ce cas rien n'arrive.
	 *@see Operation#setVal2(Noeud)*/
	protected boolean setArg1(Noeud n){
		return((Operation)ast).setVal1(n);
	}

	/**
	 *Essaie d'affecter le noeud comme valeur de droite de l'AST de this.
	 *@return true si l'affectation c'est bien passee (donc le typage est correct). false sinon, et dans ce cas rien n'arrive.
	 *@see Operation#setVal2(Noeud)*/
	protected boolean setArg2(Noeud n){
		return ((Operation)ast).setVal2(n);
	}

	/**
	 * Ajoute le bloc b a la gauche de l'operateur. Adapte la taille de this en fonction de la taille du nouveau bloc.
	 * @param b : le blockEdition a ajouter a gauche
	 */
	public void addG(BlockEdition b){
		if(!setArg1(b.getAST())) return;

		/*Maintenant, on change notre taille.*/
		Dimension d1=GP.getSize();
		Dimension d2=b.getPreferredSize();
		Dimension d3=getSize();
		setPreferredSize(new Dimension((int)(d3.getWidth()+d2.getWidth()-d1.getWidth()), (int)(d3.getHeight()+d2.getHeight()-d1.getHeight())));
		if(father!=null){
			if(isLeft)
				father.addG(this);
			else
				father.addD(this);
		}
		setSize(getPreferredSize());
		remove(GP);
		GP=b;
		add(GP, BorderLayout.LINE_START);
		((BlockEdition)GP).setDad(this, true);
	}

	/**
	 * Ajoute le bloc b a la gauche de l'operateur. Adapte la taille de this en fonction de la taille du nouveau bloc.
	 * @param b : BlockEdition a ajouter a droite
	 */
	public void addD(BlockEdition b){
		if(!setArg2(b.getAST())) return;

		/*Maintenant, on change notre taille.*/
		Dimension d1=DP.getSize();
		Dimension d2=b.getPreferredSize();
		Dimension d3=getSize();
		setPreferredSize(new Dimension((int)(d3.getWidth()+d2.getWidth()-d1.getWidth()), (int)(d3.getHeight()+d2.getHeight()-d1.getHeight())));
		if(father!=null){
			if(isLeft)
				father.addG(this);
			else
				father.addD(this);
		}
		setSize(getPreferredSize());
		remove(DP);
		DP=b;
		add(DP, BorderLayout.LINE_END);
		((BlockEdition)DP).setDad(this, false);
	}

	/* CF commentaire de Unaire.add pour plus d'explications. */
	public void add(BlockEdition b, int posX){
		if(posX<0||posX>getWidth()) return;
		if(posX<=CP.getX()){
			if(!(GP instanceof Operator))
				addG(b);
			else
				((Operator)GP).add(b, posX-GP.getX());
		}
		else if(posX>=DP.getX()){
			if(!(DP instanceof Operator))
				addD(b);
			else
				((Operator)DP).add(b, posX-DP.getX());
		} else if(father==null) return;
		else if(isLeft)
			father.addG(b);
		else
			father.addD(b);
	}

	@Override
	public boolean hasSon(){
		return ((Operation)ast).hasSon();
	}
}
