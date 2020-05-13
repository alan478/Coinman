package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

import sun.rmi.runtime.Log;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bsckground;
	Texture[] man;
	int stae=0;
	int pause=0;
	float velocity=0;
	float gravity= (float) 0.2;
	float ManY=0;
	ArrayList<Rectangle> coinsRectangles = new ArrayList<>();
	ArrayList<Rectangle> bombsRectangles = new ArrayList<>();
	Rectangle manRectangle;

	ArrayList<Integer> coinXs =new ArrayList<>();
	ArrayList<Integer> coinYs= new ArrayList<>();
	ArrayList<Integer> bombx= new ArrayList<>();
	ArrayList<Integer>bomby=new ArrayList<>();
	Texture coins;
	int coinCount;
	Random random= new Random();
	Texture bombs;
	int bombcount=0;
	int score=0;
	BitmapFont scores;
	int gameState=0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		bsckground = new Texture("bg.png");
        man= new Texture[4];
        man[0]=new Texture("frame-1.png");
        man[1]=new Texture("frame-2.png");
        man[2]=new Texture("frame-3.png");
        man[3]=new Texture("frame-4.png");
        ManY=Gdx.graphics.getHeight()/2;

        coins = new Texture("coin.png");
        bombs = new Texture("bomb.png");
       scores= new BitmapFont();
       scores.setColor(Color.BLACK);
       scores.getData().setScale(10);


	}


	public void makeCoin()
	{
		float height = random.nextFloat()*Gdx.graphics.getHeight();

		coinYs.add((int) height);
		coinXs.add(Gdx.graphics.getWidth());
	}
	public void makebomb()
	{

		float bomb_height = random.nextFloat()*Gdx.graphics.getHeight();

		bombx.add(Gdx.graphics.getWidth());
		bomby.add((int) bomb_height);
	}


	@Override
	public void render () {
		batch.begin();
		batch.draw(bsckground,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState==1)

		{

			if(coinCount<100){
				coinCount++;
			}else{
				coinCount=0;
				makeCoin();
			}

			coinsRectangles.clear();
			for(int i=0;i<coinXs.size();i++)
			{
				batch.draw(coins, coinXs.get(i), coinYs.get(i));
				coinXs.set(i,coinXs.get(i)-4);
				coinsRectangles.add(new Rectangle(coinXs.get(i),coinYs.get(i),coins.getWidth(),coins.getHeight()));
			}

			if(bombcount<200)
			{
				bombcount++;
			}
			else{
				bombcount=0;
				makebomb();
			}

			bombsRectangles.clear();
			for(int i=0;i<bombx.size();i++){
				batch.draw(bombs,bombx.get(i),bomby.get(i));
				bombx.set(i,bombx.get(i)-8);
				bombsRectangles.add(new Rectangle(bombx.get(i),bomby.get(i),bombs.getWidth(),bombs.getHeight()));


			}
			if(Gdx.input.justTouched())
			{
				velocity=-10;
			}




			if(ManY<=0)
			{
				ManY=0;
			}

			if(pause<3)	{
				pause++;
			}else{
				pause=0;
				if (stae < 3) {
					stae++;


				}else{
					stae=0;
				}

			}

			velocity+=gravity;
			ManY-=velocity;




		}else if(gameState==0)
		{
			if(Gdx.input.justTouched())
			{
				gameState=1;
			}

		}else if(gameState==2)
		{
			if (Gdx.input.justTouched())
			{
				gameState=1;
				ManY=Gdx.graphics.getHeight()/2;
				score=0;
				velocity=0;
				coinXs.clear();
				coinYs.clear();
				coinsRectangles.clear();
				coinCount=0;
				bombx.clear();
				bomby.clear();
				bombsRectangles.clear();
				bombcount=0;
			}

		}






		batch.draw(man[stae],Gdx.graphics.getWidth()/2-man[stae].getWidth()/2, ManY);
        manRectangle= new Rectangle(Gdx.graphics.getWidth()/2-man[stae].getWidth()/2,ManY,man[stae].getWidth(),man[stae].getHeight());
        for(int i=0; i < coinsRectangles.size();i++)
		{
			if(Intersector.overlaps(manRectangle, coinsRectangles.get(i)))
			{
				score++;
				coinsRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;

			}
		}
		for(int i=0; i < bombsRectangles.size();i++)
		{
			if(Intersector.overlaps(manRectangle, bombsRectangles.get(i)))
			{
              gameState=2;

			}
		}

		scores.draw(batch,String.valueOf(score),100,200);

		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
