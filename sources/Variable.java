import java.util.InputMismatchException;

/**
 *Un arbre de syntaxe abstraite pour une variable entiere.*/
class IntVariable implements Expression<Integer>{
	/**Le nom de la variable.*/
	private String key;

	public IntVariable(String k){
		key=k;
	}

	/*On veille a ce que cette methode ne soit appelee que quand la variable est initialisee. CF methode update dans Programme.*/
	/**Cherche dans l'environnement mis en parametre la valeur actuelle de cette variable.*/
	@Override
	public Integer evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return intEnv.get(key);
	}

	@Override
	public String getType() {
		return Integer.class.getName();
	}

	/**@return true si la variable est initilisee dans l'environnement mis en parametre.*/
	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return intEnv.containsKey(key);
	}

	public String getKey(){return key;}

	public void setKey(String k){key=k;}
}

/**
 *Un arbre de syntaxe abstraite pour une variable de type String.*/
class StringVariable implements Expression<String>{
	/**Le nom de la variable.*/
	private String key;

	public StringVariable(String k){
		key=k;
	}

	/*On veille a ce que cette methode ne soit appelee que quand la variable est initialisee. CF methode update dans Programme.*/
	/**Cherche dans l'environnement mis en parametre la valeur actuelle de cette variable.*/
	@Override
	public String evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return sEnv.get(key);
	}

	@Override
	public String getType() {
		return String.class.getName();
	}

	/**@return true si la variable est initilisee dans l'environnement mis en parametre.*/
	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return sEnv.containsKey(key);
	}

	public String getKey(){return key;}

	public void setKey(String k){key=k;}
}

/**
 *Un arbre de syntaxe abstraite pour une variable booleene.*/
class BooleanVariable implements Expression<Boolean>{
	/**Le nom de la variable.*/
	private String key;

	public BooleanVariable(String k){
		key=k;
	}

	/*On veille a ce que cette methode ne soit appelee que quand la variable est initialisee. CF methode update dans Programme.*/
	/**Cherche dans l'environnement mis en parametre la valeur actuelle de cette variable.*/
	@Override
	public Boolean evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return bEnv.get(key);
	}

	@Override
	public String getType() {
		return Boolean.class.getName();
	}

	/**@return true si la variable est initilisee dans l'environnement mis en parametre.*/
	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		return bEnv.containsKey(key);
	}

	public String getKey(){return key;}

	public void setKey(String k){key=k;}
}