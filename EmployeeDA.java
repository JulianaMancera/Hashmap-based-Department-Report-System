import java.io.*;
import java.util.Scanner;

public class EmployeeDA {
    private Employee employee;

     public EmployeeDA(String empNo){
        try  (Scanner empFile = new Scanner(new FileReader("emp.csv"))){

            while(empFile.hasNextLine()){

                String empLineData = empFile.nextLine();
                String[] empSplitData = empLineData.split(",");
                 
                if(empSplitData[0].equals(empNo)){

                    employee = new Employee();
                    employee.setEmpNo(empSplitData[0].trim());
                    employee.setLastName(empSplitData[1].trim());
                    employee.setFirstName(empSplitData[2].trim());
                    break;

                }
            }
        }
        catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
     }

     public Employee getEmployee(){
        return employee;
    }
}

