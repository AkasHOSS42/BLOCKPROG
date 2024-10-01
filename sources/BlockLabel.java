import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 *Un BlockEdition qui contient un champ de texte modifiable au clavier par l'utilisateur.*/
public abstract class BlockLabel extends BlockEdition{
	/**
	 *Une zone qui sera coloriee en violet, et qui est la zone "attrappable" quand on veut deplacer this.
	 *On la garde en attribut si on veut justement rendre this indeplacable.
	 *@see BlockAffectationInt
	 *@see BlockAffectationString
	 *@see BlockAffectationBool*/
	protected JPanel gauche;
	protected static Font police=new Font("Arial", Font.PLAIN, 12);
	protected JPanel center=new JPanel();

	public BlockLabel(int l, int h, Color c, MouseAdapter m, boolean b, Noeud n){
		super(l, h, c, m, b, n);
		setLayout(new BorderLayout(0, 0));

		center.setBackground(c);
		center.setLayout(null);
		center.setPreferredSize(new Dimension(0, h));
		add(center, BorderLayout.CENTER);
		label.setBackground(c);
		center.add(label);
		label.setBounds(0, 0, 0, h);

		gauche=new JPanel();
		gauche.setBackground(Block.gripColor);
		gauche.setPreferredSize(new Dimension(10, h));
		add(gauche, BorderLayout.LINE_START);

		setPreferredSize(getSize());

		validate();
	}

	protected JTextField label=new JTextField("");

	/**
	 *Modifie le champ de texte, en adaptant la taille du bloc en fonction de la
	 *longueur de la nouvelle chaine de caracteres.*/
	protected void setText(String s){
		label.setText(s);
		label.setFont(police);
		FontMetrics mesure=label.getFontMetrics(police);
		int len=javax.swing.SwingUtilities.computeStringWidth(mesure, s);
		setPreferredSize(new Dimension(len+30, hauteur));
		center.setPreferredSize(new Dimension(len+10, hauteur));
		label.setPreferredSize(new Dimension(len+10, label.getHeight()));
		if(isLeft&&father!=null)
			father.addG(this);
		else if(father!=null)
			father.addD(this);
		setSize(getPreferredSize());
		center.setSize(center.getPreferredSize());
		label.setBounds(0, 0, len+10, hauteur);
		validate();
	}

	/**
	 *@return Le contenu actuel du champ de texte.*/
	public String getText(){
		return label.getText();
	}
}

/*On etends ensuite BlockLabel pour implementer les constantes.*/

/**
 *Un bloc de la zone edition pour une constante entiere.
 *@see Constante*/
class BlockCST_int extends BlockLabel{
	/**
	 *@param h La hauteur du bloc.
	 *@param m Le controleur du bloc.
	 *@param arg Le contenu de depart du champ de texte, ainsi que la valeur de depart de l'AST.*/
	public BlockCST_int(int h, MouseAdapter m, int arg){
		super(20, h, Block.intColor, m, true, new Constante<Integer>(arg));
		setText(arg+"");
		label.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String cmd=e.getActionCommand();
				int i=0;
				try{
					i=Integer.parseInt(cmd);
				}catch(NumberFormatException ex){}//la chaine entree n'est pas un nombre
				((Constante<Integer>)ast).setVal(i);
				setText(i+"");
			}
		});
	}

	public BlockCST_int copie (MouseAdapter m){
		BlockCST_int copie = new BlockCST_int(hauteur, m, ((Constante<Integer>)ast).evalue(null, null, null));//pas besoin d'environnement
		return copie;
	}
}

/**
 *Un bloc de la zone edition pour une constante de type String.
 *@see Constante*/
class BlockCST_string extends BlockLabel{
	/**
	 *@param h La hauteur du bloc.
	 *@param m Le controleur du bloc.
	 *@param arg Le contenu de depart du champ de texte, ainsi que la valeur de depart de l'AST.*/
	public BlockCST_string(int h, MouseAdapter m, String arg){
		super(20, h, Block.stringColor, m, true, new Constante<String>(arg));
		setText(arg);
		label.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String cmd=e.getActionCommand();
				((Constante<String>)ast).setVal(cmd);
				setText(cmd);
			}
		});
	}

	public BlockCST_string copie (MouseAdapter m){
		BlockCST_string copie = new BlockCST_string(hauteur, m, ((Constante<String>)ast).evalue(null, null, null));//pas besoin d'environnement
		return copie;
	}
}

/**
 *Un bloc de la zone edition pour une constante booleene.
 *@see Constante*/
class BlockCST_boolean extends BlockLabel{
	/**
	 *@param h La hauteur du bloc.
	 *@param m Le controleur du bloc.
	 *@param arg Le contenu de depart du champ de texte, ainsi que la valeur de depart de l'AST.*/
	public BlockCST_boolean(int h, MouseAdapter m, boolean arg){
		super(20, h, Block.booleanColor, m, true, new Constante<Boolean>(arg));
		setText(arg+"");
		label.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String cmd=e.getActionCommand();
				boolean b=Boolean.parseBoolean(cmd);
				((Constante<Boolean>)ast).setVal(b);
				setText(b+"");
			}
		});
	}

	public BlockCST_boolean copie (MouseAdapter m){
		BlockCST_boolean copie = new BlockCST_boolean(hauteur, m, ((Constante<Boolean>)ast).evalue(null, null, null));//pas besoin d'environnement
		return copie;
	}
}
