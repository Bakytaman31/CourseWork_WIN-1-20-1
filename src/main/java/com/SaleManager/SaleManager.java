package com.SaleManager;

import com.Database.Database;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class SaleManager {
    public static void main(String name) {
        Scanner in = new Scanner(System.in);

        boolean status = true;
        System.out.println("Greetings, "+ name +"!");
        while (status) {
            System.out.println("\nType the number of option. Type 11 when you're done:");
            System.out.println("1. Show all clients;");
            System.out.println("2. Find client;");
            System.out.println("3. Show free apartments to buy;");
            System.out.println("4. Show sold apartments");
            System.out.println("5. Show the most expensive apartment");
            System.out.println("6. Show the cheapest apartment");
            System.out.println("7. Sell an apartment");
            System.out.println("8. Rent an apartment");
            System.out.println("9. Show available properties for rent:");
            System.out.println("10. Show occupied objects for rent:");
            System.out.println("11. Exit");
            System.out.println("Choose option: >>");
            String divider = "=====================";
            int choice = in.nextInt();
            switch (choice) {
                case 1:
                    int step=1;
                    for (Document document : Database.foundedClients) {
                        System.out.println(step +".\t"+ document.getString("name"));
                        step++;
                    }
                    System.out.println(divider);
                    break;
                case 2:
                    String foundedName;
                    System.out.println("Type clients name for search: >>>");
                    foundedName = in.next();
                    step=1;
                    for (Document document : Database.foundedClients)
                        if (document.getString("name").contains(foundedName))
                            step++;
                    if (step>2) {
                        System.out.println("Probably you searched:");
                        step = 1;
                        for (Document document : Database.foundedClients) {
                            if (document.getString("name").contains(foundedName)){
                                System.out.println(step + ".\t" + document.getString("name"));
                                step++;
                            }
                        }
                    }else if(step==2){
                        System.out.println("Client founded:");
                        for (Document document : Database.foundedClients)
                            if (document.getString("name").contains(foundedName)){
                                System.out.printf("Name:        %s%n" +
                                                "Place:       %s%n" +
                                                "Phonenumber: %s%n",
                                        document.getString("name"),
                                        document.getString("place"),
                                        document.getString("phoneNumber")
                                );
                            }
                    }
                    else{
                        System.out.println("Client not found");
                    }

                    break;
                case 3:
                    for (Document document : Database.foundedObjects) {
                        if (document!= null && document.getString("status").equals("free") && document.getString("sellPrice") != null) {
                            System.out.println(document.getString("address"));
                        }
                    }
                    System.out.println(divider);
                    break;
                case 4:
                    for (Document document : Database.foundedObjects) {
                        if (document!= null && document.getString("status").equals("sold") && document.getString("sellPrice") != null) {
                            System.out.println(document.getString("address"));
                        }
                    }
                    System.out.println(divider);
                    break;
                case 5:
                    int max=0;
                    String maxID="";

                    for (Document document : Database.foundedObjects) {
                        if (document!= null && document.getString("status").equals("free") && document.getString("sellPrice") != null) {
                            if (max < Integer.parseInt(document.getString("sellPrice"))) {
                                max = Integer.parseInt(document.getString("sellPrice"));
                                maxID = document.getObjectId("_id").toString();
                            }
                        }
                    }
                    System.out.println("Most expensive apartment:");
                    for (Document document : Database.foundedObjects) {
                        if (document != null && maxID.equals(document.getObjectId("_id").toString()) && document.getString("sellPrice") != null) {
                            System.out.printf("Adress:  %s%n" +
                                            "Prise:   %s%n",
                                    document.getString("address"),
                                    document.getString("sellPrice")
                            );
                        }
                    }
                    break;
                case 6:
                    int min=999999999;
                    String minID="";

                    for (Document document : Database.foundedObjects) {
                        if (document!= null && document.getString("status").equals("free") && document.getString("sellPrice") != null) {
                            if (min > Integer.parseInt(document.getString("sellPrice"))) {
                                min = Integer.parseInt(document.getString("sellPrice"));
                                minID = document.getObjectId("_id").toString();
                            }
                        }
                    }
                    System.out.println("The cheapest apartment:");
                    for (Document document : Database.foundedObjects) {
                        if (document != null && minID.equals(document.getObjectId("_id").toString()) && document.getString("sellPrice") != null) {
                            System.out.printf("Adress:  %s%n" +
                                            "Prise:   %s%n",
                                    document.getString("address"),
                                    document.getString("sellPrice")
                            );
                        }
                    }
                    break;
                case 7:
                    System.out.println("Type address: >>>");

                    String foundedAddress = in.nextLine();
                    foundedAddress = in.nextLine();

                    String AddressID;
                    ObjectId AddressTrueID;
                    step=1;
                    for (Document document : Database.foundedObjects)
                        if (document.getString("address").contains(foundedAddress))
                            step++;
                    if (step>2) {
                        System.out.println("Probably you searched:");
                        step = 1;
                        for (Document document : Database.foundedObjects) {
                            if (document.getString("address").contains(foundedAddress)){
                                System.out.println(step + ".\t" + document.getString("address"));
                                step++;
                            }
                        }
                    }else if(step==2) {
                        System.out.println("Apartment found, please type the full address to confirm");
                        for (Document document : Database.foundedObjects)
                            if (document.getString("address").contains(foundedAddress)) {
                                System.out.printf("Address:               %20s%n",
                                        document.getString("address")
                                );
                                AddressID = document.getObjectId("_id").toString();
                                AddressTrueID = document.getObjectId("_id");
                                System.out.println(">>>");
                                foundedName = in.next();

                                if (AddressID.equals(document.getObjectId("_id").toString())) {



                                    //2 input
                                    System.out.println("Please type new owner: >>>");
                                    foundedName = in.nextLine();
                                    foundedName = in.nextLine();
                                    String foundedID = "";

                                    step = 1;
                                    for (Document document2 : Database.foundedClients)
                                        if (document2.getString("name").contains(foundedName))
                                            step++;
                                    if (step > 2) {
                                        System.out.println("Probably you searched:");
                                        step = 1;
                                        for (Document document2 : Database.foundedClients) {
                                            if (document2.getString("name").contains(foundedName)) {
                                                System.out.println(step + ".\t" + document2.getString("name"));
                                                step++;
                                            }
                                        }
                                    } else if (step == 2) {
                                        System.out.println("Client found, please type the full name to confirm");
                                        for (Document document2 : Database.foundedClients)
                                            if (document2.getString("name").contains(foundedName)) {
                                                System.out.printf("Name:               %20s%n",
                                                        document2.getString("name")
                                                );
                                                foundedID = document2.getObjectId("_id").toString();
                                                System.out.println(">>>");
                                                foundedName = in.next();
                                                foundedName = in.next();

                                                if (foundedID.equals(document2.getObjectId("_id").toString())) {

                                                    System.out.println("Apartment sold successfully!");

                                                    Database.objects.updateOne(Filters.eq("_id", AddressTrueID), new Document(
                                                            "$set",
                                                            new Document("status", "sold")
                                                    ));
                                                    Database.objects.updateOne(Filters.eq("_id", AddressTrueID), new Document(
                                                            "$set",
                                                            new Document("owner", foundedID)
                                                    ));

                                                }
                                            }

                                    }
                                }
                            }
                    }

                    break;
                case 8:
                    System.out.println("Please indicate the address of the object for rent>>>:");
                    foundedAddress = in.next();
                    step=1;
                    for (Document document : Database.foundedObjects)
                        if (document.getString("address").contains(foundedAddress))
                            step++;
                    if (step>2) {
                        System.out.println("Probably you searched:");
                        step = 1;
                        for (Document document : Database.foundedObjects) {
                            if (document.getString("address").contains(foundedAddress)){
                                System.out.println(step + ".\t" + document.getString("address"));
                                step++;
                            }
                        }
                    }else if(step==2) {
                        System.out.println("Apartment found, please type the full address to confirm");
                        for (Document document : Database.foundedObjects)
                            if (document.getString("address").contains(foundedAddress)) {
                                System.out.printf("Address:               %20s%n",
                                        document.getString("address")
                                );
                                AddressID = document.getObjectId("_id").toString();
                                AddressTrueID = document.getObjectId("_id");
                                System.out.println(">>>");
                                foundedName = in.nextLine();
                                foundedName = in.nextLine();

                                if (AddressID.equals(document.getObjectId("_id").toString())) {



                                    //2 input
                                    System.out.println("Type a name of person, who want to rent >>>");
                                    foundedName = in.nextLine();
                                    foundedName = in.nextLine();

                                    String foundedID;

                                    step = 1;
                                    for (Document document2 : Database.foundedClients)
                                        if (document2.getString("name").contains(foundedName))
                                            step++;
                                    if (step > 2) {
                                        System.out.println("Probably you searched:");
                                        step = 1;
                                        for (Document document2 : Database.foundedClients) {
                                            if (document2.getString("name").contains(foundedName)) {
                                                System.out.println(step + ".\t" + document2.getString("name"));
                                                step++;
                                            }
                                        }
                                    } else if (step == 2) {
                                        System.out.println("Client found, please type the full name to confirm");
                                        for (Document document2 : Database.foundedClients)
                                            if (document2.getString("name").contains(foundedName)) {
                                                System.out.printf("Name:               %20s%n",
                                                        document2.getString("name")
                                                );
                                                foundedID = document2.getObjectId("_id").toString();
                                                System.out.println(">>>");
                                                foundedName = in.next();
                                                if (foundedID.equals(document2.getObjectId("_id").toString())) {

                                                    System.out.println("Apartment rented successfully!!");

                                                    Database.objects.updateOne(Filters.eq("_id", AddressTrueID), new Document(
                                                            "$set",
                                                            new Document("status", "rented")
                                                    ));
                                                    Database.objects.updateOne(Filters.eq("_id", AddressTrueID), new Document(
                                                            "$set",
                                                            new Document("owner", foundedID)
                                                    ));

                                                }
                                            }

                                    }
                                }
                            }
                    }


                    break;
                case 9:
                    //Показать свободные объекты в аренду:

                    for (Document document : Database.foundedObjects) {
                        if (document!= null && document.getString("status").equals("free") && document.getString("rentPrice") != null) {
                            System.out.println(document.getString("address"));
                        }
                    }
                    System.out.println(divider);

                    break;
                case 10:

                    for (Document document : Database.foundedObjects) {
                        if (document!= null && document.getString("status").equals("rented") && document.getString("rentPrice") != null) {
                            System.out.println(document.getString("address"));
                        }
                    }
                    System.out.println(divider);

                    break;
                case 11:
                    System.out.println("Goodbye!");
                    status = false;
                    break;
                default:
                    break;
            }
        }
    }
}