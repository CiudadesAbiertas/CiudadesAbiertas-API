package org.ciudadesabiertas.dataset.model;
 

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CubeDsdRelDimension generated by hbm2java
 */
@Entity
@Table(name = "cube_dsd_rel_dimension")
public class CubeDsdRelDimension implements java.io.Serializable {

	
	private static final long serialVersionUID = -1880266005492124450L;
	
	
	private String ikey;
	private CubeDsd cubeDsd;
	private CubeDsdDimension cubeDsdDimension;

	public CubeDsdRelDimension() {
	}

	public CubeDsdRelDimension(String ikey, CubeDsd cubeDsd, CubeDsdDimension cubeDsdDimension) {
		this.ikey = ikey;
		this.cubeDsd = cubeDsd;
		this.cubeDsdDimension = cubeDsdDimension;
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
	@JoinColumn(name = "dimension_key", nullable = false)
	public CubeDsdDimension getCubeDsdDimension() {
		return this.cubeDsdDimension;
	}

	public void setCubeDsdDimension(CubeDsdDimension cubeDsdDimension) {
		this.cubeDsdDimension = cubeDsdDimension;
	}

}
