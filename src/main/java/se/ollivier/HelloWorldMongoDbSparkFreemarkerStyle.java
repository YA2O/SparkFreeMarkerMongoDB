package se.ollivier;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.IOException;
import java.io.StringWriter;
import java.net.UnknownHostException;

public class HelloWorldMongoDbSparkFreemarkerStyle {

    public static void main(String[] args) throws UnknownHostException {
        final Configuration freemarkerConf = new Configuration();
        freemarkerConf.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));
        DB database = client.getDB("course");
        final DBCollection collection = database.getCollection("hello");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                StringWriter writer = new StringWriter();
                try {
                    Template helloTemplate = freemarkerConf.getTemplate("hello.ftl");
                    final DBObject document = collection.findOne();
                    helloTemplate.process(document, writer);
                } catch (IOException | TemplateException e) {
                    halt(500);
                    e.printStackTrace();
                }
                return writer;
            }
        });
    }
}
