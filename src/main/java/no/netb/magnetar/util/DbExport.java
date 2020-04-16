package no.netb.magnetar.util;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import javax.xml.validation.Schema;
import java.io.File;
import java.util.EnumSet;

public class DbExport {

//    public static String export() {
//        SchemaExport export = new SchemaExport();
//        File file = new File("create_tables.sql");
//        export.setOutputFile(file.getAbsolutePath());
//        export.setDelimiter(";");
//
//        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
//        SchemaExport.Action action = SchemaExport.Action.CREATE;
//
//        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .configure()
//        export.execute(targetTypes, action, metadata);
//    }
}
