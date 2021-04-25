# @AuthenticationPrincipal

- Srping Security가 제공하는 기능중 Authentication Principal이라는 argument resolver가 있음
    ```java
    public String index(Model model, Principal principal){
        // ...
    }

    // 또는

    SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // 식으로 꺼내서 사용할 수 있음
    ```

- 하지만 위 예제에서 사용되는 Principal 은 서로 다름
- 어플이케이션에서는 기본으로 제공하는 Principal이 아니라 우리가 만든 domain 객체를 사용하고 싶어함