import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 *La zone de la vue dans laquelle se trouve le Sprite.*/
public class Execution extends JPanel{
	/**Le Sprite.*/
	Picture picture;

	public Execution(Picture p) throws IOException{
		picture = p;
		picture.setExec(this);
		setLayout(null);
		add(picture);
		picture.create();
		setBackground(Color.white);
	}
}
