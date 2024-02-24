/*
 * Copyright 2012-2023 The Feign Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package feign;

import java.util.HashMap;
import java.util.Map;

public class ThreadContext {

  public final Map<String, Object> context = new HashMap<>();
  private final ContextManipulateProvider delegator;

  public ThreadContext(ContextManipulateProvider delegator) {
    this.delegator = delegator;
  }

  public Map<String, Object> context() {
    return context;
  }

  public void snapshot() {
    if (delegator != null) {
      delegator.snapshot(context());
    }
  }

  public void restore() {
    if (delegator != null) {
      delegator.restore(context());
    }
  }

  // TODO add cleanup method to clear side effects made by `restore()`
}
