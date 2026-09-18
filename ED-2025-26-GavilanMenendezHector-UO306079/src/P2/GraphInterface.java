package P2;

import P2.model.DijkstraDataClass;

/**
 * Interfaz que define una estructura de datos de grafo con operaciones básicas para nodos y aristas. 
 * Las implementaciones de esta interfaz representan un grafo dirigido y ponderado.

	@param El tipo de elementos almacenados en el grafo
 */
public interface GraphInterface<T> {

	/**
	 * Añade un nodo al grafo.
	 * 
	 * @param element Elemento a añadir como nodo
	 * @return {@code true} Si el nodo se ha añadido, {@code false} si el nodo ya exste
	 * @throws NullPointerException   si el elemento es null
	 * @throws FullStructureException Si se ha alcanzado la máxima capacidad del grafo
	 */
	boolean addNode(T element);

	/**
	 * Borra nodos del grafo. Si se borra un nodo también se borran todas las aristas del mismo
	 * 
	 * @param element El nodo a borrar
	 * @return {@code true} Si el nodo ha sido correctamente borrado, {@code false} si el nodo no existe
	 * @throws NullPointerException Si el elemento es null
	 */
	boolean removeNode(T element);

	/**
	 * Chequea si existe un nodo en el grafo
	 * 
	 * @param element El elemento a chequear
	 * @return {@code true} si el nodo existe, {@code false} Otro caso
	 * @throws NullPointerException Si el elemento es null
	 */
	boolean existsNode(T element);

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
	boolean addEdge(T originElement, T destinationElement, double weight);

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

	boolean removeEdge(T originElement, T destinationElement);

	/**
	 * Verifica si existe una arista dirigida entre dos nodos.
	 * 
	 * @param originElement      El nodo de origen de la arista
	 * @param destinationElement El nodo de destino de la arista
	 * @return {@code true} si la arista existe, {@code false} en caso contrario
	 * @throws NullPointerException si alguno de los elementos es nulo
	 */

	boolean existsEdge(T originElement, T destinationElement);

	/**
	 * Obtiene el peso de una arista dirigida entre dos nodos.
	 * 
	 * @param originElement      El nodo de origen de la arista
	 * @param destinationElement El nodo de destino de la arista
	 * @return El peso de la arista, o un valor negativo si la arista no existe
	 * @throws NullPointerException       si alguno de los elementos es nulo
	 * @throws ElementNotPresentException si alguno de los nodos no existe en el grafo
	 */

	double getEdge(T originElement, T destinationElement);

	/**
	 * Obtiene el número actual de nodos en el grafo.
	 * 
	 * @return El número de nodos
	 */

	int getSize();
	
	/**
	 * Ejecuta el algoritmo de Dijkstra para encontrar los caminos más cortos desde un vértice origen
	 * hasta todos los demás vértices.
	 * 
	 * @param originElement El vértice de origen desde el cual se calcularán los caminos
	 * @return Un objeto de la clase DijkstraDataClass que contiene los caminos y costes calculados
	 * @throws NullPointerException       si startingElement es nulo
	 * @throws ElementNotPresentException si startingElement no existe en el grafo
	 */
	public DijkstraDataClass dijkstra(T originElement);
	
	/**
	 * Ejecuta el algoritmo de Floyd-Warshall para encontrar los caminos más cortos entre todos
	 * los pares de vértices. Los resultados se almacenan internamente y pueden accederse a través de
	 * minimumCostPathFloyd y printFloydPath.
	 * 
	 * @return false si el grafo está vacío (size == 0), true en caso contrario.
	 */
	public boolean floyd();

	/**
	 * Realiza un recorrido en profundidad del grafo comenzando desde el elemento especificado.
	 * 
	 * @param originElement El elemento desde el cual comenzar el recorrido
	 * @return Una representación en cadena del recorrido, con los nodos separados por tabuladores
	 * @throws NullPointerException       si el elemento de inicio es null
	 * @throws ElementNotPresentException si el elemento de inicio no existe en el grafo
	 */
	public String recorridoProfundidad(T originElement);

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
	public String printFloydPath(T originElement, T destinationElement);

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
	public double minCostPath(T originElement, T destinationElement);

	
}
