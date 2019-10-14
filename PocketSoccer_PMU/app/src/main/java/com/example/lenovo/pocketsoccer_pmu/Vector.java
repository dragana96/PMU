package com.example.lenovo.pocketsoccer_pmu;

public class Vector {

    private float x;
    private float y;

    public Vector() {
            this.x = 0;
            this.y = 0;
    }

    public Vector(float x, float y)
    {
        this.setX(x);
        this.setY(y);
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLength()
    {
        return (float)Math.sqrt(getX()*getX() + getY()*getY());
    }

    public Vector normalize()
    {
        Vector result = new Vector();
        float len = getLength();
        if (len != 0.0f)
        {
            result.setX(this.getX() / len);
            result.setY(this.getY() / len);
        }
        else
        {
            result.setX(0.0f);
            result.setY(0.0f);
        }

        return result;
    }

}
