package com.transport.company.dto;

import com.transport.company.entity.Freight;

import java.util.ArrayList;
import java.util.List;

public class FreightCreationDto {

    private List<Freight> freights;

    public FreightCreationDto() {
        freights = new ArrayList<Freight>();
    }

    public FreightCreationDto(Freight freight) {
        this.freights = new ArrayList<Freight>();
        this.freights.add(freight);
    }

    public FreightCreationDto(List<Freight> freights) {
        this.freights = freights;
    }

    public void addFreight(Freight freight) {
        this.freights.add(freight);
    }

    public void setFreights(List<Freight> freights) {
        this.freights = freights;
    }

    public List<Freight> getFreights() {
        return freights;
    }
}
