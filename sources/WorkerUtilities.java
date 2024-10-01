import javax.swing.SwingWorker;

public final class WorkerUtilities {

	/**
	 * La classe Task encapsule un programme (Instruction) en tant que
	 * SwingWorker (exécutable en tant que tel dans le framework fourni par
	 * Swing). Il s'agit en quelque sorte d'une application du patron de
	 * conception "adapter".
	 */
	private static class Task extends SwingWorker<Boolean, Object> {
		/*
		 * Le type du 2e paramètre (Object) n'a en fait pas d'importance ici. Il
		 * en aurait si vous modifiiez cette classe pour utiliser la méthode
		 * publish() à la place de SwingUtilities.invokeLater(). Dans ce cas il
		 * faudrait redéfinir la méthode process() (lire la documentation de
		 * SwingWorker).
		 */

		private final Instruction program;

		private Task(Instruction program) {
			this.program = program;
		}

		@Override
		protected Boolean doInBackground() {
			try {
				program.execute(new ListMap<Integer>(), new ListMap<String>(), new ListMap<Boolean>());
				return true; /* tout s'est bien passé */
			} catch (Exception e) {
				/*
				 * On suppose que si le programme interprété s'interrompe sur
				 * une exception, c'est un échec, donc on retourne false. Pour
				 * mieux faire, il faudrait raffiner un peu, et se limiter aux
				 * exceptions prévues par votre interpréteur. On pourrait
				 * éventuellement aussi remplacer Boolean par un type plus riche
				 * pour remonter des informations utiles, notamment transmises
				 * via cette exception.
				 */
				return false;
			}
		}
	}

	// empêche l'instanciation de cette classe
	private WorkerUtilities() {
	}

	/**
	 * Exécute un programme/une instruction sur un "worker thread".
	 * 
	 * @param p
	 *            programme/instruction à exécuter
	 */
	public static void invoke(Instruction p) {
		// Modifiez la ligne ci-dessous si la méthode execute() de votre
		// interface Instruction prend un paramètre, comme l'environnement
		// courant.
		// Comme il s'agit a priori de l'instruction racine du programme, on
		// peut, dans ce cas, passer un environnement vide.
		(new Task(p)).execute();
	}

}
