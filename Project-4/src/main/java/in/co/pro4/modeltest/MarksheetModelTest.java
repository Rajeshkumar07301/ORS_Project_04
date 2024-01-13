package in.co.pro4.modeltest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.co.pro4.bean.MarksheetBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.exception.DuplicateRecordException;
import in.co.pro4.model.MarksheetModel;

public class MarksheetModelTest {
	public static MarksheetModel model = new MarksheetModel();
	public static void main(String[] args) {
		//testadd();
		//testDelete();
		//testUpdate();
		//testFindByRollNo();
		//testFindByPK();
		// testSearch();
		testList();
		//testMeritList();
		
	}

	private static void testMeritList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.list(1, 5);
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	
		
	}

	private static void testList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		
		
	}

	private static void testSearch() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			bean.setId(1L);
			list = model.search(bean,1,10);
			
			Iterator it = list.iterator();
			while(it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		}catch (ApplicationException e) {
			e.printStackTrace();
		}	
	}

	private static void testFindByPK() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 3L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Find By pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	private static void testFindByRollNo() {
		try {
			MarksheetBean bean = model.findByRollNo("rays01");
			if (bean == null) {
				System.out.println("Test Find by rollNo fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		
	}

	private static void testUpdate() {
			try {
				MarksheetBean bean = model.findByPK(3L);

				bean.setStudentId(3L);
				bean.setRollNo("r5");
				bean.setChemistry(100);
				bean.setPhysics(20);
				bean.setMaths(50);

				model.update(bean);
				System.out.println("Update Record");
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (DuplicateRecordException e) {
				e.printStackTrace();
			}
		
	}

	private static void testDelete() {
		 try {
	            MarksheetBean bean = new MarksheetBean();
	            long pk = 3L;
	            bean.setId(pk);
	            model.delete(bean);
	            MarksheetBean deletedbean = model.findByPK(pk);
	            if (deletedbean != null) {
	                System.out.println("Test Delete fail");
	            }
	        } catch (ApplicationException e) {
	            e.printStackTrace();
	        }
		
	}

	private static void testadd() {
		  try {
	            MarksheetBean bean = new MarksheetBean();
	            // bean.setId(1L);
	            bean.setRollNo("rays05");
	            bean.setPhysics(90);
	            bean.setChemistry(62);
	            bean.setMaths(75);
	            bean.setStudentId(1L);
	            long pk = model.add(bean);
	            MarksheetBean addedbean = model.findByPK(pk);
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

