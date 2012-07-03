package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import javax.ejb.RemoveException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class RemoveComponentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("Shoplogger");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
        try {
            model.remove((Integer) Integer.parseInt(request.getParameter("id_component")));
            logger.info("Component " + request.getParameter("id_component") + " removes");
            response.sendRedirect(request.getContextPath() + "/remove_component.jsp?");
        } catch (ShopException e) {
            logger.error(e);
            throw new ShopException(e.getMessage());
        } catch (NumberFormatException e) {
            logger.error(e);
            throw new NumberFormatException("Incorrect value " + e.getMessage());
        } catch (RemoveException e) {
            logger.error(e);
            throw new ShopException(e);
        }
    }
}
