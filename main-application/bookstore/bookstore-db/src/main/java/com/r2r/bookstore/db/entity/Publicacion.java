/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r2r.bookstore.db.entity;

import com.r2r.bookstore.db.DbConstant;
import com.r2r.core.db.entity.Usuario;
import com.r2r.core.db.entity.common.CatalogAbstract;
import com.r2r.core.db.entity.common.Status;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hustler
 */
@Entity
@Table(name = "PUBLICACION", schema = DbConstant.SCHEMA, uniqueConstraints = {
    @UniqueConstraint(name = "UNQ_PUBLICACION_NOMBRE", columnNames = {"NOMBRE"})})
@XmlRootElement
public class Publicacion extends CatalogAbstract<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;

    @Basic(optional = false)
    @NotNull
    @Size(min = 10, max = 13)
    @Column(name = "ISBN", nullable = false, length = 13)
    private String isbn;
    
    @Basic(optional = false)
    @NotNull
    @Size(max = 200)
    @Column(name = "URL_ARCHIVO", nullable = false, length = 200)
    private String urlArchivo;
    
    @NotNull
    @JoinColumn(name = "USUARIOALTA", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Usuario usuarioAlta;
        
    @NotNull
    @JoinColumn(name = "ID_TEMA", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Tema tema;
        
    @NotNull
    @JoinColumn(name = "ID_AUTOR", referencedColumnName = "ID", nullable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Autor autor;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EDITORIAL", referencedColumnName = "ID", nullable = false)
    private Editorial editorial;

    @Lob
    @Column(name = "RESUMEN", length = 500)
    private String resumen;
    
    @Column(name = "PRECIO", precision = 3, scale = 2)
    private BigDecimal precio;

    @Column(name = "DESCUENTO", precision = 3, scale = 2)
    private BigDecimal descuento;

    @Lob
    @Column(name = "PORTADA")
    private byte[] portada;
    
    @Column(name = "NUMERO_PAGINAS")
    private Short numeroPaginas;
    
    
    public Publicacion(){
    }
    
    public Publicacion(Integer id){
        this.id = id;
    }
    
    
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public byte[] getPortada() {
        return portada;
    }

    public void setPortada(byte[] portada) {
        this.portada = portada;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Short getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Short numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Usuario getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(Usuario usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }
    
    @PrePersist
    public void prePersist() {
        estatus = Status.ENABLED;
        if(usuarioAlta == null){
            usuarioAlta = getUModif().getUsuarioUModif();
        }
    }

}
