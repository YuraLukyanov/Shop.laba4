package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import ua.edu.ChaliyLukyanov.laba3.model.ShopException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

import javax.ejb.RemoveException;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.rmi.RemoteException;
import javax.sql.DataSource;

public class ComponentBean implements EntityBean {

	private EntityContext context;
	
	private Integer id;
	private String title;
	private String description;
	private String producer;
	private double weight;
	private String img;
	private double price;
	
	public int ejbHomeGetIdLastComponent() throws /*RemoteException,*/ ShopException {
		try {
			return currId().intValue();
		} catch (SQLException e) {
			throw new ShopException("Can't get last component ID" + e.getMessage());
		} 
	}
	
	public Integer ejbCreate(String title, String description, 
							String producer, double weight, String img, 
							double price) throws CreateException, RemoteException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			st = conn.prepareStatement(Consts.INSERT_COMPONENT);
			st.setString(1, title);
			st.setString(2, description);
			st.setString(3, producer);
			st.setDouble(4, weight);
			st.setString(5, img);
			st.setDouble(6, price);
			st.execute();
			this.id = currId();
		} catch (SQLException e) {
			throw new CreateException();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new CreateException(Consts.CANT_CLOSE_STATEMENT);
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new CreateException(Consts.CANT_CLOSE_CONNECTION);
			}
		}
		this.title = title;
		this.description = description;
		this.producer = producer;
		this.weight = weight;
		this.img = img;
		this.price = price;
		return this.id;
	}
	
	public void ejbPostCreate(String title, String description, 
							String producer, double weight, String img, 
							double price) throws RemoteException { }
	
	public List<Integer> ejbFindAllComponents() throws FinderException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet row = null;
		List<Integer> list = new ArrayList<Integer>();
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			st = conn.prepareStatement(Consts.GET_ALL_COMPONENTS_ID);
			row = st.executeQuery();
			while (row.next()) {
				list.add(row.getInt(Consts.ID_COMPONENT));
			}
			if (list.isEmpty()) {
				throw new FinderException("Can't get components from database. DataBase is empty.");
			} else {
				return list; 
			}
		} catch (SQLException e) {
			throw new FinderException("Can't get component from database");
		} finally {
			try {
				if (row != null) {
					row.close();
				}
			} catch (SQLException e) {
				throw new FinderException(Consts.CANT_CLOSE_RESULT_SET);
			}
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new FinderException(Consts.CANT_CLOSE_STATEMENT);
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new FinderException(Consts.CANT_CLOSE_CONNECTION);
			}
		}
	}
	
	public Integer ejbFindByPrimaryKey(Integer id) throws FinderException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet row = null;
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			st = conn.prepareStatement(Consts.FIND_BY_ID_COMPONENT);
			st.setInt(1,id.intValue());
			row = st.executeQuery();
			if (row.next()) {
				return id;
			} else {
				throw new FinderException("Can't get component from database with this id = " + id);
			}
		} catch (SQLException e) {
			throw new FinderException("Can't get component from database");
		} finally {
			try {
				if (row != null) {
					row.close();
				}
			} catch (SQLException e) {
				throw new FinderException(Consts.CANT_CLOSE_RESULT_SET);
			}
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new FinderException(Consts.CANT_CLOSE_STATEMENT);
			}

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new FinderException(Consts.CANT_CLOSE_CONNECTION);
			}
		}
	}
	
    public void ejbActivate() throws EJBException, RemoteException
    {   
        // container is wonting to do some operations with component interface
        // we needed to get id for the operations with component object in database 
    }
	
    public void ejbLoad() throws EJBException, RemoteException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
		id = (Integer) context.getPrimaryKey();
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
            stat = conn.prepareStatement(Consts.GET_COMPONENT_BY_ID);
                // id we had gotten from context in the ejbActivate() method
            stat.setInt(1, id);
            res = stat.executeQuery();
            if (res.next()) {
                this.title = res.getString(1);
                this.description = res.getString(2);
				this.producer = res.getString(3);
				this.weight = res.getDouble(4);
				this.img = res.getString(5);
				this.price = res.getDouble(6);
            } else {
                throw new EJBException("No employee was found. Id = " + id);
            }
		} catch (SQLException e) {
			throw new EJBException(e);
		} finally {
			try {
				if (res != null) {
					res.close();
				}
			} catch (SQLException e) {
				throw new EJBException(Consts.CANT_CLOSE_RESULT_SET, e);
			}
			try {
				if (stat != null) {
					stat.close();
				}
			} catch (SQLException e) {
				throw new EJBException(Consts.CANT_CLOSE_STATEMENT, e);
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new EJBException(Consts.CANT_CLOSE_CONNECTION, e);
			}
		}
	}
	
	public void ejbPassivate() throws EJBException, RemoteException {}
	
	public void ejbRemove() throws EJBException, RemoteException, RemoveException {
        Connection conn = null;
        PreparedStatement stat = null;
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			stat = conn.prepareStatement(Consts.REMOVE_COMPONENT);
			stat.setInt(1,id);
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new RemoveException(e.getMessage());
		} finally {
			try {
				if (stat != null) {
					stat.close();
				}
			} catch (SQLException e) {
				throw new RemoveException(Consts.CANT_CLOSE_STATEMENT);
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new RemoveException(Consts.CANT_CLOSE_CONNECTION);
			}
		}
	}
	
	public void ejbStore() throws EJBException, RemoteException {
        Connection conn = null;
        PreparedStatement st = null;
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			st = conn.prepareStatement(Consts.UPDATE_COMPONENT);
			st.setString(1, title);
			st.setString(2, description);
			st.setString(3, producer);
			st.setDouble(4, price);
			st.setDouble(5, weight);
			st.setString(6, img);
			st.setInt(7, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new EJBException(e);
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new EJBException(Consts.CANT_CLOSE_STATEMENT, e);
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new EJBException(Consts.CANT_CLOSE_CONNECTION, e);
			}
		}
	}
	
	public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
		context = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		context = null;
	}
	
	public int getId() {
		return id.intValue();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
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
	
	private Integer currId() throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet row = null;
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			st = conn.prepareStatement(Consts.GET_ID_LAST_COMPONENT);
			row = st.executeQuery();
			if (row.next()) {
				return new Integer(row.getInt(1));
			} else {
				throw new SQLException("id error");
			}
		} finally {
			if (row != null) {
				row.close();
			}	
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}