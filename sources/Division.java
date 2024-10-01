/**
 *Un arbre de syntaxe abstraite pour la division euclidienne.*/
public class Division extends Operation<Integer, Integer>{
	/**
	 *@return Le quotient de ses fils.*/
	public Integer evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv) / val2.evalue(intEnv, sEnv, bEnv);
	}

	public Division(){super();}
	public Division(Expression<Integer>e1, Expression<Integer> e2){super(e1, e2);}

	@Override
	public String getType() {
		return Integer.class.getName();
	}

	public String getArgType(){return Integer.class.getName();}
}
