/**
 *Un AST qui teste l'egalite de deux expressions de meme type.*/
public abstract class EQUALS<T> extends Operation<Boolean, T> {
	/**
	 *@return true si les fils ont la meme valeur.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return val1.evalue(intEnv, sEnv, bEnv).equals(val2.evalue(intEnv, sEnv, bEnv) );
	}

	public String getType() {
		return Boolean.class.getName();
	}

	public abstract String getArgType();
}

class EqualsInt extends EQUALS<Integer>{
	public String getArgType(){return Integer.class.getName();}
}

class EqualsString extends EQUALS<String>{
	public String getArgType(){return String.class.getName();}
}

class EqualsBool extends EQUALS<Boolean>{
	public String getArgType(){return Boolean.class.getName();}
}
