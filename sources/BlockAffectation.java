import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

/*On veut que ces classes etendent Binaire pour profiter du code de Binaire,
  notamment pour le changement de taille dynamique. Cependant on est oblige de faire quelques amenagements,
  car l'AST associe a cette classe n'est plus une instance d'Operation.*/

/**
 *Le bloc de la zone edition pour l'afectation d'une variable entiere.
 *@see Affectation*/
class BlockAffectationInt extends Binaire{
	/**Le membre de gauche de l'affectation.*/
	private BlockVarI var;

	public BlockAffectationInt(int l, int h, MouseAdapter m){
		super(l, h, Block.intColor, m, "=", Block.intColor, false, null);
		ast=new IntAffect();
		var=new BlockVarI(h, m, "x", false);
		setSize(new Dimension(60, h));
		addG(var);
	}

	/*Separer les methodes setArg1 et addG permet de ne pas reecrire tout le code de
      Binaire.addG . Meme chose pour setArg2.*/
	protected boolean setArg1(Noeud n){
		if(n instanceof IntVariable){
			((IntAffect)ast).setKey(((IntVariable)n).getKey());
			return true;
		}
		return false;
	}

	public boolean hasSon(){return ((Affectation)ast).hasSon();}

	protected boolean setArg2(Noeud n){
		return ((IntAffect)ast).setVal(n);
	}

	public BlockAffectationInt copie(MouseAdapter m){
		return new BlockAffectationInt(longueur, hauteur, m);
	}

	/*Rappelons que les variables doivent pouvoir appeler update
      sur leur programme quand on change leur nom. Cette methode
      veille a ce que ce soit aussi le cas pour this.var .*/
	public void setProg(Fenetre.Vue.Edition.Programme p){
		var.setProg(p);
	}

	/*On redefinit legerement la methode de Binaire, pour empecher
      l'utilisateur d'ecraser this.var avec un autre bloc.*/
	public void add(BlockEdition b, int posX){
		if(posX>=DP.getX()){
			if(!(DP instanceof Operator))
				addD(b);
			else
				((Operator)DP).add(b, posX-DP.getX());
		}
	}
}

/*Les classes suivantes sont similaires a BlockAffectationInt. Voir les commentaires de BlockAffectationInt
  pour plus de details.*/

/**
 *Le bloc de la zone edition pour l'afectation d'une variable de type String.
 *@see Affectation*/
class BlockAffectationString extends Binaire{
	private BlockVarS var;

	public BlockAffectationString(int l, int h, MouseAdapter m){
		super(l, h, Block.stringColor, m, "=", Block.stringColor, false, null);
		ast=new StringAffect();
		var=new BlockVarS(h, m, "s", false);
		setSize(new Dimension(60, h));
		addG(var);
	}

	protected boolean setArg1(Noeud n){
		if(n instanceof StringVariable){
			((StringAffect)ast).setKey(((StringVariable)n).getKey());
			return true;
		}
		return false;
	}

	public boolean hasSon(){return ((Affectation)ast).hasSon();}

	protected boolean setArg2(Noeud n){
		return ((StringAffect)ast).setVal(n);
	}

	public void setProg(Fenetre.Vue.Edition.Programme p){
		var.setProg(p);
	}

	public BlockAffectationString copie(MouseAdapter m){
		return new BlockAffectationString(longueur, hauteur, m);
	}
}

/**
 *Le bloc de la zone edition pour l'afectation d'une variable entiere.
 *@see Affectation*/
class BlockAffectationBool extends Binaire{
	private BlockVarB var;

	public BlockAffectationBool(int l, int h, MouseAdapter m){
		super(l, h, Block.booleanColor, m, "=", Block.booleanColor, false, null);
		ast=new BoolAffect();
		var=new BlockVarB(h, m, "b", false);
		setSize(new Dimension(60, h));
		addG(var);
	}

	protected boolean setArg1(Noeud n){
		if(n instanceof BooleanVariable){
			((BoolAffect)ast).setKey(((BooleanVariable)n).getKey());
			return true;
		}
		return false;
	}

	protected boolean setArg2(Noeud n){
		return ((BoolAffect)ast).setVal(n);
	}

	public boolean hasSon(){return ((Affectation)ast).hasSon();}

	public void setProg(Fenetre.Vue.Edition.Programme p){
		var.setProg(p);
	}

	public BlockAffectationBool copie(MouseAdapter m){
		return new BlockAffectationBool(longueur, hauteur, m);
	}
}
