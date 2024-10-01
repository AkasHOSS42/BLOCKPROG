public interface Expression<T> extends Noeud{
	T evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv);
}
