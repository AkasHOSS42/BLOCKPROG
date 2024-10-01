/**
 *L'arbre syntaxique d'une instruction qui affecte une valeur a une variable.
 *@see Instruction 
 *@see ListMap#set(String, T)*/
public abstract class Affectation<T> implements Instruction{
	/**
	 *Le nom de la variable a laquelle on affecte une valeur.
	 *@see Variable
	 *@see ListMap#set(String, T)*/
	protected Constante<String> key;

	/**
	 *La valeur que prendra la variable.*/
	protected Expression<T> val;

	public String getType(){
		return "Instruction";
	}

	public abstract void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv);

	protected T getVal(ListMap<Integer>intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		return val.evalue(intEnv, sEnv, bEnv);
	}

	/**
	 *Execute l'affectation dans l'environnement en parametre.
	 *@param env L'environnement dans lequel realiser l'affectation.
	 *@param val La valeur a donner lors de l'affectation.
	 *@see ListMap#set(String, T)*/
	protected void exec(ListMap<T> env, T val){
		env.set(key.evalue(null, null, null), val);
	}


	public boolean isSet(ListMap<Integer>intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		if(key==null||val==null) return false;
		if(val.isSet(intEnv, sEnv, bEnv)){
			/*Il faut executer l'instruction. Sinon, on ne peut pas verifier
	      si les variables d'un programme ont bien ete initialisees.*/
			execute(intEnv, sEnv, bEnv);
			return true;
		}
		return false;
	}

	public boolean hasSon(){return val!=null;}

	public void setKey(String s){key.setVal(s);}
	public abstract boolean setVal(Noeud n);

	public Affectation(String s){key=new Constante<String>(s);}
}

/*On peut etendre la classe abstraite ci-dessus, en se servant de la methode exec pour coder execute.*/

/**
 *Un AST pour l'affectation d'une variable entiere.*/
class IntAffect extends Affectation<Integer>{
	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		exec(intEnv, getVal(intEnv, sEnv, bEnv));
	}

	/**
	 *Essaie d'attribuer a son membre droit la valeur de l'expression en parametre.
	 *@return true si l'attribution s'est bien passee, false si rien ne se passe.*/
	public boolean setVal(Noeud n){
		if(n==null){
			val=null;
			return true;
		}
		if(!(n.getType().equals(Integer.class.getName()))) return false;
		val=((Expression<Integer>) n);
		return true;
	}

	public IntAffect(){super("x");}
}

/**
 *Un AST pour l'affectation d'une variable de type String.*/
class StringAffect extends Affectation<String>{
	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		exec(sEnv, getVal(intEnv, sEnv, bEnv));
	}

	/**
	 *Essaie d'attribuer a son membre droit la valeur de l'expression en parametre.
	 *@return true si l'attribution s'est bien passee, false si rien ne se passe.*/
	public boolean setVal(Noeud n){
		if(n==null){
			val=null;
			return true;
		}
		if(!(n.getType().equals(String.class.getName()))) return false;
		val=((Expression<String>) n);
		return true;
	}

	public StringAffect(){super("s");}
}

/**
 *Un AST pour l'affectation d'une variable booleene.*/
class BoolAffect extends Affectation<Boolean>{
	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		exec(bEnv, getVal(intEnv, sEnv, bEnv));
	}

	/**
	 *Essaie d'attribuer a son membre droit la valeur de l'expression en parametre.
	 *@return true si l'attribution s'est bien passee, false si rien ne se passe.*/
	public boolean setVal(Noeud n){
		if(n==null){
			val=null;
			return true;
		}
		if(!(n.getType().equals(Boolean.class.getName()))) return false;
		val=((Expression<Boolean>) n);
		return true;
	}

	public BoolAffect(){super("b");}
}
