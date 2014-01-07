package se.ollivier;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = configuration.getTemplate("hello.ftl");
                    Map<String, Object> helloMap = new HashMap<>();

                    helloMap.put("name", "Freemarker");

                    helloTemplate.process(helloMap, writer);

                    System.out.println(writer);
                } catch (IOException | TemplateException e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
