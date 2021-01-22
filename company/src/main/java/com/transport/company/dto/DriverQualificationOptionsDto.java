package com.transport.company.dto;

import com.transport.company.entity.DriverQualificationEnum;

import java.util.HashMap;
import java.util.Map;

public class DriverQualificationOptionsDto {
    private final Map<DriverQualificationEnum, String> options;

    public DriverQualificationOptionsDto(){
        options = new HashMap<>();
        for(var value : DriverQualificationEnum.values()){
            options.put(value, value.toString());
        }
    }

    public Map<DriverQualificationEnum, String> getOptions(){
        return options;
    }

}
