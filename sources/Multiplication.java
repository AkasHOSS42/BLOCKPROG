/**
 *Un arbre de syntaxe abstraite pour le produit de deux entiers.
 *@see Operation*/
public class Multiplication extends Operation<Integer, Integer>{
	/**@return Le produit de ses fils.*/
	public Integer evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv)*val2.evalue(intEnv, sEnv, bEnv);
	}

	@Override
	public String getType() {
		return Integer.class.getName();
	}

	public String getArgType(){return Integer.class.getName();}
}
