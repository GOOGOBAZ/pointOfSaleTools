/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package googo.pmms.project2.accountsHelper;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Stanchart
 */
public class TestJson {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       TestJson thisc=new TestJson();
    Gson emp=new Gson();
    System.out.print(thisc.employeesDetails().size());
    System.out.print(emp.toJson(thisc.employeesDetails()));
    }
    
private Map<String,Object> employeesDetails(){

 Map<String,Object> employeeData=new HashMap();int z=0;List datum=new ArrayList();


    fileInputOutPutStreams fios =new fileInputOutPutStreams();

    String data=fios.stringFileReader(fios.createFileName("employee","employeeDetails","EmpDetails.txt"));
    
    String[] employees=data.split(";");
    
    while( z<employees.length){
   Map<String,Object>  dataj=new HashMap();   
    String[] finalEm=employees[z].replace(";", "").split("[,]", 3);
    dataj.put("employeeName", finalEm[0]);
    dataj.put("department", finalEm[1]);
   dataj.put("salary", finalEm[2].replace(",", ""));
   datum.add(dataj);
    z++;
    }
  employeeData.put("employees", datum);
return employeeData;
}
}
