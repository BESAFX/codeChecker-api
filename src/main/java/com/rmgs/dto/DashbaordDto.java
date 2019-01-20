package com.rmgs.dto;

import java.util.List;

import com.rmgs.dto.Facet;

public class DashbaordDto {
	private Paging paging;
	private List<Component> components;
	private List<Facet> facets;
	public List<Facet> getFacets() {
		return facets;
	}
	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}
	public Paging getPaging() {
		return paging;
	}
	public void setPaging(Paging paging) {
		this.paging = paging;
	}
	public List<Component> getComponents() {
		return components;
	}
	public void setComponents(List<Component> components) {
		this.components = components;
	}
	
	
}
