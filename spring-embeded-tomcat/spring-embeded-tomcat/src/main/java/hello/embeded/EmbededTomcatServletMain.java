package hello.embeded;

import hello.servlet.HelloServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class EmbededTomcatServletMain {
    public static void main(String[] args) throws LifecycleException {
        System.out.println("embeded tomcat servlet");
        // 톰캣 생성
        Tomcat tomcat = new Tomcat();

        // 커넥터 생성
        Connector connector = new Connector();
        connector.setPort(8080);

        // 연결
        tomcat.setConnector(connector);

        // 서블릿 등록
        Context context = tomcat.addContext("", "/");
        tomcat.addServlet("","helloServlet", new HelloServlet());
        context.addServletMappingDecoded("/hello-servlet","helloServlet");

        tomcat.start();
    }
}
