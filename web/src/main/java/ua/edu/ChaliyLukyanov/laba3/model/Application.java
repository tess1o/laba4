package ua.edu.ChaliyLukyanov.laba3.model;

import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.device.DeviceHome;

public class Application implements ServletRequestListener, ServletContextListener {

    public static final String DEVICE = "shop_devices";
    public static final String COMPONENT = "shop_components";

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
    }

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        try {
            Context context = new InitialContext();
            Object compRef = context.lookup("Component");
            Object devRef = context.lookup("Device");
            ComponentHome componentHome = (ComponentHome) javax.rmi.PortableRemoteObject.narrow(compRef, ComponentHome.class);
            DeviceHome deviceHome = (DeviceHome) javax.rmi.PortableRemoteObject.narrow(devRef, DeviceHome.class);
            event.getServletRequest().setAttribute(COMPONENT, componentHome);
            event.getServletRequest().setAttribute(DEVICE, deviceHome);
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void sendErrorRedirect(HttpServletRequest request, HttpServletResponse response, String errorPageURL, String error)
            throws ServletException, IOException {
        request.setAttribute("exception", error);
        RequestDispatcher dispatcher = request.getRequestDispatcher(errorPageURL);
        dispatcher.forward(request, response);
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        System.setProperty("rootPath", context.getRealPath("/"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}
