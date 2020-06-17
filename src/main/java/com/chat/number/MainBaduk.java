import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Baduk;

public class MainBaduk {
	static int DEFAULT_NUM = 6;
	static Map<String, String> edgeMap = new HashMap<String, String>();
	static String TYPE_CHECK = "B";
	
	public static void main(String[] args) {

		
		/*
		 * B : BLACK
		 * W : WHITE
		 * K : BLANK
		 * L : BLOCK
		 * BL : BLACK BLOCK
		 * WL : WHITE BLOCK
		 */
		ArrayList<Baduk> list = new ArrayList<Baduk>();
		Baduk baduk = null;
		
		int width = 0;
		int height = 0;
		
		for (int i = 0; i < DEFAULT_NUM*DEFAULT_NUM; i++) {
			baduk = new Baduk();
			baduk.setType("N");
			
			if ((i+1) % DEFAULT_NUM == 0) {
				baduk.setI(width++);
				baduk.setJ(height);
				height = 0;
			} else {
				baduk.setI(width);
				baduk.setJ(height++);
			}
			
			list.add(baduk);
		}
		
	
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i).getType() + "("+list.get(i).getI()+":"+list.get(i).getJ()+")"+ i +" ");
			if ((i+1) % DEFAULT_NUM == 0) {
				System.out.println();
			}
		}
		
		MainBaduk n = new MainBaduk();
		n.init();
		n.replayList(list, "B");
		n.replayList(list, "W");
		
		Scanner input = new Scanner(System.in);
		
		int number = 0;
		int value = 0;
		
		while (!"Q".equals(input.next())) {
			System.out.println("black turn : ");
			System.out.println("number : ");
			number = input.nextInt();
			System.out.println("value : ");
			value = input.nextInt();
			n.gamePlay(list, number, value, "B");
			n.replayList(list, "B");
			n.replayList(list, "W");
			
			
			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i).getType() + "("+list.get(i).getI()+":"+list.get(i).getJ()+")"+ i +" ");
				if ((i+1) % DEFAULT_NUM == 0) {
					System.out.println();
				}
			}
			
			System.out.println("white turn : ");
			System.out.println("number : ");
			number = input.nextInt();
			System.out.println("value : ");
			value = input.nextInt();
			n.gamePlay(list, number, value, "W");
			n.replayList(list, "W");
			n.replayList(list, "B");

			for (int i = 0; i < list.size(); i++) {
				System.out.print(list.get(i).getType() + "("+list.get(i).getI()+":"+list.get(i).getJ()+")"+ i +" ");
				if ((i+1) % DEFAULT_NUM == 0) {
					System.out.println();
				}
			}
		}
	}
	
	public void init(){
		edgeMap.put("LU","-" + (DEFAULT_NUM+1));
		edgeMap.put("U","-" + (DEFAULT_NUM));
		edgeMap.put("RU","-" + (DEFAULT_NUM-1));
		edgeMap.put("L","-1");
		edgeMap.put("R","1");
		edgeMap.put("LD","" + (DEFAULT_NUM-1));
		edgeMap.put("D","" + (DEFAULT_NUM));
		edgeMap.put("RD","" + (DEFAULT_NUM+1));
	}
	
	public void gamePlay(ArrayList<Baduk> list, int i, int value, String type){
		if ("N".equals(list.get(i).getType())) {
			playLogic(list, i, value, type);
		} else {
			System.out.println("�ٸ� ���� �νñ� �ٶ��ϴ�.");
		}
	}

	private void playLogic(ArrayList<Baduk> list, int i, int value, String type) {
		init();	// �׻� �ʱ�ȭ�� �ؾ��մϴ�.
		int width = list.get(i).getI();
		int height = list.get(i).getJ();	//Height
		list.get(i).setType(type);
		int cnt = 0;
		
		if (width == 0) {
			edgeMap.put("LU","X");
			edgeMap.put("U","X");
			edgeMap.put("RU","X");
		}
		
		if (height == 0) {
			edgeMap.put("LU","X");
			edgeMap.put("L","X");
			edgeMap.put("LD","X");
		}
		
		if (width == (DEFAULT_NUM-1)) {
			edgeMap.put("LD","X");
			edgeMap.put("D","X");
			edgeMap.put("RD","X");
		}
		
		if (height == (DEFAULT_NUM-1)) {
			edgeMap.put("RU","X");
			edgeMap.put("R","X");
			edgeMap.put("RD","X");
		}
		
		/*
		 * B : BLACK
		 * W : WHITE
		 * K : BLANK
		 * N : NONE
		 * BL : BLACK BLOCK
		 * WL : WHITE BLOCK
		 */
		for (Map.Entry<String, String> entry : edgeMap.entrySet() )
		{
			if (!"X".equals(entry.getValue())) {
				System.out.println(i + Integer.parseInt(entry.getValue()));
				cnt += parseList(list.get(i + Integer.parseInt(entry.getValue())));
			}
		}
		
		if (cnt == value) {
			for (Map.Entry<String, String> entry : edgeMap.entrySet() )
			{
				if (!"X".equals(entry.getValue())) {
					chageList(type, list.get(i + Integer.parseInt(entry.getValue())));
				}
			}
		} else {
			list.get(i).setType(type);
			list.get(i).setValue(value);
		}
	}
	
	public int replayList(ArrayList<Baduk> list, String type){
		int score = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getValue() > 0) {
				if(type.equals(list.get(i).getType())){
					playLogic(list, i, list.get(i).getValue(), list.get(i).getType());
				}
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getValue() > 0) {
				if (type.equals(list.get(i).getType())){
					score += list.get(i).getValue();
				} else if ((type+"L").equals(list.get(i).getType())){
					score++;
				}
			}
		}
		return score;
	}
	
	public int parseList(Baduk daduk){
		if("B".equals(daduk.getType()) || "W".equals(daduk.getType())){
			return 1;
		} else {
			return 0;
		}
	}
	
	public void chageList(String type, Baduk daduk){
		if("B".equals(daduk.getType()) || "W".equals(daduk.getType())){
			if ("B".equals(type)){
				daduk.setType("BL");
				daduk.setValue(-1);
			} else if ("W".equals(type)){
				daduk.setType("WL");
				daduk.setValue(-1);
			}
		}
	}
}