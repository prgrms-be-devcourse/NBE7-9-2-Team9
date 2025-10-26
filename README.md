# NBE7-9-2-Team9
> 본 프로젝트는 **여행 일정 관리와 장소 추천 기능을 중심으로**, 사용자가 여행을 준비할 수 있도록 돕는 것을 목표로 합니다.

</br>

## 1. 프로젝트 개요
**갈래? 말래**는 여행지를 추천받고, 일정을 함께 계획할 수 있는 여행 일정 관리 플랫폼입니다.  
사용자는 인기 있는 여행지를 기반으로 장소를 추천받고, 이를 일정표 형태로 구성할 수 있습니다.  
또한, 관리자는 등록된 장소와 회원 정보를 효율적으로 관리할 수 있습니다.
<br>

---

## 2. 팀원

| 이름 | 역할 | 주요 담당 기능 |
|------|------|----------------|
| **박준석 (팀장)** | **BE \| FE** | 장소(Place) 및 관리자(Admin) API, 예외 처리 구조 설계 |
| **강휘윤** | **BE \| FE** | 플랜(Plan) 도메인 API, 일정 CRUD, 쿼리 최적화 |
| **김영인** | **BE \| FE** | 회원 인증, JWT, Security 설정, 관리자 회원 API |
| **김윤수** | **BE \| FE** | React UI, API 연동, UX 개선 |
| **허성찬** | **BE \| FE** | 장소 및 플랜 화면 구현, 프론트 로직 보완 |


---

## 3. 주요 기능

| 구분 | 설명 |
|------|------|
| 회원 관리 | 회원가입, 로그인, 회원정보 수정, 탈퇴 처리 |
| 인증/인가 | JWT 기반 Access/Refresh Token 발급 및 검증, Role별 접근 제어 |
| 플랜 관리 | 여행 일정 생성, 수정, 조회, 삭제 |
| 장소 관리 (Admin) | 관리자 전용 장소 등록, 수정, 삭제 기능 |
| 북마크 | 사용자가 관심 장소를 저장 및 해제 |
| 예외 처리 | 전역 예외(GlobalExceptionHandler) 및 ErrorCode 기반 표준 응답 |
| 데이터 관리 | BaseEntity를 통한 생성/수정 시간 자동 관리 |

---

## 4. 기술 스택

<div>
    <table>
        <tr>
            <td colspan="2" align="center">
               FrontEnd
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/HTML5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white">
                <img src="https://img.shields.io/badge/JavaScript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E">
                <img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                BackEnd / Framework
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
                <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
                <img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=hibernate&logoColor=white">
                <img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white">
                <img src="https://img.shields.io/badge/Cookie Auth-555555?style=for-the-badge">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Database
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/H2 Database-0078D6?style=for-the-badge&logo=h2&logoColor=white">
                <img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                API / Docs / Test
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black">
                <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white">
                <img src="https://img.shields.io/badge/REST API-005571?style=for-the-badge">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Tool
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/IntelliJ IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white">
                <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                Collaboration
            </td>
            <td colspan="4">
                <img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white"/>
                <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white">
                <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white">
            </td>
        </tr>
    </table>
</div>



---

## 5. 아키텍처 구조 (Backend)
```
com.backend
├── domain — 주요 비즈니스 도메인 계층
│ ├── admin — 관리자 기능 (카테고리/회원/장소 관리 등)
│ ├── auth — 인증 및 인가 로직 (JWT, OAuth 등)
│ ├── bookmark — 북마크 관련 도메인
└──── ...

├── external.seoul — 외부 API 연동 (서울시 오픈데이터)
│ ├── hotel — 서울시 호텔 데이터
│ ├── modelrestaurant — 모범음식점 데이터
│ └── nightspot — 야간명소 데이터

├── global — 전역 설정 및 공통 유틸
│ ├── config — 환경 설정, CORS, Swagger 등
│ ├── entity — 공통 엔티티 (BaseEntity 등)
│ ├── exception — 전역 예외 처리 및 ErrorCode 관리
│ ├── init — 초기 데이터 세팅 (Seeder, Loader 등)
│ ├── jwt — JWT 발급/검증 로직
│ ├── reponse — 공통 응답 포맷 (ApiResponse 등)
│ └── security — Spring Security 관련 설정 및 필터

└── BackendApplication.java — Spring Boot 실행 클래스
```

| Layer | Description |
|:------|:-------------|
| **Domain** | 핵심 비즈니스 로직을 담당하는 계층으로, 사용자, 장소, 리뷰, 일정 등 주요 기능이 포함됩니다. 각 도메인은 Controller, Service, Repository 구조를 따릅니다. |
| **Global** | 공통 설정, 시큐리티, 예외, JWT 등 시스템 전역 로직 |
| **External** | 외부 API 연동 및 데이터 수집 모듈 |

---


## 6. ERD
<img width="1252" height="810" alt="travel _plan (1)" src="https://github.com/user-attachments/assets/7ae0463e-07b3-405e-9b32-a7cd51c884f8" />

