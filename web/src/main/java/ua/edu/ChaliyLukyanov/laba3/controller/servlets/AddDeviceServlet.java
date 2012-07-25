package ua.edu.ChaliyLukyanov.laba3.controller.servlets;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
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
 * Add device to database
 * @author chalyi
 *
 */
public class AddDeviceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(Consts.LOGGER_NAME);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
        	
        	DeviceHome model = (DeviceHome) request.getAttribute(Application.DEVICE);
            String title = request.getParameter(Consts.TITLE);

            if ("".equals(title)) {
            	RuntimeException e = new IllegalArgumentException(Consts.TITLE_SHOULD_BE);
                logger.error(e);
                throw e;
            }
            Device dev =
                    model.create(title, Integer.valueOf(request.getParameter(Consts.ID_PREV_DEVICE)),
                    Integer.valueOf(request.getParameter(Consts.ID_COMPONENT)));
            logger.info("Device added to");
            response.sendRedirect(request.getContextPath() + "/shownextleveldevices?id=" + dev.getId());
        } catch (ShopException e) {
            logger.error(e);
            throw e;
        } catch (NoSuchDeviceException e) {
            logger.error(e);
            throw e;
        } catch (CreateException e) {
            logger.error(e);
            throw new ShopException(e);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    		try{
				ComponentHome compHome = (ComponentHome) request.getAttribute(Application.COMPONENT);
				DeviceHome deviceHome = (DeviceHome) request.getAttribute(Application.DEVICE);
				
				Collection<Component> components = compHome.findAll();
				Collection<Device> devices = deviceHome.findAll();
				
				request.setAttribute(Consts.COMPONENTS, components);
				request.setAttribute(Consts.DEVICES, devices);

				RequestDispatcher dispatcher = request.getRequestDispatcher("/add_device.jsp");
	            dispatcher.forward(request, response);
    		} catch (FinderException e){
    			logger.error(e);
    			throw new ShopException(e);
    		} catch (RemoteException e){
    			logger.error(e);
    			throw e;
    		}
    }
}
