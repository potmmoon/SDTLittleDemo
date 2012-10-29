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
			System.out.println("��" + i + "�غ�");
			tower.attack(attackers.get(0));
			for (int j = 0; j < attackers.size(); j++)
				attackers.get(j).attack(tower);
			if (!attackers.get(0).getAlive()) {
				attackers.remove(0);
				System.out
						.println("����"
								+ (attackersData.length / 2 - attackers.size())
								+ "����ɱ");
			}
			if (!tower.getAlive()) {
				System.out.println("tower������-----------ʧ��");
				return false;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("towerʤ����ʣ��Ѫ��Ϊ" + tower.getHealth()+"---------ʤ��");
		return true;
	}

	//AI Tower
	boolean smart_tower_defense(int H, int D, int[] attackersData){
		int[] smartAttackersData=changeAttackersToSmartOrder(attackersData,D);
		return tower_defense(H, D, smartAttackersData);
	}
	
	//AI ѡ�����ŵĹ�������
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
	 * ÿ�غ�����һ�������߽��й������ܵ����˺�
	 * @param h �����ߵ�Ѫ��
	 * @param d �����ߵĹ�����
	 * @param towerD ���Ĺ�����
	 * @return
	 */
	private int targetDamagePerRound(int h,int d,int towerD){
		return d/(h/towerD+1);
	}
	
	public static void main(String args[]) {
		TowerDefense game = new TowerDefense();
		System.out.println("��Ԫ����1----towerdefense--start");
		int H = 100;
		int D = 15;
		int[] attackersData = { 10, 5, 10, 10, 40, 5, 30, 10 };
		game.tower_defense(H, D, attackersData);
		System.out.println("��Ԫ����2----towerdefense--start---�ֶ�ѡȡ����Ŀ��");
		int[] attackersDataTwo = { 10, 10, 30, 10, 10, 5,40,5};
		game.tower_defense(H, D, attackersDataTwo);
		System.out.println("��Ԫ����3-��smartTower����������	AIѡ�񹥻�Ŀ��");
		game.smart_tower_defense(H, D, attackersData);
		System.out.println("��Ԫ����4-��ͬ���ھ������������������");
		int NewH=100;
		int NewD=20;
		int[] attackersDateThree={100,20};
		game.tower_defense(NewH, NewD, attackersDateThree);
	}
}
