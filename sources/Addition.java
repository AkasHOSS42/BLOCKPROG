import java.util.InputMismatchException;

/**
 *Un arbre syntaxique pour la somme de deux entiers.
 *@see Operation */
public class Addition extends Operation<Integer, Integer>{
	/**
	 *Renvoie la somme de ses deux fils.*/
	public Integer evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) throws InputMismatchException{
		return val1.evalue(intEnv, sEnv, bEnv) + val2.evalue(intEnv, sEnv, bEnv);
	}

	public Addition(){super();}
	public Addition(Expression<Integer>e1, Expression<Integer> e2){super(e1, e2);}

	@Override
	public String getType() {
		return Integer.class.getName();
	}

	public String getArgType(){return Integer.class.getName();}
}
