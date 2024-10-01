/**
 *Un arbre de syntaxe abstraite pour la negation.
 *@see Operation*/
public class NOT extends Operation<Boolean, Boolean> {
	public NOT() {
		super();
	}

	public NOT(Expression<Boolean> c1) {
		super(c1, null);
	}

	/**@return La negation de son fils.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return !val1.evalue(intEnv, sEnv, bEnv);
	}

	public String getType() {
		return Boolean.class.getName();
	}

	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		if (val1 != null)
			return val1.isSet(intEnv, sEnv, bEnv);
		return false;
	}

	public boolean hasSon(){return val1!=null;}

	public String getArgType(){return Boolean.class.getName();}
}
