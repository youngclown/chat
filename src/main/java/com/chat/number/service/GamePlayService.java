package com.chat.number.service;

import com.chat.number.model.NumberOthello;
import com.chat.number.type.CheckerBoardRangeType;
import com.chat.number.type.NumberOthelloType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Log4j
@Getter
@Setter
public class GamePlayService {
  public Map<CheckerBoardRangeType, Integer> CHECKERBOARD = new HashMap<>();
  public ArrayList<NumberOthello> list = new ArrayList<>();
  final int CHECKERBOARD_SIZE = 7; // 가로 세로 체커보드

  public void init() {
    CHECKERBOARD.put(CheckerBoardRangeType.LEFT_UP, -(CHECKERBOARD_SIZE + 1));
    CHECKERBOARD.put(CheckerBoardRangeType.CNETER_UP, -(CHECKERBOARD_SIZE));
    CHECKERBOARD.put(CheckerBoardRangeType.RIGHT_UP, -(CHECKERBOARD_SIZE - 1));
    CHECKERBOARD.put(CheckerBoardRangeType.CNETER_LEFT, -1);
    CHECKERBOARD.put(CheckerBoardRangeType.CNETER_RIGHT, 1);
    CHECKERBOARD.put(CheckerBoardRangeType.LEFT_DOWN, (CHECKERBOARD_SIZE - 1));
    CHECKERBOARD.put(CheckerBoardRangeType.CNETER_DOWN, (CHECKERBOARD_SIZE));
    CHECKERBOARD.put(CheckerBoardRangeType.RIGHT_DOWN, (CHECKERBOARD_SIZE + 1));
  }


  public GamePlayService() {
    init();

    int width = 0;
    int height = 0;

    for (int i = 0; i < CHECKERBOARD_SIZE * CHECKERBOARD_SIZE; i++) {
      NumberOthello othello = new NumberOthello();
      othello.setType("N");

      if ((i + 1) % CHECKERBOARD_SIZE == 0) {
        othello.setI(width++);
        othello.setJ(height);
        height = 0;
      } else {
        othello.setI(width);
        othello.setJ(height++);
      }

      list.add(othello);
    }
  }

//  public static void main(String[] args) {
//    GamePlayService gamePlayService = new GamePlayService();
//    List<NumberOthello> list = gamePlayService.getList();
//    for (NumberOthello numberOthello: list) {
//      System.out.println(numberOthello.toString());
//    }
//  }

  public void gamePlay(String numberOthello, String numberChoice, String type) {

    // 배열 초기화 작업
    init();

    int i = Integer.parseInt(numberOthello);
    int value = Integer.parseInt(numberChoice);
    int width = list.get(i).getI();
    int height = list.get(i).getJ();  //Height

    int cnt = 0;

    if (width == 0) {
      CHECKERBOARD.put(CheckerBoardRangeType.LEFT_UP, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.CNETER_UP, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.RIGHT_UP, -999);
    } else if (width == (CHECKERBOARD_SIZE - 1)) {
      CHECKERBOARD.put(CheckerBoardRangeType.LEFT_DOWN, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.CNETER_DOWN, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.RIGHT_DOWN, -999);
    }

    if (height == 0) {
      CHECKERBOARD.put(CheckerBoardRangeType.LEFT_UP, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.CNETER_LEFT, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.LEFT_DOWN, -999);
    } else if (height == (CHECKERBOARD_SIZE - 1)) {
      CHECKERBOARD.put(CheckerBoardRangeType.RIGHT_UP, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.CNETER_RIGHT, -999);
      CHECKERBOARD.put(CheckerBoardRangeType.RIGHT_DOWN, -999);
    }

    /*
     * B : BLACK
     * W : WHITE
     * K : BLANK
     * N : NONE
     * BL : BLACK BLOCK
     * WL : WHITE BLOCK
     */
    for (Map.Entry<CheckerBoardRangeType, Integer> entry : CHECKERBOARD.entrySet()) {
      if (entry.getValue() != -999) {
        System.out.println(i + entry.getValue());
        cnt += parseList(list.get(i + entry.getValue()));
      }
    }

    if (cnt == value) {
      for (Map.Entry<CheckerBoardRangeType, Integer> entry : CHECKERBOARD.entrySet()) {
        if (entry.getValue() != -999) {
          chageList(type, list.get(i + entry.getValue()));
        }
      }
    } else {
      list.get(i).setType(type);
      list.get(i).setValue(value);
    }
  }

  public int parseList(NumberOthello othello){
    if(NumberOthelloType.PLAYER_ONE.getValue().equals(othello.getType()) ||
       NumberOthelloType.PLAYER_TWO.getValue().equals(othello.getType())){
      return 1;
    } else {
      return 0;
    }
  }

  public void chageList(String type, NumberOthello othello){
    if(NumberOthelloType.PLAYER_ONE.getValue().equals(othello.getType()) ||
       NumberOthelloType.PLAYER_TWO.getValue().equals(othello.getType())){
      if (NumberOthelloType.PLAYER_ONE.getValue().equals(type)){
        othello.setType(NumberOthelloType.PLAYER_ONE_BLOCK.getValue());
        othello.setValue(1);  // 해당 배열의 '1' 값 설정
      } else if (NumberOthelloType.PLAYER_TWO.getValue().equals(type)){
        othello.setType(NumberOthelloType.PLAYER_TWO_BLOCK.getValue());
        othello.setValue(1);
      }
    }
  }
}
