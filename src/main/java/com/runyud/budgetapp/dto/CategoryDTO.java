package com.runyud.budgetapp.dto;

public class CategoryDTO {
	private String value;
	private String label;

	public CategoryDTO() {

	}

	public CategoryDTO(String value, String label) {
		this.value = value;
		this.label = label;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
