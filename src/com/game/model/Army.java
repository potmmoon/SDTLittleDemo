package com.game.model;

/**
 * 
 * @author hcy
 *
 */
public class Army {

	private int health;
	private int damage;
	private boolean isAlive=true;;
	public Army(int health,int damage){
		this.health=health;
		this.damage=damage;
	}
	
	public void attack(Army army){
		army.setHealth(army.getHealth()-this.damage);
		if(army.getHealth()<=0){
			army.isAlive=false;
		}
	}
	
	public int getHealth(){
		return this.health;
	}
	public int getDamage(){
		return this.damage;
	}
	public void setHealth(int health){
		this.health=health;
	}
	
	public void setAlive(boolean alive){
		this.isAlive=alive;
	}
	
	public boolean getAlive(){
		return isAlive;
	}
}
