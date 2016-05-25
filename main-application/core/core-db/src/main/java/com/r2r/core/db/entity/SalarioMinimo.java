package com.r2r.core.db.entity;

import com.r2r.core.db.DbConstant;
import com.r2r.core.db.entity.common.ModifTrackVersionableAbstract;
import com.r2r.core.db.exception.DatabaseConstraintIndex;
import com.r2r.core.db.exception.InterfaceConstraintViolated;
import com.r2r.core.util.Triple;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.joda.time.LocalDateTime;

@Entity
@Table(name = "SALARIO_MINIMO", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_SAL_MIN_DATE", columnNames = {"FECHA_APLICACION"})
})
@NamedQueries({
    //<editor-fold defaultstate="collapsed" desc="devalux-sl Queries">
    @NamedQuery(name = "SalarioMinimo.getForEdit", query = "SELECT sm FROM SalarioMinimo sm WHERE sm.id = :id"),
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sauza Queries">
    @NamedQuery(name = SalarioMinimo.GET_SALARIO_MINIMO, query = "SELECT a.cantidad FROM SalarioMinimo a where a.version = "
            + "(SELECT MAX(b.version) FROM SalarioMinimo b)"),
    @NamedQuery(name = SalarioMinimo.GET_MAX_VERSION, query = "SELECT MAX(a.version) FROM SalarioMinimo a"), //</editor-fold>
})
@XmlRootElement
public class SalarioMinimo extends ModifTrackVersionableAbstract<Short> {

    public static final String GET_MAX_VERSION = "getMaxVersion";
    public static final String GET_SALARIO_MINIMO = "getSalarioMinimo";

    static {
        DatabaseConstraintIndex.MAP_CONSTRAINTS.put("UNQ_SAL_MIN_DATE", new Triple<String, DatabaseConstraintIndex.ConstraintType, InterfaceConstraintViolated>("fechaAplicacion", DatabaseConstraintIndex.ConstraintType.UNIQUE, new InterfaceConstraintViolated<SalarioMinimo>() {
            @Override
            public Object[] getValues(SalarioMinimo entity) {
                return new Object[]{entity.getFechaAplicacion().toString("dd/MM/yyyy")};
            }

            @Override
            public String getLabel() {
                return "core.salMinExistsDate";
            }
        }
        ));
    }

    @Column(name = "FECHA_APLICACION", nullable = false)
    @Basic(optional = false)
    @NotNull
    private LocalDateTime fechaAplicacion;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "CANTIDAD", nullable = false, precision = 5, scale = 2)
    @Basic(optional = false)
    @Min(0)
    @Max(999)
    @NotNull
    private BigDecimal cantidad;

    public SalarioMinimo() {

    }

    public LocalDateTime getFechaAplicacion() {
        return this.fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDateTime fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    @Override
    public Short getId() {
        return this.id;
    }

    @Override
    public void setId(Short id) {
        this.id = id;
    }

    public BigDecimal getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

}
