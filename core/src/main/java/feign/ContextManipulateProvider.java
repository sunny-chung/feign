package feign;

import java.util.Map;

public interface ContextManipulateProvider {

  void snapshot(Map<String, Object> context);

  void restore(Map<String, Object> context);
}
