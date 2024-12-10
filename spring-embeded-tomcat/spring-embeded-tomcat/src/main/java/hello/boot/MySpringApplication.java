package hello.boot;

import hello.HelloConfig;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MySpringApplication {
    public static void run(Class configClass, String[] args) {
        System.out.println("embeded tomcat servlet");
        // 톰캣 생성
        Tomcat tomcat = new Tomcat();

        // 커넥터 생성
        Connector connector = new Connector();
        connector.setPort(8080);

        // 연결
        tomcat.setConnector(connector);

        // 스프링 컨네이터 생성
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        ac.register(configClass);

        DispatcherServlet dispatcher = new DispatcherServlet(ac);

        // 서블릿 등록
        Context context = tomcat.addContext("", "/");
        tomcat.addServlet("","dispatcher", dispatcher);
        context.addServletMappingDecoded("/","dispatcher");

        try {
            tomcat.start();
        } catch (Exception e) {}
    }
}
