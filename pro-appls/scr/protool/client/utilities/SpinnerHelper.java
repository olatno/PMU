package scr.protool.client.utilities;
import java.awt.*;
import java.awt.event.*; 
import javax.swing.event.*; 
import javax.swing.*; 
import javax.swing.table.*; 
import java.util.*; 
import java.text.*;




public class SpinnerHelper{
	//private SimpleDateFormat formatdate;
	//Calendar calendar = Calendar.getInstance();
    private SpinnerModel dateModel;
	private SpinnerModel durationlist;
	private SpinnerModel durationlist2;
	private SpinnerModel durationlist3;
	private SpinnerModel dayModel;
    Date initDate, earliestDate , latestDate;
    Calendar calendar = Calendar.getInstance();
    DateFormat  df = new SimpleDateFormat("dd-MM-yyyy", Locale.UK );
	//DateFormat  df = new SimpleDateFormat();

    public SpinnerHelper(){

	//reservation date and dateModel use by checkin date 
	initDate = calendar.getTime();
	calendar.add(Calendar.YEAR, -1000);
	earliestDate = calendar.getTime();
	calendar.add(Calendar.YEAR, 2000);
	latestDate = calendar.getTime();
	dateModel = new SpinnerDateModel(initDate, earliestDate,latestDate, Calendar.YEAR);
	java.util.List<String> weekstr = new ArrayList<String>();
	String str = null;
	for(int i = 0; i < 53; i++){
		if(i == 1)
			str = "week";
		else{ str = "weeks";}
		String strs = String.format("%1s %s\n", String.valueOf(i), str);
		weekstr.add(strs);
	}
	durationlist = new SpinnerListModel(weekstr);
	
	java.util.List<String> daystr = new ArrayList<String>();
	String days = null;		
	for(int i = 0; i <= 365; i++){
		if(i == 1)
			days = "day";
		else{
			days = "days";
			}
		daystr.add( String.format("%d %s", i, days));
		}
		 dayModel = new SpinnerListModel(daystr);
		 
	java.util.List<String> monthstr = new ArrayList<String>();
	String strmonth = null;
	for(int i = 1; i < 13; i++){
		if(i == 1)
			strmonth = "Month";
		else{ strmonth = "Months";}
		String strsmonth = String.format("%1s %s\n", String.valueOf(i), strmonth);
		monthstr.add(strsmonth);
	}
	durationlist2 = new SpinnerListModel(monthstr);
	
	java.util.List<String> yearstr = new ArrayList<String>();
	String stryear = null;
	for(int i = 1; i < 100; i++){
		if(i == 1)
			stryear = "Year";
		else{ stryear = "Years";}
		String strsyear = String.format("%1s %s\n", String.valueOf(i), stryear);
		yearstr.add(strsyear);
	}
	durationlist3 = new SpinnerListModel(yearstr);

    }
    

    public JSpinner getGenericDate(JSpinner date){
		date  = new JSpinner(dateModel);
		date.setEditor(new JSpinner.DateEditor(date, "dd-MM-yyyy"));
	//   System.out.println( latestDate.toString());
		return date;
    }
	
	public JSpinner getGenericDateEdit(JSpinner date, String defaultdate){
	  try{
		dateModel.setValue(df.parse(defaultdate));
	   }
		catch (ParseException p){
			System.out.print(p);
	  }
		date  = new JSpinner(dateModel);
		date.setEditor(new JSpinner.DateEditor(date, "dd-MM-yyyy"));
	//   System.out.println( latestDate.toString());
		return date;
    }
	
	public String dateString(JSpinner date){
		calendar.setTime(((SpinnerDateModel)getGenericDate(date).getModel()).getDate());
		//SpinnerModel  strmodel = getGenericDate(date).getModel();
		return df.format(calendar.getTime());
		//return String.valueOf(((SpinnerDateModel)strmodel).getDate());
	}
	
	public JSpinner getWeekSpinner(JSpinner week){
		week = new JSpinner(durationlist);
		week.setEditor(new JSpinner.ListEditor(week));
		return week;
	}
	
	public JSpinner getWeekSpinnerEdit(JSpinner week, String defaultValue){
		durationlist.setValue(defaultValue);
		week = new JSpinner(durationlist);
		week.setEditor(new JSpinner.ListEditor(week));
		return week;
	}
	
	public JSpinner getDaySpinnerEdit(JSpinner day, String defaultValue){
		dayModel.setValue(defaultValue);
		day = new JSpinner(dayModel);
		day.setEditor(new JSpinner.ListEditor(day));
		return day;
	}
	
	public JSpinner getGenericSpinnerEdit(JSpinner spinner, SpinnerModel model, String defaultValue){
		model.setValue(defaultValue);
		spinner = new JSpinner(model);
		spinner.setEditor(new JSpinner.ListEditor(spinner));
		return spinner;
	}
	
	public String weekString(JSpinner week){
		SpinnerModel  strmodel = getWeekSpinner(week).getModel();
		return String.valueOf(((SpinnerListModel)strmodel).getValue());
	}
	
	public String dayString(JSpinner day){
		SpinnerModel  strmodel = getDaysSpinner(day).getModel();
		return String.valueOf(((SpinnerListModel)strmodel).getValue());
	}
	
	public JSpinner getDaysSpinner(JSpinner day){
		day = new JSpinner(dayModel);
		day.setEditor(new JSpinner.ListEditor(day));
		return day;
	
	}
	
	public JSpinner getMonthSpinner(JSpinner month){
		month = new JSpinner(durationlist2);
		month.setEditor(new JSpinner.ListEditor(month));
		return month;
	}
	
	public String monthString(JSpinner month){
		SpinnerModel  strmodel = getMonthSpinner(month).getModel();
		return String.valueOf(((SpinnerListModel)strmodel).getValue());
	}
	
	public JSpinner getYearSpinner(JSpinner year){
		year = new JSpinner(durationlist3);
		year.setEditor(new JSpinner.ListEditor(year));
		return year;
	}
	
	public String yearString(JSpinner year){
		SpinnerModel  strmodel = getYearSpinner(year).getModel();
		return String.valueOf(((SpinnerListModel)strmodel).getValue());
	}
	
	public SpinnerModel getDateModel(){
		return dateModel;
	}
	
	public SpinnerModel getYearModel(){
		return durationlist3;
	}
	
	public SpinnerModel getDayModel(){
		return dayModel;
	}
	
	public SpinnerModel getMonthModel(){
		return durationlist2;
	}
		public SpinnerModel getWeekModel(){
		return durationlist;
	}
}