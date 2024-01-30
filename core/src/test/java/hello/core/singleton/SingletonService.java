package hello.core.singleton;

public class SingletonService {

    // static 영역에 객체를 딱 1개 생성
    private static final SingletonService instance = new SingletonService();

    // 객체 인스턴스가 필요하다면 이 static 메서드를 통해서만 조회하도록 허용
    public static SingletonService getInstance() {
        return instance;
    }

    // 외부에서 생성되는 것을 막음
    private SingletonService() {}

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
