## 프로젝트 개발 동기

> 스프링, 스프링 부트, JPA 등의 기술스택들을 이론적으로 학습한 뒤에 
> 실제로 어떻게 동작하는지 이해하기 위해서 쇼핑몰 프로젝트를 구현해보았습니다. <br>
> 흔한 쇼핑몰 프로젝트일지라도 완성하는 것에 초점을 두지 않고, 사용되는 기술에 대한 
> 정확한 개념 및 사용법, 그리고 동작원리에 대해서 초점을 맞추었고, <br>
> 개발 과정에서 발생하는 에러 및 궁금증들을 자세하게 찾아보고 정리하면서
> 전체적인 흐름 파악을 목표로 두었으며 프론트보다는 백엔드에 좀 더 집중했습니다.

## 프로젝트 개발 환경

1. 운영체제 : Windows 10
2. 통합개발환경(IDE) : IntelliJ
3. JDK 버전 : JDK 11
4. 스프링 부트 버전 : 2.6.3
5. 데이터베이스 : MySQL
6. 빌드 툴 : Maven
7. 관리 툴 : Git, GitHub

## 프로젝트 기술 스택

<img src="https://user-images.githubusercontent.com/35963403/166197893-361d4863-5b6f-406a-a18f-8d5f53719e54.png" width="300">

- 프론트엔드
  - HTML, CSS, JS, BootStrap, Thymeleaf
- 백엔드
  - Spring Boot, Spring Security, Spring Data JPA, QueryDsl
- 데이터베이스
  - Hibernate, MySQL

## 프로젝트 구현 기능

- 회원 (Member)
  - 회원가입 / 로그인 및 로그아웃
- 상품 (Item)
  - 상품 등록 / 상품 관리 / 상품 수정 / 상품 조회 / 상품 상세 페이지
- 주문 (Order)
  - 상품 주문 / 주문 내역 조회 / 주문 취소
- 장바구니 (Cart)
  - 장바구니 담기 / 장바구니 조회 / 장바구니 삭제 / 상바구느 상품 주문

## 프로젝트 DB 모델링

<img src="https://user-images.githubusercontent.com/35963403/166202903-9b6e0615-efef-423f-b92d-5bba7d8ed314.PNG" width="550">

> member - 쇼핑몰 회원 정보 테이블 <br>
> cart - 회원의 장바구니 목록 테이블 <br>
> cart_item - 장바구니에 담긴 상품 정보 테이블 <br>
> orders - 쇼핑몰 회원들의 주문 목록 테이블 <br>
> order_item - 주문된 상품 정보 테이블 <br>
> item - 쇼핑몰 상품 정보 테이블 <br>
> item_img - 상품에 대한 이미지 정보를 담고 있는 테이블 <br>
> board - 게시판의 게시글 정보 테이블 <br>
> comment - 게시글에 대한 댓글 정보를 담고 있는 테이블 <br>

## 프로젝트 API 명세서

| Function   | Method | End Point                            |    | Function   | Method     | End Point                                                     |
|------------|--------|--------------------------------------|----|------------|------------|---------------------------------------------------------------|
| 회원가입 페이지   | GET    | /member/new                          |    | 상품 등록 페이지  | GET        | /admin/item/new                                               |
| 회원가입       | POST   | /member/new                          |    | 상품 등록      | POST       | /admin/item/new                                               |
| 로그인 페이지    | GET    | /member/login                        |    | 상품 조회      | GET        | /admin/item/{itemId}<br/>/admin/items<br/>/admin/items/{page} |
| 로그인        | POST   | /member/login                        |    | 상품 수정      | POST       | /admin/item/{itemId}                                          |
| 장바구니 담기    | POST   | /cart                                |    | 주문하기       | POST       | /order                                                        |
| 장바구니 페이지   | GET    | /cart                                |    | 주문 내역 페이지  | GET        | /orders<br/>/orders/{page}                                    |
| 장바구니 상품 수정 | PATCH  | /cartItem/{cartItemId}               |    | 주문 취소      | POST       | /order/{orderId}/cancel}                                      |
| 장바구니 상품 제거 | DELETE | /cartItem/{cartItemId}               |    | 게시글 등록 페이지 | GET        | /board/new                                                    |
| 장바구니 상품 주문 | POST   | /cart/orders                         |    |게시글 등록| POST       | /board/new                                                    |
| 댓글 등록      | POST   | /board/{boardId}/comment             | | 게시글 수정 페이지 |GET| /board/{boardId}/edit                                         |
| 댓글 수정 페이지  | GET    | /board/{boardId}/comment/{commentId} | |게시글 수정|POST| /board/{boardId}                                              |
|댓글 수정|POST| /board/{boardId}/comment/{commentId} | |게시글 삭제|DELETE| /board/{boardId}                                              |
|댓글 삭제|DELETE| /board/{boardId}/comment/{commentId} | |게시글 조회|GET| /board/{boardId}                                              |
| | | | |게시판 조회|GET| /boards<br/>/boards/{page}                                    |



## 프로젝트 진행하며 발생한 궁금증 및 에러에 대한 해결볍 정리

- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/project/query-projection.md">@QueryProjection 와 Projections (QueryDSL)</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/paging.md">Count 쿼리를 분리한 페이징 최적화 (QueryDSL)</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/project/stack-over-flow-error.md">toString 무한 순환 참조 문제</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/project/swagger-error.md">swagger 연동 에러</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/project/jqXHR.md">ajax jqXHR (ajax 에러처리)</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/project/postman-error.md">Postman에서 API 테스트할 때 401 Unauthorized 문제 해결법</a>

- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/valid.md">@NotNull, @NotEmpty, @NotBlank 사용법 및 차이점</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/rest-api.md">@RestController을 이용한 REST API 개발</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/response-entity.md">ResponseEntity 사용법</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/login.md">세션 기반 인증 방식과 토큰 기반 인증 방식 (JWT)</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/dto.md">엔티티와 DTO를 분리해야 하는 이유</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/command-query-seperation.md">커맨드와 쿼리 분리 (CQS) 활용법</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/builder.md">빌더(Builder) 패턴 장점과 사용법</a>
- #### <a href="https://github.com/gxdxx/TIL/blob/main/Spring/ajax.md">AJAX란?</a>