package hello.embeded;

import hello.HelloConfig;
import hello.servlet.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class EmbededTomcatSpringMain {
    public static void main(String[] args) throws LifecycleException {
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
        ac.register(HelloConfig.class);

        DispatcherServlet dispatcher = new DispatcherServlet(ac);

        // 서블릿 등록
        Context context = tomcat.addContext("", "/");
        tomcat.addServlet("","dispatcher", dispatcher);
        context.addServletMappingDecoded("/","dispatcher");

        tomcat.start();
    }
}
