package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.FinderException;
import javax.ejb.RemoveException;

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
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

/**
 * Remove component from database. Parse ID from parameters and remove component with this id.
 * @author chalyi
 *
 */
public class RemoveComponentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			ComponentHome model = (ComponentHome) request.getAttribute(Application.COMPONENT);
			model.remove((Integer) Integer.parseInt(request.getParameter(Consts.ID_COMPONENT)));
			logger.info("Component " + request.getParameter(Consts.ID_COMPONENT) + " removed");
			Collection<Component> components = model.findAll();
			request.setAttribute(Consts.COMPONENTS, components);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove_component.jsp");
			dispatcher.forward(request, response);
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NumberFormatException e) {
			logger.info(e);
			throw new NumberFormatException(Consts.INCORRECT_VALUE	+ e.getMessage());
		} catch (RemoveException e) {
			logger.error(e);
			throw new ShopException(e);
		} catch (FinderException e) {
			logger.error(e);
			throw new ShopException(e);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT);
			Collection<Component> components = compHome.findAll();
			request.setAttribute(Consts.COMPONENTS, components);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove_component.jsp");
			dispatcher.forward(request, response);
		} catch (FinderException e) {
			logger.error(e);
			throw new ShopException(e);
		} catch (RemoteException e) {
			logger.error(e);
			throw e;
		}
	}
}
