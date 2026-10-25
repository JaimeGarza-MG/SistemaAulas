#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
module ${package} {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires org.mariadb.jdbc;

    opens ${package} to javafx.fxml;
    opens ${package}.controladores to javafx.fxml;
    opens ${package}.entidades to org.hibernate.orm.core;

    exports ${package};
}