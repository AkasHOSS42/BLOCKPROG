import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Dimension;

/**
 * bloc représentant l'instruction conditionnelle if ainsi que son else.
 * @see BlockEdition
 * */
public class BlocIfElse extends BlockEdition {
	private BlocIF blocIF;
	private BlocELSE blocELSE;

	public BlocIfElse (int l, int hi, int he, Color c, MouseAdapter m,Color c2, boolean isExpr, IF n){
		super(Ligne.l,hi+he,c, null, isExpr, n);
		setLayout(new FlowLayout(FlowLayout.LEFT,0,0));

		Unaire opif =  new Unaire(l,BlockEditionFactory.h,c,m,"IF",c2, isExpr, null);
		FinBlock fif = new FinBlock(l,BlockEditionFactory.h,c,"FIN IF", isExpr);
		blocIF = new BlocIF(l,hi,c,m,opif,fif,new ArrayList<BlockEdition>(),isExpr,n);
		opif.setBoucle(this);

		blocELSE = new BlocELSE(l+40,he,c,m,"ELSE",isExpr,n, blocIF);
		this.add(blocIF);
		this.add(blocELSE);
	}

	public BlocIfElse (BlocIF bif, BlocELSE belse, MouseAdapter m){
		super(Ligne.l, bif.getH()+belse.getH(), bif.getC(), null,bif.isExpr(), bif.getAST());
		setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
		blocIF = bif;
		blocELSE = belse;
		this.add(blocIF);
		this.add(blocELSE);
	}

	public BlocIF getBlocIf(){
		return blocIF;
	}
	public BlocELSE getBlocElse(){
		return blocELSE;
	}

	@Override
	public boolean hasSon(){
		return blocIF.hasSon() || blocELSE.hasSon();
	}
	@Override
	public BlocIfElse copie(MouseAdapter m){
		return (BlocIfElse)BlockEditionFactory.makeIF(m);
		/*BlocIF nouvIf = blocIF.copie(m);

	  BlocELSE nouvElse = blocELSE.copie(null, nouvIf);
	  BlocIfElse nouv = new BlocIfElse(nouvIf, nouvElse, m);
	  ((Unaire)nouvIf.getOp()).setBoucle(nouv);
	  return nouv;*/
	}

	public void add(BlockEdition bloc, int posX, int posY){
		int ajout =0;
		if(posY <= blocIF.getH()){
			ajout += blocIF.add(bloc, posX, posY);
		}else{
			ajout+=blocELSE.add(bloc, posX, posY-blocIF.getH());
		}
		hauteur += ajout;
		setPreferredSize(new Dimension(Ligne.l, hauteur));
	}

	public void removeLigne(int yPos){
		int asupp = 0;
		if(yPos <= blocIF.getH()){
			asupp = asupp + blocIF.removeLigne(yPos);
		}else{
			asupp = asupp + blocELSE.removeLigne(yPos-blocIF.getH());
		}
		hauteur -= asupp;
		setPreferredSize(new Dimension(Ligne.l,hauteur));
	}

	public int maxWidth(){
		return Math.max(blocIF.maxWidth(), blocELSE.maxWidth());
	}

	public void setWidth(int m){
		setSize(m, getHeight());
		blocIF.setWidth(m);
		blocELSE.setWidth(m);
	}
}
