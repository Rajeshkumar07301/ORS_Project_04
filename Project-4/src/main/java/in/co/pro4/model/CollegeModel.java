package in.co.pro4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import in.co.pro4.bean.CollegeBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DataBaseException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.util.JDBCDataSource;

/**
 * JDBC Implementation of CollegeModel.
 * 
 * @author Rajesh Kumar
 *
 */
public class CollegeModel {
	//The Log.
	private static Logger Log = Logger.getLogger(CollegeModel.class);
	
	/**
	 * NextPK.
	 *
	 * @return the integer
	 * @throws DatabaseException the database exception
	 */
	public Integer nextPK() throws DataBaseException {
		Log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_COLLEGE");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();

		} catch (Exception e) {
			Log.error("Database Exception..", e);
			throw new DataBaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model nextPK End");
		return pk + 1;
	}
	
	/**
	 * Adds a College.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException     the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		Log.debug("Model add Started");
		Connection conn = null;
		int pk = 0;
		CollegeBean duplicateCollegeName = findByName(bean.getName());
		if (duplicateCollegeName != null) {
			throw new DuplicateRecordException("College Name already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			// Get auto genrated next primary key
			conn.setAutoCommit(false);// Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5, bean.getCity());
			pstmt.setString(6, bean.getPhoneNo());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();// End transaction
			pstmt.close();
		} catch (Exception e) {
			Log.error("Database Exception...", e);
			try {
				conn.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new ApplicationException("Exception:add rollback exception" + e1.getMessage());
			}
			throw new ApplicationException("Exception:Exception in add College");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model add End");
		return pk;
	}
	
	/**
	 * Deletes a College.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public void Delete(CollegeBean bean) throws ApplicationException {
		Log.debug("Model add Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);// Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();// End transaction
			pstmt.close();
		} catch (Exception e) {
			Log.error("Database Exception..", e);

			try {
				conn.rollback();
			} catch (SQLException e1) {
				throw new ApplicationException("Exception:Delete rollback exception" + e1.getMessage());
			}
			throw new ApplicationException("Exception:Exception in delete College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model delete End");
	}
	/**
	 * Finds College by Name.
	 *
	 * @param Name : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public CollegeBean findByName(String name) throws ApplicationException {
		Log.debug("Model findByName Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE NAME=?");
		CollegeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();

		} catch (Exception e) {
			Log.error("Database Exception..", e);
			e.printStackTrace();
			// throw new ApplicationException("Exception:Exception in getting College by
			// Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model findByName End");
		return bean;
	}
	
	/**
	 * Finds College by PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public CollegeBean findByPK(long pk) throws ApplicationException {
		Log.debug("Model findByPK Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE ID=?");
		CollegeBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
			rs.close();
		} catch (Exception e) {
			Log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model findByPK End");
		return bean;
	}
	
	/**
	 * Update College by PK.
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */
	public void update(CollegeBean bean) throws ApplicationException, DuplicateRecordException {
		Log.debug("Model update Started");
		Connection conn = null;

		CollegeBean beanExist = findByName(bean.getName());

		// Check if updated College already exist
		if (beanExist != null && beanExist.getId() != bean.getId()) {

			throw new DuplicateRecordException("College is already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4, bean.getCity());
			pstmt.setString(5, bean.getPhoneNo());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			Log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				e.printStackTrace();
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating College ");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model update End");
	}

	/**
	 * Searches College with pagination.
	 *
	 * @param bean     : Search Parameters
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of College
	 * @throws ApplicationException the application exception
	 */
	public List search(CollegeBean bean, int pageNo, int pageSize) throws ApplicationException {
		Log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" AND STATE like '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" AND CITY like '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + bean.getPhoneNo());
			}

		}

		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}

		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			Log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		Log.debug("Model search End");
		return list;
	}

	/**
	 * Searches College.
	 *
	 * @param bean : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public List search(CollegeBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}


	/**
	 * Gets List of College.
	 *
	 * @return list : List of College
	 * @throws ApplicationException the application exception
	 */
	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * get List of College with pagination.
	 *
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @return list : List of College
	 * @throws ApplicationException the application exception
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
		Log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CollegeBean bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			Log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		Log.debug("Model list End");
		return list;
	}

}
