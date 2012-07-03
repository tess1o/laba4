package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;

import javax.ejb.CreateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.edu.ChaliyLukyanov.laba3.model.Application;
import ua.edu.ChaliyLukyanov.laba3.model.device.Device;
import ua.edu.ChaliyLukyanov.laba3.model.device.DeviceHome;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class AddDeviceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger("Shoplogger");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DeviceHome model = (DeviceHome) request.getAttribute(Application.DEVICE);
        try {
            String title = request.getParameter("title");

            if ("".equals(title)) {
                throw new IllegalArgumentException("Title should be!");
            }
            Device dev =
                    model.create(title, Integer.valueOf(request.getParameter("id_prev_device")),
                    Integer.valueOf(request.getParameter("id_component")));
            logger.info("Device added");
            response.sendRedirect(request.getContextPath() + "/shownextleveldevices?id=" + dev.getId());

        } catch (ShopException e) {
            logger.error(e);
            throw new ShopException(e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error(e);
            response.sendRedirect(request.getContextPath()
                    + "/add_device.jsp?&prev=" + request.getParameter("id_prev_device")
                    + "&component=" + request.getParameter("id_component")
                    + "&error=" + e.getMessage());
        } catch (NoSuchDeviceException e) {
            logger.error(e);
            throw new NoSuchDeviceException(e.getMessage());
        } catch (CreateException e) {
            logger.error(e);
            throw new ShopException(e);
        }
    }
}
