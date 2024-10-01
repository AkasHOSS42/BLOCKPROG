import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 *Un bloc de la zone edition. Il est lie a un AST.*/
public class BlockEdition extends Block{
	/**
	 *Si this est l'argument d'une operation, on pourra la recuperer avec cet attribut.*/
	protected Operator father=null;

	/**
	 *Si this est l'agrument d'une operation, on pourra savoir si il est membre gauche ou droite grace a cet attribut.*/
	protected boolean isLeft=false;

	/**
	 *Cet attribut permet de savoir si l'AST de this est une expression ou une instruction.
	 *@see Expression
	 *@see Instruction*/
	protected boolean isExpr;

	/**
	 *L'arbre de syntaxe abstraite associe au bloc.*/
	protected Noeud ast;

	public Noeud getAST(){return ast;}

	/**
	 *@param b Si this a pour AST une expression.
	 *@param l
	 *@param h
	 *@param c
	 *@param m
	 *@param n */
	public BlockEdition(int l, int h, Color c, MouseAdapter m, boolean b, Noeud n){
		super(l, h, c, m);
		isExpr=b;
		ast=n;
	}

	@Override
	public BlockEdition copie (MouseAdapter m){
		BlockEdition copie = new BlockEdition(longueur, hauteur, couleur, m, isExpr, ast);
		if(ast != null){
			copie.add(new JLabel(((JLabel)this.getComponent(0)).getText()));
		}
		return copie;
	}

	/**
	 *Change de pere, et modifie ses attributs en consequence.
	 *@param s Le nouveau pere.
	 *@param l Si il s'agit du membre gauche d'une operation.
	 *@see Operator*/
	public void setDad(Operator s, boolean l){
		father=s;
		isLeft=l;
	}    

	public boolean hasDad(){	
		return father!=null;
	}

	/**
	 *Methode qui supprime this du programme dans lequel il se trouve, dans le cas ou this a un pere(bloc englobant).*/
	public void gudBye(){
		/*On remplace this par un bloc vierge.*/
		BlockEdition brother=new BlockEdition(20, BlockEditionFactory.h, couleur, null, false, null);
		if(isLeft)
			father.addG(brother);
		else
			father.addD(brother);
	}

	/**
	 *Un BlocEdition ne pourra etre deplace que si il n'a pas de fils. On verifie cela avec cette methode.*/
	public boolean hasSon(){
		return false;
	}

	public boolean isExpr(){return isExpr;}
}
