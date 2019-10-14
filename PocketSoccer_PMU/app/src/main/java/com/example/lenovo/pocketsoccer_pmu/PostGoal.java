package com.example.lenovo.pocketsoccer_pmu;

import android.graphics.Bitmap;
import android.graphics.Paint;

public class PostGoal extends Ball {
    public PostGoal(Bitmap bitmap, float x, float y, float mass, Paint paint) {
        super(bitmap, x, y, mass, paint);
        speed = new Speed();

    }



    public void resolvePostCollision(Ball ball) {

        Vector normalVector = new Vector();
        float nvX = getX() - ball.getX();
        float nvY = getY() - ball.getY();

        normalVector.setX(nvX);
        normalVector.setY(nvY);

        Vector jedinicniVektorNormale = normalVector.normalize();
        Vector jedinicniVektorTangente = new Vector(-jedinicniVektorNormale.getY(),jedinicniVektorNormale.getX());

        // 2. skalarne vrednosti brzine

        float brzinaNormale1 = this.speed.getXv()*jedinicniVektorNormale.getX() + this.speed.getYv()*jedinicniVektorNormale.getY();
        float tangencijalnaBrzina1 = this.speed.getXv()*jedinicniVektorTangente.getX() + this.speed.getYv()*jedinicniVektorTangente.getY();
        float brzinaNormale2 = ball.speed.getXv()*jedinicniVektorNormale.getX() + ball.speed.getYv()*jedinicniVektorNormale.getY();
        float tangencijalnaBrzina2 = ball.speed.getXv()*jedinicniVektorTangente.getX() + ball.speed.getYv()*jedinicniVektorTangente.getY();


        float novaTangencijalnaBrzina2 = tangencijalnaBrzina2;


        float novaBrzinaNormale2 = (brzinaNormale2 * (ball.getMass() - getMass()) + 2 * getMass() * brzinaNormale1) / (getMass() + ball.getMass());



        float noviVektorNormaleX1 = jedinicniVektorNormale.getX()*novaBrzinaNormale2;
        float noviVektorNormaleY1 = jedinicniVektorNormale.getY()*novaBrzinaNormale2;
        Vector noviVektorNormale2 = new Vector(noviVektorNormaleX1, noviVektorNormaleY1);


        float noviVektorTangenteX1 = jedinicniVektorTangente.getX()*novaTangencijalnaBrzina2;
        float noviVektorTangenteY1 = jedinicniVektorTangente.getY()*novaTangencijalnaBrzina2;
        Vector noviVektorTangente2 = new Vector(noviVektorTangenteX1, noviVektorTangenteY1);



        float newBallVelocityX = noviVektorNormale2.getX()+ noviVektorTangente2.getX();
        float newBallVelocityY = noviVektorNormale2.getY() + noviVektorTangente2.getY();
        ball.speed.setXv(newBallVelocityX );
        ball.speed.setYv(newBallVelocityY );



    }
}

