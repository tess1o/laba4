package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.ejb.FinderException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.component.Component;
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentComparator;
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class FilterComponentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger("Shoplogger");

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ComponentHome model = (ComponentHome) request
				.getAttribute(Application.COMPONENT);
		try {
			List<Component> components = (List<Component>) model.findAll();

			Comparator<Component> comparator = null;

			String sortOrder = request.getParameter("sortOrder");
			String sortBy = request.getParameter("sortBy");
			String producer = request.getParameter("producer");
			String priceOrder = request.getParameter("priceOrder");

			double priceValue = 0.0;
			if (!"none".equals(priceOrder)) {
				priceValue = Double.parseDouble(request
						.getParameter("priceValue"));
				if (">=".equals(priceOrder)) {
					Iterator<Component> iter = components.iterator();
					while (iter.hasNext()) {
						if (!(iter.next().getPrice() >= priceValue)) {
							iter.remove();
						}
					}
				} else if ("<=".equals(priceOrder)) {
					Iterator<Component> iter = components.iterator();
					while (iter.hasNext()) {
						if (!(iter.next().getPrice() <= priceValue)) {
							iter.remove();
						}
					}
				}
			}

			if (!"none".equals(producer)) {
				Iterator<Component> iter = components.iterator();
				while (iter.hasNext()) {
					if (!iter.next().getProducer().equals(producer)) {
						iter.remove();
					}
				}
				request.setAttribute("show", producer);
			} else {
				request.setAttribute("show", "all");
			}

			if ("title".equals(sortBy)) {
				comparator = new ComponentComparator.ProducerComparator();
			} else if ("producer".equals(sortBy)) {
				comparator = new ComponentComparator.ProducerComparator();
			} else if ("price".equals(sortBy)) {
				comparator = new ComponentComparator.PriceComparator();
			}

			if (!"none".equals(sortBy)) {
				Collections.sort(components, comparator);
			}

			if ("desc".equals(sortOrder)) {
				Collections.reverse(components);
			}

			request.setAttribute("components", components);
			request.setAttribute("sortBy", sortBy);
			request.setAttribute("sortOrder", sortOrder);

			if (!"none".equals(priceOrder)) {
				request.setAttribute("sign", priceOrder);
			} else {
				request.setAttribute("sign", "no filter");
			}

			if (Math.abs(priceValue) > 1e-7) {
				request.setAttribute("price", priceValue);
			} else {
				request.setAttribute("price", "");
			}

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/filter_components.jsp");
			dispatcher.forward(request, response);
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new NumberFormatException("Incorrect value " + e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
			throw new NoSuchComponentException(e);
		}

	}
}
