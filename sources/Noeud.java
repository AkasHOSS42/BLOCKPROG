/**Un noeud d'un arbre de syntaxe abstraite.*/
public interface Noeud {
	/**
	 *@return Un String qui represente le type de ce Noeud. Permet de gerer les erreurs de type.*/
	public String getType();

	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv);
}