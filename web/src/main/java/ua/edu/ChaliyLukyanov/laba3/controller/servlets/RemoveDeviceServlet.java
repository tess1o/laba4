package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Enumeration;

import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.Consts;
import ua.edu.ChaliyLukyanov.laba3.model.device.Device;
import ua.edu.ChaliyLukyanov.laba3.model.device.DeviceHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class RemoveDeviceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		DeviceHome model = (DeviceHome) request
				.getAttribute(Application.DEVICE);
		try {
			Enumeration<String> names = (Enumeration<String>) request
					.getParameterNames();
			while (names.hasMoreElements()) {
				int id = Integer.parseInt(names.nextElement());
				model.remove(id);
				logger.info("Device " + id + " removed");
			}
			DeviceHome deviceHome = (DeviceHome) request.getAttribute(Application.DEVICE);
			Collection<Device> devices = deviceHome.findAll();
			request.setAttribute(Consts.DEVICES, devices);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove_device.jsp");
			dispatcher.forward(request, response);
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NumberFormatException e) {
			logger.info(e);
			throw new NumberFormatException(e.getMessage());
		} catch (RemoveException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
			throw new ShopException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			DeviceHome deviceHome = (DeviceHome) request.getAttribute(Application.DEVICE);
			Collection<Device> devices = deviceHome.findAll();
			request.setAttribute(Consts.DEVICES, devices);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/remove_device.jsp");
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
