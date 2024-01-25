package hello.core.xml;

import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/*
* XML을 사용한 스프링 컨테이너 설정
* 최근엔 스프링 부트를 많이 사용하며 XML 기반의 설정은 잘 사용하진 않음
* 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점 존재
* xml 파일은 resources 하위에
*/
public class XmlAppContext {

    @Test
    void xmlAppContext() {
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
