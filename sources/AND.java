/**
 *L'expression pour un et logique, donc une operation binaire.
 *@see Operation */
public class AND extends Operation<Boolean, Boolean> {
	public AND() {
		super();
	}

	public AND(Expression<Boolean> c1, Expression<Boolean> c2) {
		super(c1, c2);
	}

	/**
	 *Renvoie la conjonction de ses fils.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return  val1.evalue(intEnv, sEnv, bEnv) && val2.evalue(intEnv, sEnv, bEnv);
	}

	@Override
	public String getType() {
		return Boolean.class.getName();
	}

	public String getArgType(){return Boolean.class.getName();}
}
