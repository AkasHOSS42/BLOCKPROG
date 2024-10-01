import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 * représente les boucles while, les blocs if et les blocs else
 * */
public class Boucle extends BlockEdition{
	protected Operator operation;
	protected FinBlock fin;
	/**
	 * la liste des blocs contenus dans la boucle 
	 * */
	protected ArrayList<BlockEdition> liste;

	/**
	 * liste d'instruction correspondant à la liste des blocs
	 */
	protected ListInstr contenu;

	/**
	 * JPanel qui contient la liste de blocs dans la vue
	 */
	protected JPanel center; 


	public Boucle(int l, int h, Color c, MouseAdapter m, String label, Color c2, boolean isExpr, Noeud n){
		this(l,h,c,m,new Unaire(l,BlockEditionFactory.h,c,m,label,c2, isExpr, null), new FinBlock(l,BlockEditionFactory.h,c, "FIN "+label, isExpr),  new ArrayList<BlockEdition>(), isExpr, n);
		((Unaire)operation).setBoucle(this);
	}

	public Boucle(int l, int h, Color c, MouseAdapter m,Operator op, FinBlock f, ArrayList<BlockEdition> list, boolean isExpr, Noeud n){
		super(l,h,c,null,isExpr, n);
		hauteur = h;
		operation = op;

		if(operation instanceof Unaire){
			((Unaire)operation).setBoucle(this);
		}
		fin = f;
		center = new JPanel();
		center.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 2));
		liste = list;
		for(BlockEdition b : liste){
			if(b.getH()  == BlockEditionFactory.h)  center.add(new Ligne(b, Ligne.l));
			else center.add(b);
		}
		JPanel indentation = new JPanel();
		indentation.setPreferredSize(new Dimension(20, BlockEditionFactory.h));
		indentation.setBackground(Color.white);

		setLayout(new BorderLayout());
		add(new Ligne(operation, Ligne.l), BorderLayout.NORTH);
		add(new Ligne(fin, Ligne.l),BorderLayout.SOUTH);
		add(indentation,BorderLayout.WEST);
		add(center, BorderLayout.CENTER);
		setPreferredSize(new Dimension(Ligne.l, hauteur));
		setBackground(Color.gray);
		this.validate();
	}

	public BlockEdition getOp(){
		return operation;
	}

	public int add(BlockEdition block, int posX, int posY){
		int ajout = 0;
		Ligne l=(Ligne)((BorderLayout)getLayout()).getLayoutComponent(this, BorderLayout.NORTH); //récupérer l'entête de la boucle
		if(posY>=0 && posY <=(int)l.getSize().getHeight() && block.isExpr()){ //sur l'entete
			l.add(block, posX-l.getX());
			ajout = 0;
		}else{ // dans le corps de la boucle
			posY-=(int)l.getSize().getHeight(); // remettre l'origine des y au niveau du centre de la boucle
			int y=getNLigne(posY);
			posX-=(int)((BorderLayout)getLayout()).getLayoutComponent(this, BorderLayout.WEST).getSize().getWidth();

			JPanel jp=block;
			if(!(block instanceof Boucle || block instanceof BlocIfElse)) // quand block prend une seule ligne
				jp=new Ligne(block, Ligne.l);
			if(y==-1){
				if(!contenu.add(block.getAST())) return 0;
				center.add(jp);
				liste.add(block);

				ajout = ajout + block.getH();
				hauteur += block.getH();

				setPreferredSize(new Dimension(Ligne.l,hauteur));

			}else{
				Component ligne=center.getComponent(y);

				if(ligne instanceof Boucle){
					((Boucle)ligne).add(block, posX-ligne.getX(), posY-ligne.getY());

					if(!block.isExpr()){
						ajout+= block.getH();
						hauteur += block.getH();
						setPreferredSize(new Dimension(Ligne.l,hauteur));
					}

				}else if (ligne instanceof BlocIfElse){
					((BlocIfElse)ligne).add(block, posX-ligne.getX(), posY-ligne.getY());

					if(!block.isExpr()){
						ajout+= block.getH();
						hauteur += block.getH();
						setPreferredSize(new Dimension(Ligne.l,hauteur));
					}
				}
				else if(block.isExpr()){
					((Ligne)ligne).add(block, posX-ligne.getX());
				}
				else{
					if(!contenu.add(y,block.getAST())) return 0;
					center.add(jp, y);
					liste.add(y, block);

					hauteur += block.getH();
					setPreferredSize(new Dimension(Ligne.l,hauteur));
				}
			}
		}
		validate();
		return ajout;
	}

	public int removeLigne(int yPos){
		int y=getNLigne(yPos);
		int asupp = 0;
		if(y==-1) return 0;
		Component c=center.getComponent(y);
		if(c instanceof Boucle){
			yPos = yPos-c.getY() - (int)((BorderLayout)((Container)c).getLayout()).getLayoutComponent(this,BorderLayout.NORTH).getSize().getHeight();
			if(yPos>0){
				asupp = asupp + ((Boucle)c).removeLigne(yPos);
				hauteur -= asupp;
				setPreferredSize(new Dimension(Ligne.l,hauteur));
				return asupp;
			}
		}
		contenu.remove(y);
		center.remove(y);
		liste.remove(y);
		asupp =(int)c.getPreferredSize().getHeight(); 
		hauteur-=asupp;
		setPreferredSize(new Dimension(Ligne.l,hauteur));
		return asupp;
	}


	public int getNLigne(int yPos){
		int ans=0;
		Component[] comps=center.getComponents();
		int len=comps.length;
		if(len==0) return -1;
		for(int y=2+(int)comps[0].getSize().getHeight(); y<yPos; y+=(int)comps[ans].getSize().getHeight()+2){// le +2 vient de l'espacement vertical
			ans++;
			if(ans==len) return -1;
		}
		return ans;
	}

	@Override
	public Boucle copie(MouseAdapter m){
		Operator operation = this.operation.copie(null);
		FinBlock fin = this.fin.copie(null);
		Boucle copie = new Boucle(longueur, hauteur, couleur, m, operation, fin,liste, isExpr, ast);
		return copie;
	}
	/**
	 *	@return true si la boucle est non vide
	 * */
	public boolean hasSon(){
		return liste.size() != 0;
	}

	public void setControleur(MouseAdapter nouveau){
		operation.setControleur(nouveau);
	}

	public int maxWidth(){
		int ans=Ligne.l;
		for(BlockEdition b : liste){
			if(b instanceof Boucle)
				ans=Math.max(ans, ((Boucle)b).maxWidth());
			else if(b instanceof BlocIfElse)
				ans=Math.max(ans, ((BlocIfElse)b).maxWidth());
			else
				ans=Math.max(ans, b.getWidth());
		}
		return ans+center.getX();
	}

	public void setWidth(int m){
		setSize(m, getHeight());
		for(BlockEdition b : liste){
			if(b instanceof Boucle)
				((Boucle)b).setWidth(m-center.getX());
			else if(b instanceof BlocIfElse)
				((BlocIfElse)b).setWidth(m-center.getWidth());
			else
				b.setSize(Math.max(b.getWidth(), m-center.getX()), b.getHeight());
		}
	}
}

class BlocIF extends Boucle{
	public BlocIF(int l, int h, Color c, MouseAdapter m, String label, Color c2, boolean isExpr, IF n){
		this(l, h, c, m, new Unaire(l,BlockEditionFactory.h,c,m,label,c2, isExpr, null), 
				new FinBlock(l,BlockEditionFactory.h,c, "FIN "+label, isExpr),  new ArrayList<BlockEdition>(), isExpr, n);
	}

	public BlocIF(int l, int h, Color c, MouseAdapter m,Operator op, FinBlock f, ArrayList<BlockEdition> list, boolean isExpr, IF n){
		super(l, h, c, m, op, f, list, isExpr, n);
		operation.ast=new Operation<Boolean, Boolean>(){
			public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				return val1.evalue(intEnv, sEnv, bEnv);
			}

			public String getType(){
				return Boolean.class.getName();
			}

			public String getArgType(){return Boolean.class.getName();}

			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				return val1!=null && val1.isSet(intEnv, sEnv, bEnv);
			}
		};
		n.setC_if((Expression<Boolean>)operation.ast);
		contenu=n.getInstr1();
	}

	@Override
	public BlocIF copie(MouseAdapter m){
		Operator operation = this.operation.copie(m);
		FinBlock fin = this.fin.copie(null);
		BlocIF copie = new BlocIF(longueur, hauteur, couleur, m, operation, fin,liste, isExpr, (IF)ast);
		return copie;
	}
}

class BlocELSE extends Boucle{
	public BlocELSE(int l, int h, Color c, MouseAdapter m, String label,/* Color c2,*/boolean isExpr, IF n, BlocIF thi){
		this(l,h,c,m,new Unaire(l,BlockEditionFactory.h,c,null,label,null,isExpr, null),new FinBlock(l,BlockEditionFactory.h,c,"FIN "+label, isExpr), new ArrayList<BlockEdition>(), isExpr,n, thi);
		setBackground(Color.gray);
	}
	public BlocELSE(int l, int h, Color c, MouseAdapter m,Operator op, FinBlock f, ArrayList<BlockEdition> list, boolean isExpr, IF n, BlocIF thi){
		super(l, h, c,null, op, f, list, isExpr, n);
		operation.ast=new Operation<Boolean, Boolean>(){
			public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				return ((Operation<Boolean, Boolean>)thi.operation.getAST()).evalue(intEnv, sEnv, bEnv);
			}

			public String getType(){
				return Boolean.class.getName();
			}

			public String getArgType(){return Boolean.class.getName();}

			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				return thi.operation.getAST().isSet(intEnv, sEnv, bEnv);
			}
		};

		//n.setC_if((Expression<Boolean>)operation.ast);
		contenu=n.getInstr2();
	}

	public BlocELSE copie(MouseAdapter m,BlocIF thi){
		Operator operation = this.operation.copie(null);
		FinBlock fin = this.fin.copie(null);
		BlocELSE copie = new BlocELSE(longueur, hauteur, couleur, null, operation, fin,liste, isExpr, (IF)ast,thi.copie(m));
		return copie;
	}


}

class BlocWHILE extends Boucle{
	public BlocWHILE(int l, int h, Color c, MouseAdapter m, String label, Color c2, boolean isExpr, WHILE n){
		this(l, h, c, m, new Unaire(l,BlockEditionFactory.h,c,m,label,c2, isExpr, null), 
				new FinBlock(l,BlockEditionFactory.h,c, "FIN "+label, isExpr),  new ArrayList<BlockEdition>(), isExpr, n);

		setBackground(Color.lightGray);
	}

	public BlocWHILE(int l, int h, Color c, MouseAdapter m,Operator op, FinBlock f, ArrayList<BlockEdition> list, boolean isExpr, WHILE n){
		super(l, h, c, m, op, f, list, isExpr, n);
		operation.ast=new Operation<Boolean, Boolean>(){
			public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				return val1.evalue(intEnv, sEnv, bEnv);
			}

			public String getType(){
				return Boolean.class.getName();
			}

			public String getArgType(){return Boolean.class.getName();}

			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				return val1!=null && val1.isSet(intEnv, sEnv, bEnv);
			}
		};
		n.setCondition((Expression<Boolean>)operation.ast);
		contenu=n.getInstruction();
	}

	@Override
	public BlocWHILE copie(MouseAdapter m){
		Operator op = this.operation.copie(m);
		FinBlock f = this.fin.copie(null);
		BlocWHILE copie = new BlocWHILE(longueur, hauteur, couleur, m, op, f,liste, isExpr, (WHILE)ast);
		return copie;
	}
}
