package com.webtoonscorp.spring.domain;

public class Shape {

    private int point;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Shape() { }

    public Shape(int point) {
        setPoint(point);
    }
}
