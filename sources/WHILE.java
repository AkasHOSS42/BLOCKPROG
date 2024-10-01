/**Un arbre de syntaxe abstraite pour une boucle WHILE.*/
public class WHILE implements Instruction{
	/**La condition de la boucle.*/
	private Expression<Boolean> condition;

	/**Le corps de la boucle.*/
	private ListInstr instruction;

	public WHILE(Expression<Boolean> c, ListInstr i){
		condition=c;
		instruction=i;
	}

	public WHILE (){
		condition = null;
		instruction = new ListInstr();
	}

	public void setCondition(Expression<Boolean> condition) {
		this.condition = condition;
	}

	public void setInstruction(ListInstr instruction) {
		this.instruction = instruction;
	}

	public Expression<Boolean> getCondition() {
		return condition;
	}

	public ListInstr getInstruction() {
		return instruction;
	}

	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
		while(condition.evalue(intEnv, sEnv, bEnv)){
			/*On ajoute un niveau de profondeur a l'environnement.*/
			intEnv.push(new MapVariables<Integer>());
			sEnv.push(new MapVariables<String>());
			bEnv.push(new MapVariables<Boolean>());

			instruction.execute(intEnv, sEnv, bEnv);

			/*On retire un niveau de profondeur a l'environnement.*/
			intEnv.pop();
			sEnv.pop();
			bEnv.pop();
		}
	}

	@Override
	public String getType() {
		return "Instruction";
	}

	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		if(condition != null && instruction != null){
			if(!condition.isSet(intEnv, sEnv, bEnv)) return false;
			/*On ajoute un niveau de profondeur a l'environnement.*/
			intEnv.push(new MapVariables<Integer>());
			sEnv.push(new MapVariables<String>());
			bEnv.push(new MapVariables<Boolean>());

			if(!instruction.isSet(intEnv, sEnv, bEnv)) return false;

			/*On retire un niveau de profondeur a l'environnement.*/
			intEnv.pop();
			sEnv.pop();
			bEnv.pop();
			return true;
		}
		return false;
	}
}
