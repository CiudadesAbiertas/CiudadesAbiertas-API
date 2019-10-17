package org.ciudadesAbiertas.rdfGeneratorZ;


public class Parameter {
	private String name;
	private String description = "";
	private String paramType;
	private String defaultValue;
	private boolean required;
	private boolean allowMultiple;
	private String dataType;
	private String schema;
	private String testValue;
	private String[] allowedValues;
	
//	private AllowableValues allowableValues;
	
	public Parameter() {
		
	}
	
	public String[] getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(String[] allowedValues) {
		this.allowedValues = allowedValues;
	}

	public Parameter(String name, String description, String dataType, String paramType){
		super();
		this.name = name;
		this.description = description;
		this.dataType = dataType;
		this.paramType = paramType;
	}
	
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
	public boolean getRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean getAllowMultiple() {
		return allowMultiple;
	}
	public void setAllowMultiple(boolean allowMultiple) {
		this.allowMultiple = allowMultiple;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}


	public String getDefaultValue() {
		return defaultValue;
	}


	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

//	public AllowableValues getAllowableValues() {
//		return allowableValues;
//	}
//
//	public void setAllowableValues(AllowableValues allowableValues) {
//		this.allowableValues = allowableValues;
//	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	@Override
	public String toString() {
		return "Parameter [name=" + name + ", description=" + description
				+ ", paramType=" + paramType + ", defaultValue=" + defaultValue
				+ ", required=" + required + ", allowMultiple=" + allowMultiple
				+ ", dataType=" + dataType 
//				+ ", allowableValues=" + allowableValues 
				+ "]";
	}
	
	

}
