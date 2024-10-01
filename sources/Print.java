/**
 *Un arbre de syntaxe abstraite d'une instruction qui affiche son fils dans le terminal.*/
public class Print implements Instruction{
	/**L'expression a afficher.*/
	private Expression<?> arg;

	public Print(Expression<?> arg){
		this.setVal1(arg);
	}

	public Print() {
	}

	public String getType(){return "Instruction";}

	@SuppressWarnings("unchecked")
	public boolean setVal1(Noeud n){
		if(n==null){
			arg=null;
			return true;
		}
		if(!"Instruction".equals(n.getType())){
			arg=(Expression<?>)n;
			return true;
		}
		return false;
	}

	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		return arg!=null&&arg.isSet(intEnv, sEnv, bEnv);
	}

	/**Affiche son fils dans le terminal.*/
	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		System.out.println(arg.evalue(intEnv, sEnv, bEnv).toString());
	}

	public boolean hasSon(){return arg!=null;}
}