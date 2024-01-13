package in.co.pro4.modeltest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.pro4.bean.CollegeBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.CollegeModel;

public class CollegeModelTest {
	public static CollegeModel model = new CollegeModel();
	public static void main(String[] args) throws DuplicateRecordException {
		testAdd();
		//testDelete();
		//testUpdate();
		//testFindByName();
		//testFindByPK();
		//testSearch();
		//testList();
		
	}
	private static void testList() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			list = model.list(1,10);
			if(list.size()<0) {
				System.out.println("Test List Fail");
			}
			Iterator it = list.iterator();
			while(it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
	}
	private static void testSearch() {
		try {
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			bean.setId(1L);
			//bean.setName("davv");
			list = model.search(bean,1,5);
			if(list.size()<0) {
				System.out.println("Test Search fail ");
			}
			Iterator it = list.iterator();
			while(it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getState());
				System.out.println(bean.getCity());
				System.out.println(bean.getPhoneNo());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());	
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}	
	}
	private static void testFindByPK() {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 1L;
			bean = model.findByPK(pk);
			if(bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
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
			CollegeBean bean = model.findByName("davv");
			if (bean == null) {
				System.out.println("Test Find By Name Fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
	}
	private static void testUpdate() {
		try {
			CollegeBean bean = model.findByPK(1L);
			bean.setName("hdu");
			bean.setAddress("building no.11");
			bean.setState("up");
			bean.setCity("kanpur");
			model.update(bean);
			System.out.println("Test Update Succ");
			
//			if (!"oit".equals(updateBean.getName())) {
//                System.out.println("Test Update fail");
//            }
			
		}catch(ApplicationException e) {
			e.printStackTrace();
		}catch(DuplicateRecordException e) {
			e.printStackTrace();
		}
		
	}
	private static void testDelete() {
		try {
			CollegeBean bean = new CollegeBean();
			long pk = 1L;
			bean.setId(pk);
			model.Delete(bean);
			System.out.println("Test Delete succ");
			CollegeBean DeleteBean = model.findByPK(pk);
			if(DeleteBean != null) {
				System.out.println("Test Delete Fail");
			}
		}catch(ApplicationException e) {
			e.printStackTrace();
		}
		
		
	}
	private static void testAdd() throws DuplicateRecordException {
		
	try {
		CollegeBean bean = new CollegeBean();
		//bean setName(2L);
		bean.setName("ssdmn");
		bean.setAddress("Bhopal");
		bean.setState("MadhyPardesh");
		bean.setCity("Bhopal");
		bean.setPhoneNo("8770889672");
		bean.setCreatedBy("Admin");
		bean.setModifiedBy("Admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		long pk = model.add(bean);
		System.out.println("Test add succ");
		CollegeBean addedBean = model.findByPK(pk);
		if(addedBean == null) {
			System.out.println("Test add fail");
		}
	}catch(ApplicationException e) {
		e.printStackTrace();
	}
	}

}
