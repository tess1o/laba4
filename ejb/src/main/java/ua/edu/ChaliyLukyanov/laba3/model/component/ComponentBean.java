package ua.edu.ChaliyLukyanov.laba3.model.component;

import java.rmi.RemoteException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.RemoveException;

import javax.sql.DataSource;

import ua.edu.ChaliyLukyanov.laba3.model.constants.Consts;
import ua.edu.ChaliyLukyanov.laba3.model.exception.NoSuchComponentException;
import ua.edu.ChaliyLukyanov.laba3.model.exception.ShopException;

public class ComponentBean implements EntityBean {

	private static final long serialVersionUID = 1L;
	private EntityContext context;
    private Integer id;
    private String title;
    private String description;
    private String producer;
    private double weight;
    private String img;
    private double price;

    public Collection<Integer> ejbFindAll() throws FinderException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        Collection<Integer> res = new ArrayList<Integer>();
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_ALL_COMPONENTS);
            row = st.executeQuery();
            while (row.next()) {
                res.add(row.getInt(Consts.ID_COMPONENT));
            }
            return res;
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_FIND_COMPONENTS, e);
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
    
    public void ejbPostCreate(String title, String description, String producer,
            double weight, String img, double price) throws  CreateException {}

    public Integer ejbCreate(String title, String description, String producer,
            double weight, String img, double price) throws  CreateException {


        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.INSERT_COMPONENT);
            st.setString(1, title);
            st.setString(2, description);
            st.setString(3, producer);
            st.setDouble(4, weight);
            st.setString(5, img);
            st.setDouble(6, price);
            st.execute();
            this.id = this.getCurrentComponentId();
            setTitle(title);
            setImg(img);
            setDescription(description);
            setProducer(producer);
            setPrice(price);
            setWeight(weight);
            return id;
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_CREATE_COMPONENT, e);
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

    public Integer ejbFindByPrimaryKey(Integer id) throws FinderException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_COMPONENT_BY_ID);
            st.setInt(1, id.intValue());
            row = st.executeQuery();
            if (!row.next()) {
                throw new FinderException(Consts.CANT_GET_COMPONENT);
            }
            this.id = id;
            return id;

        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_GET_COMPONENT, e);
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
    public void ejbStore() throws EJBException, RemoteException {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.UPDATE_COMPONENT);
            st.setString(1, getTitle());
            st.setString(2, getDescription());
            st.setString(3, getProducer());
            st.setDouble(4, getWeight());
            st.setString(5, getImg());
            st.setDouble(6, getPrice());
            st.setInt(7, getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_STORE_COMPONENT, e);
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
            this.id = (Integer) context.getPrimaryKey();
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_COMPONENT_BY_ID);
            st.setInt(1, id.intValue());
            row = st.executeQuery();
            if (!row.next()) {
                throw new NoSuchEntityException(Consts.CANT_LOAD_COMPONENT);
            }

            setTitle(row.getString(Consts.TITLE));
            setDescription(row.getString(Consts.DESCRIPTION));
            setProducer(row.getString(Consts.PRODUCER));
            setWeight(row.getDouble(Consts.WEIGHT));
            setImg(row.getString(Consts.IMG));
            setPrice(row.getDouble(Consts.PRICE));
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
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
    public void ejbActivate() throws EJBException, RemoteException {
    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException {
    }

    @Override
    public void setEntityContext(EntityContext context) throws EJBException,
            RemoteException {
        this.context = context;
    }

    @Override
    public void unsetEntityContext() throws EJBException, RemoteException {
        context = null;
    }

    @Override
    public void ejbRemove() throws RemoveException, EJBException, RemoteException {

        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.REMOVE_COMPONENT);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new ShopException(Consts.CANT_REMOVE_COMPONENT, e);
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


    private Integer getCurrentComponentId() {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet row = null;
        int ident = 0;
        try {
            conn = getConnection();
            st = conn.prepareStatement(Consts.GET_ID_LAST_COMPONENT);
            row = st.executeQuery();
            if (row.next()) {
                ident = row.getInt(1);
            } else {
                throw new NoSuchComponentException("Can't get last component ID");
            }
            return ident;
        } catch (SQLException e) {
            throw new ShopException("Can't get components from database", e);
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

    private Connection getConnection() throws SQLException {
        DataSource ds = (DataSource) context.lookup(Consts.DB_NAME);
        return ds.getConnection();
    }

    public int getId() {
        return id.intValue();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            this.title = "";
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
