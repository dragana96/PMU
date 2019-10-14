package com.example.lenovo.pocketsoccer_pmu;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
    private Bitmap bitmap; // the actual bitmap
    private float x;   // the X coordinate
    private float y;   // the Y coordinate
    private float radius;
    private float mass;
    private Paint paint;
    // we need to add some state
    private boolean touched;
    private int player;
    private boolean madeMove;

    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    // ball has its speed
    public Speed speed;

    public Ball(Bitmap bitmap, float x, float y, float mass, Paint paint) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
        this.mass = mass;
        this.radius = bitmap.getWidth()/2;
        this.paint = paint;
        this.player = 0;
        this.madeMove = false;
    }

    public boolean isMadeMove() {
        return madeMove;
    }

    public void setMadeMove(boolean madeMove) {
        this.madeMove = madeMove;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return paint;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public Speed getSpeed() {
        return speed;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
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

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, x - (float)((bitmap.getWidth() / 2)*1.1), y -(float)((bitmap.getWidth() / 2)*1.1), this.paint);
    }

    public boolean handleActionDown(int eventX, int eventY) {

        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2))) {
                // droid touched


               if(AppConstants.getSelectedValues().getMadeMove() == false){
                   setTouched(true);
                   AppConstants.getSelectedValues().setSelectedForPlaying(this);
                   AppConstants.getSelectedValues().setMadeMove(true);
               }
               return true;

            } else {
                setTouched(false);
                return false;
            }
        } else {
            setTouched(false);
            return false;
        }

    }
    public float getRadius() {
        return this.radius;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void update() {
        if (x - radius < 0) {
            setX(radius);
            speed.setXv(-speed.getXv());

        } else if (x + radius > screenWidth) {
            setX(screenWidth - radius);
            speed.setXv(-speed.getXv());
        }


        if (y - radius < 0) {
            setY(radius);
            speed.setYv(-speed.getYv());
        } else if (y + radius > screenHeight) {
            setY(screenHeight - radius);
            speed.setYv(-speed.getYv());
        }


        //if (!touched) {
            x += (speed.getXv() * speed.getxDirection());
            y += (speed.getYv() * speed.getyDirection());
            speed.setXv(speed.getXv()*0.98f);
            speed.setYv(speed.getYv()*0.98f);
        //}





    }



    public float getDistance(float x1, float y1)
    {
        return (float) Math.sqrt((x1 - getX()) * (x1 - getX()) + (y1 - getY()) * (y1 - getY()));
    }


    public void resolveCollision(Ball ball){

        // vracamo loptu jedan korak unazad (kao da se nije pomerila)
        // time smanjujemo koliko ulazi u drugu loptu
        setX(getX() - speed.getXv());
        setY(getY() - speed.getYv());

        // 1. jedinicni vektori normale i tangente

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

        float novaTangencijalnaBrzina1 = tangencijalnaBrzina1;
        float novaTangencijalnaBrzina2 = tangencijalnaBrzina2;

        float novaBrzinaNormale1 = (brzinaNormale1 * (mass - ball.getMass()) + 2 * ball.getMass() * brzinaNormale2) / (mass + ball.getMass());
        float novaBrzinaNormale2 = (brzinaNormale2 * (ball.getMass() - mass) + 2 * mass * brzinaNormale1) / (mass + ball.getMass());

        float noviVektorNormaleX = jedinicniVektorNormale.getX()*novaBrzinaNormale1;
        float noviVektorNormaleY = jedinicniVektorNormale.getY()*novaBrzinaNormale1;
        Vector noviVektorNormale1 = new Vector(noviVektorNormaleX, noviVektorNormaleY);

        float noviVektorNormaleX1 = jedinicniVektorNormale.getX()*novaBrzinaNormale2;
        float noviVektorNormaleY1 = jedinicniVektorNormale.getY()*novaBrzinaNormale2;
        Vector noviVektorNormale2 = new Vector(noviVektorNormaleX1, noviVektorNormaleY1);

        float noviVektorTangenteX = jedinicniVektorTangente.getX()*novaTangencijalnaBrzina1;
        float noviVektorTangenteY = jedinicniVektorTangente.getY()*novaTangencijalnaBrzina1;
        Vector noviVektorTangente1 = new Vector(noviVektorTangenteX, noviVektorTangenteY);

        float noviVektorTangenteX1 = jedinicniVektorTangente.getX()*novaTangencijalnaBrzina2;
        float noviVektorTangenteY1 = jedinicniVektorTangente.getY()*novaTangencijalnaBrzina2;
        Vector noviVektorTangente2 = new Vector(noviVektorTangenteX1, noviVektorTangenteY1);

        float newVelocityX = noviVektorNormale1.getX()+ noviVektorTangente1.getX();
        float newVelocityY = noviVektorNormale1.getY() + noviVektorTangente1.getY();

            this.speed.setXv(newVelocityX);
            this.speed.setYv(newVelocityY);


        float newBallVelocityX = noviVektorNormale2.getX()+ noviVektorTangente2.getX();
        float newBallVelocityY = noviVektorNormale2.getY() + noviVektorTangente2.getY();
        if(!(ball instanceof PostGoal)) {
            ball.speed.setXv(newBallVelocityX);
            ball.speed.setYv(newBallVelocityY);
        }
        float deltaX = x-ball.getX();
        float deltaY = y-ball.getY();
        Vector delta = new Vector(deltaX,deltaY);

        float deltaLength = delta.getLength();

        float valueForMultypling = (getRadius()+ball.getRadius() - deltaLength)/deltaLength;
        float newDeltaX = delta.getX()*valueForMultypling;
        float newDeltaY = delta.getY()*valueForMultypling;

        Vector mtdVector = new Vector(newDeltaX, newDeltaY);

        float newvX = this.speed.getXv()-ball.speed.getXv();
        float newvY = this.speed.getYv()-ball.speed.getYv();
        Vector v = new Vector(newvX,newvY);

        Vector mtdNormalised = mtdVector.normalize();

        float vn = v.getX()*mtdNormalised.getX() + v.getY()*mtdNormalised.getY();

        if (vn > 0.0f) return;

        float distanca = getDistance(ball.getX(), ball.getY());
        if (distanca <= getRadius() + ball.getRadius()) {
            float a = x - ball.getX();
            float b = speed.getXv();
            float c = y - ball.getY();
            float d = speed.getYv();
            float e = (radius + ball.getRadius()) * (radius + ball.getRadius());



            double distance = (Math.sqrt(((d * d) + (b * b)) * e - (a * a) * (d * d) + 2 * a * b * c * d - (b * b) * (c * c)) - c * d - a * b) / ((d * d) + (b * b));


            Vector newVector = new Vector ((float)(distance*speed.getXv()),(float) (distance*speed.getYv()));
            Vector newVector1 = new Vector ((float)(distance*ball.speed.getXv()),(float) (distance*ball.speed.getYv()));
            Vector add = new Vector(x+newVector.getX(), y+newVector.getY());
            Vector sub = new Vector(ball.getX() - newVector1.getX(), ball.getY() - newVector1.getY());


            float distance1 = (float) Math.sqrt((add.getX() - getX()) * (add.getX() - getX()) + (add.getY() - getY()) * (add.getY() - getY()));
            float distance2 = (float) Math.sqrt((sub.getX() - getX()) * (sub.getX() - getX()) + (sub.getY() - getY()) * (sub.getY() - getY()));


            float distanceBall2 = (float) Math.sqrt((sub.getX() - ball.getX()) * (sub.getX() - ball.getX()) + (sub.getY() - ball.getY()) * (sub.getY() - ball.getY()));
            if (distance1 < distance2 && distance1 < getRadius()) {

                x = add.getX();
                y = add.getY();

            } else if (distanceBall2 < ball.getRadius()) {

                ball.setX(sub.getX());
                ball.setY(sub.getY());

            } else {
                if (distance1 < distanceBall2) {
                    x = add.getX();
                    y = add.getY();
                } else {

                    ball.setX(sub.getX());
                    ball.setY(sub.getY());
                }
            }
        }


    }





}
