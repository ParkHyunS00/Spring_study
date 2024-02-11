package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
/*
* MyLogger 가짜 프록시 클래스를 만들고 주입해 일단 동작되게 함
* Request 요청받고 응답하는 동안 살아있는 빈이기 때문에 스프링 서버 시작 시 빈에 등록할 수 없음
* 스프링이 CGLIB 라이브러리 사용해 프록시 클래스를 주입하고, 실제 기능을 호출하는 시점에 진짜 클래스를 찾아서 동작함 -> Provider 처럼
*/
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create : " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close : " + this);
    }
}
