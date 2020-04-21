package org.ciudadesabiertas.dataset.model;
 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Juan Carlos Ballesteros (Localidata)
 * @author Carlos Martínez de la Casa (Localidata)
 * @author Oscar Corcho (UPM, Localidata) 
 */
@Entity
@Table(name = "cube_dsd_rel_measure")
public class CubeDsdRelMeasure implements java.io.Serializable {
		
	@JsonIgnore
	private static final long serialVersionUID = 8338581075899180554L;
	
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private String ikey;
	
	private CubeDsd cubeDsd;
	
	private CubeDsdMeasure cubeDsdMeasure;

	public CubeDsdRelMeasure() {
	}

	public CubeDsdRelMeasure(String ikey, CubeDsd cubeDsd, CubeDsdMeasure cubeDsdMeasure) {
		this.ikey = ikey;
		this.cubeDsd = cubeDsd;
		this.cubeDsdMeasure = cubeDsdMeasure;
	}

	@Id

	@Column(name = "ikey", unique = true, nullable = false, length = 50)
	public String getIkey() {
		return this.ikey;
	}

	public void setIkey(String ikey) {
		this.ikey = ikey;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cube_key", nullable = false)
	public CubeDsd getCubeDsd() {
		return this.cubeDsd;
	}

	public void setCubeDsd(CubeDsd cubeDsd) {
		this.cubeDsd = cubeDsd;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "measure_key", nullable = false)
	public CubeDsdMeasure getCubeDsdMeasure() {
		return this.cubeDsdMeasure;
	}

	public void setCubeDsdMeasure(CubeDsdMeasure cubeDsdMeasure) {
		this.cubeDsdMeasure = cubeDsdMeasure;
	}

}
