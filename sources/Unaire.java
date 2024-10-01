import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;
/**
 *Un bloc de la zone edition pour une operation unaire.
 * @see Operator */
public class Unaire extends Operator{
	/**
	 * l'argument unique de l'operateur
	 */
	protected JPanel fils=new JPanel();

	protected JPanel nom=new JPanel();

	/**
	 *La representation algebrique de l'operateur. Par exemple, pour la negation, on utilisera "!".*/
	protected String label;

	/**
	 *La couleur de l'argument.*/
	protected Color c2;

	/**
	 * attribut correspondant a la boucle dont l'operateur est l'instance, si la boucle existe
	 */
	protected BlockEdition inBoucle;

	public Unaire(int l, int h, Color c, MouseAdapter m, String label, Color c2, boolean b, Operation n){
		super(l, h, c, m, b, n);
		setLayout(new BorderLayout(0, 0));
		this.label = label;
		this.c2 = c2;
		this.inBoucle = null;

		int w=20;
		if("WHILE".equals(label) || "ELSE".equals(label))
			w=80;
		fils.setPreferredSize(new Dimension(l-w, h));
		nom.setPreferredSize(new Dimension(w, h));
		add(nom, BorderLayout.LINE_START);
		add(fils, BorderLayout.LINE_END);

		fils.setBackground(c2);
		nom.setBackground(Color.pink);
		nom.add(new JLabel(label));
	}

	/**
	 * @param m : un controleur
	 * @return une copie de l'instance avec le controleur m
	 */
	@Override
	public Unaire copie(MouseAdapter m){
		Unaire tmp =  new Unaire(longueur,hauteur,couleur,m, label, c2, isExpr, (Operation)ast);
		tmp.inBoucle =  inBoucle;
		return tmp;
	}

	protected boolean setArg(Noeud n){
		return ((Operation)ast).setVal1(n);
	}

	@Override
	public void addD(BlockEdition b){
		if(!setArg(b.getAST())) return;
		Dimension d1=fils.getSize();
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
		remove(fils);
		fils=b;
		add(fils, BorderLayout.LINE_END);
		((BlockEdition)fils).setDad(this, false);
	}

	@Override
	public void addG(BlockEdition b){
		addD(b);
	}


	public boolean hasSon(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		return ((Operation)ast).hasSon();
	}

	@Override
	public boolean hasSon(){
		if(fils instanceof BlockEdition && ((BlockEdition)fils).getAST()!= null) return true;
		if(inBoucle != null) {
			return inBoucle.hasSon();
		}
		return ((Operation)ast).hasSon();
	}

	/* Il y a plusieurs cas à vérifier. Si b est déposé sur autre chose qu'une opération,
       il est clair que l'utilisateur veut remplacer fils par b. Si fils est une opération et
       que b est posé sur fils, alors b doit être ajouté en argument de fils.
       Si b est déposé sur l'opérateur, alors l'utilisateur veut remplacer this pas b
       dans l'opération father. */
	public void add(BlockEdition b, int posX){
		if(posX<0||posX>getWidth()) return;
		if(posX>=fils.getX()){
			if(!(fils instanceof Operator))
				addG(b);
			else
				((Operator)fils).add(b, posX-fils.getX());
		} else if (father==null) return;
		else if(isLeft)
			father.addG(b);
		else
			father.addD(b);
	}


	public BlockEdition inBoucle(){
		return inBoucle;
	}

	public void setBoucle(BlockEdition boucle){
		inBoucle = boucle;
	}
}

/* Comme pour les affectations avec binaire, on le fait etendre unaire
 pour factoriser du code. Il faut cependant faire quelques amenagements car l'AST
 lie a ce bloc n'est plus une instance d'Operation.*/

/**
 * Bloc qui permet d'afficher sur le terminal son argument.
 *@see Print
 */
class BlocPrint extends Unaire{
	public BlocPrint(MouseAdapter m){
		super(100, BlockEditionFactory.h, Block.voidColor, m, "Affiche", Block.jokerColor, false, null);
		nom.setPreferredSize(new Dimension(80, BlockEditionFactory.h));
		fils.setPreferredSize(new Dimension(20, BlockEditionFactory.h));
		ast=new Print();
	}

	@Override
	protected boolean setArg(Noeud n){
		return ((Print)ast).setVal1(n);
	}

	@Override
	public BlocPrint copie(MouseAdapter m){
		return new BlocPrint(m);
	}

	@Override
	public boolean hasSon(){
		if(inBoucle != null) {
			return inBoucle.hasSon();
		}
		return ((Print)ast).hasSon();
	}
}
