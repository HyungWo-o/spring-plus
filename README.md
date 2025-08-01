# SPRING PLUS

lv1.
TocoService 클래스 전체에 적용되어 있던 @Transactional(readOnly = true) 를 제거하고 하위 메서드에 개별 적용
saveTodo() 메서드에 @Transactional 적용
saveTodo() 메서드 외 나머지 조회만 하는 메서드에 @Transactional(readOnly = true) 적용


lv2.
User 엔티티에 nickname 필드 추가
관련된 dto에 nickname 필드 추가, 생성자에 nickname 추가
JWT 토큰에 nickname 값 가져와 저장하도록 수정


lv3.
TodoController의 getTodos() 메서드에 매개변수로 String weather, String startDate, String endDate 받도록 추가
날짜데이터는 포맷함수를 거쳐 String에서 LocalDateTime으로 변환
TodoService의 getTodos() 메서드에서 weather, startDate, endDate 전달받아 default 값인 "all"일 경우를 처리.
TodoRepository에 findByWeatherWithModifiedAtOrderByModifiedAtDesc() 메서드에 JPQL로 쿼리 작성하여 해당 조건으로 데이터 조회


lv4.
TodoControllerTest의 todo_단건_조회_시_todo가_존재하지_않아_예외가_발생한다() 메서드의 then 부분에
andExpect로 응답값 검증하는 부분에서 400 BAD_REQUEST 에러가 나야 테스트가 통과되는데
200 OK 와 비교 검증하고 있어 테스트 실패하고 있음.

-> OK를 BAD_REQUEST로 바꿔주었고 테스트 통과함.


lv5.
AdminAccessLogginAspect 클래스의 logAfterChangeUserRole() 메서드에 적용된
@After 어노테이션을 @Before 어노테이션으로 수정,
@Before(execution())의 적용 경로가 UserController의 getUser() 메서드로 잘못 적용되어있어
UserAdminController.changeUserRole(..) 로 수정


lv6.
Todo 엔티티의 managers 필드에 적용된 @OneToMany 어노테이션에
cascade = CascadeType.PERSIST를 추가하여 할 일 등록 시 등록자를 담당자로 자동 설정


lv7.
CommentRepository의 findByTodoIdWithUser() 메서드에서 N+1 문제가 발생
@Query에서 comment와 user가 JOIN 되어있는데 N+1 문제를 해결하기 위해 JOIN FETCH로 수정하여 페치조인 적용


lv8.
JPQL로 작성된 findByIdWithUser() 쿼리메서드를 QueryDSL 의존성과 파일들을 추가하여 QueryDSL 코드로 체인지
leftJoin의 N+1 문제를 함께 해결하기위하여 leftJoin() 뒤에 fetchJoin()을 추가하여 N+1 문제를 방지함


lv9.
Spring Security를 도입하기 위하여 기존에 적용되어있던 Filter와 AuthArgumentResolver를 수정
Security 의존성을 추가하였고, SecurityConfig 파일을 추가하여 인가 설정함
기존에 AuthArgumentResolver에서 HttpServletRequest에 저장하고 AuthUser로 가져오던 데이터를
SecurityContextHolder에 Security의 UserDetails를 커스텀한 Dto에 담아서 Controller에 전달
AuthUser를 사용하던 메서드들에서 CustomUserDetails로 수정


lv10.
QueryDsl을 사용하여 Todos 검색 기능 페이징 반환
새로운 API로 만들기 위하여 Controller에 todoSearch() 메서드 작성
반환타입 요구사항에 맞추기 위하여 TodoSearch DTO 작성
Service에 todoSearch() 메서드 작성, QueryDSL 사용을 위한 쿼리메서드 작성
Repository QImpl파일에 QueryDSL로 응답 DTO에 필요한 필드, 카운트 조회
## 여기서 managers 카운트 할 때 같은 매니저 id를 중복으로 카운트해서 애먹었습니다.
## 구글링하여 distinct를 카운트에도 사용하는 countDistinct()를 사용하여 해결.
페이징을 위한 전체 카운트 totalTodos 조회하여 페이징에 추가
