package in.co.pro4.model;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import in.co.pro4.bean.DropdownListBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DataBaseException;
import in.co.pro4.util.DataUtility;
import in.co.pro4.util.JDBCDataSource;
/**
 * The Class BaseModel
 * 
 * @author  Rajesh Kumar
 *
 */
public abstract class BaseModel implements Serializable,DropdownListBean,Comparable<BaseModel> {
	//The Log..
    private static Logger Log = Logger.getLogger(BaseModel.class);

	protected long id;
	protected String createdBy;
	protected String modifiedBy;
	protected Timestamp createdDatetime;
	protected Timestamp modifiedDatetime;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}
	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}
	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}
	
	public int compareTo(BaseModel next) {
		
		 return (int) (id - next.getId());
	}
	
	/**
	 * NextPK.
	 *
	 * @return the integer
	 * @throws DatabaseException the database exception
	 */
	public long nextPK()throws DataBaseException {
		Log.debug("Model nextPK Started");
		Connection conn = null;
		long pk=0;
		try {
			conn = JDBCDataSource.getConnection();
			 PreparedStatement pstmt =  conn.prepareStatement("SELECT MAX(ID) FROM " + getTableName());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		}catch(Exception e) {
			Log.error("Database Exception...",e);
			throw new DataBaseException("Exception:Exception in getting PK");
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		Log.debug("Model nextPK End");
		return pk+1;	
	}
	 public abstract String getTableName();
	 
	 public void updateCreatedInfo() throws ApplicationException {
		 Log.debug("Model update Started..." + createdBy);
		 Connection conn = null;
		 String sql = "UPDATE " + getTableName()
         + " SET CREATED_BY=?, CREATED_DATETIME=? WHERE ID=?";
         Log.debug(sql);
         
         try {
             conn = JDBCDataSource.getConnection();
             conn.setAutoCommit(false); // Begin transaction
             PreparedStatement pstmt = conn.prepareStatement(sql);
             pstmt.setString(1, createdBy);
             pstmt.setTimestamp(2, DataUtility.getCurrentTimestamp());
             pstmt.setLong(3, id);            
             pstmt.executeUpdate();
             conn.commit(); // End transaction
             pstmt.close();
         } catch (SQLException e) {
             Log.error("Database Exception..", e);
             JDBCDataSource.trnRollback(conn);
             throw new ApplicationException(e.toString());
         } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } finally {
             JDBCDataSource.closeConnection(conn);
         }
         Log.debug("Model update End");
     }
	 public void updateModifiedInfo() throws Exception {
		 Log.debug("Model update Started");
		 Connection conn = null;
		 String sql = "UPDATE"+getTableName() + " SET MODIFIED_BY=?, MODIFIED_DATETIME=? WHERE ID=?";
		 
		 try {
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);//Begin transaction
			PreparedStatement pstmt =  conn.prepareStatement(sql.toString());
			pstmt.setString(1, modifiedBy);
			pstmt.setTimestamp(2,DataUtility.getCurrentTimestamp());
			pstmt.setLong(3,id);
			pstmt.executeUpdate();
			conn.commit();//End transaction
			pstmt.close();
		} catch (SQLException e) {
			Log.error("Database Exception..", e);
			JDBCDataSource.trnRollback(conn);
		}finally {
			JDBCDataSource.closeConnection(conn);
		}
		 Log.debug("Model update End");
	 }
	 protected <T extends BaseModel> T populateModel(T model, ResultSet rs)
	            throws SQLException {
	        model.setId(rs.getLong("ID"));
	        model.setCreatedBy(rs.getString("CREATED_BY"));
	        model.setModifiedBy(rs.getString("MODIFIED_BY"));
	        model.setCreatedDatetime(rs.getTimestamp("CREATED_DATETIME"));
	        model.setModifiedDatetime(rs.getTimestamp("MODIFIED_DATETIME"));
	        return model;

	 }	 
}

