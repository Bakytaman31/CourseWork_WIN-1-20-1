package com.Director;

import com.Database.Database;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

        public class Director {
            public static void main(String name) {

                final String budgetID = "606624505afe5468c793940f";
                Scanner in = new Scanner(System.in);

                boolean status = true;
                String s="";
                int marketing;

                System.out.println("Greetings, " + name + "!");
                while (status) {
                    System.out.println("Type the number of option. Type 9 when you're done:");
                    System.out.println("1. Show a list of all coverage areas;");
                    System.out.println("2. Show a list of budget categories;");
                    System.out.println("3. Show the allocated budget for a specific category of places for marketing;");
                    System.out.println("4. Show current marketing funds;");
                    System.out.println("5. Show the total budget required for the salary;");
                    System.out.println("6. Increase an employee's salary;");
                    System.out.println("7. Lower an employee's salary;");
                    System.out.println("8. Show the list of equipment for construction of objects;");
                    System.out.println("9. Exit.");
                    System.out.println("Choose option: >>");

                    float n=0; float m=0;
                    String place_name = "";
                    String divider = "=====================";


                    int counter = 1;
                    List ids = new ArrayList();
                    List salaries = new ArrayList();
                    int choice = in.nextInt();

                    List tags = new ArrayList() {};
                    for (Document document : Database.foundedBudgets) {
                        if (!(tags.contains(document.getString("place"))) && document.getString("place")!=null)
                            tags.add(document.getString("place"));
                    }

                    switch (choice) {
                        case 1:
                            //Show the percentage of clients depending by their city

                            System.out.println("Select customer coverage area:");
                            System.out.println("1. Bishkek");
                            System.out.println("2. Chui");
                            System.out.println("3. Talas");
                            System.out.println("4. Jalal-Abad");
                            System.out.println("5. Osh");
                            System.out.println("6. Naryn");
                            System.out.println("7. Issyk-Kul");
                            System.out.println("8. Batken");
                            System.out.println("Choose region: >>");

                            int city = in.nextInt();



                            for (Document document : Database.foundedClients) {
                                if(!document.getString("place").equals("")){
                                    n++;
                                }
                            }

                            switch (city){
                                case 1:
                                    place_name = "Bishkek";
                                    break;
                                case 2:
                                    place_name = "Chui";
                                    break;
                                case 3:
                                    place_name = "Talas";
                                    break;
                                case 4:
                                    place_name = "Jalal-Abad";
                                    break;
                                case 5:
                                    place_name = "Osh";
                                    break;
                                case 6:
                                    place_name = "Naryn";
                                    break;
                                case 7:
                                    place_name = "Issyk-Kul";
                                    break;
                                case 8:
                                    place_name = "Batken";
                                    break;
                            }
                            for (Document document : Database.foundedClients) {
                                if(document.getString("place").equals(place_name)){
                                    m++;
                                }
                            }
                            System.out.printf("In %s: %.2f%s(%.0f/%.0f)%n%n",place_name,(m/n),"%",m,n);
                            System.out.println(divider);
                            break;



                        case 2:
                            System.out.println("Choose category:");
                            System.out.println("1. Marketing budget");
                            System.out.println("2. Salary budget");
                            System.out.println(">>");
                            int budget_place = in.nextInt();
                            String budgetType="";

                            switch (budget_place) {
                                case 1:
                                    budgetType = "marketing";
                                    break;
                                case 2:
                                    budgetType = "salary";
                                    break;  
                            }


                            for (Document document : Database.foundedBudgets) {
                                if (budgetID.equals(document.getObjectId("_id").toString())) {
                                    System.out.println("budget of "+ budgetType + " = " + document.getLong(budgetType) + "\n");
                                }
                            }
                            System.out.println(divider);
                            break;
                        case 3:

                            while (true) {
                                System.out.println("Enter marketing type:");
                                for (int i = 0; i < tags.size(); i++) {
                                    System.out.println(i + 1 + ". " + tags.get(i));
                                }
                                System.out.println("Enter zone: >>");
                                marketing = in.nextInt();
                                if (marketing < tags.size() && marketing >= 0) {
                                    s = (String) (tags.get(marketing - 1));
                                    break;
                                }
                            }
                            System.out.println("Actual projects:");
                            for(Document document : Database.foundedBudgets) {
                                if (s.equals(document.getString("place")) && document.getInteger("cost")!=null && document.getString("name of event")!=null) {
                                    System.out.printf("%s: %d%n", document.getString("name of event"), document.getInteger("cost"));
                                }
                            }
                            System.out.println(divider);
                            break;
                        case 4:
                            int sumM = 0;
                            for (Document document : Database.foundedBudgets) {
                                if ("marketing".equals(document.getString("type"))) {
                                    sumM += document.getInteger("cost");
                                }
                            }
                            System.out.printf("used budget = %d%n%n", sumM);
                            System.out.println(divider);
                            break;
                        case 5:
                            int sumS = 0;
                            for (Document document : Database.foundedUsers) {
                                if (
                                        "manager".equals(document.getString("position")) ||
                                                "marketing".equals(document.getString("position")) ||
                                                "saleManager".equals(document.getString("position")) ||
                                                "worker".equals(document.getString("position"))
                                ) {
                                    sumS += document.getInteger("salary");
                                }
                            }
                            System.out.printf("used budget = %d%n%n", sumS);
                            System.out.println(divider);
                            break;
                        case 6:
                            int workers=0;

                            for (Document document : Database.foundedUsers) {
                                if (!document.getString("position").equals("director")) {
                                    System.out.println(counter + ") " + document.getString("name") + ": " + document.getInteger("salary"));
                                    ObjectId workerId = document.getObjectId("_id");
                                    int salary = document.getInteger("salary");
                                    salaries.add(salary);
                                    ids.add(workerId);
                                    counter++;
                                }
                            }

                            workers = 0;
                            for (Document document : Database.foundedUsers) {
                                if(!document.getString("position").equals("director")){
                                    workers++;
                                }
                            }

                            System.out.printf("Choose worker (1-%d)%n",workers);

                            int chooseWorker;
                            do{
                                chooseWorker = in.nextInt();
                            }while (chooseWorker > workers || chooseWorker < 1);

                            System.out.println("Type an allowance");
                            int typeAllowance = in.nextInt();
                            Database.users.updateOne(Filters.eq("_id", ids.get(chooseWorker - 1)), new Document(
                                    "$set",
                                    new Document("salary", typeAllowance + (int)salaries.get(chooseWorker - 1))
                            ));
                            System.out.println(divider);
                            break;
                        case 7:
                            for (Document document : Database.foundedUsers) {
                                if (!document.getString("position").equals("director")) {
                                    System.out.println(counter + ") " + document.getString("name") + ": " + document.getInteger("salary"));
                                    ObjectId workerId = document.getObjectId("_id");
                                    int salary = document.getInteger("salary");
                                    salaries.add(salary);
                                    ids.add(workerId);
                                    counter++;
                                }
                            }

                            workers = 0;
                            for (Document document : Database.foundedUsers) {
                                if(!document.getString("position").equals("director")){
                                    workers++;
                                }
                            }

                            System.out.printf("Choose worker (1-%d)%n",workers);

                            int chooseWorker2;
                            do{
                                chooseWorker2 = in.nextInt();
                            }while (chooseWorker2 > workers || chooseWorker2 < 1);
                            System.out.println("Type a subtraction");
                            int typeSalary = in.nextInt();
                            int save =  (-1)*typeSalary + (int)salaries.get(chooseWorker2 - 1);
                            Database.users.updateOne(Filters.eq("_id", ids.get(chooseWorker2 - 1)), new Document(
                                    "$set",
                                    new Document("salary", (int)(save))//String.valueOf(
                            ));
                            System.out.println(divider);
                            break;
                        case 8:
                            //Show equipment list
                            for (Document document : Database.foundedEquipment) {
                                System.out.println(document.getString("name") + ": " + document.getString("quantity"));
                            }
                            System.out.println(divider);
                            break;
                        case 9:
                            status = false;
                            System.out.println("Goodbye!");
                            break;
                        default:
                            break;
                    }
                }
            }
        }