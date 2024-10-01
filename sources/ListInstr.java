import java.util.LinkedList;
import javax.swing.SwingUtilities;

/**
 *Un arbre syntaxique pour un bloc d'instructions.*/
public class ListInstr extends LinkedList<Instruction> implements Instruction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8481093031391504964L;

	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		for(Instruction i : this){
			i.execute(intEnv, sEnv, bEnv);
			try{
				Thread.sleep(1);//Les ordres sur le Sprite pourraient s'executer dans le mauvais ordre sans cette petite pause.
			}catch(InterruptedException e){}
		}
	}

	@Override
	public String getType() {
		return "Instruction";
	}

	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		for (Instruction i : this){
			if(i == null||!i.isSet(intEnv, sEnv, bEnv))
				return false;
		}
		return true;
	}

	/**
	 *Essaie d'ajouter n en fin de liste.
	 *@return true si l'ajout a lieu, false si rien ne se passe.*/
	public boolean add(Noeud n){
		if(n!=null&&"Instruction".equals(n.getType())){
			add((Instruction)n);
			return true;
		}
		return false;
	}

	/**
	 *Essaie d'ajouter n en kieme position dans la liste.
	 *@return true si l'ajout a lieu, false si rien ne se passe.*/
	public boolean add(int k, Noeud n){
		if(n!=null&&"Instruction".equals(n.getType())){
			add(k, (Instruction)n);
			return true;
		}
		return false;
	}
}
