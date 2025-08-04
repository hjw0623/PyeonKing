# :convenience_store: 편킹 - 편의점 할인정보 제공 앱

**고 물가 시대 할인받고 구매하자!!**  
편의점 할인 정보를 제공하며, 리뷰를 작성할 수 있는 Android 앱입니다.

---

## 📱 프로젝트 개요

- **앱 이름**: PyeonKing  
- **개발 기간**: 2025.06 ~ 2025.08(마켓 등록 준비중)
- **플랫폼**: Android  
- **개발 언어**: Kotlin  
- **개발 환경**: Android Studio  
- **데이터**: REST서버, kakao POI

## 🤷‍♀️ 기획 의도  
해마다 상승하는 물가로 인해, 편의점에서 상품을 구매할때 할인정보를 확인하고 합리적인 소비를 위해 개발했습니다.

---

## 🛠 사용 기술 스택

| 구분 | 기술 |
|----------|------------|
| Language | Kotlin |
| UI/UX | Jetpack Compose Material3 |
| Architecture | MVVM, Repository Pattern, ViewModelFactory |
| Async Programming | Kotlin Coroutines, StateFlow, SharedFlow |
| Navigation | Jetpack Navigation |
| LBS | Fused Location Provider |
| Local Storage | DataStore |
| Networking | Retrofit2, OkHttp, Moshi |
| Map | Google Compose Map |
| Image Loading | Coil |
| Tools | Git, Figma |

---

## 📱 주요 기능 시연 및 설명

### 👍 1. 사용자 맞춤 추천 상품 

| 추천 상품 화면|
|:--:|
| <img src="https://github.com/user-attachments/assets/4e7a44e2-919d-45f5-a59b-56e9762749d6" width="250"/> |
|서비스 사용자의 행동을 분석해 개인화된 추천 상품 목록을 제공합니다.|

---

### 📷 2. AI 서버와 연동된 이미지 검색 기능 

| 이미지 검색 화면 |
|:--:|
| <img src="https://github.com/user-attachments/assets/e1cde4d7-a70c-4d33-ae5e-d001e9e8c310"/> |
| 상품의 사진을 찍으면 해당 상품의 이미지를 AI서버와 연동해 할인 정보를 제공합니다. |

---

### 🔍  3. 상품 조회 및 검색

|상품 검색|상품 조회|
|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/df004b41-c827-49e5-84ae-47fc0d61af30" width="250"/> |<img src="https://github.com/user-attachments/assets/5dfa3b49-cea5-4b49-8b7b-89c284be00c9"  width="250"/> | 
|이름을 검색하여 정보를 얻을 수 있습니다.| 필터링을 통해 정보를 얻을 수 있습니다.|

---

### 📄 4. 상품 상세 정보 화면

| 지도 화면 | 리뷰 조회 화면 |  리뷰 작성 화면 | 
|:--:|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/11d18972-9b48-478f-b435-148b2a601196" width="250"/> | <img src="https://github.com/user-attachments/assets/ee032acf-c1bc-4bac-a46a-96880c91c655" width="250"/> | <img src="https://github.com/user-attachments/assets/06997b75-87b5-417d-bdd5-d7c0b3fb16c4" width="250"/> | 
|해당 편의점의 위치를 확인할 수 있습니다 |상품에 대한 리뷰를 볼 수 있습니다.|상품에 대해 리뷰를 작성할 수 있습니다.|

---

### 📝 4. 리뷰 기능

| 작성한 리뷰 확인 | 리뷰 작성 | 리뷰 수정 |
|:--:|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/87ad17c2-c6c6-40ef-9149-a1d49a60b6b1" width="250"/>  | <img src="https://github.com/user-attachments/assets/06997b75-87b5-417d-bdd5-d7c0b3fb16c4" width="250"/> | <img src="https://github.com/user-attachments/assets/36e4417a-d180-4c0c-936f-5d317fb5180a" width="250"/>  |
| 작성한 리뷰리스트를 볼 수 있습니다. | 상품에 대한 나만의 후기를 직접 작성할 수 있습니다. | 작성한 리뷰를 수정 할 수 있습니다. |

---

### ❤️ 5. 마이페이지

| 닉네임 변경 화면 | 비밀번호 변경 화면 | 작성한 리뷰 확인 |
|:--:|:--:|:--:|
| <img src="https://github.com/user-attachments/assets/d247f209-e85d-468d-b831-aa2f7d4b41ca" width="250"/>  | <img src="https://github.com/user-attachments/assets/f3edebfe-2df3-4bae-ae8c-772f1cd36e04" width="250"/>  | <img src="https://github.com/user-attachments/assets/87ad17c2-c6c6-40ef-9149-a1d49a60b6b1" width="250"/>  | 
|닉네임을 변경할 수 있습니다. |비밀번호를 변경할 수 있습니다. |작성한 리뷰를 확인할 수 있습니다. |

---

### 👤 6. 로그인

| 로그인 화면 | 
|:--:|
| <img src="https://github.com/user-attachments/assets/da41ac71-37c5-449f-83b1-744bc4917b57" width="250"/>  | 
|로그인을 하거나 회원가입을 할 수 있습니다.|

---

### 👤 7. 회원가입

| 회원가입 화면 | 
|:--:|
| <img src="https://github.com/user-attachments/assets/69659958-45c7-4eff-9df1-5f7292116a8e" width="250"/>  | 
|닉네임, 이메일, 비밀번호로 회원가입을 할 수 있습니다.|

---

### 🚀 7. 스플래시 화면

| 앱 실행 |
|:--:|
| <img src="https://github.com/user-attachments/assets/2c76129d-1138-4895-810f-29c4bb86387c" width="250"/>  |
|앱 실행 시 깔끔한 스플래시 화면을 통해 자연스럽게 시작됩니다.|
