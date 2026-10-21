module edu.ufg.plantillaaulas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.mariadb.jdbc;

    opens edu.ufg.plantillaaulas to javafx.fxml;
    opens edu.ufg.plantillaaulas.controladores to javafx.fxml;
    opens edu.ufg.plantillaaulas.entidades to org.hibernate.orm.core;

    exports edu.ufg.plantillaaulas;
}