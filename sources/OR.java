/**
 *Un arbre de syntaxe abstraite pour le OU logique.
 *@see Operation*/
public class OR extends Operation<Boolean, Boolean> {
	public OR() {
		super();
	}

	public OR(Expression<Boolean> c1, Expression<Boolean> c2) {
		super(c1, c2);
	}

	/**@return La disjonction de ses fils.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv) || val2.evalue(intEnv, sEnv, bEnv);
	}

	public String getType() {
		return Boolean.class.getName();
	}

	public String getArgType(){return Boolean.class.getName();}
}