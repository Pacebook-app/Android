# Pacebook Android README
2025-1 HY-End 재학생 프로젝트 Pacebook의 Android 레포지토리입니다.  
페이스북(Pacebook)은 러닝 기록 측정과 사용자 간의 커뮤니티 활동을 돕는 애플리케이션입니다.

## 📁 패키지 구조
```
com.example.pacebook
├─ adaptor     # Adaptor
├─ api         # Network/API
├─ data        # Data Class, Model
└─ ui          # Activity, Fragment
```

## 🤝 협업 규칙
### Commit 타입
- `[UI]` : UI 작업  
- `[Logic]` : 기능 구현  
- `[Fix]` : 버그 수정  
- `[Merge]` : Merge 작업  
- `[Back]` : 백엔드 API/데이터 연결

### Branch 전략
모든 작업은 `main` 브랜치에서 파생된 하위 브랜치에서 진행합니다. 
- **브랜치명**: `팀원명`

### Issue 컨벤션
- **제목 형식**: `[팀원명/타입] - 구현 내용`  
- **예시**: `[스탠다드/UI] - 메인 화면 UI 구현`

### Commit 컨벤션
- **메시지 형식**: `#이슈번호 [팀원명/타입] - 작업 내용`  
- **예시**: `#1 [스탠다드/UI] - 메인 화면 UI 구현 완료`

### PR 컨벤션
- **제목 형식**: `[팀원명/타입] - 작업 내용`
- **내용 형식**: `작업 내용`, `이슈 번호`

### Code 컨벤션
- #### Activity & Fragment 네이밍
    Camel Case 사용  예) `RunningActivity`, `HistoryFragment`
- #### 위젯 ID 네이밍
    ```
    [Activity/Fragment]_[기능]_[위젯이름]
    예) MainActivity 뒤로가기 버튼 → main_back_btn
    ```
