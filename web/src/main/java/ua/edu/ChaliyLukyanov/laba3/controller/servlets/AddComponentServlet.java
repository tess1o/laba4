package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import javax.ejb.CreateException;

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

public class AddComponentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("Shoplogger");

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        String producer = request.getParameter("producer");
        String img = request.getParameter("img");
        String price = request.getParameter("price");
        String weight = request.getParameter("weight");

        try {
            Component comp = model.create(title, desc, producer, new Double(weight), img, new Double(price));
            response.sendRedirect(request.getContextPath() + "/showcomponent?id=" + comp.getId());
        } catch (ShopException e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath()
                    + "/add_component.jsp?title=" + title + "&desc=" + desc
                    + "&prod=" + producer + "&img=" + img + "&pr=" + price
                    + "&w=" + weight + "&error=" + e.getMessage());
        } catch (NumberFormatException e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath()
                    + "/add_component.jsp?title=" + title + "&desc=" + desc
                    + "&prod=" + producer + "&img=" + img + "&pr=" + price
                    + "&w=" + weight + "&error=" + e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath()
                    + "/add_component.jsp?title=" + title + "&desc=" + desc
                    + "&prod=" + producer + "&img=" + img + "&pr=" + price
                    + "&w=" + weight + "&error=" + e.getMessage());
        } catch (NoSuchComponentException e) {
            logger.error(e);
            throw new NoSuchComponentException(e.getMessage());
        } catch (CreateException e) {
            logger.error(e);
            throw new ShopException(e);
        }
    }
}
