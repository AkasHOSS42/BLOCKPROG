import java.util.Stack;
/**
 *L'environnement des variables pendant l'execution.*/
public class ListMap<T> extends Stack<MapVariables<T>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ListMap(){
		super();
		this.push(new MapVariables());
	}

	public void set(String key, T value){
		MapVariables<T> env=conteneur(key);
		if(env==null)//Si env==null il s'agit d'une declaration. Sinon c'est une affectation.
			env=peek();
		env.put(key, value);
	}

	/** @return la map de la pile qui contient une cle egale a key, et null si il n'y en a pas.*/
	private MapVariables<T> conteneur(String key){
		ListMap<T> tmp=new ListMap<T>();
		tmp.pop();//Une ListMap n'est jamais vide a l'initialisation.
		boolean exit=false;
		MapVariables<T> ans=null;
		while((!exit) && (!empty())){
			if(peek().containsKey(key)){
				exit=true;
				ans=peek();
			}
			tmp.push(pop());//Il faut bien conserver les maps quelque part.
		}

		/*On remplit de nouveau this.*/
		while(!tmp.empty())
			push(tmp.pop());
		return ans;
	}

	/**@return true si une variable de nom nom est initialisee dans this.*/
	public boolean containsKey(String nom) {
		ListMap<T> tmp=new ListMap<T>();
		tmp.pop();//Une ListMap n'est jamais vide a l'initialisation.
		boolean exit=false;
		boolean ans=false;
		while((!empty()) && (!exit)){
			if(peek().containsKey(nom)){
				exit=true;
				ans=peek().get(nom)!=null;
			}
			tmp.push(pop());//Il faut bien conserver les maps quelque part.
		}

		/*On remplit de nouveau this.*/
		while(!tmp.empty())
			push(tmp.pop());
		return ans;
	}

	/*On veille avec Programme.update() que cette methode ne soit appelee que si la variable est initialisee.*/
	/**@return La valeur courante de la variable de nom key dans this.*/
	public T get(String key){
		ListMap<T> tmp=new ListMap<T>();
		tmp.pop();//Une ListMap n'est jamais vide a l'initialisation.
		boolean exit=false;
		T ans=null;
		while((!empty()) && (!exit)){
			if(peek().containsKey(key)){
				exit=true;
				ans=peek().get(key);
			}
			tmp.push(pop());//Il faut bien conserver les maps quelque part.
		}

		/*On remplit de nouveau this.*/
		while(!tmp.empty())
			push(tmp.pop());
		return ans;
	}
}
