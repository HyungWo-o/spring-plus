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


