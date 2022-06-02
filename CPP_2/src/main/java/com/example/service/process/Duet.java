package com.example.service.process;

public class Duet {
    public Duet (String _number, Integer _system) {
        number = _number;
        system = _system;
    }
    private String number;
    public String getNumber(){
        return number;
    }
    private Integer system;
    public Integer getSystem(){
        return system;
    }
}
