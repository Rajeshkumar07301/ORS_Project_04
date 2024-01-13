package in.co.pro4.modeltest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.pro4.bean.FacultyBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.FacultyModel;

public class FacultyModelTest {
	public static FacultyModel model = new FacultyModel();
	public static void main(String[] args) throws Exception {
		testadd();
		//testDelete();
		//testUpdate();
		//testFindByPK();
		//testfindByEmailID();
		//testList();
		//testsearch();
	}
	private static void testsearch() {
	     try {
	    	 FacultyBean bean = new FacultyBean();
	    	 List list = new ArrayList();
	    	// bean.setId(1L);
	    	// bean.setFirstName("Rajesh");
	    	 //bean.setCollegeId(2);
	    	 //bean.setSubjectId(1);
	    	 bean.setCourseId(1);
	    	 
	    	 list = model.search(bean, 1, 5);
	    	 if(list.size()>0) {
	    		 System.out.println("Test search fail");
	    	 }
	    	 
	    	 Iterator it = list.iterator();
	    	 while(it.hasNext()) {
	    		bean = (FacultyBean) it.next();
	    		System.out.println(bean.getId());
	    		System.out.println(bean.getFirstName());
	    		System.out.println(bean.getLastName());
	    		System.out.println(bean.getGender());
	    		System.out.println(bean.getEmailId());
	    		System.out.println(bean.getMobileNo());
	    		System.out.println(bean.getCollegeId());
	    		System.out.println(bean.getCollegeName());
	    		System.out.println(bean.getCourseId());
	    		System.out.println(bean.getCourseName());
	    		System.out.println(bean.getSubjectId());
	    		System.out.println(bean.getSubjectName());
	    		System.out.println(bean.getCreatedBy());
	    		System.out.println(bean.getCreatedDatetime());
	    		System.out.println(bean.getModifiedBy());
	    		System.out.println(bean.getModifiedDatetime());
	    	 }
	     }catch(ApplicationException e) {
	    	 e.printStackTrace();
	     }
		
	}
	private static void testList() throws ApplicationException {
		try {
		FacultyBean bean = new FacultyBean();
		List list = new ArrayList();
		list = model.list(1,10);
		
		if(list.size()>0) {
			System.out.println("test list fail");
		}
		Iterator it = list.iterator();
		while(it.hasNext()) {
			bean = (FacultyBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCollegeId());
			System.out.println(bean.getCollegeName());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}
		}catch(ApplicationException e ){
			e.printStackTrace();
			}
		}
	private static void testfindByEmailID() {
		try {
			FacultyBean  bean = new FacultyBean();
			bean = model.findByEmailId("mehta@gmail.com");
			if(bean!=null) {
				System.out.println("test Find by EmailID fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testFindByPK() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 3L;
			bean = model.findByPK(pk);
			if(bean!=null) {
				System.out.println("test Find by pk Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getGender());
			System.out.println(bean.getEmailId());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testUpdate() throws DuplicateRecordException {
		try {
			//FacultyBean bean = new FacultyBean();
			FacultyBean bean = model.findByPK(1L);
			bean.setFirstName("Vishal");
			bean.setLastName("Dubey");
			model.update(bean);
			System.out.println("update Successfully");
//			FacultyBean updateBean=model.findByPK(2L);
//			if(!"Vishal".equals(updateBean.getFirstName())){
//			  System.out.println("test update fail");
//		  }
			 
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testDelete() {
		try {
			FacultyBean bean = new FacultyBean();
			long pk = 2L;
			bean.setId(pk);
			model.delete(bean);
			System.out.println("Data delete");
			FacultyBean deletebean = model.findByPK(pk);
			if(deletebean != null) {
				System.out.println("test delete fail");
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testadd() {
		//not working
		try {
			FacultyBean bean = new FacultyBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			bean.setFirstName("neeraj");
			bean.setLastName("patidar");
			bean.setGender("male");
			bean.setEmailId("neeraj@gmail.com");
			bean.setMobileNo("9081000029");
			bean.setDob(sdf.parse("22/05/1995"));
			bean.setCollegeId(1);
			bean.setCollegeName("davav");
			bean.setCourseId(4);
			bean.setCourseName("b.scm");
			
			bean.setSubjectId(2);
			bean.setSubjectName("physics");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk = model.add(bean);
			System.out.println("Add Successfully");
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	

}
