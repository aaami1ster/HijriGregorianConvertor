/**
 * Rules for Hijri calender:
 * 	-	Islamic year 1 (Muharram 1,1 A.H.) is equivilent to absolute date 227015 (Friday, July 16, 622 C.E. - Julian).
 * 	-	Leap Years occur in the 2, 5, 7, 10, 13, 16, 18, 21, 24, 26, & 29th years of a 30-year cycle.
 * 		Year = leap if((11y+14) mod 30 < 11).
 * 	-	There are 12 months which contain  alternately 30 and 29 days.
 * 	-	The 12th month, Dhu al-Hijjah, contains 30 days instead of 29 days in leap year.
 * 	-	Common years have 354 days. Leap years have 355 days.
 * 	-	There are 10,631 days in a 30-year cycle.
 * 	-	The Islamic months are:
 * 			1.	Muharram		(30 days)
 * 			2.	Safar			(29 days)
 * 			3.	Rabi I			(30 days)
 * 			4. 	Rabi II			(29 days)
 * 			5.	Jumada I		(30 days)
 * 			6.	Jumada II		(29 days)
 * 			7.	Rajab			(30 days)
 * 			8.	Sha'ban			(29 days)
 * 			9.	Ramadan			(30 days)
 * 			10.	Shawwal			(29 days)
 * 			11.	Dhu al-Qada		(30 days)
 * 			12.	Dhu al-Hijjah	(29 days) {30}
 * NOTE::::::::::::::::
 * The calculation of Hijri is based on the absolute date.
 * The absolute date means the number of days from January 1st, 1 A.D.
 * Therefore the days before the January 1st, 1 A.D. is not supported 
 ***************************************************************************
 *Calendar range:
 *		Calendar 	  Minimum	 Maximum
 *		=========	==========	==========
 *		Gregorian	0622/07/08	9999/12/31
 *		Hijri		0001/01/01	9666/04/03
 * 
 */
package a.abdalla;

//import java.util.Date;
//import java.util.TimeZone;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
/**
 * 
 * @author abdalla
 *
 */
public class HijriGregorianConv extends Activity{
	
	EditText inday,inmonth,inyear;
	String sinday,sinmonth,sinyear;
	Integer iday,imonth,iyear;
	RadioButton tohijri,togregorian;
	TextView outdayw,outday,outmonth,outyear;//,note;
	//private final static int ENGLISH = 0;
	//private final static int ARABIC = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear);
        ////////////////// input date /////////////////////////////////////////////////////////////
        inday = (EditText)findViewById(R.id.inday);
        inmonth = (EditText)findViewById(R.id.inmonth);
        inyear = (EditText)findViewById(R.id.inyear);
        //////////////// input selection /////////////////////////////////////////////////////////
        tohijri = (RadioButton)findViewById(R.id.tohijri);
        togregorian = (RadioButton)findViewById(R.id.togregorian);
        /////////////// output result ///////////////////////////////////////////////////////////
        outdayw = (TextView)findViewById(R.id.outdayw);
        outday = (TextView)findViewById(R.id.outday);
        outmonth = (TextView)findViewById(R.id.outmonth);
        outyear = (TextView)findViewById(R.id.outyear);
        /////////// note message{v1.2 note be fixed in xml}/////////////////////////////////////////////////////////////////
        //note = (TextView)findViewById(R.id.note);
        //convert = (Button)findViewById(R.id.btn_convert);
        //////////////// Initialize the fields with current date
        
        try {
			setInit();
		} catch (Exception e) {
			//e.printStackTrace();
			//note.setText(error);
			error(0);//display error message
		}
        
    }
    /**
     * Initialize the app. with current gregorian date and its equivelent hijri
     * @throws Exception 
     */
    private void setInit() throws Exception{
    	Day now = new Day();
        inday.setText(Integer.valueOf(now.getDayOfMonth()).toString());
        inmonth.setText(Integer.valueOf(now.getMonthNo()).toString());
        inyear.setText(Integer.valueOf(now.getYear()).toString());
        int i=getDate();
    	if(i==1){
    		ok();
    	}else{
    		if (Locale.getDefault().getLanguage().equals("ar"))
    			Aerror(i);
    		else
    			error(i);
    	}
    	
    }
    
	
	
	/**
     * 
     * @param view
	 * @throws Exception 
	 * @throws NumberFormatException 
     */
    public void convert(View view) throws NumberFormatException, Exception{
    	int i=getDate();
    	if(i==1){
    		ok();
    	}else{
    		if (Locale.getDefault().getLanguage().equals("ar"))
    			Aerror(i);
    		else
    			error(i);
    	}
 	}
    /**
     * -read the input {day , month , year}
     * -check input length and return false if input string length = 0
     * -check format{non numeric input} and return false if there is format error 
     * @return 	-8 no input data(one or all fields is empty)
     * 			-9 non numeric format
     * @throws Exception 
     * 
     */
    public int getDate() throws Exception{
    	String sy = inyear.getText().toString();
    	String sm = inmonth.getText().toString();
    	String sd = inday.getText().toString();
    	int y=0,m=0,d=0,y_=0,m_=0,d_=0;
    	///////// check input strings length
    	if(sy.length()==0 || sm.length()==0 || sd.length()==0){
    		return -8;
    	}
    	/////// check input string format: is it numeric
    	try{
    		y_ = Integer.valueOf(sy);
    		m_ = Integer.valueOf(sm);
    		d_ = Integer.valueOf(sd);
    	} catch(NumberFormatException e){
    		//outdayw.setText("Number Format Error");
    		return -9;
    	}
    	//// check conversion type: tohijri/togregorian
    	if(tohijri.isChecked()){
    		//if tohijri is checked:
    		//check if the input year, month, and day is right?
    		//if true convert the input date to hijri
    		int i=HijriCalendar.getHijriDate( y_, m_ , d_ );
    		if(i==1){
    			/// read hijri year, month, day
    			y = HijriCalendar.getYear();
    			m = HijriCalendar.getMonth();
    			d = HijriCalendar.getDay();
    			/// write hijri year, month, day, day of week to output view
    			outday.setText(Integer.valueOf(d).toString());
    			outmonth.setText(HijriCalendar.getMonthS());
    			outyear.setText(Integer.valueOf(y).toString());
    			outdayw.setText(GreCalendar.getDayW(y_,m_,d_));
    			
    		}else{
    			//there must be wrong in entered hijri date
    			return i;
    		}
    	}else if(togregorian.isChecked()){
    		int i=GreCalendar.getGreDate(y_, m_, d_);
    		if(i==1){
    			y = GreCalendar.getYear();
    			m = GreCalendar.getMonth();
    			d = GreCalendar.getDay();
    			outday.setText(Integer.valueOf(d).toString());
    			outmonth.setText(GreCalendar.getMonthS());
    			outyear.setText(Integer.valueOf(y).toString());
    			outdayw.setText(GreCalendar.getDayW(y,m,d));
    		}else{
    			//outdayw.setText("Hijri: is out of range");
    			return i;
    		}
    	}
		return 1;
    }
    /*
     * -1 Year is out of range 623 TO 9999
	 * -2 Month is out of range MAX Gre. DATE is 31/12/9999
	 * -3 Day is out of range MAX Gre. DATE is 31/12/9999
	 * -4 Month is out of range MIN Gre. DATE is 7/7/623
	 * -5 Day is out of range MIN Gre. DATE is 7/7/623
	 * -6 Month is wrong. 1 to 12
	 * -7 Day of month is wrong. 1 to 30 or 31 (depends on month)
	 * -8 no input data(on or all fields is empty)
     * -9 non numeric format
	 * -10 Year is out of range 2 TO 9666
	 * -20 Month is out of range MAX Hijri DATE is 3/4/96666
	 * -30 Day is out of range MAX Hijri DATE is 3/4/96666
	 * -40 Month is out of range MIN Hijri DATE is 1/1/2
	 * -50 Day is out of range MIN Hijri DATE is 1/1/2
	 * -60 Month is wrong. 1 to 12
	 * -70 Day of month is wrong. 1 to 30 or 29 (depends on month)
     */
    private void error(int i) {
    	String error = "";
    	switch(i){
    	case -1:
    		error="Year Is Out Of Range (623 to 9999)";
    		inyear.setTextColor(Color.RED);
    		break;
    	case -2:
    		error="Date Is Out Of Range. (MAX. Gregorian Date = December 31, 9999)";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -3:
    		error="Date Is Out Of Range. (MAX. Gregorian Date = December 31, 9999)";
    		inday.setTextColor(Color.RED);
    		break;
    	case -4:
    		error="Date Is Out Of Range. (MIN. Gregorian Date = July 7, 623)";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -5:
    		error="Date Is Out Of Range. (MIN. Gregorian Date = July 7, 623)";
    		inday.setTextColor(Color.RED);
    		break;
    	case -6:
    		error="Error in Month (1 to 12)";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -7:
    		error="Error in Day (1 to 30/31 depends on Gregorian month)";
    		inday.setTextColor(Color.RED);
    		break;
    	case -8:error="Please Enter Date"; break;
    	case -9:error="Non Numeric Input Data"; break;
    	case -10:
    		error="Year Is Out Of Range (2 to 9666)";
    		inyear.setTextColor(Color.RED);
    		break;
    	case -20:
    		error="Date Is Out Of Range (MAX. Hijri Date = Rabi I 3, 9666)";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -30:
    		error="Date Is Out Of Range (MAX. Hijri Date = Rabi I 3, 9666)";
    		inday.setTextColor(Color.RED);
    		break;
    	case -40:
    		error="Date Is Out Of Range (MIN. Hijri Date = Muharram 1, 2)";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -50:
    		error="Date Is Out Of Range. (MIN. Hijri Date = Muharram 1, 2)";
    		inday.setTextColor(Color.RED);
    		break;
    	case -60:
    		error="Month Is Out Of Range (1 to 12)";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -70:
    		error="Day Is Out Of Range (1 to 29/30 depends on Hijri month)";
    		inday.setTextColor(Color.RED);
    		break;
    	default:error="Error";
    	}
		
		outdayw.setText("");
		outday.setText("");
		outmonth.setText("");
		outyear.setText("");
		//note.setText("");
		//note.setTextColor(Color.RED);
		//toast launch
		Toast.makeText(HijriGregorianConv.this, error, Toast.LENGTH_LONG).show();		
	}
    private void Aerror(int i) {
    	String error = "";
    	switch(i){
    	case -1:
    		error="623-9999 السنة الميلادية خارج النطاق";
    		inyear.setTextColor(Color.RED);
    		break;
    	case -2:
    		error="التاريخ الميلادي خارج النطاق أقصى تاريخ هو 31 ديسمبر 9999";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -3:
    		error="التاريخ الميلادي خارج النطاق أقصى تاريخ هو 31 ديسمبر 9999";
    		inday.setTextColor(Color.RED);
    		break;
    	case -4:
    		error="التاريخ الميلادي خارج النطاق أدنى تاريخ هو 7 يوليو 623";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -5:
    		error="التاريخ الميلادي خارج النطاق أقصى تاريخ هو 7 يوليو 623";
    		inday.setTextColor(Color.RED);
    		break;
    	case -6:
    		error="خطأ في الشهر (من 1 إلى 12)ء";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -7:
    		error="خطأ في اليوم (من 1 إلى 30 أو 31 حسب الشهر الميلادي)ء";
    		inday.setTextColor(Color.RED);
    		break;
    	case -8:error="أدخل التاريخ إذا سمحت"; break;
    	case -9:error="البيانات المدخلة خاطئة"; break;
    	case -10:
    		error="2-9666 السنة الهجرية خارج النطاق";
    		inyear.setTextColor(Color.RED);
    		break;
    	case -20:
    		error="التاريخ الهجري خارج النطاق أقصى تاريخ هو 3 ربيع الأول 9666";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -30:
    		error="التاريخ الهجري خارج النطاق أقصى تاريخ هو 3 ربيع الأول 9666";
    		inday.setTextColor(Color.RED);
    		break;
    	case -40:
    		error="التاريخ الهجري خارج النطاق أقل تاريخ هو 1 محرم 2";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -50:
    		error="التاريخ الهجري خارج النطاق أقل تاريخ هو 1 محرم 2";
    		inday.setTextColor(Color.RED);
    		break;
    	case -60:
    		error="خطأ في الشهر (1 إلى 12)ء";
    		inmonth.setTextColor(Color.RED);
    		break;
    	case -70:
    		error="خطأ في اليوم (من 1 إلى 30 أو 29 حسب الشهر الهجري)ء";
    		inday.setTextColor(Color.RED);
    		break;
    	default:error="خطأ";
    	}
		
		outdayw.setText("");
		outday.setText("");
		outmonth.setText("");
		outyear.setText("");
		//note.setText("");
		//note.setTextColor(Color.RED);
		//toast launch
			Toast.makeText(HijriGregorianConv.this, error, Toast.LENGTH_LONG).show();
	}
    private void ok(){
    	inday.setTextColor(Color.BLUE);
		inmonth.setTextColor(Color.BLUE);
		inyear.setTextColor(Color.BLUE);
		
		//note.setTextColor(Color.WHITE);
		//note.setText(snote);
    }
    /**
     * 
     * @param view
	 * @throws Exception 
     */
    public void today(View view) throws Exception{
    	tohijri.setChecked(true);
    	try {
			setInit();
			//note.setText(snote);
		} catch (Exception e) {
			//e.printStackTrace();
			//note.setText(error);
		}
    }
	/**
     * 
     * @param view
	 * @throws Exception 
     */
    public void clear(View view) throws Exception{
    	ok();
    	//note.setText("");
		inday.setText("");
		inmonth.setText("");
		inyear.setText("");
		
    	outdayw.setText("");
		outday.setText("");
		outmonth.setText("");
		outyear.setText("");
    }
	//////////////////////////////////// menu
	// Called first time user clicks on the menu button
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater(); //
		inflater.inflate(R.menu.menu, menu); //
		return true; //
	}
	// Called when an options item is clicked
	/**
	 * @param item
	 * @return menu item
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { //
		case R.id.about:
			startActivity(new Intent(this, AboutActivity.class)); //
			break;
		}
		return true; //
	}
}

