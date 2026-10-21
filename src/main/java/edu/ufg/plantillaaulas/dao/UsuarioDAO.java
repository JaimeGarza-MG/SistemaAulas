package edu.ufg.plantillaaulas.dao;

import edu.ufg.plantillaaulas.entidades.Usuario;
import edu.ufg.plantillaaulas.util.ClaveUtil;
import edu.ufg.plantillaaulas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioDAO {

    public void prepararUsuarioDemo() {
        Transaction transaccion = null;

        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            transaccion = sesion.beginTransaction();

            Long cantidad = sesion.createQuery(
                    "select count(u) from Usuario u",
                    Long.class
            ).getSingleResult();

            if (cantidad == 0) {
                Usuario usuario = new Usuario(
                        "admin@ufg.edu.sv",
                        ClaveUtil.crearHash("Aulas2026!")
                );

                sesion.persist(usuario);
            }

            transaccion.commit();

        } catch (RuntimeException e) {
            if (transaccion != null && transaccion.isActive()) {
                transaccion.rollback();
            }

            throw e;
        }
    }

    public boolean autenticar(String correo, String clave) {
        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            Usuario usuario = sesion.createQuery(
                    "from Usuario u where u.correo = :correo",
                    Usuario.class
            )
                    .setParameter("correo", correo)
                    .uniqueResult();

            return usuario != null
                    && ClaveUtil.verificar(
                            clave, usuario.getClaveHash()
                    );
        }
    }
}