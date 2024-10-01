/**Une Operation est un arbre de syntaxe abstraite qui a deux fils.*/
public abstract class Operation<R, A> implements Expression<R> {//R le type de retour, A celui des arguments
	protected Expression<A> val1, val2;

	public Operation() {
		this(null, null);
	}

	public Operation(Expression<A> val1, Expression<A> val2) {
		this.val1 = val1;
		this.val2 = val2;
	}

	/**
	 *@return Un String qui represente le type des arguments. Permet de tester les erreurs de type.*/
	public abstract String getArgType();

	/**
	 *Essaie de faire de val1 son fils gauche.
	 *@return true si val1 devient bien le fils gauche de this. false si il y a une erreur de type, dans ce cas rien ne se passe.*/
	public boolean setVal1(Noeud val1) {
		if(val1==null){
			this.val1=null;
			return true;
		}
		if(val1.getType().equals(getArgType())){
			this.val1 = (Expression<A>)val1;
			return true;
		}
		return false;
	}

	/**
	 *Essaie de faire de val1 son fils droit.
	 *@return true si val1 devient bien le fils droit de this. false si il y a une erreur de type, dans ce cas rien ne se passe.*/
	public boolean setVal2(Noeud val2) {
		if(val2==null){
			this.val2=null;
			return true;
		}
		if(val2.getType().equals(getArgType())){
			this.val2 = (Expression<A>)val2;
			return true;
		}
		return false;
	}

	@Override
	public abstract R evalue(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv);

	public boolean hasSon(){return val1!=null||val2!=null;}

	public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {
		boolean b1=val1==null;
		boolean b2=val2==null;
		if(!b1)
			b1=!val1.isSet(intEnv, sEnv, bEnv);
		if(!b2)
			b2=!val2.isSet(intEnv, sEnv, bEnv);
		return (!b1)&&(!b2);
	}
}