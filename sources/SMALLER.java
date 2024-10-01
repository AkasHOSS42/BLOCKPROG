/**
 *Un arbre de syntaxe abstraite pour tester une inegalite orientee vers la gauche.
 *@see Operation*/
public class SMALLER extends Operation<Boolean, Integer> {
	/**@return true si le fils gauche est strictement inferieur au fils droit.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv) < val2.evalue(intEnv, sEnv, bEnv);
	}

	public String getType() {
		return Boolean.class.getName();
	}

	public String getArgType(){return Integer.class.getName();}
}