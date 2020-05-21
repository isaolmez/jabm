package net.sourceforge.jabm.report;

import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.collections15.Transformer;

public class DotWriter<V, E> {

    protected DecimalFormat labelFormatter = new DecimalFormat("#.00");

    /**
     * graph graphname { // The label attribute can be used to change the label of a node a [label="Foo"]; // Here, the
     * node shape is changed. b [shape=box]; // These edges both have different line properties a -- b -- c
     * [color=blue]; b -- d [style=dotted]; }
     **/

    public DotWriter() {
    }

    /**
     * Saves <code>g</code> to <code>filename</code>. Labels for vertices may be supplied by <code>vs</code>. Edge
     * weights are specified by
     * <code>nev</code>.
     */
    public void save(Graph<V, E> g, String filename, Transformer<V, String> vs,
      Transformer<E, Number> nev, Transformer<V, Point2D> vld)
      throws IOException {
        save(g, new FileWriter(filename), vs, nev, vld);
    }

    /**
     * Saves <code>g</code> to <code>filename</code>. Labels are specified by
     * <code>vs</code>, and edge weights by <code>nev</code>; vertex coordinates
     * are not written out.
     *
     * @param g the graph to write out
     */
    public void save(Graph<V, E> g, String filename, Transformer<V, String> vs,
      Transformer<E, Number> nev) throws IOException {
        save(g, new FileWriter(filename), vs, nev, null);
    }

    /**
     * Saves <code>g</code> to <code>filename</code>; no vertex labels are written out, and the edge weights are written
     * as 1.0.
     */
    public void save(Graph<V, E> g, String filename) throws IOException {
        save(g, filename, null, null, null);
    }

    /**
     * Saves <code>g</code> to <code>w</code>; no vertex labels are written out, and the edge weights are written as
     * 1.0.
     */
    public void save(Graph<V, E> g, Writer w) throws IOException {
        save(g, w, null, null, null);
    }

    /**
     * Saves <code>g</code> to <code>w</code>; vertex labels are given by
     * <code>vs</code> and edge weights by <code>nev</code>.
     */
    public void save(Graph<V, E> g, Writer w, Transformer<V, String> vs,
      Transformer<E, Number> nev) throws IOException {
        save(g, w, vs, nev, null);
    }

    /**
     * Writes <code>graph</code> to <code>w</code>. Labels for vertices may be supplied by <code>vs</code> (defaults to
     * no labels if null), edge weights may be specified by <code>nev</code> (defaults to weights of 1.0 if null), and
     * vertex locations may be specified by <code>vld</code> (defaults to no locations if null).
     */
    public void save(Graph<V, E> graph, Writer w, Transformer<V, String> vs,
      Transformer<E, Number> nev, Transformer<V, Point2D> vld)
      throws IOException {

        BufferedWriter writer = new BufferedWriter(w);

        if (nev == null) {
            nev = new Transformer<E, Number>() {
                public Number transform(E e) {
                    return 1;
                }
            };
        }

        // writer.write("*Vertices " + graph.getVertexCount());
        // writer.newLine();

        List<V> id = new ArrayList<V>(graph.getVertices());// Indexer.getIndexer(graph);

        //

        Collection<E> d_set = new HashSet<E>();
        Collection<E> u_set = new HashSet<E>();

        boolean directed = graph instanceof DirectedGraph;

        boolean undirected = graph instanceof UndirectedGraph;

        String graphName = "jung" + graph.hashCode();

        // if it's strictly one or the other, no need to create extra sets
        if (directed) {
            d_set.addAll(graph.getEdges());
            writer.write("digraph ");
        }
        if (undirected) {
            u_set.addAll(graph.getEdges());
            writer.write("graph ");
        }
        if (!directed && !undirected) // mixed-mode graph
        {
            writer.write("digraph ");
            u_set.addAll(graph.getEdges());
            d_set.addAll(graph.getEdges());
            for (E e : graph.getEdges()) {
                if (graph.getEdgeType(e) == EdgeType.UNDIRECTED) {
                    d_set.remove(e);
                } else {
                    u_set.remove(e);
                }
            }
        }
        writer.write(graphName + " {");
        writer.newLine();

        for (V currentVertex : graph.getVertices()) {
            // convert from 0-based to 1-based index
            int v_id = id.indexOf(currentVertex) + 1;
            writer.write("" + v_id + " [");
            if (vs != null) {
                String label = vs.transform(currentVertex);
                if (label != null) {
                    writer.write("label = \"" + label + "\"");
                }
            }
//			if (vld != null) {
//				Point2D location = vld.transform(currentVertex);
//				if (location != null)
//					writer.write(" " + location.getX() + " " + location.getY()
//							+ " 0.0");
//			}
//			writer.write("fontsize=6");
//			writer.write(",height=" +nodeHeight + ",width=" + nodeWidth);
//			writer.write(",shape=circle");
            writer.write("]");
            writer.newLine();
        }

        // write out directed edges
//		if (!d_set.isEmpty()) {
//			writer.write("*Arcs");
//			writer.newLine();
//		}
        for (E e : d_set) {
            int source_id = id.indexOf(graph.getEndpoints(e).getFirst()) + 1;
            int target_id = id.indexOf(graph.getEndpoints(e).getSecond()) + 1;
            float weight = nev.transform(e).floatValue();
            String label = labelFormatter.format(weight);
            writer.write("  " + source_id + " -> " + target_id + " [label=\""
              + label + "\"]");
            writer.newLine();
        }

        // write out undirected edges
//		if (!u_set.isEmpty()) {
//			writer.write("*Edges");
//			writer.newLine();
//		}
        for (E e : u_set) {
            Pair<V> endpoints = graph.getEndpoints(e);
            int v1_id = id.indexOf(endpoints.getFirst()) + 1;
            int v2_id = id.indexOf(endpoints.getSecond()) + 1;
            float weight = nev.transform(e).floatValue();
            String label = labelFormatter.format(weight);
            writer.write(v1_id + " -- " + v2_id + " [label=\"" + label + "\"]");
            writer.newLine();
        }

        writer.write("}");
        writer.newLine();

        writer.close();
    }
}
