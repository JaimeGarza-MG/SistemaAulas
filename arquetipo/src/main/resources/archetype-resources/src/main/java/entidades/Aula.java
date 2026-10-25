#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "aulas",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_aula_edificio",
                columnNames = {"nombre", "edificio"}
        )
)
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 80)
    private String nombre;

    @Column(nullable = false, length = 80)
    private String edificio;

    @Column(nullable = false)
    private int planta;

    @Column(nullable = false)
    private int capacidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_aula_id", nullable = false)
    private TipoAula tipoAula;

    @Column(nullable = false, length = 150)
    private String responsable;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estado_aula_id", nullable = false)
    private EstadoAula estado;

    public Aula() {
    }

    public Aula(
            String nombre,
            String edificio,
            int planta,
            int capacidad,
            TipoAula tipoAula,
            String responsable,
            EstadoAula estado) {

        this.nombre = nombre;
        this.edificio = edificio;
        this.planta = planta;
        this.capacidad = capacidad;
        this.tipoAula = tipoAula;
        this.responsable = responsable;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEdificio() {
        return edificio;
    }

    public int getPlanta() {
        return planta;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public TipoAula getTipoAula() {
        return tipoAula;
    }

    public String getResponsable() {
        return responsable;
    }

    public EstadoAula getEstado() {
        return estado;
    }
}