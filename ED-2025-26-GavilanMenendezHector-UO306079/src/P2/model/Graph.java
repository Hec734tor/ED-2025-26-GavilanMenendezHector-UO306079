package P2.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import P2.GraphInterface;
import P2.exception.ElementNotPresentException;
import P2.exception.FullStructureException;

/**
 * Clase Grafo que va a contar con: 
 * Nodos -> Vector Aristas -> Matrices
 * bidimensionales T/F 
 * Pesos -> Matrices bidimensionales double 
 * size -> Número de nodos en el grafo
 * 
 * 
 * 
 * @param <T>
 */
public class Graph<T> implements GraphInterface<T> {
	protected int size;
	protected T[] nodes;
	protected boolean[][] edges;
	protected double[][] weights;

	protected double[][] A;
	protected int[][] P;

	/**
	 * Constructor para la clase grafo. Inicializa el grafo con sus matrices de
	 * pesos, aristas y nodos y número de nodos igual a 0
	 * 
	 * @param maxCapacity, la capcidad maximaw del grafo
	 */
	@SuppressWarnings("unchecked")
	public Graph(int maxCapacity) {
		this.nodes = (T[]) new Object[maxCapacity];
		this.size = 0;
		this.edges = new boolean[maxCapacity][maxCapacity];
		this.weights = new double[maxCapacity][maxCapacity];

	}
	/**
	 * Añade un nodo al grafo.
	 * 
	 * @param element Elemento a añadir como nodo
	 * @return {@code true} Si el nodo se ha añadido, {@code false} si el nodo ya exste
	 * @throws NullPointerException   si el elemento es null
	 * @throws FullStructureException Si se ha alcanzado la máxima capacidad del grafo
	 */
	@Override                                 
	public boolean addNode(T element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (nodes.length <= size) {
			throw new FullStructureException(element);
		}
		if (getNode(element) != -1) {
			return false;
		}
		// añades el elemento en el vector de elementos
		nodes[size] = element;
		// bucle para inicilializaar las aristas y los pesos a false
		// y 0 y con el orden del "baile" modo fila-columna
		for (int i = 0; i < size; i++) {
			edges[size][i] = false;
			edges[i][size] = false;
			weights[size][i] = 0;
			weights[i][size] = 0;
		}
		// tras añadir el elemento se aumenta el número de nodos
		size++;
		return true;

	}
	/**
	 * Añade un nodo al grafo.
	 * 
	 * @param element Elemento a añadir como nodo
	 * @return {@code true} Si el nodo se ha añadido, {@code false} si el nodo ya exste
	 * @throws NullPointerException   si el elemento es null
	 * @throws FullStructureException Si se ha alcanzado la máxima capacidad del grafo
	 */
	@Override
	public boolean removeNode(T element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (getNode(element) == -1) {
			return false;
		}
		int index = getNode(element);
		--size;
		nodes[index] = nodes[size];
		for (int i = 0; i < size; i++) {

			edges[i][index] = edges[i][size];
			edges[index][i] = edges[size][i];

			weights[i][index] = weights[i][size];
			weights[index][i] = weights[size][i];
		}
		edges[index][index] = edges[size][size];
		weights[index][index] = weights[size][size];
		return true;

	}


	/**
	 * Chequea si existe un nodo en el grafo
	 * 
	 * @param element El elemento a chequear
	 * @return {@code true} si el nodo existe, {@code false} Otro caso
	 * @throws NullPointerException Si el elemento es null
	 */
	@Override
	public boolean existsNode(T element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (getNode(element) == -1) {
			return false;
		}
		return true;
	}
	/**
	 * Añade una arista dirigida entre el nodo origen y destino con un peso > 0.
	 * 
	 * @param originElement      El nodo origen de la arista
	 * @param destinationElement El nodo destino de la arista
	 * @param weight             El peso de la arista
	 * @return {@code true}Si la arista se ha añadido, {@code false} si la arista ya existe
	 * @throws NullPointerException       Si algún elemento es null
	 * @throws IllegalArgumentException   Si la arista es 0 o menor que 0
	 * @throws ElementNotPresentException Si el nodo no existe en el grafo
	 */
	@Override
	public boolean addEdge(T originElement, T destinationElement, double weight) {
		if (weight <= 0) {
			throw new IllegalArgumentException();
		}
		if (originElement == null || destinationElement == null) {
			throw new NullPointerException();

		}
		if (getNode(originElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (getNode(destinationElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (existsEdge(originElement, destinationElement)) {
			return false;
		} else {
			edges[getNode(originElement)][getNode(destinationElement)] = true;
			weights[getNode(originElement)][getNode(destinationElement)] = weight;
			return true;
		}
	}
	/**
	 * Elimina una arista dirigida entre dos nodos.
	 * 
	 * @param originElement      El nodo de origen de la arista
	 * @param destinationElement El nodo de destino de la arista
	 * @return {@code true} si la arista fue eliminada correctamente, {@code false} si
	 *         la arista no existe
	 * @throws NullPointerException       si alguno de los elementos es nulo
	 * @throws ElementNotPresentException si alguno de los nodos no existe en el grafo
	 */
	@Override
	public boolean removeEdge(T originElement, T destinationElement) {
		if (originElement == null || destinationElement == null) {
			throw new NullPointerException();

		}
		if (getNode(originElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (getNode(destinationElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (!existsEdge(originElement, destinationElement)) {
			return false;
		} else {
			edges[getNode(originElement)][getNode(destinationElement)] = false;
			weights[getNode(originElement)][getNode(destinationElement)] = 0;
			return true;
		}
	}
	/**
	 * Verifica si existe una arista dirigida entre dos nodos.
	 * 
	 * @param originElement      El nodo de origen de la arista
	 * @param destinationElement El nodo de destino de la arista
	 * @return {@code true} si la arista existe, {@code false} en caso contrario
	 * @throws NullPointerException si alguno de los elementos es nulo
	 */
	@Override
	public boolean existsEdge(T originElement, T destinationElement) {
		if (originElement == null || destinationElement == null) {
			throw new NullPointerException();
		}
		if (getNode(originElement) == -1) {
			return false;
		}
		if (getNode(destinationElement) == -1) {
			return false;
		}

		return edges[getNode(originElement)][getNode(destinationElement)];

	}
	/**
	 * Obtiene el peso de una arista dirigida entre dos nodos.
	 * 
	 * @param originElement      El nodo de origen de la arista
	 * @param destinationElement El nodo de destino de la arista
	 * @return El peso de la arista, o un valor negativo si la arista no existe
	 * @throws NullPointerException       si alguno de los elementos es nulo
	 * @throws ElementNotPresentException si alguno de los nodos no existe en el grafo
	 */
	@Override
	public double getEdge(T originElement, T destinationElement) {
		if (originElement == null || destinationElement == null) {
			throw new NullPointerException();
		}
		if (getNode(originElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (getNode(destinationElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (edges[getNode(originElement)][getNode(destinationElement)]) {
			return weights[getNode(originElement)][getNode(destinationElement)];
		} else {
			return -1;
		}
	}
	/**
	 * Obtiene el número actual de nodos en el grafo.
	 * 
	 * @return El número de nodos
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Obtiene el indice que coincide con el elemento pasado como parámetrp
	 * 
	 * @param element, elemento a buscar en la lista de nodos
	 * @return el número si existe o -1 si no existe
	 */
	public int getNode(T element) {
		for (int i = 0; i < size; i++) {
			if (nodes[i].equals(element)) {
				return i;
			}

		}
		return -1; // no existe el nodo
	}

	/**
	 * @return Cadena de texto  mostrando la información del grafo
	 * incluyendo los nodos con sus aristas y pesos
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		String cadena = "";
		cadena += "NODOS\n";
		for (int i = 0; i < size; i++) {
			cadena += nodes[i].toString() + "\t";
		}
		cadena += "\n\nARISTAS\n";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (edges[i][j])
					cadena += "T\t";
				else
					cadena += "F\t";
			}
			cadena += "\n";
		}
		cadena += "\nPESOS\n";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cadena += (edges[i][j] ? df.format(weights[i][j]) : "-") + "\t";
			}
			cadena += "\n";
		}
		return cadena;
	}

	/**
	 * Algoritmo de Dijkstra
	 * 
	 * @param nodoOrigen, desde el cual se empieza a ejecutar el algoritmo
	 * @return un objecto de la clase dijkstra
	 */
	public DijkstraDataClass dijkstra(T nodoOrigen) {
		if (nodoOrigen == null) {
			throw new NullPointerException();

		}
		if (getNode(nodoOrigen) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		int index = getNode(nodoOrigen);

		double D[] = inicializarD(nodoOrigen);
		int P[] = inicializarP(nodoOrigen);
		boolean S[] = inicializarS(nodoOrigen);

		int w = getPivote(D, S);

		while (w != -1) {
			S[w] = true;
			for (int i = 0; i < size; i++) {
				if (!S[i] && edges[w][i] && D[i] > D[w] + weights[w][i]) {
					D[i] = D[w] + weights[w][i];
					P[i] = w;
				}
			}
			w = getPivote(D, S);
		}
		return new DijkstraDataClass(size, index, D, P);
	}

	/**
	 * Inicializa el vector S del algorimto, el vector de los nodos recorridos
	 * 
	 * @param nodoOrigen, desde le que se empieza el algoritmo
	 * @return el vector inicializado a true la posicion del nodo origen
	 */
	private boolean[] inicializarS(T nodoOrigen) {
		boolean S[] = new boolean[size];
		S[getNode(nodoOrigen)] = true;
		return S;
	}

	/**
	 * Inicializa el vector p de caminos del algoritmo
	 * 
	 * @param nodoOrigen, nodo desde el que se empieza
	 * @return el vector p inicilializado a -1
	 */
	private int[] inicializarP(T nodoOrigen) {
		int P[] = new int[size];
		for (int i = 0; i < size; i++) {
			P[i] = -1;
		}
		return P;
	}

	/**
	 * Inicializa el vector de costes si es el mismo elemento con 0, con el peso si
	 * hay arista y con infinito si no se alcanza
	 * 
	 * @param nodoOrigen, desde que se empieza el algoritmo
	 * @return el vector de costes
	 */
	private double[] inicializarD(T nodoOrigen) {
		int index = getNode(nodoOrigen);
		double D[] = new double[size];

		for (int i = 0; i < size; i++) {
			if (index == i) {
				D[i] = 0;
			} else if (edges[index][i]) {
				D[i] = weights[index][i];
			} else {
				D[i] = Double.POSITIVE_INFINITY;
			}

		}
		return D;
	}

	/**
	 * Obtiene el pivote del algoritomo, el camino de coste mínimo
	 * 
	 * @param D vector de costes
	 * @param S vector de uso de elementos del grafo
	 * @return el pivote a utilizar
	 */
	public int getPivote(double D[], boolean S[]) {
		double min = Double.POSITIVE_INFINITY;
		int pivote = -1;
		for (int i = 0; i < size; i++) {

			if (!S[i] && D[i] < min) {
				min = D[i];
				pivote = i;
			}

		}
		return pivote;
	}

	@Override
	/**
	 * Ejecuta el algoritmo de Floyd-Warshall para encontrar los caminos más cortos entre todos
	 * los pares de vértices. Los resultados se almacenan internamente y pueden accederse a través de
	 * minimumCostPathFloyd y printFloydPath.
	 * 
	 * @return false si el grafo está vacío (size == 0), true en caso contrario.
	 */
	public boolean floyd() {
		if (size == 0) {
			return false;
		}
		P = new int[size][size];
		A = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				P[i][j] = -1;
				if (i == j) {
					A[i][j] = 0;
				} else if (edges[i][j]) {
					A[i][j] = weights[i][j];
				} else {
					A[i][j] = Double.POSITIVE_INFINITY;
				}
			}

		}

		for (int k = 0; k < size; k++) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (A[i][k] + A[k][j] < A[i][j]) {
						A[i][j] = A[i][k] + A[k][j];
						P[i][j] = k;
					}
				}
			}

		}
		return true;

	}

	/**
	 * Realiza un recorrido en profundidad del grafo comenzando desde el elemento especificado.
	 * 
	 * @param originElement El elemento desde el cual comenzar el recorrido
	 * @return Una representación en cadena del recorrido, con los nodos separados por tabuladores
	 * @throws NullPointerException       si el elemento de inicio es null
	 * @throws ElementNotPresentException si el elemento de inicio no existe en el grafo
	 */
	@Override
	public String recorridoProfundidad(T originElement) {
		if (originElement == null) {
			throw new NullPointerException();
		}
		if (getNode(originElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		int index = getNode(originElement);
		boolean[] visitados = new boolean[size];
		List<T> resultado = new ArrayList<>();

		recorrido(index, visitados, resultado);

		return ponerTabuladores(resultado);
	}
/**
 * Inserta los tabuladores 
 * @param resultado, la lista de los nodos recorridos
 * @return una cadena los los nodos recorridos con tabuladors de por medio
 */
	private String ponerTabuladores(List<T> resultado) {
		String result = "";
		for (T element : resultado) {
			result += element.toString() + "\t";
		}
		return result;
	}

	/**
	 *  Algoritmo recursivo para realizar el recorrido en profundidad
	 *  Marcamos el nodos que estamos a true, ya se visito
	 *  Lo añadimos a la lista de resultados
	 *  Miramos todos sus vecinos y si no han sido alcanzados lo añadimos a la lista de sus vecinos
	 *  Ejecutamos el algoritmo recursivamente con toods los vecinos 
	 *  
	 * @param currentIndex, indice del nodo actual que estamos mirando
	 * @param visitados, el vector de nodos que ya han sido alcanzados
	 * @param resultado, lista en la que se van añadiendo los nodos conforme se van recorriendo
	 */
	private void recorrido(int currentIndex, boolean[] visitados, List<T> resultado) {
		visitados[currentIndex] = true;
		resultado.add(getElement(currentIndex));

		List<Integer> vecinos = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			if (edges[currentIndex][i] && !visitados[i]) {
				vecinos.add(i);
			}
		}

		for (Integer indiceVecino : vecinos) {
			if (!visitados[indiceVecino]) {
				recorrido(indiceVecino, visitados, resultado);
			}
		}

	}
	/**
	 * Devuelve una representación en cadena del camino más corto entre dos nodos usando
	 * el algoritmo de Floyd. 
	 * @param originElement      El nodo de inicio
	 * @param destinationElement El nodo de destino
	 * @return Una cadena que muestra el camino desde el origen al destino, o un mensaje
	 *         indicando que no existe camino
	 *	 
	 *	/**
		* Indica el camino entre los nodos que se le pasan como parametros de esta
		* forma: donde cada nodo (Origen, Destino, IntermedioN,...) se refiere al
		* toString del nodo correspondiente
		* Origen<tabulador>(coste0)<tabulador>Intermedio1<tabulador>(coste1)<tabulador>IntermedioN<tabulador>(costeN)<tabulador>Destino<tabulador>
		* Si no hay camino: Origen<tabulador>(Infinity)<tabulador>Destino<tabulador>
		* Si Origen y Destino coinciden: Origen<tabulador> 
		* Si no existen los 2 nodos devuelve una cadena vacia
		* 
		 */
	@Override
	public String printFloydPath(T originElement, T destinationElement) {
		if (originElement == null) {
			return "";
		}
		if (destinationElement == null) {
			return "";
		}
		if (originElement.equals(destinationElement)) {
			return originElement.toString() + "\t";
		}
		int origen = getNode(originElement);
		int destino = getNode(destinationElement);
		if (A[origen][destino] == Double.POSITIVE_INFINITY) {
			return originElement + "\t(Infinity)\t" + destinationElement + "\t";
		}
		String path = originElement.toString() + printPath(origen, destino);

		return path + "\t";
	}
/**
 * Metodo recursivo del print panth
 * Caso base hay camino entre los nodos
 * Se devuelve el coste en entre ellos
 * Caso recursivo no hay camino directo entre los dos nodos y se pasa por un
 * pivote en el medio 
 * @param i elemento de origen
 * @param j elemento de destino
 * @return El recorrido desde el nodo de origen hasta el destino
 */
	private String printPath(int i, int j) {
		int k = P[i][j];

		if (k == -1) {
			double cost = A[i][j];
			T destinationElement = getElement(j);
			return "\t(" + cost + ")\t" + destinationElement.toString();

		} else {
			String path1 = printPath(i, k);
			String path2 = printPath(k, j);
			return path1 + path2;
		}

	}
/**
 * Devuelve el nodo en la poscion indicada
 * @param j, la posicion indicada
 * @return el nodo 
 */
	private T getElement(int j) {
		if (j > size) {
			return null;
		}
		return nodes[j];
	}
	/**
	 * Obtiene el coste mínimo del camino desde el origen al destino usando el algoritmo de Floyd.
	 * 
	 * 
	 * @param originElement      El nodo de inicio
	 * @param destinationElement El nodo de destino
	 * @return El coste mínimo, o INFINITO si no existe camino
	 * @throws NullPointerException       si alguno de los elementos es null
	 * @throws ElementNotPresentException si alguno de los nodos no existe en el grafo
	 */
	@Override
	public double minCostPath(T originElement, T destinationElement) {
		if (originElement == null || destinationElement == null) {
			throw new NullPointerException();
		}
		if (getNode(originElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}
		if (getNode(destinationElement) == -1) {
			throw new ElementNotPresentException("Alguno de los elementos no está en el grafo");
		}

		double[][] newA = getA();
		return newA[getNode(originElement)][getNode(destinationElement)];
	}
/**
 * 
 * @return La matriz A de floyd
 */
	public double[][] getA() {
		return A;
	}
/**
 * 
 * @return La matriz P de floyd
 */
	public int[][] getP() {
		return P;
	}


}
