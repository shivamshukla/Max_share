import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Company {
   private List<Company> companies;
   private int lastReadIdx=0;

   // Return the next set of company data(if any).
   private Company getNextCompanyData() {
      if (companies == null) {
         lastReadIdx = 0;
         try {
            loadCompanies();
         } catch (Exception e) {
         }
      }
      if (companies == null) return null;
      if (lastReadIdx < companies.size()) return companies.get(lastReadIdx++);
      return null;
   }

   //Read the CSV file and separate the data for comparison.
   public void loadCompanies() throws Exception {
      Scanner s = null;
      try {
         companies = new ArrayList<Company>();
         File f = new File("Absolute_path\\Test.csv");
         s = new Scanner(new FileInputStream(f));
         String[] headers = readLine(s);
         if (headers != null && headers.length >0) {
            String[] data = null;
            while ((data = readLine(s)) != null) {
               if (data.length != headers.length) {
                  companies = null;
                  throw new Exception("Invalid Data - headers count " + headers.length + " does not match with data count "+data.length);
               }
               String year = data[0];
               String month = data[1];
               for (int x=2; x<data.length; x++) {
                  double price = new Double(data[x]).doubleValue();
                  Company company = new Company(headers[x], year, month, price);
                  companies.add(company);
               }
            }
         }
      } finally {
         if (s != null) s.close();
      }
   }

  //Utility method for reading comma separated value line
   private String[] readLine(Scanner s) {
      if (s.hasNextLine()) {
         return s.nextLine().trim().split(",");
      }
      return null;
   }

  //Method to process and do the comparison the values and show the result.
   public void processCompanies() {
      Map<String, Company> companies = new HashMap<String, Company>();
      Company newCompany = null;

      // repeat until all company data processed from CSV file
      while ((newCompany = getNextCompanyData()) != null) {
         Company oldCompany = companies.get(newCompany.getName());
         if (oldCompany == null || newCompany.getPrice() > oldCompany.getPrice())
            companies.put(newCompany.getName(), newCompany);
      }
      // Time to display the winners :)
      for (String name : companies.keySet()) {
         Company company = companies.get(name);
         System.out.println(company.getName() + " highest price " + company.getPrice() + " in " + company.getMonth() + " " + company.getYear());
      }
   }
   
   public class Company {
	   private String name;
	   private String year;
	   private String month;
	   private double price;

	   public Company(String name, String year, String month, double price) {
	      super();
	      this.name = name;
	      this.year = year;
	      this.month = month;
	      this.price = price;
	   }
	   public String getName() {
	      return name;
	   }
	   public void setName(String name) {
	      this.name = name;
	   }
	   public String getYear() {
	      return year;
	   }
	   public void setYear(String year) {
	      this.year = year;
	   }
	   public String getMonth() {
	      return month;
	   }
	   public void setMonth(String month) {
	      this.month = month;
	   }
	   public double getPrice() {
	      return price;
	   }
	   public void setPrice(double price) {
	      this.price = price;
	   }

	   @Override
	   public String toString() {
	      return "Company [name=" + name + ", year=" + year + ", month=" + month + ", price=" + price + "]";
	   }
	}
   //Main method to start the processing.
   public static void main(String[] args) {
      CompanyLoader loader = new CompanyLoader();
      loader.processCompanies();
   }
}
