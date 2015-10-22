package lotto;

import java.util.Arrays;

/**
 * @file_name : Lotto.java
 * @author    : june2ne1@naver.com
 * @date      : 2015. 10. 22.
 * @story     :로또 알고리즘
 */
public class Lotto {
	//public static void main(String[] args) {지우기
		//Lotto lotto = new Lotto();지우기
		//int[] temp = lotto.getLotto(); ui에 옮기기
	//	for (int i = 0; i < temp.length; i++) {ui에 옮기기
			//System.out.println(temp[i]+"\t");ui에 옮기기
		//}
	//}
	int[] lotto = new int[6]; //SBS 에서는 단 하나의 로또 번호만 출력
	public Lotto() {
		this.setLotto();
		
	}
	public int[] getLotto() {
		return lotto;
	}
	public void setLotto() {
		//0으로 초기화가 이미 되었으므로
		for (int i = 0; i < lotto.length; i++) {
			int randomNum = (int) (Math.random()*45+1);
			boolean exist = false;
			for (int j = 0; j < lotto.length; j++) {
				if (randomNum == lotto[j]) {
					exist = true; //
					break;
				}
			}
			if (exist) {
				i--; // 중복된값이 출력되면 카운트 숫자를 줄여준다.
				continue;
			}else {
				// if문을 타지 않은 경우
				lotto[i] = randomNum;
			}
		}
		Arrays.sort(lotto); // 오름차순 정렬
		}
	
	}
	


