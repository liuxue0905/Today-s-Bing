package com.lx.todaysbing;

import de.greenrobot.daogenerator.ContentProvider;
import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Index;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 *
 * Run it as a Java application (not Android).
 *
 * @author Markus
 */
public class ExampleDaoGenerator {

//    public static void main(String[] args) throws Exception {
//        Schema schema = new Schema(1000, "de.greenrobot.daoexample");
//
//        addNote(schema);
//        addCustomerOrder(schema);
//
//        new DaoGenerator().generateAll(schema, "../DaoExample/src-gen");
//    }

    private static void addNote(Schema schema) {
        Entity note = schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
    }

    private static void addCustomerOrder(Schema schema) {
        Entity customer = schema.addEntity("Customer");
        customer.addIdProperty();
        customer.addStringProperty("name").notNull();

        Entity order = schema.addEntity("Order");
        order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
        order.addIdProperty();
        Property orderDate = order.addDateProperty("date").getProperty();
        Property customerId = order.addLongProperty("customerId").notNull().getProperty();
        order.addToOne(customer, customerId);

        ToMany customerToOrders = customer.addToMany(order, customerId);
        customerToOrders.setName("orders");
        customerToOrders.orderAsc(orderDate);
    }

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(/*1000*/1, "binggallery.chinacloudsites.cn");

        addBingGalleryImage(schema);

        java.io.File file = new java.io.File("greendaogenerator/src-gen");
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            file.mkdir();
        }

//        new DaoGenerator().generateAll(schema, "../greendaogenerator/src-gen");
//        new DaoGenerator().generateAll(schema, "E:/Users/liuxue/AndroidStudioProjects/BingWallpaper/greendaogenerator/src-gen");
        new DaoGenerator().generateAll(schema, "greendaogenerator/src-gen");
    }

    private static void addBingGalleryImage(Schema schema) {
        Entity customer = schema.addEntity("BingGalleryImage");
        customer.addIdProperty().autoincrement();
        Property uidProperty =  customer.addStringProperty("uid").notNull().getProperty();
        customer.addStringProperty("minpix");
        customer.addStringProperty("maxpix");
        customer.addStringProperty("copyright");
        customer.addStringProperty("desc");

        Index index = new Index();
        index.addPropertyAsc(uidProperty);
        index.makeUnique();
        customer.addIndex(index);

        ContentProvider cp = customer.addContentProvider();
        cp.setJavaPackage("binggallery.chinacloudsites.cn");
        cp.setClassName("BingGalleryImageProvider");
        cp.setAuthority("binggallery.chinacloudsites.cn.BingGalleryImage");
        cp.setBasePath("BingGalleryImage");
    }

}
