package com.myweb.www.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
// @RequiredArgsConstructor	// NoArgsConstructor 지우고 작성함. 필요한 파라미터를 선택해서 만들 수 있는 생성자
// 어차피 두개뿐이고 두개 다 써야하니까 all만 있어도 됨
@AllArgsConstructor
@ToString
public class BoardDTO {
	private BoardVO bvo;
	private List<BFileVO> bfList;
}
// DTO (Data Tranfer Object) : 데이터들을 가지고 다니면서 어떠한 연산이나 기능 없이 값들을 묶어 전송만 하는 역할을 하는 객체
// board와 file의 값들을 가지고 다닐 예정
// serive에서 int register(BoardVO bvo, List<BfileVO> bfList);
// 라고 해도 되긴 하는데 그럼 이제 왜 그렇게 만듦? 이라고 물어보면 그냥..이라고 밖엔 대답을 못함
