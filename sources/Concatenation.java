/**
 *L'expression qui est le produit de concatenation de deux chaines.
 *@see Operation*/
public class Concatenation extends Operation<String, String>{
	public Concatenation(Expression<String> g, Expression<String> d){
		super(g, d);
	}

	public Concatenation(){
		super();
	}

	/**
	 *@return Le produit de concatenation de ses fils.*/
	public String evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		return val1.evalue(intEnv, sEnv, bEnv)+val2.evalue(intEnv, sEnv, bEnv);
	}

	@Override
	public String getType() {
		return String.class.getName();
	}

	public String getArgType(){return String.class.getName();}
}
