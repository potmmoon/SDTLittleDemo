package com.game;

import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.DATA_CONVERSION;

import com.game.model.Army;
import com.game.model.Attacker;
import com.game.model.Tower;

public class TowerDefense {

	private Army tower;
	private List<Army> attackers;

	public TowerDefense() {
	}

	public void init(int H, int D, int[] attackersData) {
		tower = new Tower(H, D);
		attackers = new ArrayList<Army>();
		for (int i = 0; i < attackersData.length; i += 2) {
			Army attacker = new Attacker(attackersData[i], attackersData[i + 1]);
			attackers.add(attacker);
		}
	}

	// tower kill attackers by order
	boolean tower_defense(int H, int D, int[] attackersData) {
		init(H, D, attackersData);
		int i = 0;
		while (attackers.size() > 0) {
			i++;
			System.out.println("第" + i + "回合");
			tower.attack(attackers.get(0));
			for (int j = 0; j < attackers.size(); j++)
				attackers.get(j).attack(tower);
			if (!attackers.get(0).getAlive()) {
				attackers.remove(0);
				System.out
						.println("敌人"
								+ (attackersData.length / 2 - attackers.size())
								+ "被击杀");
			}
			if (!tower.getAlive()) {
				System.out.println("tower被攻破-----------失败");
				return false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("tower胜利：剩余血量为" + tower.getHealth()+"---------胜利");
		return true;
	}

	//AI Tower
	boolean smart_tower_defense(int H, int D, int[] attackersData){
		int[] smartAttackersData=changeAttackersToSmartOrder(attackersData,D);
		return tower_defense(H, D, smartAttackersData);
	}
	
	//AI 选择最优的攻击方案
	private int[] changeAttackersToSmartOrder(int[] Data,int towerD){
		int count=Data.length;
		int i,j;
		for(i=2;i<count;i+=2){
			int tempH=Data[i];
			int tempD=Data[i+1];
			int tempDamagePerRound=targetDamagePerRound(tempH, tempD, towerD);
			for(j=i;j>=2&&targetDamagePerRound(Data[j-2], Data[j-1], towerD)<tempDamagePerRound;j-=2){
				Data[j]=Data[j-2];
				Data[j+1]=Data[j-1];
			}
			Data[j]=tempH;
			Data[j+1]=tempD;
		}
		return Data;
	}

	/**
	 * 每回合塔对一个攻击者进行攻击所受到的伤害
	 * @param h 攻击者的血量
	 * @param d 攻击者的攻击力
	 * @param towerD 塔的攻击力
	 * @return
	 */
	private int targetDamagePerRound(int h,int d,int towerD){
		return d/(h/towerD+1);
	}
	
	public static void main(String args[]) {
		TowerDefense game = new TowerDefense();
		System.out.println("单元测试1----towerdefense--start");
		int H = 100;
		int D = 15;
		int[] attackersData = { 10, 5, 10, 10, 40, 5, 30, 10 };
		game.tower_defense(H, D, attackersData);
		System.out.println("单元测试2----towerdefense--start---手动选取攻击目标");
		int[] attackersDataTwo = { 10, 10, 30, 10, 10, 5,40,5};
		game.tower_defense(H, D, attackersDataTwo);
		System.out.println("单元测试3-－smartTower－－－智能	AI选择攻击目标");
		game.smart_tower_defense(H, D, attackersData);
		System.out.println("单元测试4-－同归于尽－－结果还是塔方输");
		int NewH=100;
		int NewD=20;
		int[] attackersDateThree={100,20};
		game.tower_defense(NewH, NewD, attackersDateThree);
	}
}
