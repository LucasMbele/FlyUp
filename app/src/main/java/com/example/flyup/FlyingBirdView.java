package com.example.flyup;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import static android.os.Build.VERSION_CODES.O;


//This view will be used as a view of our PlayFragment

public class FlyingBirdView extends View {
     //DIFFERENT LEVELS
    private final static int MAX_SCORE_LEVEL_1 = 106;
    private final static int MAX_SCORE_LEVEL_2 = 210;
    private final static int MAX_SCORE_LEVEL_3 = 315;
    private final static int MAX_SCORE_LEVEL_4 = 420;
    private final static int MAX_SCORE_LEVEL_5 = 525;

    //Life, Initial Score
    private int Score = 0;
    private int index_level = 1;
    private int life_count = 3;
    //Canvas Dimension
    int canvasHeight;
    int canvasWidth;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }
//Bird dimensions

    int birdY, birdX = 10, birdSpeed;

    //Coin Speed
    int coinSpeed = 15;

    //Ball
    //int ballSpeed = 15;
    int ball_X[] = new int[10];
    int ball_Y[] = new int[10];
    int coin_X[] = new int[10];
    int coin_Y[] = new int[10];


    private boolean touchFlag = false;

    //Bird
    private Bitmap bird[] = new Bitmap[2];

    //Background
    private Bitmap background;

    //Rewards
    private Bitmap coin;

    //Score
    private Paint PaintScore = new Paint();

    private Paint black_ball = new Paint();
    private Paint red_ball = new Paint();

    //Level
    private Paint level = new Paint();

    //Heart
    private Bitmap Life[] = new Bitmap[2];

    public FlyingBirdView(Context context) {

        super(context);
        // We draw our bird
        bird[0] = BitmapFactory.decodeResource(getResources(), R.drawable.rsz_bird_2);
        bird[1] = BitmapFactory.decodeResource(getResources(), R.drawable.rsz_bird_1);


        //We draw our coin

        coin = BitmapFactory.decodeResource(getResources(), R.drawable.rsz_coin);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.on_road);

           //VERSION CHECKING (OREO)
         if(Build.VERSION.SDK_INT >= O)
         {
             PaintScore.setColor(Color.BLACK);
             PaintScore.setTextSize(30);
             PaintScore.setTypeface(Typeface.DEFAULT_BOLD);
             PaintScore.setAntiAlias(true);

             level.setColor(Color.BLACK);
             level.setTextSize(30);
             level.setTypeface(Typeface.DEFAULT_BOLD);
             level.setTextAlign(Paint.Align.CENTER);
             level.setAntiAlias(true);
         }
         else {
             PaintScore.setColor(Color.BLACK);
             PaintScore.setTextSize(60);
             PaintScore.setTypeface(Typeface.DEFAULT_BOLD);
             PaintScore.setAntiAlias(true);
             level.setColor(Color.BLACK);
             level.setTextSize(60);
             level.setTypeface(Typeface.DEFAULT_BOLD);
             level.setTextAlign(Paint.Align.CENTER);
             level.setAntiAlias(true);

         }

       //OPPONENT
        black_ball.setColor(Color.BLACK);
        black_ball.setAntiAlias(false);
        red_ball.setColor(Color.RED);
        red_ball.setAntiAlias(false);



        Life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
        Life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_g);


    }
     //GETTER AND SETTER
    public int getLife_count() {
        return life_count;
    }

    public void setLife_count(int life_count) {
        this.life_count = life_count;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Drawing our background
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        //Let's draw our background
        canvas.drawBitmap(background, 0, 0, null);


        final int minBirdY = bird[0].getHeight();
        final int maxBirdY = canvasHeight - bird[0].getHeight() * 3;
        // SPEED
        birdY += birdSpeed;
        if (birdY < minBirdY) birdY = minBirdY;
        if (birdY > maxBirdY) birdY = maxBirdY;
        birdSpeed += 2;

        //Level 1
        if (getScore() < MAX_SCORE_LEVEL_5) {
            //COORD COINS (X,Y) AND COORDS BLACK BALLS (W,Z)
            setGame(0,1,0,3,15,1,canvas);

        }
        //Level 2
        if (getScore() >= MAX_SCORE_LEVEL_1 && getScore() < MAX_SCORE_LEVEL_5)
        {
            setGame(1,2,3,5,20,2,canvas);

        }
        //Level 3
        if(getScore()>=MAX_SCORE_LEVEL_2 && getScore() <MAX_SCORE_LEVEL_5)
        {
            setGame(2,3,5,7,30,3,canvas);
        }
        if(getScore() >=MAX_SCORE_LEVEL_3 && getScore() < MAX_SCORE_LEVEL_5)
        {
            setGame(3,4,7,9,50,4,canvas);
        }
        //Level 5
        if(getScore() == MAX_SCORE_LEVEL_4 && getScore() == MAX_SCORE_LEVEL_5)
        {
            setGame(4,5,9,11,60,4,canvas);
        }
        //Life Count
        for (int i = 0; i < 3; i++) {
            //About changing positions lulu's lives

            if(Build.VERSION.SDK_INT >= O)
            {
                int levelX = (int) (500 + Life[0].getWidth() * 1.5 * i);
                int levelY = 20;

                if (i < life_count) {
                    //Lulu kept his lives
                    canvas.drawBitmap(Life[0], levelX, levelY, null);
                } else {
                    //Lost live
                    canvas.drawBitmap(Life[1], levelX, levelY, null);
                }
            }
            else
            {
                int levelX = (int) (760 + Life[0].getWidth() * 1.5 * i);
                int levelY = 20;

                if (i < life_count) {
                    //Lulu kept his lives
                    canvas.drawBitmap(Life[0], levelX, levelY, null);
                } else {
                    //Lost live
                    canvas.drawBitmap(Life[1], levelX, levelY, null);
                }

            }
        }


        if (touchFlag) {
            //Flap wings
            canvas.drawBitmap(bird[1], birdX, birdY, null);
            touchFlag = false;
        } else {

            canvas.drawBitmap(bird[0], birdX, birdY, null);
        }
        canvas.drawText("Score: " + Score, 20, 60, PaintScore);
        canvas.drawText("Lv." + index_level, canvasWidth / 2, 60, level);

    }



    public void setGame(int x, int y,int w, int z, int speedBall,int level,Canvas canvas)
    {
        index_level = level;
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();


        final int minBirdY = bird[0].getHeight();
        final int maxBirdY = canvasHeight - bird[0].getHeight() * 3;
        for (int i = x; i < y; i++)
        {
            coin_X[i] -= coinSpeed;
            if (coin_X[i] < 0)
            {
                coin_X[i] = canvasWidth + 20*(2*(i+1));
                coin_Y[i] = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
            }
            canvas.drawBitmap(coin, coin_X[i], coin_Y[i], null);
            //Check if "Lulu" touched coin
            if (hitBird(coin_X[i], coin_Y[i]))
            {
                Score += 15;
                setScore(Score);
                coin_X[i] = -100;
            }
        }
        for (int i = w; i < z; i++) {

            ball_X[i] -= speedBall;

            if (ball_X[i] < 0) {
                ball_X[i] = canvasWidth + 50 * (5 * (i + 1));
                ball_Y[i] = (int) Math.floor(Math.random() * (maxBirdY - minBirdY)) + minBirdY;
            }

            canvas.drawCircle(ball_X[i], ball_Y[i], 30, black_ball);
            //canvas.drawCircle(1100, 700, 35, black_ball);
            Log.i("X_value", "Value:" + ball_X[i]);
            Log.i("Y_value", "Value:" + ball_Y[i]);

            if (hitBird(ball_X[i], ball_Y[i])) {
                ball_X[i] = -100;
                life_count--;
                setLife_count(life_count);
                Log.i("life_count_fly", "Life count is :" + getLife_count());
                if (life_count == 0) {
                    Log.i("Game", "Game_over");
                    //End of Game, Calling A fragment
                }
            }
        }

    }

   /*
    }*/

    private boolean hitBird(int x, int y) {

        if (birdX < x && x < (birdX + bird[0].getWidth()) && birdY < y && y < (birdY + bird[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchFlag = true;
            birdSpeed = -20;
        }
        return true;
    }


}
