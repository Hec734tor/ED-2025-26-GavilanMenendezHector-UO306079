package P2.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import P2.exception.ElementNotPresentException;
import P2.exception.FullStructureException;
import P2.model.Graph;

class TestBench {
	private Graph<Integer> grafo;

	@BeforeEach
	public void setUp() {
		grafo = new Graph<Integer>(4);
	}

	@Test
	void testAdd() {
		assertTrue(grafo.addNode(1));
		assertTrue(grafo.addNode(15));
		assertTrue(grafo.addNode(3));
		assertEquals(0, grafo.getNode(1));
		assertEquals(1, grafo.getNode(15));
		assertEquals(2, grafo.getNode(3));
		assertThrows(NullPointerException.class, () -> {
			grafo.addNode(null);
		});
		assertFalse(grafo.addNode(3));
		grafo.addNode(4);
		assertEquals(4, grafo.getSize());
		assertThrows(FullStructureException.class, () -> {
			grafo.addNode(7);
		});
		System.out.println(grafo.toString());
	}

	@Test
	void testExistNode() {
		grafo.addNode(3);
		grafo.addNode(2);
		assertFalse(grafo.existsNode(5));
		assertTrue(grafo.existsNode(3));
		assertTrue(grafo.existsNode(2));

		assertThrows(NullPointerException.class, () -> {
			grafo.addNode(null);
		});

	}

	@Test
	void testAddEdge() {
		grafo.addNode(2);
		grafo.addNode(5);
		grafo.addNode(7);
		assertTrue(grafo.addEdge(2, 7, 10));
		assertFalse(grafo.addEdge(2, 7, 10));
		assertEquals(10, grafo.getEdge(2, 7));
		assertTrue(grafo.existsEdge(2, 7));
		assertThrows(IllegalArgumentException.class, () -> {
			grafo.addEdge(2, 7, -1);
		});
		assertThrows(NullPointerException.class, () -> {
			grafo.addEdge(2, null, 10);
		});
		assertThrows(NullPointerException.class, () -> {
			grafo.addEdge(null, 7, 10);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.addEdge(9, 7, 10);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.addEdge(2, 45, 10);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			grafo.addEdge(2, 5, -1);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			grafo.addEdge(2, 5, 0);
		});

	}

	@Test
	void testRemoveEdge() {
		grafo.addNode(2);
		grafo.addNode(6);
		grafo.addNode(9);
		grafo.addEdge(2, 6, 10);
		assertFalse(grafo.removeEdge(6, 9));
		assertTrue(grafo.removeEdge(2, 6));
		assertThrows(NullPointerException.class, () -> {
			grafo.removeEdge(null, 6);
		});
		assertThrows(NullPointerException.class, () -> {
			grafo.removeEdge(2, null);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.removeEdge(6, 7);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.removeEdge(9, 5);
		});

	}

	@Test
	void testExistEdge() {
		grafo.addNode(3);
		grafo.addNode(8);
		grafo.addNode(15);
		assertFalse(grafo.existsEdge(3, 8));
		grafo.addEdge(3, 8, 10);
		assertTrue(grafo.existsEdge(3, 8));
		assertFalse(grafo.existsEdge(3, 4));
		assertThrows(NullPointerException.class, () -> {
			grafo.existsEdge(null, 8);
		});
		assertThrows(NullPointerException.class, () -> {
			grafo.existsEdge(3, null);
		});

	}

	@Test
	void testGetEdge() {
		grafo.addNode(4);
		grafo.addNode(8);
		grafo.addNode(12);
		grafo.addEdge(4, 8, 5);
		assertEquals(5, grafo.getEdge(4, 8));
		assertThrows(NullPointerException.class, () -> {
			grafo.getEdge(null, 8);
		});
		assertThrows(NullPointerException.class, () -> {
			grafo.getEdge(4, null);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.getEdge(6, 8);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.getEdge(4, 5);
		});
	}

	@Test
	void Floyd() {
		grafo = new Graph<Integer>(10);
		grafo.addNode(1);
		grafo.addNode(2);
		grafo.addNode(3);
		grafo.addNode(4);
		grafo.addNode(5);
		grafo.addNode(6);

		grafo.addEdge(1, 5, 8);
		grafo.addEdge(1, 2, 3);
		grafo.addEdge(1, 3, 4);

		grafo.addEdge(2, 5, 5);

		grafo.addEdge(3, 5, 3);

		grafo.addEdge(5, 4, 7);

		grafo.addEdge(5, 6, 3);
		grafo.addEdge(6, 4, 2);

		double inf = Double.POSITIVE_INFINITY;

		assertTrue(grafo.floyd());
		double[][] A = grafo.getA();
		int[][] P = grafo.getP();

		double[][] expectedA = new double[][] { { 0, 3, 4, 12, 7, 10 }, { inf, 0, inf, 10, 5, 8 },
				{ inf, inf, 0, 8, 3, 6 }, { inf, inf, inf, 0, inf, inf }, { inf, inf, inf, 5, 0, 3 },
				{ inf, inf, inf, 2, inf, 0 } };
		int[][] expectedP = new int[][] { { -1, -1, -1, 5, 2, 4 }, { -1, -1, -1, 5, -1, 4 }, { -1, -1, -1, 5, -1, 4 },
				{ -1, -1, -1, -1, -1, -1 }, { -1, -1, -1, 5, -1, -1 }, { -1, -1, -1, -1, -1, -1 } };

		assertArrayEquals(expectedA, A);
		assertArrayEquals(expectedP, P);

		assertEquals(0, grafo.minCostPath(1, 1));
		assertEquals(inf, grafo.minCostPath(2, 1));
		assertEquals(10, grafo.minCostPath(2, 4));
		System.out.println(grafo.printFloydPath(1, 5));
		System.out.println(grafo.printFloydPath(1, 6));
		System.out.println(grafo.printFloydPath(1, 4));
		System.out.println(grafo.printFloydPath(1, 2));
		System.out.println(grafo.printFloydPath(5, 1));

	}

	@Test
	public void floydClase() {
		grafo = new Graph<Integer>(10);
		grafo.addNode(1);
		grafo.addNode(2);
		grafo.addNode(3);
		grafo.addNode(4);
		grafo.addNode(5);

		grafo.addEdge(1, 2, 1);
		grafo.addEdge(1, 4, 3);
		grafo.addEdge(1, 5, 10);

		grafo.addEdge(2, 3, 5);

		grafo.addEdge(3, 5, 1);
		grafo.addEdge(4, 5, 6);
		grafo.addEdge(4, 3, 2);

		double inf = Double.POSITIVE_INFINITY;

		assertTrue(grafo.floyd());
		double[][] A = grafo.getA();
		int[][] P = grafo.getP();

		double[][] expectedA = new double[][] { { 0, 1, 5, 3, 6 }, { inf, 0, 5, inf, 6 }, { inf, inf, 0, inf, 1 },
				{ inf, inf, 2, 0, 3 }, { inf, inf, inf, inf, 0 }, };
		int[][] expectedP = new int[][] { { -1, -1, 3, -1, 3, }, { -1, -1, -1, -1, 2 }, { -1, -1, -1, -1, -1 },
				{ -1, -1, -1, -1, 2, }, { -1, -1, -1, -1, -1 }, };

		assertArrayEquals(expectedA, A);
		assertArrayEquals(expectedP, P);

		assertEquals(0, grafo.minCostPath(1, 1));
		assertEquals(inf, grafo.minCostPath(2, 1));
		assertEquals(inf, grafo.minCostPath(2, 4));
		
		assertEquals ("1	(3.0)	4	(2.0)	3	(1.0)	5	",grafo.printFloydPath(1, 5));
		assertEquals ("1	(3.0)	4	",grafo.printFloydPath(1, 4));
		assertEquals ("1	(1.0)	2	",grafo.printFloydPath(1, 2));
		assertEquals ("5	(Infinity)	1	",grafo.printFloydPath(5, 1));
		
		
		
	
		
		
	}

	@Test
	public void recorridoEjemploClase() {
		grafo = new Graph<Integer>(10);
		grafo.addNode(1); // A
		grafo.addNode(2); // B
		grafo.addNode(3); // C
		grafo.addNode(4); // D
		grafo.addNode(5); // E
		grafo.addNode(6); // F
		grafo.addNode(7); // G
		grafo.addNode(8); // H
		grafo.addNode(9); // I
		grafo.addNode(10); // J

		grafo.addEdge(1, 2, 1);
		grafo.addEdge(1, 8, 1);

		grafo.addEdge(2, 3, 1);
		grafo.addEdge(2, 4, 1);
		grafo.addEdge(2, 7, 1);

		grafo.addEdge(3, 5, 1);
		grafo.addEdge(3, 6, 1);

		grafo.addEdge(4, 9, 1);

		grafo.addEdge(5, 6, 1);
		grafo.addEdge(5, 4, 1);

		grafo.addEdge(7, 8, 1);

		grafo.addEdge(9, 6, 1);
		grafo.addEdge(9, 10, 1);
		assertThrows(NullPointerException.class, () -> {
			grafo.recorridoProfundidad(null);
		});
		assertThrows(ElementNotPresentException.class, () -> {
			grafo.recorridoProfundidad(25);
		});
		assertEquals("1	2	3	5	4	9	6	10	7	8	", grafo.recorridoProfundidad(1));
	}

	@Test
	public void testDijktraClase() {
		Graph<String> graph = new Graph<String>(8);
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addNode("D");
		graph.addNode("E");
		graph.addNode("F");
		graph.addNode("G");
		graph.addNode("H");

		graph.addEdge("A", "B", 1);
		graph.addEdge("A", "E", 7);

		graph.addEdge("B", "C", 3);
		graph.addEdge("B", "A", 9);
		graph.addEdge("B", "F", 10);

		graph.addEdge("C", "G", 3);

		graph.addEdge("D", "F", 4);
		graph.addEdge("D", "H", 9);

		graph.addEdge("E", "G", 6);
		graph.addEdge("E", "H", 9);

		graph.addEdge("G", "E", 4);
		graph.addEdge("G", "H", 8);

		graph.addEdge("H", "F", 8);
		assertThrows(ElementNotPresentException.class, () -> {
			graph.dijkstra("W");
		});
		assertThrows(NullPointerException.class, () -> {
			graph.dijkstra(null);
		});

		Assert.assertArrayEquals(new double[] { 9.0, 0.0, 3.0, Double.POSITIVE_INFINITY, 10.0, 10.0, 6.0, 14.0 },
				graph.dijkstra("B").getdDijkstra(), 0);

	}

	@Test
	public void testDijkstraSinAristas() {
		Graph<Integer> graph = new Graph<Integer>(4);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);

		Assert.assertArrayEquals(
				new double[] { 0.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
				graph.dijkstra(1).getdDijkstra(), 0);
		Assert.assertArrayEquals(new int[] { -1, -1, -1, -1 }, graph.dijkstra(1).getpDijkstra());
	}

	@Test
	public void testDijkstraNodosAislados() {
		Graph<Integer> graph = new Graph<Integer>(5);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);
		graph.addNode(4);
		graph.addNode(5);

		graph.addEdge(1, 2, 4);
		graph.addEdge(2, 3, 1);
		// nodos 4 y 5 aislados

		Assert.assertArrayEquals(new double[] { 0.0, 4.0, 5.0, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY },
				graph.dijkstra(1).getdDijkstra(), 0);
		Assert.assertArrayEquals(new int[] { -1, -1, 1,-1,-1 }, graph.dijkstra(1).getpDijkstra());
	}

	@Test
	public void testDijkstraConBucle() {
		Graph<Integer> graph = new Graph<Integer>(3);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);

		graph.addEdge(1, 1, 2); // bucle
		graph.addEdge(1, 2, 5);
		graph.addEdge(2, 3, 1);

		Assert.assertArrayEquals(new double[] { 0.0, 5.0, 6.0 }, graph.dijkstra(1).getdDijkstra(), 0);
		Assert.assertArrayEquals(new int[] { -1, -1, 1 }, graph.dijkstra(1).getpDijkstra());
	}

	@Test
	public void testDijkstraNodoFuenteInicio() {
		Graph<Integer> graph = new Graph<Integer>(3);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);

		graph.addEdge(1, 1, 2);
		graph.addEdge(1, 2, 5);
		graph.addEdge(1, 3, 1);
		Assert.assertArrayEquals(new double[] { 0.0, 5, 1 }, graph.dijkstra(1).getdDijkstra(), 0);
		Assert.assertArrayEquals(new int[] { -1, -1, -1 }, graph.dijkstra(1).getpDijkstra());
	}

	@Test
	public void testDijkstraNodoSumidero() {
		Graph<Integer> graph = new Graph<Integer>(3);
		graph.addNode(1);
		graph.addNode(2);
		graph.addNode(3);

		graph.addEdge(1, 1, 2);
		graph.addEdge(1, 2, 5);
		graph.addEdge(1, 3, 1);
		Assert.assertArrayEquals(new double[] { Double.POSITIVE_INFINITY, 0, Double.POSITIVE_INFINITY },
				graph.dijkstra(2).getdDijkstra(), 0);
		Assert.assertArrayEquals(new int[] { -1, -1, -1 }, graph.dijkstra(2).getpDijkstra());
	}

}
