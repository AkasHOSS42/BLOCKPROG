/**
 *Arbre de syntaxe abstraite pour tester une inegalite orientee a droite.
 *@see Operation*/
public class GREATER extends Operation<Boolean, Integer> {
	/**
	 *@return true Si le fils gauche est superieur au fils droit.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv) > val2.evalue(intEnv, sEnv, bEnv);
	}

	public String getType() {
		return Boolean.class.getName();
	}

	public String getArgType(){return Integer.class.getName();}
}
