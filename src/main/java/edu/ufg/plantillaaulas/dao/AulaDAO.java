package edu.ufg.plantillaaulas.dao;

import edu.ufg.plantillaaulas.entidades.Aula;
import edu.ufg.plantillaaulas.entidades.EstadoAula;
import edu.ufg.plantillaaulas.entidades.TipoAula;
import edu.ufg.plantillaaulas.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AulaDAO {

    public List<Integer> obtenerPlantas() {
        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            return sesion.createNativeQuery(
                    "SELECT numero FROM plantas ORDER BY numero",
                    Integer.class
            ).getResultList();
        }
    }

    public List<TipoAula> obtenerTipos() {
        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            return sesion.createQuery(
                    "from TipoAula order by nombre",
                    TipoAula.class
            ).getResultList();
        }
    }

    public List<EstadoAula> obtenerEstados() {
        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            return sesion.createQuery(
                    "from EstadoAula order by nombre",
                    EstadoAula.class
            ).getResultList();
        }
    }

    public void guardar(Aula aula) {
        try (Session sesion = HibernateUtil
                .getSessionFactory().openSession()) {

            Transaction transaccion = sesion.beginTransaction();

            try {
                Long repetidas = sesion.createQuery(
                        "select count(a) from Aula a "
                        + "where a.nombre = :nombre "
                        + "and a.edificio = :edificio",
                        Long.class
                )
                        .setParameter("nombre", aula.getNombre())
                        .setParameter("edificio", aula.getEdificio())
                        .getSingleResult();

                if (repetidas > 0) {
                    throw new IllegalArgumentException(
                            "Ya existe un aula con ese nombre "
                            + "en el mismo edificio."
                    );
                }

                sesion.persist(aula);
                transaccion.commit();

            } catch (RuntimeException e) {
                if (transaccion.isActive()) {
                    transaccion.rollback();
                }

                throw e;
            }
        }
    }
}