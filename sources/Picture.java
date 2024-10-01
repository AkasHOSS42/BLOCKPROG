import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**Le Sprite.*/
public class Picture extends JPanel{
	Fenetre.Vue vue;
	Execution conteneur;

	/**Le thread sur lequel on controle le Sprite.*/
	java.util.Timer timer=new java.util.Timer();

	/*Les animations prennent plusieurs images. Max pose le nombre
    total d'images de l'animation en cours.*/
	int max=0;

	BufferedImage image;

	/**Si le crayon est baisse.*/
	boolean pen;
	boolean exist = false;

	/**L'orientation horizontale du Sprite.*/
	private int dx=1;

	/**L'orientation verticale du sprite.*/
	private int dy=0;

	public Picture() throws IOException{
		pen = false;
	}

	public void setVue(Fenetre.Vue v){vue=v;}
	public void setExec(Execution e){conteneur=e;}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image,0,0,this);
		g.create(110,110, image.getWidth(),image.getHeight());

		if(pen){
			super.paintComponents(g);
			g.setColor(Color.BLACK);
			g.drawLine(getX()-10, getY()-10, getX(), getY());
		}
	}

	//créer l image et permet les futures interactions de fonctionner
	public void create() throws IOException{
		if(!exist){
			image = ImageIO.read(new File("oignon_immobile.jpg"));
			setBounds(200, 200, image.getWidth(), image.getHeight());
			exist = true;
		}
	}

	//le déplacement dépends de la direction du sprite
	/**Deplace le Sprite vers l'avant, en fonction de la direction dans laquelle il est tourne.*/
	public void deplace(){
		if (exist){
			int x =this.getX();
			int y = this.getY();
			if(dx==1&&x>=conteneur.getWidth()-image.getWidth()) return;
			if(dx==-1&&x<=0)return;
			if(dy==1&&y>=conteneur.getHeight()-image.getHeight())return;
			if(dy==-1&&y<=0)return;
			this.setBounds(this.getX()+10*dx, this.getY()+10*dy, image.getWidth(), image.getHeight());
		}
	}

	//permet de tracer une ligne suivant la trajectoire du sprite
	public void line(Graphics g){
		if (pen){
			int x =this.getX();
			int y = this.getY();

			g.setColor(Color.BLACK) ;
			x=x+conteneur.getX()+vue.getX()+vue.getInsets().left;
			y=y+conteneur.getY()+vue.getY()+vue.getInsets().top;
			g.drawLine(x-10*dx, y-10*dy, x, y);

		}
	}

	/* Met à jour l'image du sprite en fonction de sa position. */
	public void update(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, getX(), getY(), this);
	}

	/** Abaisse le crayon si le crayon est leve.
	 *Leve le crayon si le crayon est abaisse. */
	public void usePen(){
		timer.schedule(new TimerTask(){public void run(){
			pen=!pen;
		}
		}, 0);
	}

	/*Création des nombreux images du sprite*/
	private static File f0=new File("oignon_immobile.jpg");
	private static File f1=new File("oignon_marche_1.jpg");
	private static File f2=new File("oignon_marche_2.jpg");
	private static File f3=new File("oignon_marche_3.jpg");
	private static File f4=new File("oignon_marche_4.jpg");

	private static File f5=new File("oignon_immobile_g.jpg");
	private static File f6=new File("oignon_marche_g_1.jpg");
	private static File f7=new File("oignon_marche_g_2.jpg");
	private static File f8=new File("oignon_marche_g_3.jpg");
	private static File f9=new File("oignon_marche_g_4.jpg");

	private static File f10=new File("oignon_immobile_f.jpg");
	private static File f11=new File("oignon_marche_f_1.jpg");
	private static File f12=new File("oignon_marche_f_2.jpg");
	private static File f13=new File("oignon_marche_f_3.jpg");
	private static File f14=new File("oignon_marche_f_4.jpg");

	private static File f15=new File("oignon_immobile_b.jpg");
	private static File f16=new File("oignon_marche_b_1.jpg");
	private static File f17=new File("oignon_marche_b_2.jpg");
	private static File f18=new File("oignon_marche_b_3.jpg");
	private static File f19=new File("oignon_marche_b_4.jpg");

	private static File f20=new File("oignon_salut_1.jpg");
	private static File f21=new File("oignon_salut_2.jpg");
	private static File f22=new File("oignon_salut_3.jpg");


	public void deplace(int n) throws InterruptedException{
		max=n;
		timer.schedule(new TimerTask(){public void run(){
			int count = 0;

			if(exist){
				for(int i=0; i<max; i++){
					count ++;
					deplace();
					line(vue.getGraphics());
					if(count%8==1||count%8==3){
						try {
							if(dx==1){
								image = ImageIO.read(f1);
							}else if(dy==1){
								image = ImageIO.read(f11);
							}else if(dx==-1){
								image = ImageIO.read(f6);
							}else{
								image = ImageIO.read(f16);
							}


						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(count%8==2){
						try {
							if(dx==1){
								image = ImageIO.read(f2);
							}else if(dy==1){
								image = ImageIO.read(f12);
							}else if(dx==-1){
								image = ImageIO.read(f7);
							}else{
								image = ImageIO.read(f17);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(count%8==0||count%8==4){
						try {
							if(dx==1){
								image = ImageIO.read(f0);
							}else if(dy==1){
								image = ImageIO.read(f10);
							}else if(dx==-1){
								image = ImageIO.read(f5);
							}else{
								image = ImageIO.read(f15);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(count%8==5||count%8==7){
						try {
							if(dx==1){
								image = ImageIO.read(f3);
							}else if(dy==1){
								image = ImageIO.read(f13);
							}else if(dx==-1){
								image = ImageIO.read(f8);
							}else{
								image = ImageIO.read(f18);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						try {
							if(dx==1){
								image = ImageIO.read(f4);
							}else if(dy==1){
								image = ImageIO.read(f14);
							}else if(dx==-1){
								image = ImageIO.read(f9);
							}else{
								image = ImageIO.read(f19);
							}

						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					try{
						Thread.sleep(300);
					}catch(InterruptedException e){}
				}
				try{
					Thread.sleep(300);
				}catch(InterruptedException e){}
			}
		}}, 0);

	}

	//ces 2 depceSal permettent la mise à jour de l'affichage du sprite lorsque ce dernier salue.
	public void deplaceSal1(){
		if (exist){
			int x =this.getX();
			int y = this.getY();

			if(dx==1&&x>=conteneur.getWidth()-image.getWidth()) return;
			if(dx==-1&&x<=0)return;
			if(dy==1&&y>=conteneur.getHeight()-image.getHeight())return;
			if(dy==-1&&y<=0)return;
			this.setBounds(this.getX()+1*dx, this.getY()+1*dy, image.getWidth(), image.getHeight());
		}
	}

	public void deplaceSal2(){
		if (exist){
			int x =this.getX();
			int y = this.getY();

			if(dx==1&&x>=conteneur.getWidth()-image.getWidth()) return;
			if(dx==-1&&x<=0)return;
			if(dy==1&&y>=conteneur.getHeight()-image.getHeight())return;
			if(dy==-1&&y<=0)return;
			this.setBounds(this.getX()-1*dx, this.getY()-1*dy, image.getWidth(), image.getHeight());
		}
	}

	/**Declenche une petite animation de la part du Sprite.*/
	public void salut(int n)throws InterruptedException{
		int max =n;
		BufferedImage imageMem = image;
		timer.schedule(new TimerTask(){public void run(){
			int count = 0;

			if(exist){
				for(int i=0; i<max; i++){
					count++;
					if(count%2==1){
						deplaceSal1();
					}else{
						deplaceSal2();
					}


					if(count%8==1||count%8==7){
						try {
							image = ImageIO.read(f10);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(count%8==2||count%8==6){
						try {
							image = ImageIO.read(f20);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(count%8==3||count%8==5){
						try {
							image = ImageIO.read(f21);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(count%8==4){
						try {
							image = ImageIO.read(f22);

						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						image = imageMem;
					}

					try{
						Thread.sleep(300);
					}catch(InterruptedException e){}
				}
				try{
					Thread.sleep(300);
				}catch(InterruptedException e){}
			}
		}}, 0);
	}


	/* L'axe y est dirige vers le bas en Swing. */

	public void turnRight(){
		timer.schedule(new TimerTask(){public void run(){
			/*Une simple rotation vectorielle.*/
			int tmp=dx;
			dx=-dy;
			dy=tmp;

		}
		}, 0);
	}

	public void turnLeft(){
		timer.schedule(new TimerTask(){public void run(){
			/*Une simple rotation vectorielle.*/
			int tmp=dx;
			dx=dy;
			dy=-tmp;
		}
		}, 0);
	}

	public void turnBehind(){
		timer.schedule(new TimerTask(){public void run(){
			/*Une simple rotation vectorielle.*/
			dx=-dx;
			dy=-dy;

		}
		}, 0);
	}
}
