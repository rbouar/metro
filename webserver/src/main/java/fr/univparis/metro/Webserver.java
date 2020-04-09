package fr.univparis.metro;
import java.io.IOException;
import java.io.File;
import io.javalin.plugin.rendering.template.TemplateUtil;
import io.javalin.*;
import java.util.HashMap;
import java.util.Set;
import java.util.List;

public class Webserver {
  public static void main(String[] args) throws IOException {
    Configuration.loadFrom(new File("src/main/resources/cities.json"));
    Trafics.initTrafics();
    Javalin app = launch();
    installIndex(app);
    installCity(app);
    installToCity(app);
    InstallItinerary(app);
    installAddPerturbation(app);
    installRemovePerturbation(app);
  }


  public static Javalin launch() {
    Javalin app = Javalin.create (config -> {
      // config.addStaticFiles ("public/");
      config.enableWebjars ();
    }).start (8080);
    return app;
  }

  public static void installIndex(Javalin app) {
    app.get("/", ctx -> {
      ctx.render("/public/index.ftl", TemplateUtil.model ( "cities", WebserverLib.toOption() ));
    });
  }

  public static void installToCity(Javalin app) {
    app.post("/toCity", ctx -> {
      ctx.redirect("/" + ctx.formParam("city"));
    });
  }

  public static void installCity(Javalin app) {
    app.get("/:city", ctx -> {
      String city = ctx.pathParam("city");
      Set<String> cities = Trafics.getCities();
      if (! cities.contains(city)) {
        app.error(404, c -> {});
        return;
      }
      ctx.render("/public/city.ftl", TemplateUtil.model(
        "city", city,
        "perturbation", WebserverLib.perturbationToHtml(city)
      ));
    });
  }

  public static void InstallItinerary(Javalin app) {
    app.post("/:city/itinerary", ctx -> {
        WGraph<Station> g = Trafics.getGraph(ctx.pathParam("city"));
        Station start = new Station(ctx.formParam("start"), "Meta Station Start");
        Station end = new Station(ctx.formParam("end"), "Meta Station End");
        String body = "";
        if (! g.getVertices().contains(start)){
          body = start.getName() + " doesn't exist";
        }
        else if (! g.getVertices().contains(end)) {
          body = end.getName() + " doesn't exist";
        }
        else {
          if (ctx.formParam("type").equals("shortest")) {
            HashMap<Station, Station> prev = new HashMap<Station, Station>();
            HashMap<Station, Double> dist = new HashMap<Station, Double>();
            Dijkstra.shortestPath(g, start, prev, dist);
            try {
              String time = WebserverLib.time(dist.get(end));
              String itinerary = WebserverLib.path(prev, end);
              body =
                "<h2>Time</h2>\n" +
                time + "\n" +
                "<h2>Itinerary</h2>\n" +
                itinerary;
            } catch(NullPointerException e) {
              body = "Due to actual trafics perturbation we couldn't find any path from " + start.getName() + " to " + end.getName();
            }
          }
          else if(ctx.formParam("type").equals("leastConnexion")){
            HashMap<String, MatriceWGraph> lines = MatriceWGraph.initializeAllLineGraphs(g);
            MatriceWGraph matriceGraph = new MatriceWGraph(g, lines);
            ArrayList<Pair<String, String>> l = LimitedConnectionSearch.getPath(matriceGraph, start.getName(), end.getName());
            Collections.reverse(l);
            Double t = matriceGraph.getDirect()[matriceGraph.getSetOfVertices().get(start.getName())][matriceGraph.getSetOfVertices().get(end.getName())];
            ctx.render("/public/itinerary.ftl", TemplateUtil.model("time", WebserverLib.time(t), "itinerary", WebserverLib.path(l, start.getName(), end.getName())));
          }
        }
        ctx.render("/public/itinerary.ftl", TemplateUtil.model(
        "body", body
        ));
    });
  }

  public static void installAddPerturbation(Javalin app) {
    app.post("/:city/addPerturbation", ctx -> {
      String city = ctx.pathParam("city");
      String name = ctx.formParam("name");
      Trafics.Perturbation type = Trafics.Perturbation.valueOf(ctx.formParam("type"));
      Object parameter = null;
      switch (type) {
        case LINE_SHUTDOWN:
          parameter = ctx.formParam("line");
          break;
        case LINE_SLOW_DOWN:
          parameter = new Pair<String, Double>(ctx.formParam("line"), Double.valueOf(ctx.formParam("times")));
          break;
        case ENTIRE_STATION_SHUT_DOWN:
          parameter = ctx.formParam("station_name");
          break;
        case PART_STATION_SHUT_DOWN:
          parameter = new Station(ctx.formParam("station_name"), ctx.formParam("station_line"));
          break;
      }

      Trafics.addPerturbation(city, type, name, parameter);
      ctx.redirect("/" + city);
    });
  }

  public static void installRemovePerturbation(Javalin app) {
    app.post("/:city/removePerturbation", ctx -> {
      String city = ctx.pathParam("city");
      List<String> l = ctx.formParams("removePerturbation");
      for (String s : l) {
        Trafics.revertPerturbation(city, s);
      }
      ctx.redirect("/" + city);
    });
  }


}
