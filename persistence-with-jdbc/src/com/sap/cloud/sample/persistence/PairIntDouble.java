package com.sap.cloud.sample.persistence;

public class PairIntDouble
{
    private final Integer anInt;
    private final Double aDouble;

    public PairIntDouble(Integer anInt, Double aDouble)
    {
        this.anInt   = anInt;
        this.aDouble = aDouble;
    }

    public Integer first()   { return anInt; }
    public Double second() { return aDouble; }
}