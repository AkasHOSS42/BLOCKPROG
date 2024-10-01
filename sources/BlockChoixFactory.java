import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 *Une simple classe dans laquelle il y a toutes les methodes pour construire les
 *blocs choix dont on se servira.
 *@see BlockChoix*/
public class BlockChoixFactory{
	public static BlockChoix makeIF(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(30, BlockEditionFactory.h, Block.voidColor, m1, "IF"){
			public BlockEdition getEd(){return BlockEditionFactory.makeIF(m2);}
		};
	}

	public static BlockChoix makeWHILE(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(80, BlockEditionFactory.h, Block.voidColor, m1, "WHILE"){
			public BlockEdition getEd(){return BlockEditionFactory.makeWHILE(m2);}
		};
	}

	public static BlockChoix makeSUM(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(80, BlockEditionFactory.h, Block.intColor, m1, "Addition"){
			public BlockEdition getEd(){return BlockEditionFactory.makeSUM(m2);}
		};
	}

	public static BlockChoix makeDIFF(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.intColor, m1, "Soustraction"){
			public BlockEdition getEd(){return BlockEditionFactory.makeDIFF(m2);}
		};
	}

	public static BlockChoix makePROD(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(80, BlockEditionFactory.h, Block.intColor, m1, "Produit"){
			public BlockEdition getEd(){return BlockEditionFactory.makePROD(m2);}
		};
	}

	public static BlockChoix makeQUOT(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(80, BlockEditionFactory.h, Block.intColor, m1, "Quotient"){
			public BlockEdition getEd(){return BlockEditionFactory.makeQUOT(m2);}
		};
	}

	public static BlockChoix makeNOT(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(80, BlockEditionFactory.h, Block.booleanColor, m1, "Négation"){
			public BlockEdition getEd(){return BlockEditionFactory.makeNOT(m2);}
		};
	}

	public static BlockChoix makeOR(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(30, BlockEditionFactory.h, Block.booleanColor, m1, "OU"){
			public BlockEdition getEd(){return BlockEditionFactory.makeOR(m2);}
		};
	}

	public static BlockChoix makeAND(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(30, BlockEditionFactory.h, Block.booleanColor, m1, "ET"){
			public BlockEdition getEd(){return BlockEditionFactory.makeAND(m2);}
		};
	}

	public static BlockChoix makeEQUALS_boolean(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.booleanColor, m1, "Equivalence"){
			public BlockEdition getEd(){return BlockEditionFactory.makeEQUALS_boolean(m2);}
		};
	}

	public static BlockChoix makeEQUALS_int(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.booleanColor, m1, "Egalite"){
			public BlockEdition getEd(){return BlockEditionFactory.makeEQUALS_int(m2);}
		};
	}

	public static BlockChoix makeEQUALS_string(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.booleanColor, m1, "Homographie"){
			public BlockEdition getEd(){return BlockEditionFactory.makeEQUALS_string(m2);}
		};
	}

	public static BlockChoix makeCCT(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.stringColor, m1, "Concatenation"){
			public BlockEdition getEd(){return BlockEditionFactory.makeCCT(m2);}
		};
	}

	public static BlockChoix makeCST_boolean(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.booleanColor, m1, "Constante"){
			public BlockEdition getEd(){return BlockEditionFactory.makeCST_boolean(m2);}
		};
	}

	public static BlockChoix makeCST_int(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.intColor, m1, "Constante"){
			public BlockEdition getEd(){return BlockEditionFactory.makeCST_int(m2);}
		};
	}

	public static BlockChoix makeCST_string(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(100, BlockEditionFactory.h, Block.stringColor, m1, "Constante"){
			public BlockEdition getEd(){return BlockEditionFactory.makeCST_string(m2);}
		};
	}

	public static BlockChoix deplace(MouseAdapter m1, MouseAdapter m2, Picture p){
		return new BlockChoix(80, BlockEditionFactory.h, Block.voidColor, m1, "Avance"){
			public BlockEdition getEd(){return BlockEditionFactory.deplace(m2, p);}
		};
	}

	public static BlockChoix turnRight(MouseAdapter m1, MouseAdapter m2, Picture p){
		return new BlockChoix(120, BlockEditionFactory.h, Block.voidColor, m1, "Tourne a droite"){
			public BlockEdition getEd(){return BlockEditionFactory.turnRight(m2, p);}
		};
	}

	public static BlockChoix turnLeft(MouseAdapter m1, MouseAdapter m2, Picture p){
		return new BlockChoix(120, BlockEditionFactory.h, Block.voidColor, m1, "Tourne a gauche"){
			public BlockEdition getEd(){return BlockEditionFactory.turnLeft(m2, p);}
		};
	}

	public static BlockChoix demiTour(MouseAdapter m1, MouseAdapter m2, Picture p){
		return new BlockChoix(80, BlockEditionFactory.h, Block.voidColor, m1, "Demi tour"){
			public BlockEdition getEd(){return BlockEditionFactory.demiTour(m2, p);}
		};
	}

	public static BlockChoix makePrint(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(80, BlockEditionFactory.h, Block.voidColor, m1, "Affiche"){
			public BlockEdition getEd(){
				return new BlocPrint(m2);
			}
		};
	}

	public static BlockChoix usePen(MouseAdapter m1, MouseAdapter m2, Picture p){
		return new BlockChoix(80, BlockEditionFactory.h, Block.voidColor, m1, "Trace"){
			public BlockEdition getEd(){
				return BlockEditionFactory.usePen(m2, p);
			}
		};
	}

	public static BlockChoix salut(MouseAdapter m1, MouseAdapter m2, Picture p){
		return new BlockChoix(80, BlockEditionFactory.h, Block.voidColor, m1, "Salut"){
			public BlockEdition getEd(){
				return BlockEditionFactory.salut(m2, p);
			}
		};
	}

	public static BlockChoix makeAffectInt(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.voidColor, m1, "Affectation-entier"){
			public BlockEdition getEd(){
				return new BlockAffectationInt(60, BlockEditionFactory.h, m2);
			}
		};
	}

	public static BlockChoix makeAffectString(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.voidColor, m1, "Affectation-chaine"){
			public BlockEdition getEd(){
				return new BlockAffectationString(60, BlockEditionFactory.h, m2);
			}
		};
	}

	public static BlockChoix makeAffectBool(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.voidColor, m1, "Affectation-booleen"){
			public BlockEdition getEd(){
				return new BlockAffectationBool(60, BlockEditionFactory.h, m2);
			}
		};
	}

	public static BlockChoix makeVarInt(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.varInt, m1, "Variable"){
			public BlockEdition getEd(){
				return new BlockVarI(BlockEditionFactory.h, m2, "x", true);
			}
		};
	}

	public static BlockChoix makeVarString(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.varString, m1, "Variable"){
			public BlockEdition getEd(){
				return new BlockVarS(BlockEditionFactory.h, m2, "s", true);
			}
		};
	}

	public static BlockChoix makeVarBool(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.varBool, m1, "Variable"){
			public BlockEdition getEd(){
				return new BlockVarB(BlockEditionFactory.h, m2, "b", true);
			}
		};
	}

	public static BlockChoix makeGreater(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.booleanColor, m1, "Superieur"){
			public BlockEdition getEd(){
				return BlockEditionFactory.makeGreater(m2);
			}
		};
	}

	public static BlockChoix makeSmaller(MouseAdapter m1, MouseAdapter m2){
		return new BlockChoix(120, BlockEditionFactory.h, Block.booleanColor, m1, "Inferieur"){
			public BlockEdition getEd(){
				return BlockEditionFactory.makeSmaller(m2);
			}
		};
	}
}
