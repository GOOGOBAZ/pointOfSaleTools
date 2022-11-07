/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import googo.pmms.project2.databases.DatabaseQuaries;
import java.awt.Component;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.text.DecimalFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author STAT SOLUTIONS
 */
public class DepreciationAmort {
    
       DatabaseQuaries dbq =new DatabaseQuaries();
    DepreciationFormulas depForm =new DepreciationFormulas();
    Calendar cal = Calendar.getInstance();
Formartter fmt = new Formartter();
fileInputOutPutStreams fios= new fileInputOutPutStreams();

    DecimalFormat df1 = new DecimalFormat("#.##");
DecimalFormat df2 = new DecimalFormat("#");
 DecimalFormat NumberFormat =new DecimalFormat("#,###.##");
    public int createDepreciationStoreSalvage(List theDepriciationDetails){
    
    dbq.createAmortisableDepreciation(theDepriciationDetails,20);
    
    return dbq.getThelatestValueDepreciation();
    
    }
    
    
     public int createDepreciationStore(List theDepriciationDetails){
    
    dbq.createAmortisableDepreciation(theDepriciationDetails,30);
    
      return dbq.getThelatestValueDepreciation();
    }
    
    public boolean amortizeTheDepreciation(List theDetails,Component c){
        
        boolean success=false;
//     amortDepr.add(theAddedValue);// Item Id 0
//       amortDepr.add(depe.get(0));//Method 1
//       amortDepr.add(depe.get(1));//Asset Name 2
//      amortDepr.add(depe.get(3));//Purchase Price 3
//      amortDepr.add(depe.get(4));//Useful Life 4
//       amortDepr.add(depe.get(5));//Start Date 5
//       amortDepr.add("0.0");//Salvage Value     6 
    
  switch(theDetails.get(1).toString()){
    
      case "Straight-line":
   success=dbq. createAmortionDepreciationSchedule(amortizeStraghtLineMethod(theDetails,c));   
          
          break;
  case "Double declining balance":
      
   success=dbq. createAmortionDepreciationSchedule(amortizeReducingMethod(theDetails,c));       
      break;
  case "Units of production":break;
  case "Sum of years digits":break;
  }
    
    
 return   success; 
    }
    
    
    
      public List<List> amortizeStraghtLineMethod(List theStraightDetails,Component c){
    //     amortDepr.add(theAddedValue);// Item Id 0
//       amortDepr.add(depe.get(0));//Method 1
//       amortDepr.add(depe.get(1));//Asset Name 2
//      amortDepr.add(depe.get(3));//Purchase Price 3
//      amortDepr.add(depe.get(4));//Useful Life 4
//       amortDepr.add(depe.get(5));//Start Date 5
//       amortDepr.add("0.0");//Salvage Value     6 
    double initialPurchasePrice=parseDouble(theStraightDetails.get(3).toString());//purchase price
    
    double initialSalvageValue=parseDouble(theStraightDetails.get(6).toString());//salvage life
    
    int initialUsefulLife= parseInt(theStraightDetails.get(4).toString().replace("'", "").replace(".0", ""));//useful life
    
    
    String initialDepreciationDate= theStraightDetails.get(5).toString();//
    
    int count=1;
    
    double openingAssetValaue= initialPurchasePrice;
    double depreciation= depForm.depreciatedStraightLine(initialPurchasePrice, initialSalvageValue, initialUsefulLife);
    double closingAssetValue=initialPurchasePrice-depreciation;
   
    
       
// long instDate=fmt.convertTimeToMillseconds(initialDepreciationDate);


     List<List>  data5v=new ArrayList<>();
    //     amortDepr.add(theAddedValue);// Item Id 0
//       amortDepr.add(depe.get(0));//Method 1
//       amortDepr.add(depe.get(1));//Asset Name 2
//      amortDepr.add(depe.get(3));//Purchase Price 3
//      amortDepr.add(depe.get(4));//Useful Life 4
//       amortDepr.add(depe.get(5));//Start Date 5
//       amortDepr.add("0.0");//Salvage Value     6  
       
     for(int i=0;i<initialUsefulLife;i++){
       List  data4v=new ArrayList<>();
      YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(initialDepreciationDate).substring(0, 7));  
//       String depreciationDate=fmt.dateConverter(instDate);
//       JOptionPane.showMessageDialog(c, depreciationDate);
//        JOptionPane.showMessageDialog(c, instDate+"");
       data4v.add(count);//Periods
        data4v.add(initialDepreciationDate);//Depreciation Date
         data4v.add(theStraightDetails.get(0).toString());//Asset Id
           data4v.add(theStraightDetails.get(2).toString());//Asset Account
       data4v.add(Math.abs(parseDouble(df2.format(openingAssetValaue))));//Opening Asset Value
       data4v.add( Math.abs(parseDouble(df2.format(depreciation))));//Depreciation
       data4v.add(Math.abs(parseDouble(df2.format(closingAssetValue))));//Closing Asset Value
       data4v.add(theStartDateObject.getMonth().toString());
        data4v.add(theStartDateObject.getYear());
       data5v.add(data4v);
  
     count++;
     openingAssetValaue=openingAssetValaue-depreciation;
     closingAssetValue=closingAssetValue-depreciation;
    initialDepreciationDate=fmt.newInstalmentDate(initialDepreciationDate);
//intslmentDate=fmt.newInstalmentDate(intslmentDate);

     }
           
return data5v;
    }
      
      
  public List<List>amortizeReducingMethod(List theStraightDetails,Component c){
    //     amortDepr.add(theAddedValue);// Item Id 0
//       amortDepr.add(depe.get(0));//Method 1
//       amortDepr.add(depe.get(1));//Asset Name 2
//      amortDepr.add(depe.get(3));//Purchase Price 3
//      amortDepr.add(depe.get(4));//Useful Life 4
//       amortDepr.add(depe.get(5));//Start Date 5
//       amortDepr.add("0.0");//Salvage Value     6 
    double initialPurchasePrice=parseDouble(theStraightDetails.get(3).toString());//purchase price
    
    double initialSalvageValue=parseDouble(theStraightDetails.get(6).toString());//salvage life
    
    int initialUsefulLife= parseInt(theStraightDetails.get(4).toString().replace("'", "").replace(".0", ""));//useful life
    
    
    String initialDepreciationDate= theStraightDetails.get(5).toString();//
    
    int count=1;
    
    double openingAssetValaue= initialPurchasePrice;
    double depreciation= depForm.depreciatedReducingBalance(initialPurchasePrice, initialSalvageValue, initialUsefulLife);
    double closingAssetValue=initialPurchasePrice-depreciation;
   
    
       
// long instDate=fmt.convertTimeToMillseconds(initialDepreciationDate);


     List<List>  data5v=new ArrayList<>();
    //     amortDepr.add(theAddedValue);// Item Id 0
//       amortDepr.add(depe.get(0));//Method 1
//       amortDepr.add(depe.get(1));//Asset Name 2
//      amortDepr.add(depe.get(3));//Purchase Price 3
//      amortDepr.add(depe.get(4));//Useful Life 4
//       amortDepr.add(depe.get(5));//Start Date 5
//       amortDepr.add("0.0");//Salvage Value     6  
       
     for(int i=0;i<initialUsefulLife;i++){
       List  data4v=new ArrayList<>();
   YearMonth theStartDateObject=YearMonth.parse(fmt.forDatabaseWithFullYearBeginningWithDate(initialDepreciationDate).substring(0, 7));  
//   JOptionPane.showMessageDialog(c, theStartDateObject.getMonth()+" "+theStartDateObject.getYear());
//       String depreciationDate=fmt.dateConverter(instDate);
       data4v.add(count);//Periods
        data4v.add(initialDepreciationDate);//Depreciation Date
         data4v.add(theStraightDetails.get(0).toString());//Asset Id
           data4v.add(theStraightDetails.get(2).toString());//Asset Account
       data4v.add(Math.abs(parseDouble(df2.format(openingAssetValaue))));//Opening Asset Value
       data4v.add( Math.abs(parseDouble(df2.format(depreciation))));//Depreciation
       data4v.add(Math.abs(parseDouble(df2.format(closingAssetValue))));//Closing Asset Value
       data4v.add(theStartDateObject.getMonth().toString());
   data4v.add(theStartDateObject.getYear());
    data5v.add(data4v);
     count++;
     openingAssetValaue=openingAssetValaue-depreciation;
     closingAssetValue=closingAssetValue-depreciation;
    initialDepreciationDate=fmt.newInstalmentDate(initialDepreciationDate);
//intslmentDate=fmt.newInstalmentDate(intslmentDate);
depreciation= depForm.depreciatedReducingBalance(openingAssetValaue, initialSalvageValue, initialUsefulLife);
     }
           
return data5v;
  
  }  
    
    
    
    
    
}
