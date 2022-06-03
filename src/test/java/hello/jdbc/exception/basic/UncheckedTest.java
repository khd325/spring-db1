package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    void unchecked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    public void unchecked_throw() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyUncheckedException.class);
    }



    /**
     * RuntimeException을 상속받은 예외 -> 언체크 예외
     */
    static class MyUncheckedException extends RuntimeException{
        public MyUncheckedException(String message) {
            super(message);
        }
    }

    static class Service {
        Repository repository = new Repository();

        /**
         * 필요한 경우 예외를 잡아서 처리해도 됨
         */
        public void callCatch() {
            try{
                repository.call();
            } catch (MyUncheckedException e){
                log.info("예외 처리, message={}",e.getMessage(),e);
            }
        }

        /**
         * 예외를 잡지 않으면 상위로 넘어감
         * throws 예외 선언 하지 않아도 됨
         */
        public void callThrow(){
            repository.call();
        }
    }

    static class Repository {
        public void call() {
            throw new MyUncheckedException("ex");
        }
    }


}
