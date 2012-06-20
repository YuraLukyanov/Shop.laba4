package ua.edu.ChaliyLukyanov.laba3.model.EJB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ua.edu.ChaliyLukyanov.laba3.model.ShopException;

import javax.ejb.CreateException;
import javax.ejb.RemoveException;
import javax.ejb.FinderException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.rmi.RemoteException;
import javax.sql.DataSource;

import java.util.*;

public class DeviceBean implements EntityBean {
	
	private EntityContext context;
	
	private Integer id;
	private Integer id_prev;
	private Integer id_component;
	private String title;

	/**
	 * Finds device's entity by id in the database.
	 */	
	public Integer ejbFindByPrimaryKey(Integer id) throws FinderException, EJBException, RemoteException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet row = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(Consts.FIND_BY_ID_DEVICE);
			st.setInt(1, id);
			row = st.executeQuery();
			if (row.next()) {
				return id;
			} else {
				throw new FinderException("No such device with id " + id + " in database!");
			}			
		} catch (SQLException e) {
			throw new EJBException(Consts.CANT_GET_DEVICE_FROM_DB, e);
		} finally {
			closeAll(conn, st, row);
		}
	}
	/**
	 * Loads device's entity from database.
	 */
    public void ejbLoad() throws EJBException, RemoteException {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;
		id = (Integer) context.getPrimaryKey();
		try {
			conn = getConnection();
            stat = conn.prepareStatement(Consts.GET_DEVICE_BY_ID);
            stat.setInt(1, id);
            res = stat.executeQuery();
            if (res.next()) {
                this.id_prev = res.getInt(1);
                this.id_component = res.getInt(2);
				this.title = res.getString(3);
			}
		} catch (SQLException e) {
			throw new EJBException(e);
		} finally {
			closeAll(conn, stat, res);
		}
	}

	public void ejbStore() throws EJBException, RemoteException {
/*        Connection conn = null;
        PreparedStatement st = null;
		try {
			DataSource ds = (DataSource) context.lookup("java:/Oracle");
			conn = ds.getConnection();
			st = conn.prepareStatement(Consts.UPDATE_DEVICE);
			st.setString(1, title);
			st.setInt(2, id_prev);
			st.setInt(3, id_component);
			st.setInt(4, id);
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
		}*/
	}	
	
	public void setEntityContext(EntityContext ctx) throws EJBException, RemoteException {
		context = ctx;
	}

	public void unsetEntityContext() throws EJBException, RemoteException {
		context = null;
	}

    public void ejbActivate() throws EJBException, RemoteException {   }

    public void ejbPassivate() throws EJBException, RemoteException {   }	

	/**
	 * Creates a new device's entity.
	 */	
	public Integer ejbCreate(Integer id_prev, Integer id_component, String title) throws CreateException, EJBException, RemoteException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(Consts.INSERT_DEVICE);
			st.setInt(1, id_prev);
			st.setInt(2, id_component);
			st.setString(3, title);
			st.executeUpdate();
			this.id = currId();
		} catch (SQLException e) {
			throw new EJBException(Consts.CANT_ADD_DEVICE_TO_DB, e);
		} finally {
			closeAll(conn, st, null);
		}
		this.id_prev = id_prev;
		this.id_component = id_component;
		this.title = title;
		return id;
	}

	public void ejbPostCreate(Integer id_prev, Integer id_component, String title) throws RemoteException { }	

	/**
	 * Finds all device's entities in the database.
	 */
	public List<Integer> ejbFindAllDevices() throws FinderException, EJBException, RemoteException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet row = null;
		List<Integer> list = new LinkedList<Integer>();
		try {
			conn = getConnection();
			st = conn.prepareStatement(Consts.GET_ALL_DEVICES_ID);
			row = st.executeQuery();
			while (row.next()) {
				list.add(row.getInt(Consts.ID_DEVICE));
			}
			if (list.isEmpty()) {
				throw new FinderException(Consts.DEVICE_IS_EMPTY);
			} else {
				return list; 
			}
		} catch (SQLException e) {
			throw new EJBException(Consts.CANT_GET_DEVICES_FROM_DB, e);
		} finally {
			closeAll(conn, st, row);
		}
	}

	/**
	 * Removes device's entity from database.
	 */	
	public void ejbRemove() throws RemoveException, EJBException, RemoteException {
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(Consts.REMOVE_DEVICE);
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new EJBException(Consts.CANT_REMOVE_DEVICE_FROM_DB, e);
		} finally {
			closeAll(conn, st, null);
		}
	}

	/**
	 * Finds parent devices in hierarchy entity's structure.
	 *
	 * @param id id of child device.
	 */
	public List<Integer> ejbFindPrevLevelsDeviceByID(Integer id) throws FinderException, EJBException, RemoteException {
		try {
			return getLevelDevices(id, Consts.GET_PREV_LEVELS_DEVICE_BY_ID);
		} catch (SQLException e) {
			throw new EJBException(e);
		}		
	}

	/**
	 * Finds child devices in hierarchy entity's structure.
	 *
	 * @param id id of parent device.
	 */
	public List<Integer> ejbFindNextLevelsDeviceByID(Integer id) throws FinderException, EJBException, RemoteException {
		try {
			return getLevelDevices(id, Consts.GET_NEXT_LEVEL_DEVICE_BY_ID);
		} catch (SQLException e) {
			throw new EJBException(e);
		}	
	}

	/**
	 * Finds root device in hierarchy entity's structure.
	 */
	public List<Integer> ejbFindFirstLevelsDeviceByID(Integer id) throws FinderException, EJBException, RemoteException {
		try {		
			return getLevelDevices(id, Consts.GET_FIRST_LEVEL_DEVICES);
		} catch (SQLException e) {
			throw new EJBException(e);
		}	
	}

	private List<Integer> getLevelDevices(Integer id, String sql) throws SQLException, EJBException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<Integer> ids = new LinkedList<Integer>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (id != null) {
				ps.setInt(1, id);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				ids.addFirst(rs.getInt(Consts.ID_DEVICE));
			}
			return (List<Integer>) ids;
		} catch (SQLException e) {
			throw new SQLException("Can't show device from database", e);
		} finally {
			closeAll(conn, ps, rs);
		}
	}	
	
	private Integer currId() throws SQLException {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet row = null;
		try {
			conn = getConnection();
			st = conn.prepareStatement(Consts.GET_ID_LAST_DEVICE);
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
	
	public int getId() {
		return id;
	}

	public int getIdPrev() {
		return id_prev;
	}

	public int getIdComponent() {
		return id_component;
	}

	public String getTitle() {
		return title;
	}
	
	private void closeAll(Connection conn, PreparedStatement st, ResultSet rs) throws EJBException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new EJBException(Consts.CANT_CLOSE_RESULT_SET, e);
		}
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
	
	private Connection getConnection() throws SQLException {
		DataSource ds = (DataSource) context.lookup("java:/Oracle");
		return ds.getConnection();
	}
}