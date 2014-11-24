package com.rhefew.cocdrive;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animate {

    public static void Alpha(View view, Context ctx, int duracionMS){

        Animation alpha = AnimationUtils.loadAnimation(ctx, R.anim.alpha);
        alpha.reset();
        alpha.setDuration(duracionMS);
        view.startAnimation(alpha);
    }

    public static void AlphaOut(View view, Context ctx, int duracionMS){

        Animation transparente = AnimationUtils.loadAnimation(ctx, R.anim.alphaout);
        transparente.reset();
        transparente.setDuration(duracionMS);
        view.startAnimation(transparente);
    }

    public static void Rotate(View view, Context ctx){

        Animation rotacion = AnimationUtils.loadAnimation(ctx, R.anim.rotar);
        rotacion.reset();
        view.startAnimation(rotacion);
    }

    public static void Traslate(View view, Context ctx, int duracion, Direction dir){

        Animation movimiento = null;
        switch(dir){
            case Down:
                movimiento = AnimationUtils.loadAnimation(ctx, R.anim.mover_abajo);break;
            case Up:
                movimiento = AnimationUtils.loadAnimation(ctx, R.anim.mover_arriba);break;
            case Left:
                movimiento = AnimationUtils.loadAnimation(ctx, R.anim.mover_izquierda);break;
            case Right:
                movimiento = AnimationUtils.loadAnimation(ctx, R.anim.mover_derecha);break;
            default:
                break;

        }

        movimiento.reset();
        movimiento.setDuration(duracion);
        view.startAnimation(movimiento);
    }

    public static void TraslateOut(View view, Context ctx, boolean toRight, int startAfterSeconds){

        Animation movimiento = AnimationUtils.makeOutAnimation(ctx, toRight);
        movimiento.setStartTime(startAfterSeconds);
        movimiento.reset();
        view.startAnimation(movimiento);
    }

    public static void TraslateIn(View view, Context ctx, boolean toLeft, int startAfterSeconds){

        Animation movimiento = AnimationUtils.makeInAnimation(ctx, toLeft);
        movimiento.setStartTime(startAfterSeconds);
        movimiento.reset();
        view.startAnimation(movimiento);
    }

    public static void Scale(View view, Context ctx){

        Animation escalado = AnimationUtils.loadAnimation(ctx, R.anim.escalar);
        escalado.reset();
        view.startAnimation(escalado);
    }

    public static enum Direction {
        Up,
        Down,
        Left,
        Right,
    }
}