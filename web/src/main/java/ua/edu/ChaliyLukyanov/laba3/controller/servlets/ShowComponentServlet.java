package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
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

public class ShowComponentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("Shoplogger");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
        try {
            Component component = model.findByPrimaryKey(id);
            request.setAttribute("component", component);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/show_component.jsp");
            dispatcher.forward(request, response);
        } catch (ShopException e) {
            logger.error(e);
            throw new ShopException(e.getMessage());
        } catch (NoSuchComponentException e) {
            logger.error(e);
            throw new NoSuchComponentException(e.getMessage());
        } catch (FinderException e){
            logger.error(e);
            throw new NoSuchComponentException(e);
        }
    }
}
