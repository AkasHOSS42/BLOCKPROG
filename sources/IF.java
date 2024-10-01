/**
 *Arbre de syntaxe abstraite pour une boucle IF.*/
public class IF implements Instruction{
	/**
	 *La condition de la boucle.*/
	Expression<Boolean> c_if;

	/**
	 *Le bloc du IF.*/
	ListInstr instr1;

	/**
	 *Le bloc du ELSE.*/
	ListInstr instr2;

	public IF(){
		this(null, new ListInstr());
	}

	public IF(Expression<Boolean> c_if, ListInstr instr){
		this.c_if = c_if;
		this.instr1 = instr;
		this.instr2 = new ListInstr();
	}

	public IF(Expression<Boolean> c_if, ListInstr instruction, ListInstr instruction2){
		this.c_if = c_if;
		this.instr1 = instruction;
		this.instr2 = instruction2;
	}

	@Override
	public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		/*On ajoute un niveau de profondeur a l'environnement.*/
		intEnv.push(new MapVariables<Integer>());
		sEnv.push(new MapVariables<String>());
		bEnv.push(new MapVariables<Boolean>());

		if (c_if.evalue(intEnv, sEnv, bEnv))
			instr1.execute(intEnv, sEnv, bEnv);
		else
			instr2.execute(intEnv, sEnv, bEnv);

		/*On retire un niveau de profondeur a l'environnement.*/
		intEnv.pop();
		sEnv.pop();
		bEnv.pop();
	}

	public void setC_if(Expression<Boolean> c_if) {this.c_if = c_if;}
	public void setInstr1(ListInstr instr1) {this.instr1 = instr1;}
	public void setInstr2(ListInstr instr2) {
		if (instr2 !=null){this.instr2 = instr2;}
		else{
			this.instr2 = new ListInstr();
		}
	}

	public Expression<Boolean> getC_if() {return c_if;}
	public ListInstr getInstr1() {return instr1;}
	public ListInstr getInstr2() {return instr2;}

	@Override
	public String getType() {
		return "Instruction";
	}

	@Override
	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		if (c_if != null && instr1 != null && instr2 != null){
			if(!c_if.isSet(intEnv, sEnv, bEnv)) return false;

			/*On ajoute un niveau de profondeur a l'environnement.*/
			intEnv.push(new MapVariables<Integer>());
			sEnv.push(new MapVariables<String>());
			bEnv.push(new MapVariables<Boolean>());

			if(!instr1.isSet(intEnv, sEnv, bEnv)) return false;

			/*On retire un niveau de profondeur a l'environnement.*/
			intEnv.pop();
			sEnv.pop();
			bEnv.pop();

			/*On ajoute un niveau de profondeur a l'environnement.*/
			intEnv.push(new MapVariables<Integer>());
			sEnv.push(new MapVariables<String>());
			bEnv.push(new MapVariables<Boolean>());
			if(!instr2.isSet(intEnv, sEnv, bEnv)) return false;

			/*On retire un niveau de profondeur a l'environnement.*/
			intEnv.pop();
			sEnv.pop();
			bEnv.pop();
			return true;
		}
		return false;
	}
}
