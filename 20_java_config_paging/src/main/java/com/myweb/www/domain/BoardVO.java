package com.myweb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor	// 아무것도 없는 생성자를 만드는 어노테이션
@AllArgsConstructor	// 모든 인자가 있는 생성자를 만드는 어노테이션 (얘도 안써도 되긴 함)
@Getter	// getter 만드는 어노테이션
@Setter	// setter 만드는 어노테이션
@ToString	// toString 만드는 어노테이션
// 다 lombok에서 지원해주는 기능이다. Slf4j도 마찬가지로 lombok에서 지원해주는 기능이다. + EqulsAndHashCode, lombok.value

// 이 모든걸 다 합쳐서 하나로 선언이 가능하다
// @Data로 쓰면 되는데, 이렇게 되면 우리가 쓰는 것 이외에 추가되는것들 (위의 equl~ lombok~)이 생기기 때문에 위처럼 쓸 것만 생성해주는 것이 좋다
public class BoardVO {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private String regAt;
	private String modAt;
	private int readCount;
	
	// register : title, content, writer
	// list : bno, title, writer, readCount, modAt
	// detail : *
	// modify : title, content
	// remove : bno;
	
}
