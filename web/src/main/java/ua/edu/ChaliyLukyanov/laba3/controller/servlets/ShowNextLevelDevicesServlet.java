package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
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
import ua.edu.ChaliyLukyanov.laba3.model.component.ComponentHome;
import ua.edu.ChaliyLukyanov.laba3.model.device.Device;
import ua.edu.ChaliyLukyanov.laba3.model.device.DeviceHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class ShowNextLevelDevicesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("Shoplogger");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeviceHome deviceModel = (DeviceHome) request.getAttribute(Application.DEVICE);
        ComponentHome componentModel = (ComponentHome) request.getAttribute(Application.COMPONENT);
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id != 0) {
                Collection<Device> devices = deviceModel.findNextLevelsDeviceByID(id);
                request.setAttribute("devices", devices);

                Collection<Device> prev_devices = deviceModel.findPrevLevelsDeviceByID(id);
                request.setAttribute("prev_devices", prev_devices);

                request.setAttribute("this_device", deviceModel.findByPrimaryKey(id));

                Component component = componentModel.findByPrimaryKey(deviceModel.findByPrimaryKey(Integer.parseInt(request.getParameter("id"))).getIdComponent());
                request.setAttribute("component", component);
            } else {
                Collection<Device> devices = deviceModel.findFirstLevelsDeviceByID(id);
                request.setAttribute("devices", devices);
                request.setAttribute("prev_devices", null);
                request.setAttribute("this_device", null);
                request.setAttribute("component", null);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/show_devices.jsp");
            dispatcher.forward(request, response);
        } catch (NumberFormatException e) {
            logger.error(e);
            throw new NumberFormatException("Incorrect value " + e.getMessage());
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
