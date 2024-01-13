package in.co.pro4.modeltest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.pro4.bean.StudentBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.StudentModel;

public class StudentModelTest {
	public static StudentModel model= new StudentModel();
	public static void main(String[] args) throws ParseException {
		testadd();
		//testDelete();
		//testUpdate();
		//testFindByPK();
		//testFindByEmailId();
		//testSearch();
		  // testList();
	}

	private static void testList() {
		 try {
	            StudentBean bean = new StudentBean();
	            List list = new ArrayList();
	            list = model.list(1, 10);
	            if (list.size() < 0) {
	                System.out.println("Test list fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (StudentBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getFirstName());
	                System.out.println(bean.getLastName());
	                System.out.println(bean.getDob());
	                System.out.println(bean.getMobileNo());
	                System.out.println(bean.getEmail());
	                System.out.println(bean.getCollegeId());
	                System.out.println(bean.getCreatedBy());
	                System.out.println(bean.getCreatedDatetime());
	                System.out.println(bean.getModifiedBy());
	                System.out.println(bean.getModifiedDatetime());
	            }

	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	    }

	private static void testSearch() {
		   try {
	            StudentBean bean = new StudentBean();
	            List list = new ArrayList();
	            bean.setFirstName("manish");
	            list = model.search(bean, 0, 0);
	            if (list.size() < 0) {
	                System.out.println("Test Serach fail");
	            }
	            Iterator it = list.iterator();
	            while (it.hasNext()) {
	                bean = (StudentBean) it.next();
	                System.out.println(bean.getId());
	                System.out.println(bean.getFirstName());
	                System.out.println(bean.getLastName());
	                System.out.println(bean.getDob());
	                System.out.println(bean.getMobileNo());
	                System.out.println(bean.getEmail());
	                System.out.println(bean.getCollegeId());
	            }

	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }

		
	}

	private static void testFindByEmailId() {
		  try {
	            StudentBean bean = new StudentBean();
	            bean = model.findByEmailId("verma30@gmail.com");
	            if (bean != null) {
	                System.out.println("Test Find By EmailId fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getFirstName());
	            System.out.println(bean.getLastName());
	            System.out.println(bean.getDob());
	            System.out.println(bean.getMobileNo());
	            System.out.println(bean.getEmail());
	            System.out.println(bean.getCollegeId());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
		
	}

	private static void testFindByPK() {
		   try {
	            StudentBean bean = new StudentBean();
	            long pk = 1L;
	            bean = model.findByPK(pk);
	            if (bean == null) {
	                System.out.println("Test Find By PK fail");
	            }
	            System.out.println(bean.getId());
	            System.out.println(bean.getFirstName());
	            System.out.println(bean.getLastName());
	            System.out.println(bean.getDob());
	            System.out.println(bean.getMobileNo());
	            System.out.println(bean.getEmail());
	            System.out.println(bean.getCollegeId());
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
	}

	private static void testUpdate() {
	    try {
            StudentBean bean = model.findByPK(3L);
            bean.setCollegeId(1L);
            bean.setFirstName("ankit");
            bean.setLastName("Roy");
            model.Update(bean);

            System.out.println("updated");
            
        } catch (ApplicationException e) {
            e.printStackTrace();
        } catch (DuplicateRecordException e) {
            e.printStackTrace();
        }
    }
	private static void testDelete() {
		try {
			StudentBean bean = new StudentBean();
			long pk = 2L;
			bean.setId(pk);
			model.delete(bean);
			StudentBean deletebean = model.findByPK(pk);
			if(deletebean != null){
				System.out.println("Test Delete fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}
		}

	private static void testadd() throws ParseException {
		try {
			StudentBean bean = new StudentBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			//bean.setId(5L);
			bean.setFirstName("govind");
			bean.setLastName("yadav");
			bean.setDob(sdf.parse("20/09/1996"));;
			bean.setMobileNo("9977005511");
			bean.setEmail("yadav30@gmail.com");
			bean.setCollegeId(2L);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			long pk = model.add(bean);
			
			StudentBean addbean = model.findByPK(pk);
			if(addbean==null){
				System.out.println("Test add fail");
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
	
		}
	}
	

}
