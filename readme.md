# 요구요구 (AI가 관리하는 요구사항명세서)
AI가 관리하는 요구사항명세서

#### 예시1
```text

사용자: 유저 요구사항을 추가해줘. 사용자는 10자의 닉네임을 가지고 이메일을 가지고 있어야해
AI: 알겠습니다. 유저(User) 도메인에 대한 요구사항 명세서를 작성하겠습니다. 기존에 검색된 문서가 없으므로 신규 문서를 생성하겠습니다. 


```
### 요구사항

- 사용자는 웹 기반의 대화창에서 AI와 대화가 가능하다.
- 클라이언트는 타임리프로 개발한다.
- 채팅경로는 /chat 으로 한다.
- 응답은 실시간으로 전송되며 응답 토큰의 최대크기를 2000 으로 한다.
- 지난 대화 기록을 표시한다.
- 채팅창에 프리셋된 프롬프트를 제시한다. \[
  '안녕? 자기소개해줘',
  '소프트웨어 개발의 "요구사항"이란 무엇인지 알려줘'
  ]

### 트러블 슈팅
#### f9659aedf8cde55de5890580fc385a3d135f5ded
##### 문제
```text
org.springframework.ai.vectorstore.pgvector.autoconfigure.PgVectorStoreAutoConfiguration required a single bean, but 2 were found:

- cohereEmbeddingModel: defined by method 'cohereEmbeddingModel' in class path resource [org/springframework/ai/model/bedrock/cohere/autoconfigure/BedrockCohereEmbeddingAutoConfiguration.class]

- titanEmbeddingModel: defined by method 'titanEmbeddingModel' in class path resource [org/springframework/ai/model/bedrock/titan/autoconfigure/BedrockTitanEmbeddingAutoConfiguration.class]
```
##### 원인 
`org.springframework.ai:spring-ai-starter-vector-store-pgvector`가 `VectorStore`를 자동 구성 할 때 의존하는 임베딩 모델이 자동 구성시 두개가 동시에 등록됨

##### 해결
`BedrockCohereEmbeddingAutoConfiguration.class , BedrockTitanEmbeddingAutoConfiguration.class` 컨디셔널 빈 등록 조건을 보고 해당 프로퍼티를 추가하여 해결 
```yaml
spring.ai.model.embedding=bedrock-titan
```


### reference
https://docs.spring.io/spring-ai/reference/api/chatclient.html