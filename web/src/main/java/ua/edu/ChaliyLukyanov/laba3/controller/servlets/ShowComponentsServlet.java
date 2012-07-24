package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import java.util.Collection;

import javax.ejb.FinderException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.Consts;
import ua.edu.ChaliyLukyanov.laba3.model.component.Component;
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class ShowComponentsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
        ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
        try {
            Collection<Component> components = model.findAll();
            request.setAttribute(Consts.COMPONENTS, components);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/show_components.jsp");
            dispatcher.forward(request, response);
        } catch (ShopException e) {
            logger.error(e);
            throw e;
        } catch (NumberFormatException e) {
            logger.info(e);
            throw e;
        } catch (FinderException e) {
            logger.error(e);
            throw new NoSuchComponentException(e);
        }
    }
}
