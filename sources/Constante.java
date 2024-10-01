/**
 *Un arbre de syntaxe abstraite pour une constante.*/
public class Constante<T> implements Expression<T> {
	private T value;
	private final String type;

	public Constante(T value){
		this.value = value;
		this.type = ((Class<? extends Object>)value.getClass()).getName();
	}

	/**
	 *@return La valeur de la constante.*/
	@Override
	public T evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		return value != null;
	}

	public void setVal(T arg){
		value=arg;
	}
}
