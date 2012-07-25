package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import javax.ejb.FinderException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Consts;
import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.component.Component;
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

/**
 * Edit components. Change title or producer or description or price.
 * @author chalyi
 *
 */
public class EditComponentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
			int id = Integer.parseInt(request.getParameter(Consts.ID));
			Component component = model.findByPrimaryKey(id);
			request.setAttribute(Consts.COMPONENT, component);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/edit_component.jsp");
			dispatcher.forward(request, response);
		} catch (ShopException e) {
			logger.error(e);
			throw e;
		} catch (NoSuchComponentException e) {
			logger.error(e);
			throw e;
		} catch (FinderException e) {
			logger.error(e);
			throw new NoSuchComponentException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
			Integer id = new Integer(request.getParameter(Consts.ID_COMPONENT));
			Component component = model.findByPrimaryKey(id);
			String title = request.getParameter(Consts.TITLE);
			String desc = request.getParameter(Consts.DESCRIPTION);
			String producer = request.getParameter(Consts.PRODUCER);
			double price = 0.0;

			if ("".equals(title) || "".equals(desc) || "".equals(producer)) {
				logger.info(Consts.EMPTY_STRING);
				throw new IllegalArgumentException(Consts.EMPTY_STRING);
			}
			
			price = Double.valueOf(request.getParameter(Consts.PRICE));

			component.setTitle(title);
			component.setDescription(desc);
			component.setProducer(producer);
			component.setPrice(price);
			logger.info("Component " + id + " updated");
			response.sendRedirect(request.getContextPath() + "/showcomponent?id=" + id);
		} catch (ShopException e) {
			logger.error(e);
			throw e;
		} catch (FinderException e) {
			logger.error(e);
			throw new NoSuchComponentException(e);
		} catch (NumberFormatException e) {
			logger.info(Consts.INCORRECT_VALUE);
			throw e;
		}
	}
}
