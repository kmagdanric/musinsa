## MUSINSA Pricing API

프로젝트는 무신사 상품의 가격비교 API를 구현하고, 이를 조회할 수 있는 웹 플랫폼을 구현합니다.

## Softwares Used

프로젝트에는 다음의 소프트웨어가 사용되었습니다. 이 소프트웨어들은 프로젝트를 실행하기 위해 이미 설치되어 있다고 가정했습니다.
- Java (version 23.0.2, OpenJDK)
- Gradle (version 8.13)

## Initial Design

구현해야 하는 API들 중 가격 관련 endpoint는 모두 SQL 쿼리를 통해 쉽게 구현할 수 있다 생각되고, API 성능에 병목이 될만한 요소를 생각할 필요는 없어 다른 소프트웨어는 추가적으로 사용하지 않아보기로 했습니다.

## Project Structure

```
priceapi/
  src/
    main/
      java/
      resources/   spring, h2, lint settings
    test/          unittest 파일
  lib/             lint, formatting에 필요한 JAR 파일
  lint.sh
  build.gradle
  settings.gradle
  gradlew
README.md
```

## Unittest

브랜드 CRUD를 위해 항상 데이터를 initialize하지 않고, 테스트 케이스간 데이터 정합성 간섭이 없도록 각 테스트 케이스는 @transactional, @BeforeEach를 통해 테이블 컨텐츠가 테스트 시작전 비어있도록 만들었습니다. 

