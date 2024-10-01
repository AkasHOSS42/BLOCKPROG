import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import java.util.*;
import java.io.IOException;

public class Fenetre {
	private Vue vue;

	/**
	 * panel pour deplacer les blocs d'une partie a une autre de la fenetre
	 */
	private JPanel glassPane = new JPanel();

	/**
	 *bloc qu'on est en train de deplacer sur la fenetre
	 */
	private Block dragged;

	/**
	 *	l'image du sprite correspondant à l'action qu'il effectue
	 */ 
	private Picture picture;

	public Fenetre () throws IOException{
		picture=new Picture();
		vue = new Vue();
		picture.setVue(vue);
		vue.setGlassPane(glassPane);
		glassPane.setBackground(Color.green);
		glassPane.setLayout(null);
		glassPane.addMouseListener(vue.controleurG);
		vue.pack();
		vue.setVisible(true);
	}

	/**
	 *	l'interface graphique
	 */ 
	public class Vue extends JFrame{
		/**
		 * zone contenant le sprite
		 */ 
		private Execution execution;

		/**
		 *	zone contenant des onglets avec les blocs disponibles pour construire un programme
		 */ 
		private JTabbedPane choix;

		/**
		 * zone d'edition de programmes
		 */ 
		private Edition edition = new Edition(); // avec JScrollBar et JViewPort

		/**
		 * 	controleur qui gere les deplacements de la zone choix et depose le bloc deplace sur le glassPane
		 */ 
		ControleurChoix controleurC = new ControleurChoix();

		/**
		 *Le controleur qui gere les situations ou un bloc est glisse-depose.*/
		ControleurGlass controleurG = new ControleurGlass();

		/**
		 * controleur gerant les deplacements de la zone edition et depose le bloc deplace sur le glassPane
		 */ 
		ControleurEdition controleurE = new ControleurEdition();

		class Edition extends JScrollPane{
			/**
			 * espace de creation de programmes
			 */ 

			private JPanel viewport;
			/**
			 * liste des programmes crees
			 */ 
			private ArrayList<Programme> programmes;
			private JScrollBar horizontalScrollBar;

			/** En fonction de la position de la barre de scrolling,
			 * cette fonction prends en @param xposOnScreen une abscisse (en coordonnées absolues par rapport à l'écran)
			 * et @return le numéro du programme de la liste qui a pour abscisse sur l'écran l'argument.
			 * @return -1 si aucun programme n'a cette abscisse.*/
			public int getNProg(int xposOnScreen){
				int xpos=xposOnScreen-Fenetre.this.vue.getX()-Fenetre.this.vue.getInsets().left-getX();
				if(xpos<0) return -1;
				int len=programmes.size();
				int ans=-1;
				xpos+=horizontalScrollBar.getValue();
				while(xpos>=0 && ans<len){
					ans++;
					xpos-=programmes.get(ans).getWidth();
				}
				if(ans==len)
					return -1;
				return ans;
			}

			/**
			 * suppression d'un Programme @param p, soit une colonne de l'edition
			 */ 
			void remove(Programme p){
				viewport.remove(p);
				programmes.remove(p);
				viewport.validate();
				viewport.repaint();
			}

			/**
			 * recuperer le n-ieme programme en fonction de la position de la souris sur l'ecran
			 * @param xposOnScreen Position de la souris a l'ecran
			 * @return le nieme programme
			 */ 
			public Programme getNthProg(int xposOnScreen){
				int n=getNProg(xposOnScreen);
				if(n==-1) return null;
				return programmes.get(n);
			}

			public Edition(){
				super();
				viewport = new JPanel();

				//barre horizontale
				horizontalScrollBar = createHorizontalScrollBar();
				setHorizontalScrollBar(horizontalScrollBar);
				setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				setLayout(new ScrollPaneLayout());

				//l'interieur: JViewport = JPanel
				viewport.setLayout(new FlowLayout(FlowLayout.LEFT));
				viewport.setBackground(Color.green);
				viewport.setAutoscrolls(true); //defilement du scroll quand on approche un block du bord
				this.setViewportView(viewport);

				// ce que contient la zone édition
				programmes = new ArrayList<Programme>();

				//ajouter des programmes
				JMenuBar menuBar = new JMenuBar();
				JButton addProg = new JButton("Ajouter un Programme");
				addProg.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						Programme p = new Programme();
						programmes.add(p);
						viewport.add(p);
						viewport.validate();
						viewport.repaint();
					}
				});
				menuBar.add(addProg);
				setColumnHeaderView(menuBar);
				//ajouter un programme dès le début
				/*
		  Programme p = new Programme();
		  programmes.add(p);
		  viewport.add(p);
				 */
			}

			/**
			 *Ajoute un bloc a un programme.
			 * @param block Le bloc a ajouter.
			 * @param numColonne numero du programme auquel il faut ajouter le bloc.
			 * @param posX abscisse du curseur de la souris relative a la zone edition.
			 * @param posY ordonnee du curseur de la souris relative a la zone edition.
			 */ 
			public void add (BlockEdition block, int numColonne, int posX, int posY){
				Programme p = programmes.get(numColonne);
				int y = posY - Fenetre.this.vue.getY() - Fenetre.this.vue.getInsets().top - getY() - p.getY();
				int x = posX-Fenetre.this.vue.getX()-Fenetre.this.vue.getInsets().left-getX() -p.getX() + horizontalScrollBar.getValue();
				p.add(block,x, y-(int)getColumnHeader().getViewSize().getHeight());
			}

			class Programme extends JScrollPane{
				private JPanel viewport;
				private ListInstr ast=new ListInstr();
				private JButton launcher=new JButton("Executer");

				/**Rend cliquable le bouton d'execution si le programme est bien construit. */
				public void update(){
					launcher.setEnabled(ast.isSet(new ListMap<Integer>(), new ListMap<String>(), new ListMap<Boolean>()));

					/*Ci-dessous une tentative pour adapter dynamiquement la largeur d'un programme en fonction de la
		      longueur de sa plus grande ligne. Les lignes de print vous montrerons que le calcul de la nouvelle taille
		      se realise correctement. C'est le changement de taille lui meme qui ne se fait pas.*/

					/*int max=maxWidth();
		      Component[] comps=viewport.getComponents();
		      for(Component comp : comps){
		      if(comp instanceof Boucle)
		      ((Boucle)comp).setWidth(max);
		      else if(comp instanceof BlocIfElse)
		      ((BlocIfElse)comp).setWidth(max);
		      else
		      comp.setSize(Math.max(comp.getWidth(), max), comp.getHeight());
		      }
		      System.out.println("max = "+max);*/
					//setPreferredSize(new Dimension(max, getHeight()));
					//setBounds(getX(), getY(), max, getHeight());
					//viewport.setPreferredSize(new Dimension(max, viewport.getHeight()));
					//viewport.setSize(viewport.getPreferredSize());
					/*viewport.setBounds(getX(), getY(), max, getHeight());
		      System.out.println("width = "+getWidth());*/
				}

				public Programme(){
					super();
					viewport = new JPanel();
					viewport.setBackground(Color.orange);
					FlowLayout layout = new FlowLayout(FlowLayout.LEFT,0,2);
					viewport.setLayout(layout);

					//définir une taille par défaut d'un programme
					Dimension size = vue.edition.getSize();
					Dimension min = new Dimension(Ligne.l, (int)(size.getHeight()-5));
					viewport.setMinimumSize(min);
					viewport.setPreferredSize(min);
					setViewportView(viewport);

					//barre verticale
					JScrollBar verticalScrollBar = createVerticalScrollBar();
					setVerticalScrollBar(verticalScrollBar);
					setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED); //apparait que si on en a besoin
					setAlignmentX(LEFT_ALIGNMENT);

					//bouton pour executer
					JMenuBar mb=new JMenuBar();
					mb.add(launcher);
					launcher.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							WorkerUtilities.invoke(new Instruction(){
								public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
									ast.execute(new ListMap<Integer>(), new ListMap<String>(), new ListMap<Boolean>());
								}

								public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){return true;}
								public String getType(){return "Instruction";}
							});
						}});

					//bouton pour supprimer
					JButton remove=new JButton("Supprimer");
					Programme pr=this;
					remove.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){Fenetre.this.vue.edition.remove(pr);}
					});
					mb.add(remove);

					setColumnHeaderView(mb);
					update();
				}

				/**
				 *	recuperer la ligne correspondant a une ordonnee
				 * @param yPos Ordonnee
				 * @return numero de la ligne
				 */ 
				public int getNLigne(int yPos){
					int ans=0;
					Component[] comps=viewport.getComponents();
					int len=comps.length;
					if(len==0) return -1;
					for(int y=2+(int)comps[0].getSize().getHeight(); y<yPos; y+=(int)comps[ans].getSize().getHeight()+2){// le +2 vient de l'espacement vertical
						ans++;
						if(ans==len) return -1;
					}
					return ans;
				}

				/**
				 *@return La longueur de la plus grande ligne de ce programme.*/
				public int maxWidth(){
					int ans=Ligne.l;
					Component[] comps=viewport.getComponents();
					for(Component comp : comps){
						if(comp instanceof Boucle)
							ans=Math.max(ans, ((Boucle)comp).maxWidth());
						else if(comp instanceof BlocIfElse)
							ans=Math.max(ans, ((BlocIfElse)comp).maxWidth());
						else
							ans=Math.max(ans, ((Ligne)comp).getContentWidth());
					}
					return ans;
				}

				/**
				 *@param yPos L'ordonnee de la ligne a supprimer, relative au programme.*/
				public void removeLigne(int yPos){
					yPos-=(int)getColumnHeader().getViewSize().getHeight();
					int y=getNLigne(yPos);
					Component c=viewport.getComponent(y);
					if(c instanceof Boucle){
						yPos=yPos-c.getY()-(int)((BorderLayout)((Container)c).getLayout()).getLayoutComponent(this, BorderLayout.NORTH).getSize().getHeight();
						if(yPos>0){
							((Boucle)c).removeLigne(yPos);
							update();
							return;
						}
					}else if (c instanceof BlocIfElse){
						yPos = yPos-c.getY()-(int)((BorderLayout)((BlocIfElse)c).getBlocIf().getLayout()).getLayoutComponent(this, BorderLayout.NORTH).getSize().getHeight();
						if(yPos > 0){
							((BlocIfElse)c).removeLigne(yPos);
							update();
							return ;
						}
					}
					viewport.remove(y);
					ast.remove(y);
					update();
				}

				/**
				 *@param posY L'ordonnee de l'endroit ou ajouter le bloc, relative au programme.
				 *@param block Le bloc a ajouter.
				 *@param posX L'abscisse de l'endroit ou ajouter le bloc, relative au programme.*/
				public void add(BlockEdition block, int posX, int posY){
					if(block instanceof BlockVar)
						((BlockVar)block).setProg(this);
					if(block instanceof BlockAffectationInt)
						((BlockAffectationInt)block).setProg(this);
					if(block instanceof BlockAffectationString)
						((BlockAffectationString)block).setProg(this);
					if(block instanceof BlockAffectationBool)
						((BlockAffectationBool)block).setProg(this);
					/* on calcule quelle doit être la position verticale de block*/
					posY-=(int)getColumnHeader().getViewSize().getHeight();
					int y=getNLigne(posY);

					JPanel jp=block;
					if(!(block instanceof Boucle) && !(block instanceof BlocIfElse)) //block ne prend qu'une ligne 
						jp=new Ligne(block, Ligne.l);
					if(y==-1){
						if(ast.add(block.getAST()))
							viewport.add(jp);
					}else{
						Component ligne=viewport.getComponent(y);
						if(ligne instanceof Boucle){
							((Boucle)ligne).add(block, posX-ligne.getX(), posY-ligne.getY());
						}else if(ligne instanceof BlocIfElse){
							((BlocIfElse)ligne).add(block,posX-ligne.getX(), posY-ligne.getY());
						}
						else if(block.isExpr()) 
							((Ligne)ligne).add(block, posX-ligne.getX());
						else{
							if(ast.add(y, block.getAST()));
							viewport.add(jp, y);
						}
					}
					update();
				}
			}
		}

		public Vue() throws IOException{
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setDefaultLookAndFeelDecorated(true);
			this.setExtendedState(this.MAXIMIZED_BOTH);
			this.setLayout(new GridLayout(1, 2));

			execution = new Execution(picture);

			//initialisation de la zone CHOIX
			choix = new JTabbedPane(SwingConstants.TOP);

			//opérations sur les booléens
			JPanel bool=new JPanel();
			choix.addTab("Logique", bool);
			bool.setLayout(new FlowLayout(FlowLayout.LEFT));

			choix.setOpaque(true);

			//tout ce qui concerne les entiers
			JPanel nbr=new JPanel();
			choix.addTab("Entiers", nbr);
			nbr.setLayout(new FlowLayout(FlowLayout.LEFT));

			//tout ce qui concerne les chaînes
			JPanel str=new JPanel();
			choix.addTab("Chaînes", str);
			str.setLayout(new FlowLayout(FlowLayout.LEFT));

			//instructions
			JPanel instr=new JPanel();
			choix.addTab("Instructions", instr);
			instr.setLayout(new FlowLayout(FlowLayout.LEFT));

			//assemblement de tout
			JPanel gauche = new JPanel();
			this.add(gauche);
			gauche.setLayout(new GridLayout(2,1));

			gauche.add(execution);
			gauche.add(choix);
			this.add(edition, BorderLayout.CENTER);

			Picture pix=Fenetre.this.picture;

			//on commence à ajouter les vrais blocks
			instr.add(BlockChoixFactory.makeIF(controleurC, controleurE));
			instr.add(BlockChoixFactory.makeWHILE(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeSUM(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeDIFF(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makePROD(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeQUOT(controleurC, controleurE));
			bool.add(BlockChoixFactory.makeNOT(controleurC, controleurE));
			bool.add(BlockChoixFactory.makeOR(controleurC, controleurE));
			bool.add(BlockChoixFactory.makeAND(controleurC, controleurE));
			bool.add(BlockChoixFactory.makeEQUALS_boolean(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeEQUALS_int(controleurC, controleurE));
			str.add(BlockChoixFactory.makeEQUALS_string(controleurC, controleurE));
			str.add(BlockChoixFactory.makeCCT(controleurC, controleurE));
			bool.add(BlockChoixFactory.makeCST_boolean(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeCST_int(controleurC, controleurE));
			str.add(BlockChoixFactory.makeCST_string(controleurC, controleurE));
			instr.add(BlockChoixFactory.deplace(controleurC, controleurE, pix));
			instr.add(BlockChoixFactory.turnRight(controleurC, controleurE, pix));
			instr.add(BlockChoixFactory.turnLeft(controleurC, controleurE, pix));
			instr.add(BlockChoixFactory.demiTour(controleurC, controleurE, pix));
			instr.add(BlockChoixFactory.makePrint(controleurC, controleurE));
			instr.add(BlockChoixFactory.usePen(controleurC, controleurE,pix));
			instr.add(BlockChoixFactory.salut(controleurC, controleurE, pix));
			instr.add(BlockChoixFactory.makeAffectInt(controleurC, controleurE));
			instr.add(BlockChoixFactory.makeAffectString(controleurC, controleurE));
			instr.add(BlockChoixFactory.makeAffectBool(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeVarInt(controleurC, controleurE));
			str.add(BlockChoixFactory.makeVarString(controleurC, controleurE));
			bool.add(BlockChoixFactory.makeVarBool(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeGreater(controleurC, controleurE));
			nbr.add(BlockChoixFactory.makeSmaller(controleurC, controleurE));
			this.validate();
		}
	}

	/**
	 * Controleur de la zone choix.
	 * Quand le curseur passe au dessus d'un bloc, il est copie sur le glassPane.
	 */ 
	public class ControleurChoix extends MouseAdapter {        
		public void mouseEntered(MouseEvent e) {
			BlockChoix courant = (BlockChoix) e.getSource();
			dragged = courant.copie(vue.controleurG);
			glassPane.add(dragged);
			glassPane.setVisible(true);
			glassPane.setOpaque(false);
			dragged.setVisible(true);
			dragged.setOpaque(true);
			dragged.setBounds(e.getXOnScreen()-Fenetre.this.vue.getX()-Fenetre.this.vue.getInsets().left-e.getX(),
					e.getYOnScreen()-Fenetre.this.vue.getY()-Fenetre.this.vue.getInsets().top-e.getY(),
					courant.getW(), courant.getH());
			dragged.setPreferredSize(dragged.getSize());
			glassPane.setEnabled(true);
			dragged.addMouseListener(Fenetre.this.vue.controleurG);
			dragged.addMouseMotionListener(Fenetre.this.vue.controleurG);
		}
	}

	/**
	 * Controleur du glassPane.
	 * Permet de deplacer le bloc sur toute la fenetre.
	 */ 
	public class ControleurGlass extends MouseAdapter {
		int x,y;
		boolean pressed=false;

		public void mousePressed(MouseEvent e){
			x=e.getX();
			y=e.getY();
		}

		public void mouseDragged(MouseEvent e){
			pressed = true;

			if(dragged==null) return;
			Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			int hauteur = (int)dimension.getHeight();
			int longueur  = (int)dimension.getWidth();

			int thisI, thisJ;
			thisJ = e.getXOnScreen() - Fenetre.this.vue.getX() - Fenetre.this.vue.getInsets().left - x;
			thisI = e.getYOnScreen() - Fenetre.this.vue.getY() - Fenetre.this.vue.getInsets().top - y;

			thisJ = (thisJ < 0)? 0 : thisJ;
			thisJ = (thisJ > longueur) ? longueur : thisJ;
			thisI = (thisI < 0)? 0 : thisI;
			thisI = (thisI > hauteur) ? hauteur : thisI;
			dragged.setLocation(thisJ, thisI);
		}

		/**
		 * quand le bloc est relache, il est ajoute recursivement au programme correspondant
		 */ 
		public void mouseReleased(MouseEvent e) {
			if(e!=null && pressed){
				int posX=e.getXOnScreen();
				int n=vue.edition.getNProg(posX);
				if(n!=-1){
					Dimension edition = vue.edition.viewport.getSize();
					// récupérer les positions du block et en fonction de sa position et l'ajouter à tel ou tel programme
					if(dragged instanceof BlockChoix){
						BlockEdition ed = ((BlockChoix)dragged).getEd();
						vue.edition.add(ed, n, posX, e.getYOnScreen());
					}
					else if(dragged instanceof BlockEdition){
						if(dragged instanceof Unaire &&
								((Unaire)dragged).inBoucle() != null){ //c'est une boucle while ou if
							vue.edition.add(((Unaire)dragged).inBoucle().copie(vue.controleurE), n, posX, e.getYOnScreen());

						}
						vue.edition.add(((BlockEdition)dragged).copie(vue.controleurE), n, posX, e.getYOnScreen());
					}
				}
			}
			if(dragged!=null) //La méthode pourrait être appelée plusieurs fois simultanément, entraînant une nullpointerexception
				glassPane.remove(dragged);

			glassPane.setVisible(false);
			glassPane.setEnabled(false);
			pressed=false;
		}

		public void mouseExited(MouseEvent e){
			if(pressed) return;
			mouseReleased(null);
		}

	}


	/**
	 *	Controleur de la zone edition.
	 *  Quand on clique sur un bloc, celui est deplace sur le glassPane (il n'est plus sur l'edition).
	 */ 
	public class ControleurEdition extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			BlockEdition courant = (BlockEdition)e.getSource();
			if(courant.hasSon()) return;
			int xScreen=e.getXOnScreen();
			int yScreen=e.getYOnScreen();
			Fenetre.Vue.Edition.Programme p=Fenetre.this.vue.edition.getNthProg(xScreen);
			if(p!=null){
				int y=yScreen - Fenetre.this.vue.getY() - Fenetre.this.vue.getInsets().top
						- Fenetre.this.vue.edition.getY() - p.getY() - (int)Fenetre.this.vue.edition.getColumnHeader().getViewSize().getHeight();
				/*On retire le bloc du programme.*/
				if(courant.hasDad()){
					courant.gudBye();
					p.update();
				}else{
					p.removeLigne(y);
					p.update();
				}
				Fenetre.this.vue.validate();
			}   
			dragged = courant;
			dragged.setControleur(vue.controleurG);

			glassPane.add(dragged);
			glassPane.setVisible(true);
			glassPane.setOpaque(false);
			dragged.setVisible(true);
			dragged.setOpaque(true);
			dragged.setBounds(xScreen-Fenetre.this.vue.getX()-Fenetre.this.vue.getInsets().left-e.getX(),
					yScreen-Fenetre.this.vue.getY()-Fenetre.this.vue.getInsets().top-e.getY(),
					courant.getW(), courant.getH());
			dragged.setPreferredSize(dragged.getSize());
			glassPane.setEnabled(true);
			vue.controleurG.mousePressed(e);
		}
	}
}
