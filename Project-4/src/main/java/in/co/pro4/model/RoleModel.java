package in.co.pro4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.pro4.bean.RoleBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DataBaseException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.util.JDBCDataSource;

/**
 * JDBC Implementation of Role Model.
 * 
 * @author Rajesh Kumar
 *
 */
public class RoleModel {
	//The Log..
	private static Logger log = Logger.getLogger(RoleModel.class);

	/**
	 * NextPK.
	 *
	 * @return the integer
	 * @throws DatabaseException the database exception
	 */
	public Integer nextPK() throws DataBaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_ROLE");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Modal nextPK End");
		return pk + 1;
	}

	/**
	 * Deletes a Role.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void delete(RoleBean bean) throws ApplicationException {

		log.debug("Modal delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("Delete FROM ST_ROLE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception...", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				// throw new ApplicationException("Exception : Delete rollback
				// exception"+ex.getMessage());
			}
			// throw new ApplicationException("Exception : Exception in delete Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Modal delete Started");
	}

	/**
	 * Adds a Role.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public long add(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;

		RoleBean duplicataRole = findByName(bean.getName());
		// Check if create Role already exist
		if (duplicataRole != null) {
			throw new DuplicateRecordException("Role already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto-generated next primary key
			System.out.println(pk + " in ModelJDBC");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_ROLE VALUES(?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getDescription());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	/**
	 * Finds Role by Name.
	 *
	 * @param Name : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public RoleBean findByName(String name) throws ApplicationException {
		log.debug("Modal findBy EmailId Started");
		StringBuffer sql = new StringBuffer("SELECT*FROM ST_ROLE WHERE NAME=?");

		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(1);
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			// throw new ApplicationException("Exception : Exception in geting User by
			// emailId");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Modal findBy EmailId End");
		return bean;

	}

	/**
	 * Finds Role by PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public RoleBean findByPK(long pk) throws ApplicationException {
		log.debug("Modal findByPK Started");

		StringBuffer sql = new StringBuffer("SELECT*FROM ST_ROLE WHERE ID=?");
		RoleBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Modal findByPK End");
		return bean;
	}

	/**
	 * Update Role by PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public void update(RoleBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		/*
		 * RoleBean duplicataRole = findByName(bean.getName()); // Check if updated Role
		 * already exist if (duplicataRole != null && duplicataRole.getId() !=
		 * bean.getId()) { throw new DuplicateRecordException("Role already exists"); }
		 */
		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_ROLE SET NAME=?,DESCRIPTION=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getDescription());
			pstmt.setString(3, bean.getCreatedBy());
			pstmt.setString(4, bean.getModifiedBy());
			pstmt.setTimestamp(5, bean.getCreatedDatetime());
			pstmt.setTimestamp(6, bean.getModifiedDatetime());
			pstmt.setLong(7, bean.getId());
			pstmt.executeUpdate();
			System.out.println("Updated");
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Role ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	/**
	 * Searches Role.
	 *
	 * @param bean : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(RoleBean bean) throws ApplicationException {
		return search(bean);
	}

	/**
	 * Searches Role with pagination.
	 *
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of Role
	 * @throws ApplicationException the application exception
	 */
	public List search(RoleBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT*FROM ST_ROLE WHERE 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" AND DESCRIPTION like '" + bean.getDescription() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			// log.error("DatabaseException.....", e);
			// throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}

	/**
	 * Gets List of Role.
	 *
	 * @return list : List of Role
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of Role with pagination.
	 *
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of Role
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_ROLE");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RoleBean bean = new RoleBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error(" Database Exception....", e);
			// throw new ApplicationException("Exception : Exception Geting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

}
