package ua.edu.ChaliyLukyanov.laba3.model.device;

import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.CreateException;

import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import ua.edu.ChaliyLukyanov.laba3.model.constants.Consts;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchDeviceException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;


public class DeviceBean implements EntityBean {

	private static final long serialVersionUID = 1L;
	private EntityContext context;
    private Integer id;
    private Integer idPrev;
    private Integer idComponent;
    private String title;
    private int level;

    public Collection<Integer> ejbFindAll() throws FinderException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        Collection<Integer> res = new ArrayList<Integer>();
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_ALL_DEVICES);
            row = st.executeQuery();
            while (row.next()) {
                res.add(row.getInt(Consts.ID_DEVICE));
            }
            return res;
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_FIND_DEVICES, e);
        } finally {

            try {
                if (row != null) {
                    row.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_RESULT_SET, e);
            }

            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }
    
    public Integer ejbFindByPrimaryKey(Integer id) throws FinderException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_DEVICE_BY_ID);
            st.setInt(1, id.intValue());
            row = st.executeQuery();
            if (!row.next()) {
                throw new FinderException(Consts.CANT_GET_DEVICE_ID);
            }
            this.id = id;
            return id;

        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_GET_DEVICE_ID, e);
        } finally {

            try {
                if (row != null) {
                    row.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_RESULT_SET, e);
            }
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }

    public void ejbPostCreate(String title, Integer idPrev, Integer idComponent)
            throws CreateException{}
    
    public Integer ejbCreate(String title, Integer idPrev, Integer idComponent)
            throws CreateException {

        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.INSERT_DEVICE);
            this.id = getNextId();
			st.setInt(1,id);
			if (idPrev == -1) {
                st.setNull(2, Types.INTEGER);
            } else {
                st.setInt(2, idPrev);
            }
            st.setInt(3, idComponent);
            st.setString(4, title);
            st.executeUpdate();
			
            this.idComponent = idComponent;
            this.title = title;
            this.idPrev = idPrev;
            return id;
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_CREATE_DEVICE, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }

    @Override
    public void ejbLoad() throws EJBException, RemoteException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_DEVICE_BY_ID);
            this.id = (Integer) context.getPrimaryKey();
            st.setInt(1, id);
            row = st.executeQuery();
            if (row.next()) {
                setId(row.getInt(Consts.ID_DEVICE));
                setIdComponent(row.getInt(Consts.ID_COMPONENT));
                setIdPrev(row.getInt(Consts.ID_PREV));
                setTitle(row.getString(Consts.TITLE));
            } else {
                throw new NoSuchDeviceException(Consts.NO_SUCH_DEVICE);
            }
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_LOAD_DEVICE, e);
        } finally {
            try {
                if (row != null) {
                    row.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_RESULT_SET, e);
            }

            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }

    @Override
    public void ejbRemove() throws RemoveException, EJBException, RemoteException {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.REMOVE_DEVICE);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_REMOVE_DEVICE, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }

    public Collection<Integer> ejbFindPrevLevelsDeviceByID(Integer id) throws FinderException, RemoteException {
        return getLevelDevices(id, Consts.GET_PREV_LEVELS_DEVICE_BY_ID);
    }

    public Collection<Integer> ejbFindNextLevelsDeviceByID(Integer id) throws FinderException, RemoteException {
        return getLevelDevices(id, Consts.GET_NEXT_LEVEL_DEVICE_BY_ID);
    }

    public Collection<Integer> ejbFindFirstLevelsDeviceByID(Integer id) throws FinderException, RemoteException {
        return getLevelDevices(id, Consts.GET_FIRST_LEVEL_DEVICES);
    }

    private List<Integer> getLevelDevices(int id, String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedList<Integer> devices = new LinkedList<Integer>();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            if (id != 0) {
                ps.setInt(1, id);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                devices.addFirst(rs.getInt(Consts.ID_DEVICE));
            }
            return (List<Integer>) devices;
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_GET_DEVICE_ID, e);
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_RESULT_SET, e);
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }

    @Override
    public void ejbStore() throws EJBException, RemoteException {
    }

    @Override
    public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
        context = ctx;
    }

    @Override
    public void unsetEntityContext() throws EJBException, RemoteException {
        context = null;
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException {
    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException {
    }

    public int getNextId() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        int ident = 0;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_NEXT_DEVICE_ID);
            row = st.executeQuery();

            if (row.next()) {
                ident = row.getInt(1);
            } else {
                throw new NoSuchDeviceException(Consts.NO_SUCH_DEVICE);
            }
            return ident;
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_GET_DEVICE_ID, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_STATEMENT, e);
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new ShopException(Consts.CANT_CLOSE_CONNECTION, e);
            }
        }
    }

	private Connection getConnection() throws SQLException {
		DataSource ds = null;
		try {
			ds = (DataSource) new InitialContext().lookup(Consts.DB_NAME);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return ds.getConnection();
	}

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdPrev() {
        return idPrev;
    }

    public void setIdPrev(Integer idPrev) {
        this.idPrev = idPrev;
    }

    public int getIdComponent() {
        return idComponent;
    }

    public void setIdComponent(Integer id_component) {
        this.idComponent = id_component;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
