/**
 *Cette classe contient les methodes qui construisent des instructions pour controler
 *le Sprite, au niveau AST.*/
public class Move{
	public static Instruction deplace(Picture p, int n){
		return new Instruction(){
			public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){
				try {p.deplace(n);}
				catch (InterruptedException e) {e.printStackTrace();}
			}
			public String getType() {return "Instruction";}
			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {return p != null;}
		};
	}

	public static Instruction turnLeft(Picture p){
		return new Instruction(){
			public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){p.turnLeft();}
			public String getType() {return "Instruction";}
			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {return p != null;}
		};
	}

	public static Instruction turnRight(Picture p){
		return new Instruction(){
			public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){p.turnRight();}
			public String getType() {return "Instruction";}
			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {return p != null;}
		};
	}

	public static Instruction demiTour(Picture p){
		return new Instruction(){
			public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){p.turnBehind();}
			public String getType() {return "Instruction";}
			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {return p != null;}
		};
	}

	public static Instruction usePen(Picture p){
		return new Instruction(){
			public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){p.usePen();}
			public String getType() {return "Instruction";}
			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {return p != null;}
		};
	}

	public static Instruction salut(Picture p){
		return new Instruction(){
			public void execute(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv){try{p.salut(8);
			} catch (InterruptedException e) {e.printStackTrace();}
			}
			public String getType() {return "Instruction";}
			public boolean isSet(ListMap<Integer> intEnv, ListMap<String> sEnv, ListMap<Boolean> bEnv) {return p != null;}
		};
	}
}
