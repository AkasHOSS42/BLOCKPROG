/**
 *Un arbre de syntaxe abstraite pour la soustraction entre deux entiers.
 *@see Operation*/
public class Soustraction extends Operation<Integer, Integer> {
	public Soustraction() {
		super();
	}

	public Soustraction(Expression<Integer> c1, Expression<Integer> c2) {
		super(c1, c2);
	}

	/**@return La valeur du fils droit soustraite a celle du fils gauche.*/
	public Integer evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv)-val2.evalue(intEnv, sEnv, bEnv);
	}

	@Override
	public String getType() {
		return Integer.class.getName();
	}

	public String getArgType(){return Integer.class.getName();}
}