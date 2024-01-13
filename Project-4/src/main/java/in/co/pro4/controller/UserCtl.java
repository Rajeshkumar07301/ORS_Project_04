package in.co.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.pro4.bean.BaseBean;
import in.co.pro4.bean.UserBean;
import in.co.pro4.controller.BaseCtl;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.RoleModel;
import in.co.pro4.model.UserModel;
import in.co.pro4.util.DataUtility;
import in.co.pro4.util.DataValidator;
import in.co.pro4.util.PropertyReader;
import in.co.pro4.util.ServletUtility;

/*
 * @author Rajesh Kumar
 * 
 */
@WebServlet(name = "UserCtl",urlPatterns = {"/ctl/UserCtl"})
public class UserCtl extends BaseCtl{
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(UserCtl.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 * 
	 */

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("UserCtl Validate Method Started");
		
		boolean pass = true;
		
		String login = request.getParameter("login");
		String dob = request.getParameter("dob");
		
		if(DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName",PropertyReader.getValue("error.require","first Name"));
			pass = false;
		}

		else if(!DataValidator.isName(request.getParameter("firstName"))) {
			request.setAttribute("firstName","Invalid First Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName",PropertyReader.getValue("error.require","last Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("lastName"))) {
			request.setAttribute("lastName","Invalid Last Name");
			pass = false;
		}
		if(DataValidator.isNull(login)) {
			request.setAttribute("login",PropertyReader.getValue("error.require","Login ID"));
			pass = false;
		}
		else if (!DataValidator.isEmail(login)) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role Name "));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth "));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile Number "));
		}
		else if(!DataValidator.isMobileNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo","Invalid Mobile Number");
			pass = false;
		}
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("password","Confirm Password should not be Matched");
			pass = false;
		}
		log.debug("UserCtl Validate Method Ended");
		return pass;
	}
	/*
	 * (non-JavaDoc)
	 * 
	 * @see  in.co.pro4.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		RoleModel model = new RoleModel();

		try {
			List l = model.list();
			request.setAttribute("roleList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#populatebean(javax.servlet.http.HttpServletRequest)
	 * 
	 */
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("UserCtl PopulatedBean Started");

		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
	
	

		populateDTO(bean, request);

		log.debug("UserCtl PopulatedBean Started");
		return bean;
	}
	/**
	 * Contains display logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("UserCtl Doget Method Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0 || op != null) {
			UserBean bean;

			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Doget Method Ended");

	}
	/**
	 * Contains submit logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("UserCtl Dopost Method Started");
		String op = DataUtility.getString(request.getParameter("operation"));

		UserModel model = new UserModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);

			try {
				if (id > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);
					bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully Saved", request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login Id Already Exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			UserBean bean = (UserBean) populateBean(request);
			try {
				model.delete(bean);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("UserCtl Dopost Method Ended");
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#getview()
	 */
	@Override
	protected String getView() {
		return ORSView.USER_VIEW;
	}
}
