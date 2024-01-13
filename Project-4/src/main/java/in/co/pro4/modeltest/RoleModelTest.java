package in.co.pro4.modeltest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.pro4.bean.RoleBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.RoleModel;

public class RoleModelTest {
	public static RoleModel model = new RoleModel();
	public static void main(String[] args) throws ApplicationException {
		//testadd();
		//testDelete();
		testUpdate();
		//testFindByPK();
		//testFindByName();
		//testSearch();
		//testList();
	}
	private static void testList() {
		try{
			
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			list=model.list(1,10);
			if(list.size() >0){
				System.out.println("test List faill");
			}
			Iterator it=list.iterator();
			while(it.hasNext()){
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		}catch(ApplicationException e){
			e.printStackTrace();
		}

		
	}
	private static void testSearch() {
		try{
			RoleBean bean=new RoleBean();
			List list=new ArrayList();
			//bean.setId(2L);
			bean.setName("piyush");
			list=model.search(bean,1,5);
			if(list.size() < 0){
				System.out.println("test Search fill");
			}
			Iterator it = list.iterator();
			
			while(it.hasNext()){
				bean=(RoleBean)it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
			
		}catch(ApplicationException e){
			e.printStackTrace();
		}
		
	}
	private static void testFindByName() {
		try{
			RoleBean bean=new RoleBean();
			bean=model.findByName("harish");
			if(bean==null){
				System.out.println("Test Find By Name fill");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		}catch(ApplicationException e){
			e.printStackTrace();
		}
		
	}
	private static void testFindByPK() {
		try{
			RoleBean bean =new RoleBean();
			long pk=2L;
			bean=model.findByPK(pk);
			if(bean==null){
				System.out.println("Test Find By PK fill");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		
	}
	private static void testUpdate() {
		try{
			RoleBean bean=model.findByPK(1L);
			bean.setName("Admin");
			bean.setDescription("Administration");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			model.update(bean);
		
		}catch(ApplicationException e){
			e.printStackTrace();
		}catch(DuplicateRecordException e){
			e.printStackTrace();
		}
		
	}
	private static void testDelete() throws ApplicationException {
		RoleBean bean = new RoleBean();
		
		bean.setId(1l);
		
		model.delete(bean);
		
		System.out.println("record deleted");
		
	}
	private static void testadd() {
		   try {
	            RoleBean bean = new RoleBean();
	            // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	            // bean.setId(1L);
	            bean.setName("Admin");
	            bean.setDescription("Administration");
	            bean.setCreatedBy("rajeshkumarhrm40@gmail.com");
	            bean.setModifiedBy("rajeshkumarhrm40@gmail.com");
	            long pk = model.add(bean);
	            RoleBean addedbean = model.findByPK(pk);
	            if (addedbean == null) {
	                System.out.println("Test add fail");
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        } catch (DuplicateRecordException e) {
	            e.printStackTrace();
	        }

			}	 
}
