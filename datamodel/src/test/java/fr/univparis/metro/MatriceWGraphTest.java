package fr.univparis.metro;

import org.junit.*;
import static org.junit.Assert.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class MatriceWGraphTest{

    static WGraph<Station> g;

    @BeforeClass
    public static void loadFile() throws IOException {
        URL url = ParserTest.class.getResource("/liste.txt");
        File f = new File(url.getFile());
        g = Parser.loadFrom(f);
    }

    @Test
    public void matriceWGraphTest(){
        HashMap<String, MatriceWGraph> q = MatriceWGraph.initializeAllLineGraphs(g);
        MatriceWGraph m = new MatriceWGraph(g, q);

        ArrayList<Pair<String, String>> l = LimitedConnectionSearch.getPath(m, "Porte des Lilas", "Temple");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Porte des Lilas")][m.getSetOfVertices().get("Temple")]);
        //for(Pair<String, String> p : l) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(l.get(0).getObj(), "Temple");
        assertEquals(l.get(0).getValue(), "FIN");

        assertEquals(l.get(1).getObj(), "République");
        assertEquals(l.get(1).getValue(), "3");
            
        assertEquals(l.get(2).getObj(), "Porte des Lilas");
        assertEquals(l.get(2).getValue(), "11");

        ArrayList<Pair<String, String>> t = LimitedConnectionSearch.getPath(m, "Charles de Gaulle - Etoile", "République");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Charles de Gaulle - Etoile")][m.getSetOfVertices().get("République")]);
        //for(Pair<String, String> p : t) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(t.get(0).getObj(), "République");
        assertEquals(t.get(0).getValue(), "FIN");

        assertEquals(t.get(1).getObj(), "Concorde");
        assertEquals(t.get(1).getValue(), "8");
            
        assertEquals(t.get(2).getObj(), "Charles de Gaulle - Etoile");
        assertEquals(t.get(2).getValue(), "1");

        ArrayList<Pair<String, String>> a = LimitedConnectionSearch.getPath(m, "Robespierre", "Marx Dormoy");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Robespierre")][m.getSetOfVertices().get("Marx Dormoy")]);
        //for(Pair<String, String> p : a) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(a.get(0).getObj(), "Marx Dormoy");
        assertEquals(a.get(0).getValue(), "FIN");

        assertEquals(a.get(1).getObj(), "Marcadet - Poissonniers");
        assertEquals(a.get(1).getValue(), "12");
            
        assertEquals(a.get(2).getObj(), "Strasbourg - Saint-Denis");
        assertEquals(a.get(2).getValue(), "4");

        assertEquals(a.get(3).getObj(), "Robespierre");
        assertEquals(a.get(3).getValue(), "9");

        ArrayList<Pair<String, String>> k = LimitedConnectionSearch.getPath(m, "Botzaris", "Chemin Vert");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Botzaris")][m.getSetOfVertices().get("Chemin Vert")]);
        //for(Pair<String, String> p : k) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(k.get(0).getObj(), "Chemin Vert");
        assertEquals(k.get(0).getValue(), "FIN");

        assertEquals(k.get(1).getObj(), "République");
        assertEquals(k.get(1).getValue(), "8");
            
        assertEquals(k.get(2).getObj(), "Place des Fêtes");
        assertEquals(k.get(2).getValue(), "11");

        assertEquals(k.get(3).getObj(), "Botzaris");
        assertEquals(k.get(3).getValue(), "7bis");

        ArrayList<Pair<String, String>> u = LimitedConnectionSearch.getPath(m, "Grands Boulevards", "Michel-Ange - Molitor");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Grands Boulevards")][m.getSetOfVertices().get("Michel-Ange - Molitor")]);
        //for(Pair<String, String> p : u) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(u.get(0).getObj(), "Michel-Ange - Molitor");
        assertEquals(u.get(0).getValue(), "FIN");

        assertEquals(u.get(1).getObj(), "Grands Boulevards");
        assertEquals(u.get(1).getValue(), "9");

        ArrayList<Pair<String, String>> n = LimitedConnectionSearch.getPath(m, "Gabriel Péri", "Mirabeau");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Gabriel Péri")][m.getSetOfVertices().get("Mirabeau")]);
        //for(Pair<String, String> p : n) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(n.get(0).getObj(), "Mirabeau");
        assertEquals(n.get(0).getValue(), "FIN");

        assertEquals(n.get(1).getObj(), "Duroc");
        assertEquals(n.get(1).getValue(), "10");
            
        assertEquals(n.get(2).getObj(), "Gabriel Péri");
        assertEquals(n.get(2).getValue(), "13");
        ArrayList<Pair<String, String>> i = LimitedConnectionSearch.getPath(m, "Porte Dauphine", "Olympiades");
        //System.out.println(m.getDirect()[m.getSetOfVertices().get("Porte Dauphine")][m.getSetOfVertices().get("Olympiades")]);
        //for(Pair<String, String> p : i) System.out.println(p.getValue() + " -> " + p.getObj());

        assertEquals(i.get(0).getObj(), "Olympiades");
        assertEquals(i.get(0).getValue(), "FIN");

        assertEquals(i.get(1).getObj(), "Châtelet");
        assertEquals(i.get(1).getValue(), "14");
            
        assertEquals(i.get(2).getObj(), "Charles de Gaulle - Etoile");
        assertEquals(i.get(2).getValue(), "1");

        assertEquals(i.get(3).getObj(), "Porte Dauphine");
        assertEquals(i.get(3).getValue(), "2");
    }
}