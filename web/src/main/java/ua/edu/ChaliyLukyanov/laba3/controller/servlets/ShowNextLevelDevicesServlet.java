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
import ua.edu.ChaliyLukyanov.laba3.model.device.Device;
import ua.edu.ChaliyLukyanov.laba3.model.device.DeviceHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

/**
 * Get all devices and send them to JSP.
 * @author chalyi
 *
 */
public class ShowNextLevelDevicesServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		try {
			DeviceHome deviceModel = (DeviceHome) request.getAttribute(Application.DEVICE);
			ComponentHome componentModel = (ComponentHome) request.getAttribute(Application.COMPONENT);
			int id = Integer.parseInt(request.getParameter(Consts.ID));
			if (id != 0) {
				
				Collection<Device> devices = deviceModel.findNextLevelsDeviceByID(id);
				Collection<Device> prev_devices = deviceModel.findPrevLevelsDeviceByID(id);
				request.setAttribute(Consts.DEVICES, devices);
				request.setAttribute(Consts.PREV_DEVICES, prev_devices);
				request.setAttribute(Consts.THIS_DEVICE, deviceModel.findByPrimaryKey(id));

				Component component = componentModel
						.findByPrimaryKey(deviceModel.findByPrimaryKey(
								Integer.parseInt(request
										.getParameter(Consts.ID)))
								.getIdComponent());
				
				request.setAttribute(Consts.COMPONENT, component);
			} else {
				Collection<Device> devices = deviceModel.findFirstLevelsDeviceByID(id);
				request.setAttribute(Consts.DEVICES, devices);
				request.setAttribute(Consts.PREV_DEVICES, null);
				request.setAttribute(Consts.THIS_DEVICE, null);
				request.setAttribute(Consts.COMPONENT, null);
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("/show_devices.jsp");
			dispatcher.forward(request, response);
		} catch (NumberFormatException e) {
			logger.error(e);
			throw new NumberFormatException(Consts.INCORRECT_VALUE + e.getMessage());
		} catch (ShopException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		} catch (NoSuchDeviceException e) {
			logger.error(e);
			throw new NoSuchDeviceException(e.getMessage());
		} catch (FinderException e) {
			logger.error(e);
			throw new ShopException(e.getMessage());
		}
	}
}
