# 1. 프로젝트 개요 및 설계

## 1. 프로젝트 소개

### 1.1 프로젝트 목적
본 프로젝트는 대규모 트래픽 환경에서 안정적으로 동작하는 프로모션 시스템을 구현하는 것을 목표로 합니다. 마이크로서비스 아키텍처를 기반으로 하여 확장성과 유연성을 확보하고, 다양한 동시성 제어 방식을 통해 성능을 최적화합니다.

### 1.2 핵심 기능
1. 쿠폰 발급 시스템
   - 동시성 제어를 통한 수량 관리
   - 다양한 발급 전략 구현 (선착순, 지정 대상)

2. 사용자 관리
   - 회원 가입/로그인
   - 권한 관리

3. 주문 처리
   - 쿠폰 적용 주문
   - 주문 상태 관리
   - 결제 연동

4. 상품 관리
   - 상품 등록/수정
   - 재고 관리
   - 상품별 쿠폰 정책

5. 포인트 시스템
   - 포인트 적립/사용
   - 포인트 내역 관리

6. 타임세일
   - 특정 시간대 할인
   - 수량 제한 판매
   - 실시간 재고 관리

## 2. 시스템 아키텍처

### 2.1 전체 시스템 구성
![CH01_01. 프로젝트 개요 및 설계.png](/docs/프로젝트%20아키텍처.png)

### 2.2 핵심 컴포넌트
1. API Gateway (Spring Cloud Gateway)
   - 인증/인가 처리
   - 요청 라우팅
   - 로드밸런싱
   - 속도 제한

2. Service Discovery (Eureka)
   - 서비스 등록/발견
   - 상태 모니터링
   - 로드밸런싱 지원

3. 마이크로서비스
   - User Service: 사용자 관리
   - Coupon Service: 쿠폰 발급/관리
   - Order Service: 주문 처리
   - Point Service: 포인트 관리
   - Product Service: 상품 관리
   - Time Sale Service: 타임세일 관리

## 3. 기술 스택

### 3.1 공통 기술 스택
- Java 17
- Spring Boot 3.4.4
- Spring Cloud
- Gradle
- JUnit 5
- H2(in-memory DB) / MySQL 
- Redis 7
- Docker

### 3.2 컴포넌트별 주요 기술
1. API Gateway
   - Spring Cloud Gateway
   - Spring Security
   - JWT
   - Rate Limiter

2. Service Discovery
   - Spring Cloud Netflix Eureka
   - Spring Cloud LoadBalancer

3. 마이크로서비스 공통
   - Spring Boot
   - Spring Data JPA
   - Spring Data Redis
   - Spring Kafka
   - Resilience4j

## 4. 구현 전략

### 4.1 동시성 제어
1. 분산 락
   - Redisson을 활용한 분산 락 구현
   - 쿠폰 발급, 재고 관리 등에 적용
   - 데드락 방지를 위한 타임아웃 설정

2. 대기열 시스템
   - Redis를 활용한 대기열 구현
   - 트래픽 부하 분산
   - 공정한 처리 순서 보장

### 4.2 확장성
1. 수평적 확장
   - 서비스별 독립적인 확장
   - 로드밸런서를 통한 부하 분산
   - 컨테이너 기반 자동 스케일링

2. 데이터 분산
   - 서비스별 독립 데이터베이스
   - 캐시 계층 활용
   - 데이터 정합성 관리

### 4.3 장애 대응
1. 서킷 브레이커
   - Resilience4j를 활용한 장애 격리
   - 폴백 메커니즘 구현
   - 장애 전파 방지

2. 모니터링
   - Spring Boot Actuator
   - 실시간 시스템 상태 모니터링

### 4.4 성능 최적화
1. 캐싱 전략
   - Redis를 활용한 캐싱
   - 캐시 정합성 관리
   - 캐시 갱신 전략

2. 비동기 처리
   - 이벤트 기반 아키텍처
   - Kafka를 활용한 메시지 큐
   - 비동기 작업 처리

## 5. 보안 설계

### 5.1 인증
1. JWT 기반 인증
   - Access Token / Refresh Token
   - 토큰 갱신 전략

### 5.2 보안 정책
1. API 보안
   - Rate Limiting
   - CORS 설정
   - Request Validation
