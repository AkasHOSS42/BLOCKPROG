import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 *Une simple classe qui contient les methodes pour construire les differents BlockEdition du projet.
 *@see BlockEdition*/
public class BlockEditionFactory{
	public static final int h=30; //la hauteur constante d'un block dans la zone edition.

	public static BlockEdition makeIF(MouseAdapter m){
		return new BlocIfElse(40, h*3, h*3, Block.booleanColor, m, Block.booleanColor, false, new IF());
	}

	public static BlockEdition makeWHILE(MouseAdapter m){
		return new BlocWHILE(120, h*3, Block.booleanColor, m, "WHILE", Block.booleanColor, false, new WHILE());
	}

	public static BlockEdition makeSUM(MouseAdapter m){
		return new Binaire(60, h, Block.intColor, m, "+", Block.intColor, true, new Addition());
	}

	public static BlockEdition makeDIFF(MouseAdapter m){//soustraction
		return new Binaire(60, h, Block.intColor, m, "-", Block.intColor, true, new Soustraction());
	}

	public static BlockEdition makePROD(MouseAdapter m){
		return new Binaire(60, h, Block.intColor, m, "*", Block.intColor, true, new Multiplication());
	}

	public static BlockEdition makeQUOT(MouseAdapter m){
		return new Binaire(60, h, Block.intColor, m, "/", Block.intColor, true, new Division());
	}

	public static BlockEdition makeNOT(MouseAdapter m){
		return new Unaire(40, h, Block.booleanColor, m, "!", Block.booleanColor, true, new NOT());
	}

	public static BlockEdition makeOR(MouseAdapter m){
		return new Binaire(60, h, Block.booleanColor, m, "||", Block.booleanColor, true, new OR());
	}

	public static BlockEdition makeAND(MouseAdapter m){
		return new Binaire(60, h, Block.booleanColor, m, "&&", Block.booleanColor, true, new AND());
	}

	public static BlockEdition makeEQUALS_boolean(MouseAdapter m){
		return new Binaire(60, h, Block.booleanColor, m, "==", Block.booleanColor, true, new EqualsBool());
	}

	public static BlockEdition makeEQUALS_int(MouseAdapter m){
		return new Binaire(60, h, Block.intColor, m, "==", Block.intColor, true, new EqualsInt());
	}

	public static BlockEdition makeEQUALS_string(MouseAdapter m){
		return new Binaire(60, h, Block.stringColor, m, "==", Block.stringColor, true, new EqualsString());
	}

	public static BlockEdition makeCCT(MouseAdapter m){//concatenation
		return new Binaire(60, h, Block.stringColor, m, ".", Block.stringColor, true, new Concatenation());
	}

	public static BlockEdition makeCST_boolean(MouseAdapter m){
		return new BlockCST_boolean(h, m, false);
	}

	public static BlockEdition makeCST_int(MouseAdapter m){
		return new BlockCST_int(h, m, 0);
	}

	public static BlockEdition makeCST_string(MouseAdapter m){
		return new BlockCST_string(h, m, "Lorem ipsum");
	}

	public static BlockEdition deplace(MouseAdapter m, Picture p){
		BlockEdition b=new BlockEdition(80, h, Block.voidColor, m, false, Move.deplace(p, 9));
		b.add(new JLabel("Avance"));
		return b;
	}

	public static BlockEdition turnRight(MouseAdapter m, Picture p){
		BlockEdition b=new BlockEdition(120, h, Block.voidColor, m, false, Move.turnRight(p));
		b.add(new JLabel("Tourne a droite"));
		return b;
	}

	public static BlockEdition turnLeft(MouseAdapter m, Picture p){
		BlockEdition b=new BlockEdition(120, h, Block.voidColor, m, false, Move.turnLeft(p));
		b.add(new JLabel("Tourne a gauche"));
		return b;
	}

	public static BlockEdition demiTour(MouseAdapter m, Picture p){
		BlockEdition b=new BlockEdition(80, h, Block.voidColor, m, false, Move.demiTour(p));
		b.add(new JLabel("Demi tour"));
		return b;
	}

	public static BlockEdition usePen(MouseAdapter m, Picture p){
		BlockEdition b=new BlockEdition(80, h, Block.voidColor, m, false, Move.usePen(p));
		b.add(new JLabel("Trace"));
		return b;
	}

	public static BlockEdition salut(MouseAdapter m, Picture p){
		BlockEdition b=new BlockEdition(80, h, Block.voidColor, m, false, Move.salut(p));
		b.add(new JLabel("Salut"));
		return b;
	}

	public static BlockEdition makeGreater(MouseAdapter m){
		return new Binaire(60, h, Block.intColor, m, ">", Block.intColor, true, new GREATER());
	}

	public static BlockEdition makeSmaller(MouseAdapter m){
		return new Binaire(60, h, Block.intColor, m, "<", Block.intColor, true, new SMALLER());
	}
}
