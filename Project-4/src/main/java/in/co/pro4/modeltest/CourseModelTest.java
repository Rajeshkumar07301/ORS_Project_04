package in.co.pro4.modeltest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.pro4.bean.CourseBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DataBaseException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.CourseModel;

public class CourseModelTest {
	public static CourseModel model = new CourseModel();
	public static void main(String[] args) throws Exception  {
		//testadd();
	    //testDelete();
		//testFindByName();
	     testFindByPk();
		//testUpdate();
		//testsearch();
		//testlist();
	}
	private static void testlist() throws Exception {
		try{
			CourseBean bean = new CourseBean();
		  List list = new ArrayList();
		  list =model.list(1,10);
	  if(list.size() < 0) { 
		  System.out.println("test list fail");
		  } 
	  Iterator it=list.iterator();
	  while(it.hasNext()) {
		  bean=(CourseBean) it.next();
	  System.out.println(bean.getName());
	  System.out.println(bean.getDescription());
	  System.out.println(bean.getDuration());
	  
	  }
	  
	  }catch(ApplicationException e) {
		  e.printStackTrace(); 
		  } 
	  }
	 
		

	private static void testsearch() throws DataBaseException {
		try {
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			bean.setId(1L);
			list=model.search(bean);
			
			Iterator it=list.iterator();
			while(it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getDuration());
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	public static void testUpdate() {
		try {
			CourseBean bean = model.FindByPK(7);
			bean.setName("Mca");
			bean.setDescription("commerce");
			model.update(bean);
			System.out.println("update succ");
			
			 
		}catch(ApplicationException e) {
			e.printStackTrace();
		}catch(DuplicateRecordException e) {
			e.printStackTrace();
		}
	}
	private static void testFindByPk() {
		try {
			CourseBean bean = new CourseBean();
			long pk = 4L;
			bean = model.FindByPK(pk);
			if(bean==null) {
				System.out.println("test findbypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testFindByName() {
		
		try {
			CourseBean bean=new CourseBean();
			bean=model.findByName("B.com");
			
			if(bean==null) {
				System.out.println("test findBy Name fail");
			}
		
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	
		
	}
	private static void testDelete() {
		try {
			CourseBean bean = new CourseBean();
			long pk=1L;
			bean.setId(3);
			model.Delete(bean);
			System.out.println("Test Deleted");
			/*
			 * CourseBean deleteBean=model.findByPK(pk); if(deleteBean == null) {
			 * System.out.println("Test Delete fail"); }
			 */
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testadd() {
		try {
			CourseBean bean= new CourseBean();
			//bean.setId(1);
			bean.setName("sss");
			bean.setDuration("2 Year");
			bean.setDescription("science");
			
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			long pk=model.add(bean);
			System.out.println("Data Succ");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
