package com.example.futuramagame;

import java.util.Objects;

public class Paquet {
    private float x, y;

    public Paquet(){};

    public Paquet(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paquet paquet = (Paquet) o;
        return Float.compare(paquet.x, x) == 0 &&
                Float.compare(paquet.y, y) == 0;
    }


}
