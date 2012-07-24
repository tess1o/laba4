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
import ua.edu.ChaliyLukyanov.laba3.model.Consts;

public class AddComponentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
        String title = request.getParameter(Consts.TITLE);
        String desc = request.getParameter(Consts.DESCRIPTION);
        String producer = request.getParameter(Consts.PRODUCER);
        String img = request.getParameter(Consts.IMG);
        String price = request.getParameter(Consts.PRICE);
        String weight = request.getParameter(Consts.WEIGHT);
        
        if ("".equals(title) || "".equals(desc) || "".equals(producer)){
        	logger.info(Consts.EMPTY_STRING);
        	throw new  IllegalArgumentException(Consts.TITLE_SHOULD_BE);
        }
        
        try {
            Component comp = model.create(title, desc, producer, new Double(weight), img, new Double(price));
            response.sendRedirect(request.getContextPath() + "/showcomponent?id=" + comp.getId());
        } catch (ShopException e) {
            logger.error(e);
            throw e;
        } catch (NoSuchComponentException e) {
            logger.error(e);
            throw e;
        } catch (CreateException e) {
            logger.error(e);
            throw new ShopException(e);
        } catch (NumberFormatException e){
        	logger.info(e);
        	throw e;
        }
    }
}
