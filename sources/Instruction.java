public interface Instruction extends Noeud{
	void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv);
}
