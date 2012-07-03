package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.FinderException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.component.Component;
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class ShowComponentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("Shoplogger");

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
        try {
            Collection<Component> components = model.findAll();
            List<String> producers = new ArrayList<String>();
            for (Component comp:components){
            	producers.add(comp.getProducer());
            }
            Set<String> prod = new HashSet<String>(producers);
            request.setAttribute("producers", prod);
            request.setAttribute("components", components);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/show_components.jsp");
            dispatcher.forward(request, response);
        } catch (ShopException e) {
            logger.error(e);
            throw new ShopException(e.getMessage());
        } catch (NumberFormatException e) {
            logger.error(e);
            throw new NumberFormatException(e.getMessage());
        } catch (FinderException e) {
            logger.error(e);
            throw new NoSuchComponentException(e);
        }
    }
}
