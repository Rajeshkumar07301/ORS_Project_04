package in.co.pro4.modeltest;



import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import in.co.pro4.bean.TimeTableBean;
import in.co.pro4.model.TimeTableModel;

public class TimeTableModelTest {
	public static TimeTableModel model = new TimeTableModel();
	public static void main(String[] args) {
		testadd();

	}

	private static void testadd() {
		try {
			TimeTableBean bean = new TimeTableBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			//bean.setId(1L);
			bean.setCourseId(1);
			bean.setCourseName("b.com");
			bean.setSubjectId(1);
			bean.setSubjectName("commerce");
			bean.setSemester("1");
			bean.setExamDate(sdf.parse("02/11/2021"));
			bean.setExamTime("09 am to 12 pm");
			bean.setDescription("xyz");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
			
			model.add(bean);
			System.out.println("data update");
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
		
	}

}
